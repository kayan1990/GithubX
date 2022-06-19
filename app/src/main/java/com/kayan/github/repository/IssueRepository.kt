package com.kayan.github.repository

import android.app.Application
import android.content.Context
import com.kayan.github.common.net.*
import com.kayan.github.model.bean.CommentRequestModel
import com.kayan.github.model.bean.Issue
import com.kayan.github.model.bean.IssueEvent
import com.kayan.github.model.conversion.IssueConversion
import com.kayan.github.model.ui.IssueUIModel
import com.kayan.github.repository.dao.IssueDao
import com.kayan.github.service.IssueService
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * Issue相关数据获取
 * Created by kayan
 * Date: 2022-06-18
 */
class IssueRepository @Inject constructor(
    private val retrofit: Retrofit,
    private val application: Application,
    private val issueDao: IssueDao
) {

    /**
     * issue 信息
     *  先从数据库中获取数据，然后再去网络请求，然后更新数据库
     */
    fun getIssueInfo(userName: String, reposName: String, number: Int, resultCallBack: ResultCallBack<IssueUIModel>?) {

        val dbService = issueDao.getIssueInfoDao(userName, reposName, number)
            .doOnNext {
                resultCallBack?.onCacheSuccess(it)
            }

        val issueService = retrofit.create(IssueService::class.java)
            .getIssueInfo(true, userName, reposName, number)
            .doOnNext {
                issueDao.saveIssueInfoDao(it, userName, reposName, number)
            }
            .flatMap {
                FlatMapResponse2Result(it)
            }.map {
                IssueConversion.issueToIssueUIModel(it)
            }.flatMap {
                FlatMapResult2Response(it)
            }

        //这里等待网络执行完成
        val zipService = Observable.zip(dbService, issueService,
            BiFunction<IssueUIModel?, Response<IssueUIModel>, Response<IssueUIModel>> { _, remote ->
                remote
            })

        //真正的执行和回调
        RetrofitFactory.executeResult(zipService, object : ResultTipObserver<IssueUIModel>(application) {
            override fun onSuccess(result: IssueUIModel?) {
                resultCallBack?.onSuccess(result)
            }

            override fun onFailure(e: Throwable, isNetWorkError: Boolean) {
                resultCallBack?.onFailure()
            }
        })
    }

    /**
     * issue 评论
     */
    fun getIssueComments(
        userName: String,
        reposName: String,
        number: Int,
        page: Int,
        resultCallBack: ResultCallBack<ArrayList<Any>>?
    ) {

        val dbService = issueDao.getIssueCommentDao(userName, reposName, number)
            .doOnNext {
                if (page == 1) {
                    resultCallBack?.onCacheSuccess(it)
                }
            }

        val issueService = retrofit.create(IssueService::class.java)
            .getIssueComments(true, userName, reposName, number, page)
            .doOnNext {
                issueDao.saveIssueCommentDao(it, userName, reposName, number, page == 1)
            }
            .flatMap {
                FlatMapResponse2ResponseResult(it, object : FlatConversionInterface<ArrayList<IssueEvent>> {
                    override fun onConversion(t: ArrayList<IssueEvent>?): ArrayList<Any> {
                        val list = ArrayList<Any>()
                        t?.apply {
                            for (issue in t) {
                                list.add(IssueConversion.issueEventToIssueUIModel(issue))
                            }
                        }
                        return list
                    }
                })
            }

        val zipService = Observable.zip(dbService, issueService,
            BiFunction<ArrayList<Any>, Response<ArrayList<Any>>, Response<ArrayList<Any>>> { _, remote ->
                remote
            })

        RetrofitFactory.executeResult(zipService, object : ResultTipObserver<ArrayList<Any>>(application) {

            override fun onPageInfo(first: Int, current: Int, last: Int) {
                resultCallBack?.onPage(first, current, last)
            }

            override fun onSuccess(result: ArrayList<Any>?) {
                resultCallBack?.onSuccess(result)
            }

            override fun onFailure(e: Throwable, isNetWorkError: Boolean) {
                resultCallBack?.onFailure()
            }
        })
    }

    /**
     * 编辑 issue
     */
    fun editIssue(
        context: Context,
        userName: String,
        reposName: String,
        number: Int,
        issue: Issue,
        resultCallBack: ResultCallBack<IssueUIModel>?
    ) {
        val issueService = retrofit.create(IssueService::class.java).editIssue(userName, reposName, number, issue)
            .flatMap {
                FlatMapResponse2ResponseObject(it, object : FlatConversionObjectInterface<Issue, IssueUIModel> {
                    override fun onConversion(t: Issue?): IssueUIModel {
                        return IssueConversion.issueToIssueUIModel(t!!)
                    }
                })
            }
        RetrofitFactory.executeResult(issueService, object : ResultProgressObserver<IssueUIModel>(context) {

            override fun onSuccess(result: IssueUIModel?) {
                resultCallBack?.onSuccess(result)
            }

            override fun onFailure(e: Throwable, isNetWorkError: Boolean) {
                resultCallBack?.onFailure()
            }
        })
    }

    /**
     * 创建 issue
     */
    fun createIssue(
        context: Context,
        userName: String,
        reposName: String,
        issue: Issue,
        resultCallBack: ResultCallBack<IssueUIModel>?
    ) {
        val issueService = retrofit.create(IssueService::class.java).createIssue(userName, reposName, issue)
            .flatMap {
                FlatMapResponse2ResponseObject(it, object : FlatConversionObjectInterface<Issue, IssueUIModel> {
                    override fun onConversion(t: Issue?): IssueUIModel {
                        return IssueConversion.issueToIssueUIModel(t!!)
                    }
                })
            }
        RetrofitFactory.executeResult(issueService, object : ResultProgressObserver<IssueUIModel>(context) {

            override fun onSuccess(result: IssueUIModel?) {
                resultCallBack?.onSuccess(result)
            }

            override fun onFailure(e: Throwable, isNetWorkError: Boolean) {
                resultCallBack?.onFailure()
            }
        })
    }

    /**
     * 评论 issue
     */
    fun commentIssue(
        context: Context,
        userName: String,
        reposName: String,
        number: Int,
        commentRequestModel: CommentRequestModel,
        resultCallBack: ResultCallBack<IssueUIModel>?
    ) {
        val issueService =
            retrofit.create(IssueService::class.java).addComment(userName, reposName, number, commentRequestModel)
                .flatMap {
                    FlatMapResponse2ResponseObject(
                        it,
                        object : FlatConversionObjectInterface<IssueEvent, IssueUIModel> {
                            override fun onConversion(t: IssueEvent?): IssueUIModel {
                                return IssueConversion.issueEventToIssueUIModel(t!!)
                            }
                        })
                }
        RetrofitFactory.executeResult(issueService, object : ResultProgressObserver<IssueUIModel>(context) {

            override fun onSuccess(result: IssueUIModel?) {
                resultCallBack?.onSuccess(result)
            }

            override fun onFailure(e: Throwable, isNetWorkError: Boolean) {
                resultCallBack?.onFailure()
            }
        })

    }

    /**
     * 锁定/解锁 issue
     */
    fun lockIssue(
        context: Context,
        userName: String,
        reposName: String,
        number: Int,
        lock: Boolean,
        resultCallBack: ResultCallBack<Boolean>?
    ) {
        val service = retrofit.create(IssueService::class.java)
        val issueService = if (lock) {
            service.lockIssue(userName, reposName, number)
        } else {
            service.unLockIssue(userName, reposName, number)
        }
        RetrofitFactory.executeResult(issueService, object : ResultProgressObserver<ResponseBody>(context) {

            override fun onSuccess(result: ResponseBody?) {
                resultCallBack?.onSuccess(true)
            }

            override fun onFailure(e: Throwable, isNetWorkError: Boolean) {
                resultCallBack?.onFailure()
            }
        })
    }


    /**
     * 编辑issue评论
     */
    fun editComment(
        context: Context,
        userName: String,
        reposName: String,
        commentId: String,
        commentRequestModel: CommentRequestModel,
        resultCallBack: ResultCallBack<IssueUIModel>?
    ) {
        val issueService =
            retrofit.create(IssueService::class.java).editComment(userName, reposName, commentId, commentRequestModel)
                .flatMap {
                    FlatMapResponse2ResponseObject(
                        it,
                        object : FlatConversionObjectInterface<IssueEvent, IssueUIModel> {
                            override fun onConversion(t: IssueEvent?): IssueUIModel {
                                return IssueConversion.issueEventToIssueUIModel(t!!)
                            }
                        })
                }
        RetrofitFactory.executeResult(issueService, object : ResultProgressObserver<IssueUIModel>(context) {

            override fun onSuccess(result: IssueUIModel?) {
                resultCallBack?.onSuccess(result)
            }

            override fun onFailure(e: Throwable, isNetWorkError: Boolean) {
                resultCallBack?.onFailure()
            }
        })
    }

    /**
     * 删除issue评论
     */
    fun deleteComment(
        context: Context,
        userName: String,
        reposName: String,
        commentId: String,
        resultCallBack: ResultCallBack<String>?
    ) {
        val issueService = retrofit.create(IssueService::class.java).deleteComment(userName, reposName, commentId)
        RetrofitFactory.executeResult(issueService, object : ResultProgressObserver<ResponseBody>(context) {
            override fun onSuccess(result: ResponseBody?) {
                resultCallBack?.onSuccess(commentId)
            }

            override fun onCodeError(code: Int, message: String) {
                if (code == 404) {
                    resultCallBack?.onSuccess(commentId)
                } else {
                    resultCallBack?.onFailure()
                }
            }

            override fun onFailure(e: Throwable, isNetWorkError: Boolean) {
                resultCallBack?.onFailure()
            }
        })
    }

}
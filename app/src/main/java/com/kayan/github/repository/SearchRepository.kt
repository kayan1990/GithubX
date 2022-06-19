package com.kayan.github.repository

import android.app.Application
import android.content.Context
import com.kayan.github.common.net.*
import com.kayan.github.model.bean.Repository
import com.kayan.github.model.bean.SearchResult
import com.kayan.github.model.bean.User
import com.kayan.github.model.conversion.ReposConversion
import com.kayan.github.model.conversion.UserConversion
import com.kayan.github.service.SearchService
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * 搜索相关数据获取
 * Created by kayan
 * Date: 2018-11-02
 */
class SearchRepository @Inject constructor(private val retrofit: Retrofit, private val application: Application) {

    /**
     * 搜索用户
     * @param q 关键字
     * @param sort 搜索排序依据，比如best_match
     * @param order 排序
     */
    fun searchUsers(context: Context, q: String, sort: String, order: String, page: Int, resultCallBack: ResultCallBack<ArrayList<Any>>?) {
        val service = retrofit.create(SearchService::class.java)
                .searchUsers(query = q, page = page, sort = sort, order = order)
                .flatMap {
                    FlatMapResponse2ResponseResult(it, object : FlatConversionInterface<SearchResult<User>> {
                        override fun onConversion(t: SearchResult<User>?): ArrayList<Any> {
                            val list = arrayListOf<Any>()
                            t?.items?.apply {
                                this.forEach { data ->
                                    val item = UserConversion.userToUserUIModel(data)
                                    list.add(item)
                                }
                            }
                            return list
                        }
                    })
                }

        RetrofitFactory.executeResult(service, object : ResultProgressObserver<ArrayList<Any>>(context, page == 1) {
            override fun onPageInfo(first: Int, current: Int, last: Int) {
                super.onPageInfo(first, current, last)
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
     * 搜索项目
     * @param q 关键字
     * @param sort 搜索排序依据，比如best_match
     * @param order 排序
     */
    fun searchRepos(context: Context, q: String, sort: String, order: String, page: Int, resultCallBack: ResultCallBack<ArrayList<Any>>?) {
        val service = retrofit.create(SearchService::class.java)
                .searchRepos(query = q, page = page, sort = sort, order = order)
                .flatMap {
                    FlatMapResponse2ResponseResult(it, object : FlatConversionInterface<SearchResult<Repository>> {
                        override fun onConversion(t: SearchResult<Repository>?): ArrayList<Any> {
                            val list = arrayListOf<Any>()
                            t?.items?.apply {
                                this.forEach { data ->
                                    val item = ReposConversion.reposToReposUIModel(application, data)
                                    list.add(item)
                                }
                            }
                            return list
                        }
                    })
                }

        RetrofitFactory.executeResult(service, object : ResultProgressObserver<ArrayList<Any>>(context, page == 1) {
            override fun onPageInfo(first: Int, current: Int, last: Int) {
                super.onPageInfo(first, current, last)
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

}
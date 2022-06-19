package com.kayan.github.repository.dao

import android.app.Application
import com.kayan.github.common.db.*
import com.kayan.github.common.net.GsonUtils
import com.kayan.github.model.bean.Issue
import com.kayan.github.model.bean.IssueEvent
import com.kayan.github.model.conversion.IssueConversion
import com.kayan.github.model.ui.IssueUIModel
import io.reactivex.Observable
import io.realm.Realm
import io.realm.RealmQuery
import io.realm.RealmResults
import retrofit2.Response
import javax.inject.Inject

/**
 * Issue相关数据库操作
 * Created by kayan
 * Date: 2018-11-07
 */
class IssueDao @Inject constructor(private val application: Application) {

    /**
     * 保存issue详情
     */
    fun saveIssueInfoDao(response: Response<Issue>, userName: String, reposName: String, number: Int) {
        FlatMapRealmSaveResult(response, IssueDetail::class.java, object : FlatTransactionInterface<IssueDetail> {
            override fun query(q: RealmQuery<IssueDetail>): RealmResults<IssueDetail> {
                return q.equalTo("fullName", "$userName/$reposName").equalTo("number", number.toString()).findAll()
            }

            override fun onTransaction(targetObject: IssueDetail?) {
                val data = GsonUtils.toJsonString(response.body())
                targetObject?.fullName = "$userName/$reposName"
                targetObject?.number = number.toString()
                targetObject?.data = data
            }
        }, true)
    }

    /**
     * 获取issue详情
     */
    fun getIssueInfoDao(userName: String, reposName: String, number: Int): Observable<IssueUIModel?> {
        return RealmClient.getRealmObservable()
                .map {
                    val item = FlatMapRealmReadObject(it, object : FlatRealmReadConversionObjectInterface<Issue, IssueDetail, IssueUIModel> {
                        override fun query(realm: Realm): RealmResults<IssueDetail> {
                            return realm.where(IssueDetail::class.java).equalTo("fullName", "$userName/$reposName").equalTo("number", number.toString()).findAll()
                        }

                        override fun onJSON(t: IssueDetail): Issue {
                            return GsonUtils.parserJsonToBean(t.data!!, Issue::class.java)
                        }

                        override fun onConversion(t: Issue?): IssueUIModel? {
                            return if (t == null) {
                                IssueUIModel()
                            } else {
                                IssueConversion.issueToIssueUIModel(t)
                            }
                        }
                    })
                    item
                }
    }

    /**
     * 保存issue评论
     */
    fun saveIssueCommentDao(response: Response<ArrayList<IssueEvent>>, userName: String, reposName: String, number: Int, needSave: Boolean) {
        FlatMapRealmSaveResult(response, IssueComment::class.java, object : FlatTransactionInterface<IssueComment> {
            override fun query(q: RealmQuery<IssueComment>): RealmResults<IssueComment> {
                return q.equalTo("fullName", "$userName/$reposName").equalTo("number", number.toString()).findAll()
            }

            override fun onTransaction(targetObject: IssueComment?) {
                val data = GsonUtils.toJsonString(response.body())
                targetObject?.fullName = "$userName/$reposName"
                targetObject?.number = number.toString()
                targetObject?.commentId = "-1"
                targetObject?.data = data
            }
        }, needSave)
    }
    /**
     * 获取issue评论
     */
    fun getIssueCommentDao(userName: String, reposName: String, number: Int): Observable<ArrayList<Any>> {
        return RealmClient.getRealmObservable()
                .map {
                    val list = FlatMapRealmReadList(it, object : FlatRealmReadConversionInterface<IssueEvent, IssueComment> {
                        override fun query(realm: Realm): RealmResults<IssueComment> {
                            return realm.where(IssueComment::class.java).equalTo("fullName", "$userName/$reposName").equalTo("number", number.toString()).findAll()
                        }

                        override fun onJSON(t: IssueComment): List<IssueEvent> {
                            return GsonUtils.parserJsonToArrayBeans(t.data!!, IssueEvent::class.java)
                        }

                        override fun onConversion(t: IssueEvent): Any {
                            return IssueConversion.issueEventToIssueUIModel(t)
                        }
                    })
                    list
                }
    }


}
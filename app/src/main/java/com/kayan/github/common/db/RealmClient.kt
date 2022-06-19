package com.kayan.github.common.db

import io.reactivex.Observable
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * Realm数据初始化
 * Created by kayan
 * Date: 2018-11-06
 */

object RealmClient {

    private const val VERSION = 1L

    fun getRealmObservable(): Observable<Realm> {
        return Observable.create { emitter ->
            val observableRealm = Realm.getDefaultInstance()
            emitter.onNext(observableRealm)
            emitter.onComplete()
        }
    }


    init {
        val config = RealmConfiguration.Builder()
            .name("gsy.realm")
            .schemaVersion(VERSION)
            .build()
        Realm.setDefaultConfiguration(config)
    }
}
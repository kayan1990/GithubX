package com.kayan.github.service

import com.kayan.github.model.bean.AccessToken
import com.kayan.github.model.bean.LoginRequestModel

import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*

/**
 * 登录服务
 */
interface LoginService {

    @POST("authorizations")
    @Headers("Accept: application/json")
    fun authorizations(@Body authRequestModel: LoginRequestModel): Observable<Response<AccessToken>>


    @GET("https://github.com/login/oauth/access_token")
    @Headers("Accept: application/json")
    fun authorizationsCode(@Query("client_id") client_id: String,
                           @Query("client_secret") client_secret: String,
                           @Query("code") code: String): Observable<Response<AccessToken>>


}

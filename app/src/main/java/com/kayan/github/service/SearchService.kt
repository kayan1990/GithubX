package com.kayan.github.service

import com.kayan.github.AppConfig
import com.kayan.github.model.bean.Issue
import com.kayan.github.model.bean.Repository
import com.kayan.github.model.bean.SearchResult
import com.kayan.github.model.bean.User

import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query


interface SearchService {

    @GET("search/users")
    fun searchUsers(
            @Query(value = "q", encoded = true) query: String,
            @Query("sort") sort: String = "best%20match",
            @Query("order") order: String = "desc",
            @Query("page") page: Int,
            @Query("per_page") per_page: Int = AppConfig.PAGE_SIZE
    ): Observable<Response<SearchResult<User>>>

    @GET("search/repositories")
    fun searchRepos(
            @Query(value = "q", encoded = true) query: String,
            @Query("sort") sort: String = "best%20match",
            @Query("order") order: String = "desc",
            @Query("page") page: Int,
            @Query("per_page") per_page: Int = AppConfig.PAGE_SIZE
    ): Observable<Response<SearchResult<Repository>>>

    @GET("search/issues")
    @Headers("Accept: application/vnd.github.html,application/vnd.github.VERSION.raw")
    fun searchIssues(
            @Header("forceNetWork") forceNetWork: Boolean,
            @Query(value = "q", encoded = true) query: String,
            @Query("page") page: Int,
            @Query("per_page") per_page: Int = AppConfig.PAGE_SIZE
    ): Observable<Response<SearchResult<Issue>>>

}

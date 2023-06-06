package com.speer.test.service


import com.speer.test.github.UserInfo
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

/**
 * Created by Charles Raj I on 05/06/23.
 * @author Charles Raj I
 */
interface RestServiceInterface {


    @GET("users/{username}")
    fun getUserDetailByUserName(@Path("username") username: String): Call<UserInfo>

    @GET
    fun getFollowers(@Url url: String): Call<List<UserInfo>>

}
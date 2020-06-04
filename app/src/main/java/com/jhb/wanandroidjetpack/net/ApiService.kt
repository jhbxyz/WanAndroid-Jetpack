package com.jhb.wanandroidjetpack.net

import com.jhb.wanandroidjetpack.bean.BaseBean
import com.jhb.wanandroidjetpack.bean.WendaListBean
import com.jhb.wanandroidjetpack.login.model.UserLoginBean
import io.reactivex.Flowable
import retrofit2.http.*

/**
 * Created by jhb on 2020-01-13.
 */
interface ApiService {

    @FormUrlEncoded
    @POST("user/login")
    fun userLogin(
            @Field("username") username: String?,
            @Field("password") password: String?
    ): Flowable<UserLoginBean>


    @GET("lg/collect/list/0/json")
    fun lgCollectList(): Flowable<BaseBean>

    @GET("wenda/list/{page}/json ")
    fun wendaList(@Path("page") page: Int): Flowable<WendaListBean>


}
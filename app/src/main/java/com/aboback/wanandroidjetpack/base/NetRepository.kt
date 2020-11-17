package com.aboback.wanandroidjetpack.base

import com.aboback.base.BaseRepository
import com.aboback.network.WanService
import com.aboback.wanandroidjetpack.network.ApiService
import com.aboback.wanandroidjetpack.network.WanServer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @author jhb
 * @date 2020/10/23
 */
open class NetRepository : BaseRepository {

    val api = WanServer.api

    suspend fun collect(id: Int) = api.collect(id)

    suspend fun unCollect(id: Int, isOnMe: Boolean = false, originId: Int = -1) = if (isOnMe) api.unCollectInMe(id, originId) else api.unCollect(id)


}
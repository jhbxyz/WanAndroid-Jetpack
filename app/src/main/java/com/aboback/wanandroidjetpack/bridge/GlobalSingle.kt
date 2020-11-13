package com.aboback.wanandroidjetpack.bridge

import androidx.lifecycle.MutableLiveData
import com.aboback.wanandroidjetpack.collect.ui.CollectContentPage
import com.aboback.wanandroidjetpack.common.EditDialogEvent
import com.aboback.wanandroidjetpack.util.CollectChangeBean
import com.aboback.wanandroidjetpack.view.EditPage

/**
 * @author jhb
 * @date 2020/11/2
 */
object GlobalSingle {

    val isLoginSuccess = MutableLiveData<Boolean>()
    val isLoginSuccessToCollect = MutableLiveData<CollectContentPage>()

    var onCollectChange = MutableLiveData<CollectChangeBean>()

    var onAddCollectWebsite = MutableLiveData<Boolean>()

    var showEditDialog = MutableLiveData<EditDialogEvent>()


}
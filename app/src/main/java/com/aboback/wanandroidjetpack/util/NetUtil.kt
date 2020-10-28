package com.aboback.wanandroidjetpack.util

import androidx.lifecycle.viewModelScope
import com.aboback.base.util.isNull
import com.aboback.base.util.logWithTag
import com.aboback.base.util.showToast
import com.aboback.base.viewmodel.BaseViewModel
import com.aboback.wanandroidjetpack.bean.BaseBean
import com.aboback.wanandroidjetpack.network.NetConstant
import kotlinx.coroutines.launch

/**
 * @author jhb
 * @date 2020/10/27
 */
const val TAG = "NetUtil"

fun BaseViewModel.launch(showDialog: Boolean = true, finish: (suspend () -> Unit)? = null, error: (suspend () -> Unit)? = null, success: suspend () -> Unit) {
    viewModelScope.launch {
        try {
            dialogState(showDialog, true)
            success.invoke()
            dialogState(showDialog, false)
        } catch (e: Throwable) {
            if (error.isNull()) {
                dialogState(showDialog, false)
                "网络错误: ${e.message}".showToast()
            } else {
                error?.invoke()
            }
            e.printStackTrace()
            "网络错误: ${e.message}".logWithTag(TAG)
        } finally {
            finish?.invoke()
        }
    }
}

private fun BaseViewModel.dialogState(showDialog: Boolean, state: Boolean) {
    if (showDialog) {
        isDialogShow.value = state
    }
}

fun <T : BaseBean> response(bean: T, result: T.() -> Unit) {
    when (bean.errorCode) {
        NetConstant.SUCCESS -> result.invoke(bean)
        else -> bean.errorMsg.showToast()
    }
}
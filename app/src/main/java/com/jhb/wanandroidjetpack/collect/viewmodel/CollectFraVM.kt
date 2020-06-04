package com.jhb.wanandroidjetpack.collect.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import com.jhb.wanandroidjetpack.base.viewmodel.BaseViewModel

/**
 * Created by jhb on 2020-01-19.
 */
class CollectFraVM(app: Application) : BaseViewModel(app) {

    var mText = ObservableField(this.javaClass.simpleName)

}
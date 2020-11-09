package com.aboback.wanandroidjetpack.find.ui

import android.app.Application
import com.aboback.base.ui.BaseVMRepositoryFragment
import com.aboback.base.util.logWithTag
import com.aboback.wanandroidjetpack.R
import com.aboback.wanandroidjetpack.collect.SelectPage
import com.aboback.wanandroidjetpack.find.viewmodel.FindContentProjectTreeVM
import com.aboback.wanandroidjetpack.find.viewmodel.FindContentProjectVM
import com.aboback.wanandroidjetpack.main.RvScrollToTop
import com.aboback.wanandroidjetpack.main.ui.MainActivity
import com.aboback.wanandroidjetpack.util.RvScrollDelegate

/**
 * @author jhb
 * @date 2020/10/27
 */
class FindContentProjectFragment : BaseVMRepositoryFragment<FindContentProjectVM>(R.layout.fragment_find_content_project), RvScrollToTop, SelectPage {

    private var mFragmentInit = false
    private var isTabLayoutClick = false

    override fun getViewModel(app: Application) = FindContentProjectVM(app)

    override fun onViewInit() {
        super.onViewInit()
        mFragmentInit = true
        bindScrollListener()
    }

    override fun onEvent() {
        super.onEvent()
        if (isTabLayoutClick) {
            onSelectPage()
        }
    }

    override fun bindScrollListener() {
        RvScrollDelegate.bindScrollListener(mainActivity = mActivity as MainActivity, rvVM = mRealVM.rvVM)
    }

    override fun scrollToTop() {
        "scrollToTop $mTag".logWithTag("AAAAAAAA")

        RvScrollDelegate.scrollToTop(mRealVM.rvVM)
    }

    override fun onSelectPage() {
        if (!mFragmentInit) {
            isTabLayoutClick = true
        } else {
            if (!mRealVM.isRequestSuccess) {
                mRealVM.requestServer()
            }
        }

    }

}
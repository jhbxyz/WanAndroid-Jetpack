package com.aboback.wanandroidjetpack.collect.ui

import android.app.Application
import androidx.fragment.app.Fragment
import com.aboback.base.ui.BaseVMRepositoryFragment
import com.aboback.wanandroidjetpack.R
import com.aboback.wanandroidjetpack.collect.adapter.CollectVpAdapter
import com.aboback.wanandroidjetpack.collect.viewmodel.CollectVM
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_collect.*

/**
 * @author jhb
 * @date 2020/10/29
 */
class CollectFragment : BaseVMRepositoryFragment<CollectVM>(R.layout.fragment_collect) {
    private val mFragments = arrayListOf<Fragment>()
    private val mTitles = arrayOf("收藏文章", "分享文章", "收藏网站", "分享项目")

    override fun getViewModel(app: Application) = CollectVM(app)

    override fun onViewInit() {
        super.onViewInit()

        mFragments.add(CollectContentFragment(CollectContentPage.COLLECT_ARTICLE))
        mFragments.add(CollectContentFragment(CollectContentPage.SHARE_ARTICLE))
        mFragments.add(CollectContentFragment(CollectContentPage.COLLECT_WEBSITE))
        mFragments.add(CollectContentFragment(CollectContentPage.SHARE_PROJECT))

        viewPager2.adapter = CollectVpAdapter(mFragments, mActivity.supportFragmentManager, lifecycle)

        TabLayoutMediator(tabLayout, viewPager2, TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            tab.text = mTitles[position]
        }).attach()
    }

}
package com.aboback.wanandroidjetpack.home.viewmodel

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.aboback.base.ItemType
import com.aboback.base.rv.BaseMultiItemViewModel
import com.aboback.base.rv.QuickMultiAdapter
import com.aboback.base.util.showToast
import com.aboback.base.viewmodel.BaseRepositoryViewModel
import com.aboback.wanandroidjetpack.R
import com.aboback.wanandroidjetpack.bean.ItemDatasBean
import com.aboback.wanandroidjetpack.home.HomeRepository
import com.aboback.wanandroidjetpack.rv.RecyclerViewVM
import com.aboback.wanandroidjetpack.util.loadSuccess
import com.aboback.wanandroidjetpack.viewmodel.*
import kotlinx.coroutines.launch

/**
 * Created by jhb on 2020-03-11.
 */

enum class HomePageState {
    INIT, REFRESH, LOAD_MORE
}

class HomeViewModel(app: Application) : BaseRepositoryViewModel<HomeRepository>(app, HomeRepository()) {

    var mTitleVM = TitleViewModel(
            leftDrawable = null,
            title = "首页"
    )

    private val mImageList = arrayListOf<String>()

    private val mBannerAdapter = BannerAdapter(mImageList)
    private var mBannerViewModel = BannerViewModel(getApplication()).apply {
        mAdapterObservable.set(mBannerAdapter)
    }

    private val mHomeBannerVM = HomeBannerVM(getApplication()).apply {
        mBannerVM.set(mBannerViewModel)
    }


    var mData = arrayListOf<BaseMultiItemViewModel>()
    val mAdapter = QuickMultiAdapter(mData).apply {
        addType(R.layout.item_rv_home_banner, ItemType.ITEM_HOME_BANNER)
        addType(R.layout.item_rv_home, ItemType.ITEM_HOME_MAIN)
    }

    private var mCurrPage = 0

    var rvVM = RecyclerViewVM(app).apply {
        mRefreshEnable = true
        mAdapterObservable.set(mAdapter)

        mOnRefresh = {
            mIsRefreshing.set(true)

            mData.clear()
            mCurrPage = 0
            requestServer(HomePageState.REFRESH)

        }

        mOnLoadMoreListener = {
            mCurrPage++
            requestServer(HomePageState.LOAD_MORE)
        }
    }


    override fun onModelBind() {
        super.onModelBind()

        requestServer(HomePageState.INIT)

    }


    private fun dialogState(state: HomePageState, isShow: Boolean) {
        if (state != HomePageState.REFRESH) {
            isDialogShow.value = isShow
            if (!isShow) {
                loadSuccess()
            }
        }
    }

    private fun hideRefreshLoading(state: HomePageState) {
        if (state == HomePageState.REFRESH) {
            rvVM.mIsRefreshing.set(false)
        }
    }

    private fun requestServer(state: HomePageState) {
        viewModelScope.launch {
            try {
                resetDataIfNeed(state)

                dialogState(state, true)

                getBannerImages(state)

                getArticleTop(state)

                getArticleList()

            } catch (e: Throwable) {
                e.message?.showToast()

            } finally {
                mAdapter.notifyDataSetChanged()

                hideRefreshLoading(state)

                dialogState(state, false)
            }
        }
    }

    private fun resetDataIfNeed(state: HomePageState) {
        if (state == HomePageState.REFRESH) {
            mData.clear()
        }
    }

    private suspend fun getBannerImages(state: HomePageState) {
        if (state == HomePageState.INIT || state == HomePageState.REFRESH) {
            mImageList.clear()
            mRepo.banner().data?.forEach {
                mImageList.add(it?.imagePath ?: "")
            }
            mData.add(mHomeBannerVM)
        }
    }

    private suspend fun getArticleTop(state: HomePageState) {
        if (state == HomePageState.REFRESH || state == HomePageState.INIT) {
            val articleTop = mRepo.articleTop()
            articleTop.data?.forEach {
                addTopTag(it)
                bindData(it)
            }
        }
    }

    private fun addTopTag(it: ItemDatasBean) {
        val tempTags = arrayListOf<ItemDatasBean.TagBean>()
        tempTags.add(ItemDatasBean.TagBean("置顶"))
        it.tags?.let { tag -> tempTags.addAll(tag) }
        it.tags = tempTags
    }

    private suspend fun getArticleList() {
        val articleList = mRepo.articleList(mCurrPage)
        articleList.data?.datas?.forEach {
            bindData(it)
        }
    }

    private fun bindData(bean: ItemDatasBean) {
        mData.add(ItemHomeVM(getApplication(), bean).apply {
            bindData()
        })
    }


}
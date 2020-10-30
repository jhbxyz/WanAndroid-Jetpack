package com.aboback.wanandroidjetpack.collect.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import com.aboback.base.rv.QuickAdapter
import com.aboback.base.viewmodel.BaseRepositoryViewModel
import com.aboback.wanandroidjetpack.R
import com.aboback.wanandroidjetpack.bean.ArticleDatasBean
import com.aboback.wanandroidjetpack.collect.CollectContentRepository
import com.aboback.wanandroidjetpack.collect.ui.CollectContentPage
import com.aboback.wanandroidjetpack.home.viewmodel.ItemHomeVM
import com.aboback.wanandroidjetpack.rv.RecyclerViewVM
import com.aboback.wanandroidjetpack.util.launch
import com.aboback.wanandroidjetpack.util.response
import com.aboback.wanandroidjetpack.viewmodel.TagViewModel

/**
 * Created by jhb on 2020-03-11.
 */
class CollectContentVM(mContentPage: CollectContentPage, app: Application) : BaseRepositoryViewModel<CollectContentRepository>(app, CollectContentRepository(mContentPage)) {

    var mData = arrayListOf<ItemHomeVM>()
    val mAdapter = QuickAdapter(R.layout.item_rv_home, mData)

    private var mCurrPage = 0

    var rvVM = RecyclerViewVM(app).apply {
        mRefreshEnable = true
        mAdapterObservable.set(mAdapter)

        mOnRefresh = {
            mIsRefreshing.set(true)

            mData.clear()
            mCurrPage = 0

            requestServer(false)
        }

        mOnLoadMoreListener = {
            mCurrPage++
            requestServer(true)
        }
    }


    override fun onModelBind() {
        super.onModelBind()

//        requestServer(true)
    }

    private fun requestServer(showDialog: Boolean = true) {
        launch(showDialog) {
            response(mRepo.wendaList(mCurrPage)) {
                data?.datas?.forEach {
                    bindData(it)
                }
                mAdapter.notifyDataSetChanged()
                if (!showDialog) {
                    rvVM.mIsRefreshing.set(false)
                }
            }
        }
    }


    private fun bindData(it: ArticleDatasBean) {
        mData.add(ItemHomeVM(getApplication()).apply {
            mTime.set(it.niceDate)
            mTitle.set(it.title)
            mAuthor.handleAuthor(it)
            mCategory.set("分类: ${it.superChapterName}")

            it.tags?.forEach { tags ->
                mTagVMList.add(TagViewModel().apply {
                    mContent.set(tags.name)
                })
            }
        })
    }

    private fun ObservableField<String>.handleAuthor(bean: ArticleDatasBean) {
        if (bean.author.isNullOrEmpty()) {
            set("分享人: ${bean.shareUser}")
        } else {
            set("作者: ${bean.author}")
        }

    }


}
package com.ddd.airplane.presenter.home.view.viewholder

import android.widget.LinearLayout
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.ddd.airplane.R
import com.ddd.airplane.common.base.BaseRecyclerViewAdapter
import com.ddd.airplane.common.extension.loadImage
import com.ddd.airplane.common.extension.showToast
import com.ddd.airplane.common.manager.ChatRoomManager
import com.ddd.airplane.common.utils.tryCatch
import com.ddd.airplane.common.views.decoration.DividerItemSpace
import com.ddd.airplane.data.response.chat.ProgramData
import com.ddd.airplane.data.response.home.HomeData
import com.ddd.airplane.databinding.HomeHorizontalBinding
import com.ddd.airplane.databinding.ThumbnailGeneralItemBinding
import kotlinx.android.synthetic.main.home_horizontal.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * 가로 스크롤 배너
 *
 * @author jess
 * @since 2020.01.23
 */
class HorizontalViewHolder(
    viewDataBinding: ViewDataBinding
) : RecyclerView.ViewHolder(viewDataBinding.root) {

    private val view = viewDataBinding.root
    private val binding = viewDataBinding as HomeHorizontalBinding
    private var itemData = HomeData.ItemData()
    private val bannerList = ArrayList<ProgramData>()

    fun onBind(item: HomeData.ItemData?) {
        tryCatch {
            initData(item)
            initLayout()
        }
    }

    private fun initData(item: HomeData.ItemData?) {
        tryCatch {
            item?.let {
                itemData = it
                bannerList.addAll(it.item as ArrayList<ProgramData>)
                Timber.d(bannerList.toString())
            }
        }
    }

    private fun initLayout() {

        // 타이틀
        binding.run {
            title = itemData.title
        }

        view.rv_horizontal.run {
            addItemDecoration(
                DividerItemSpace(
                    LinearLayout.HORIZONTAL,
                    context.resources.getDimensionPixelSize(R.dimen.dp12)
                )
            )

            val listAdapter = object :
                BaseRecyclerViewAdapter<ProgramData, ThumbnailGeneralItemBinding>(R.layout.thumbnail_general_item) {
                override fun onBindData(
                    position: Int,
                    data: ProgramData?,
                    dataBinding: ThumbnailGeneralItemBinding
                ) {
                    data?.let {
                        dataBinding.ivThumbnail.loadImage(
                            it.subjectThumbnailUrl,
                            corners = context.resources.getDimensionPixelSize(R.dimen.dp4)
                        )
                    }
                }
            }.apply {
                setOnItemClickListener { view, data ->
                    CoroutineScope(Dispatchers.IO).launch {
                        ChatRoomManager.joinChatRoom(context, data)
                    }
                }
            }

            adapter = listAdapter
            listAdapter.addAllItem(bannerList)
        }
    }
}
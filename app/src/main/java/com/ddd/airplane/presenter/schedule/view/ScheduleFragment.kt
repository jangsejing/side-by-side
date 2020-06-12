package com.ddd.airplane.presenter.schedule.view

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.ddd.airplane.R
import com.ddd.airplane.common.base.BaseFragment
import com.ddd.airplane.common.base.BaseRecyclerViewAdapter
import com.ddd.airplane.common.views.decoration.DividerItemSpace
import com.ddd.airplane.data.response.ScheduleData
import com.ddd.airplane.databinding.ScheduleFragmentBinding
import com.ddd.airplane.databinding.ScheduleHeaderItemBinding
import com.ddd.airplane.databinding.ScheduleRoomItemBinding
import com.ddd.airplane.databinding.ScheduleTypeItemBinding
import com.ddd.airplane.presenter.schedule.viewmodel.ScheduleTypeViewModel
import com.ddd.airplane.presenter.schedule.viewmodel.ScheduleViewModel
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.schedule_fragment.*
import kotlinx.android.synthetic.main.schedule_header.*
import kotlinx.android.synthetic.main.schedule_header_item.view.*
import kotlinx.android.synthetic.main.schedule_type_item.view.*
import timber.log.Timber

/**
 * 편성표
 * 1 Depth
 * @author jess
 */
class ScheduleFragment : BaseFragment<ScheduleFragmentBinding, ScheduleViewModel>() {

    override val layoutRes = R.layout.schedule_fragment
    override val viewModelClass = ScheduleViewModel::class.java

    override fun initDataBinding() {
        super.initDataBinding()
        viewModel.run {

        }
    }

    override fun initLayout() {
        setHeader()
        set1DepthViewPager()
    }

    override fun onCreated(savedInstanceState: Bundle?) {

    }

    private fun setHeader() {
        // header
        rv_header_item.apply {

            addItemDecoration( // 간격
                DividerItemSpace(
                    DividerItemSpace.HORIZONTAL,
                    context.resources.getDimensionPixelSize(R.dimen.dp8)
                )
            )

            adapter = object :

                BaseRecyclerViewAdapter<ScheduleData, ScheduleHeaderItemBinding>(
                    R.layout.schedule_header_item
                ) {

                override fun onBindData(
                    position: Int,
                    data: ScheduleData?,
                    dataBinding: ScheduleHeaderItemBinding
                ) {
                    dataBinding.let {

                        it.model = data
                        it.position = position

                        // 현재 포지션
                        viewModel.position.observe(viewLifecycleOwner, Observer { position ->
                            it.current = position
                        })

                        // 헤더 클릭
                        it.root.tv_broadcast.setOnClickListener {
                            viewModel.setCurrentPage(position)
                            vp_schedule_depth1.setCurrentItem(position, false)
                        }
                    }
                }
            }
        }
    }


    /**
     * 1 Depth ViewPager
     *
     * 방송사별, 주제별
     */
    private fun set1DepthViewPager() {
        // 1 depth pager
        vp_schedule_depth1.apply {
            adapter = object :

                BaseRecyclerViewAdapter<ScheduleData, ScheduleTypeItemBinding>(R.layout.schedule_type_item) {

                override fun onBindData(
                    position: Int,
                    model: ScheduleData?,
                    dataBinding: ScheduleTypeItemBinding
                ) {
                    // 주제별 viewPager 삽입
                    model?.typeList?.let { set2DepthViewPager(dataBinding, it) }
                }
            }
        }
    }

    /**
     * 2 Depth ViewPager
     *
     * 방송사별 : SBS, KBS, MBC ...
     * 주제별 : 종편/뉴스, 연예오락, 영화/시리즈 ...
     */
    private fun set2DepthViewPager(
        dataBinding: ScheduleTypeItemBinding,
        list: List<ScheduleData.Type>
    ) {

        // viewModel
        dataBinding.viewModel = ScheduleTypeViewModel().apply {
            setData(list)
        }

        val view = dataBinding.root
        val tabLayout = view.tl_type
        val viewPager = view.vp_schedule_depth2

        // tab
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                Timber.d(">> onTabSelected : ${tab?.position}")
                val position = tab?.position ?: 0
                viewPager.setCurrentItem(position, false)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                Timber.d(">> onTabUnselected : ${tab?.position}")
            }
        })

        // viewPager
        // 채널 보여줌
        viewPager.apply {
            adapter = object :

                BaseRecyclerViewAdapter<ScheduleData.Type, ScheduleRoomItemBinding>(R.layout.schedule_room_item) {

                override fun onBindData(
                    position: Int,
                    data: ScheduleData.Type?,
                    dataBinding: ScheduleRoomItemBinding
                ) {

                }
            }

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                    tabLayout.setScrollPosition(position, positionOffset, true)
                }

                override fun onPageSelected(position: Int) {
                    Timber.d(">> onPageSelected : $position")
                }

                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)
                }
            })
        }
    }
}

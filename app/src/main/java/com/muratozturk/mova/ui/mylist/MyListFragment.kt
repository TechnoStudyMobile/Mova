package com.muratozturk.mova.ui.mylist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.muratozturk.mova.R
import com.muratozturk.mova.common.*
import com.muratozturk.mova.common.enums.MediaTypeEnum
import com.muratozturk.mova.databinding.FragmentMyListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import www.sanju.motiontoast.MotionToastStyle

@AndroidEntryPoint
class MyListFragment : Fragment(R.layout.fragment_my_list) {
    private val binding by viewBinding(FragmentMyListBinding::bind)
    private val viewModel: MyListViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        collectData()
    }

    fun initUI() {
        with(viewModel) {
            getBookmarks()
            getCategoryFilter()
        }
    }

    private fun onClickItem(id: Int, mediaType: MediaTypeEnum) {
        val action = MyListFragmentDirections.actionMyListFragmentToDetailsFragment(id, mediaType)
        findNavController().navigate(action)
    }

    private fun collectData() {
        with(viewModel) {
            with(binding) {

                viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                    bookmarks.collectLatest { response ->
                        when (response) {
                            is Resource.Loading -> {
                                bookmarksLoading.visible()
                                bookmarksLoading.startShimmer()
                                myListRecyclerView.gone()
                                filterRecyclerView.gone()
                                emptyList.gone()
                            }
                            is Resource.Error -> {
                                myListRecyclerView.gone()
                                filterRecyclerView.gone()

                                requireActivity().showToast(
                                    getString(R.string.error),
                                    response.throwable.localizedMessage ?: "Error",
                                    MotionToastStyle.ERROR
                                )

                            }
                            is Resource.Success -> {
                                bookmarksLoading.gone()
                                bookmarksLoading.stopShimmer()

                                if (response.data.isEmpty()) {
                                    myListRecyclerView.gone()
                                    filterRecyclerView.gone()
                                    emptyList.visible()

                                } else {
                                    myListRecyclerView.visible()
                                    filterRecyclerView.visible()
                                    emptyList.gone()

                                    launch {
                                        categoryFilters.collectLatest { categories ->
                                            val myListCategoryAdapter = MyListCategoryAdapter(categories)
                                            filterRecyclerView.adapter = myListCategoryAdapter
                                        }
                                    }

                                    val myListAdapter =
                                        MyListAdapter(response.data)
                                    myListRecyclerView.adapter = myListAdapter
                                    myListAdapter.onClickHigh = ::onClickItem
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
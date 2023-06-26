package com.muratozturk.mova.ui.mylist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muratozturk.mova.common.Resource
import com.muratozturk.mova.data.model.local.Bookmark
import com.muratozturk.mova.domain.model.MyListCategoryUI
import com.muratozturk.mova.domain.use_case.bookmark.GetBookmarksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyListViewModel @Inject constructor(private val getBookmarksUseCase: GetBookmarksUseCase) :
    ViewModel() {

    private val _bookmarks = MutableStateFlow<Resource<List<Bookmark>>>(Resource.Loading)
    val bookmarks
        get() = _bookmarks.asStateFlow()

    private val _categoryFilters = MutableStateFlow<List<MyListCategoryUI>>(mutableListOf())
    val categoryFilters
        get() = _categoryFilters.asStateFlow()

    fun getBookmarks() = viewModelScope.launch {
        getBookmarksUseCase().collectLatest {
            _bookmarks.emit(it)
        }
    }

    fun getCategoryFilter() = viewModelScope.launch {
        _categoryFilters.value = getListCategoryFilter()
    }

    private fun getListCategoryFilter(): List<MyListCategoryUI> = mutableListOf(
        MyListCategoryUI("Tüm Kategoriler" , true),
        MyListCategoryUI("Filmler"),
            MyListCategoryUI("TV Dizileri")
    )
}
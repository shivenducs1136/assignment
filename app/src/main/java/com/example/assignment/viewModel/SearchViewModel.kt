package com.example.assignment.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.assignment.Repo.SearchedNewsRepo
import com.example.assignment.Repo.State
import com.example.assignment.data.Article
import com.example.assignment.data.NewsApi
import com.example.assignment.utils.Constants.API_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchedNewsRepo: SearchedNewsRepo
) : ViewModel(
) {

    private val _searchQuery = mutableStateOf("")
    val searchQuery = _searchQuery

    private val _searchedState= MutableStateFlow<State>(State.Empty)
    var searchedState=_searchedState.asStateFlow()
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun searchNews(query: String) {
        viewModelScope.launch {
            _searchedState.value = searchedNewsRepo.getSearchedNew(query,API_KEY).value
        }
    }
}
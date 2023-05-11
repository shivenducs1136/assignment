package com.example.assignment.viewModel

import androidx.lifecycle.VIEW_MODEL_STORE_OWNER_KEY
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment.Repo.APIService
import com.example.assignment.Repo.NewsRepo
import com.example.assignment.Repo.State
import com.example.assignment.data.Article

import com.example.assignment.data.NewsApi
import com.example.assignment.utils.Constants.API_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import okhttp3.internal.toImmutableList
@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepo: NewsRepo
) : ViewModel() {
    private var _newsList = mutableListOf<Article>()
    private val newsList=_newsList
    private val _state = MutableStateFlow<State>(State.Empty)
    val state = _state.asStateFlow()


    fun getNewsRepo(){
        viewModelScope.launch {
           _state.value = newsRepo.getNews("in","general", API_KEY).value
        }
    }

//    fun getNews() {
//        viewModelScope.launch {
//            try {
//                _newsList.clear()
//                _state.value = State.Loading
//                val respose = apiService.getNews("in", "general", API_KEY)
//                if (respose.isSuccessful) {
//                    respose.body()?.articles?.let { _newsList.addAll(it) }
//                    _state.value = State.Loaded(_newsList)
//                } else {
//
//                }
//
//            } catch (e: Exception) {
//                _state.value = e.message?.let { State.Error(it) }!!
//            }
//
//        }
//    }


}

package com.example.assignment.Repo

import android.widget.Toast
import com.example.assignment.data.Article
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import okhttp3.internal.toImmutableList
import org.json.JSONObject
import javax.inject.Inject


class SearchedNewsRepo @Inject constructor(
    private val apiService: APIService
) {
    private val _searchedState = MutableStateFlow<State>(State.Empty)
    private val _responseList = mutableListOf<Article>()


    suspend fun getSearchedNew(query: String, apiKey: String): MutableStateFlow<State>{
        try {
            val response = apiService.getSearchedNews(query, apiKey)
            _searchedState.value = State.Loading
            if (response.isSuccessful) {
                _responseList.clear()
                response.body()?.let { _responseList.addAll(it.articles) }
                _searchedState.value = State.Loaded(_responseList)
            } else {
                try {
                    val jObjError = JSONObject(response.errorBody()!!.string())
                    _searchedState.value = State.Error(jObjError.getJSONObject("error").toString())
                } catch (e: Exception) {
                  System.out.println(e.message)
                }
            }
        }catch(e:Exception){
            System.out.println(e.message)
        }
      return _searchedState
    }
}

sealed class State() {
    object Empty : State()
    object Loading : State()
    class Loaded(val list: MutableList<Article>) : State()
    class Error(val error: String) : State()
}
package com.example.assignment.Repo

import com.example.assignment.data.Article
import kotlinx.coroutines.flow.MutableStateFlow
import okhttp3.internal.toImmutableList
import org.json.JSONObject
import javax.inject.Inject

class NewsRepo @Inject constructor(
    private val apiService: APIService
) {
    private val _newsState = MutableStateFlow<State>(State.Empty)
    private val _newsList = mutableListOf<Article>()


    suspend fun getNews(country :String, category:String, apiKey:String): MutableStateFlow<State> {
        try {
            val response = apiService.getNews(country,category, apiKey)
            _newsState.value = State.Loading
            if (response.isSuccessful) {
                _newsList.clear()
                response.body()?.let { _newsList.addAll(it.articles) }
                _newsState.value = State.Loaded(_newsList)
            } else {
                try {
                    val jObjError = JSONObject(response.errorBody()!!.string())
                    _newsState.value = State.Error(jObjError.getJSONObject("error").toString())
                } catch (e: Exception) {
                    System.out.println(e.message)
                }
            }
        }catch(e:Exception){
            System.out.println(e.message)
        }
        return _newsState
    }
}
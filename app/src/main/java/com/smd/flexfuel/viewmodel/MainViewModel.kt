package com.smd.flexfuel.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.smd.flexfuel.model.Post
import com.smd.flexfuel.utils.SharedPrefsManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel: ViewModel() {
    private val _listOfPost = MutableStateFlow(ArrayList<Post>())
    val listOfPost: StateFlow<ArrayList<Post>> = _listOfPost.asStateFlow()

    var sharedPrefsManager: SharedPrefsManager? = null

    fun initSharedPrefsManager(context: Context) {
        sharedPrefsManager = SharedPrefsManager(context)
        getListOfPost()
    }

    fun getListOfPost(){
        onListOfPost(sharedPrefsManager?.getPostList())
        Log.d("asd", sharedPrefsManager?.getPostList().toString())
    }

    fun onListOfPost(listOfPost: ArrayList<Post>?) {
        _listOfPost.update { listOfPost!! }
    }
}
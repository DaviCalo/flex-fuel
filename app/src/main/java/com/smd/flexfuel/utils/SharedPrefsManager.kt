package com.smd.flexfuel.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.smd.flexfuel.model.Post

class SharedPrefsManager(context: Context) {
    private val PREFS_NAME = "FlexFuelPrefs"
    private val KEY_POSTS = "ListOfPosts"

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val gson = Gson()

    fun listExists(): Boolean {
        return prefs.contains(KEY_POSTS)
    }

    fun includePost(newPost: Post) {
        val currentList = getPostList().toMutableList()

        newPost.id = currentList.size + 1

        currentList.add(newPost)

        val updatedJson = gson.toJson(currentList)

        prefs.edit { putString(KEY_POSTS, updatedJson) }
    }

    fun getPostList(): ArrayList<Post> {
        val savedJson = prefs.getString(KEY_POSTS, null)

        return if (savedJson == null) {
            ArrayList()
        } else {
            val type = object : TypeToken<List<Post>>() {}.type
            val list: List<Post> = gson.fromJson(savedJson, type)
            ArrayList(list)
        }
    }
}
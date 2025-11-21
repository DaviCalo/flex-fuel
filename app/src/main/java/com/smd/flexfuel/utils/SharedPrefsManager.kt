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

    fun includePost(newPost: Post) {
        val currentList = getPostList().toMutableList()

        val maxId = currentList.maxOfOrNull { it.id } ?: 0
        newPost.id = maxId + 1

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

    fun getPost(id: Int): Post? {
        val posts = getPostList()
        return posts.find { it.id == id }
    }

    fun updatePost(updatedPost: Post) {
        val currentList = getPostList().toMutableList()
        val index = currentList.indexOfFirst { it.id == updatedPost.id }

        if (index != -1) {
            currentList[index] = updatedPost
            val updatedJson = gson.toJson(currentList)
            prefs.edit { putString(KEY_POSTS, updatedJson) }
        }
    }

    fun deletePost(id: Int) {
        val currentList = getPostList().toMutableList()
        val iterator = currentList.iterator()

        while (iterator.hasNext()) {
            if (iterator.next().id == id) {
                iterator.remove()
                break
            }
        }

        val updatedJson = gson.toJson(currentList)
        prefs.edit { putString(KEY_POSTS, updatedJson) }
    }
}
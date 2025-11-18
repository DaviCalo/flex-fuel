package com.smd.flexfuel.model

data class Post(
    var id: Int,
    val name: String,
    val gasolineValue: Double,
    val alcoholValue: Double,
    val isRatio70: Boolean,
    val location: PostLocation?
)

data class PostLocation(
    val latitude: Double,
    val longitude: Double
)
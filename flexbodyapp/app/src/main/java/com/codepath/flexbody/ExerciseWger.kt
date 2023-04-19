package com.codepath.flexbody

import android.support.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class ExerciseSearchResponse(
    @SerialName("results")
    val response: List<ExerciseWger>?
)

@Keep
@Serializable
data class ExerciseWger(
    @SerialName("name")
    val name: String?,
    @SerialName("description")
    val description: String?,
    @SerialName("id")
    val id: Long?,
    @SerialName("category")
    val category: Int?,
)
package com.codepath.flexbody

import android.support.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class ExerciseSearchResponse(
    @SerialName("suggestions")
    val suggestions: List<BaseResponse>?
)

@Keep
@Serializable
data class BaseResponse(
    @SerialName("value")
    val value: String?,
    @SerialName("data")
    val data: ExerciseWger?

)

@Keep
@Serializable
data class ExerciseWger(
    @SerialName("id")
    val id: Int?,
    @SerialName("name")
    val name: String?,
    @SerialName("category")
    val category: String?,
    @SerialName("image")
    val image: String?,
)
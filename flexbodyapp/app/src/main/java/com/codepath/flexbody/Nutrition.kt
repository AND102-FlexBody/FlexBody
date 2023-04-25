package com.codepath.flexbody


import com.google.gson.annotations.SerializedName


class Nutrition() {
    @SerializedName("data")
    var data: Data?=null
}

class Data() {
    @SerializedName("name")
    var name: String?=null

    @SerializedName("image")
    var image: String?=null
}

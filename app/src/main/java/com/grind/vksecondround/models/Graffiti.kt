package com.grind.vksecondround.models
import com.google.gson.annotations.SerializedName




data class Graffiti (

	@SerializedName("src") val src : String,
	@SerializedName("width") val width : Int,
	@SerializedName("height") val height : Int
)
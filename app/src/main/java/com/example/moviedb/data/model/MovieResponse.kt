package com.example.moviedb.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class MovieResponse(
    @SerializedName("results")
    val mResult: List<Movie>? = null,
    @SerializedName("page")
    val mPage: Int? = null,
    @SerializedName("total_pages")
    val totalPage: Int? = null
) : Parcelable
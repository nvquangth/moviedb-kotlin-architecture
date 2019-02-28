package com.example.moviedb.data.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "movie")
@Parcelize
data class Movie(
    @PrimaryKey
    @ColumnInfo(name = "id")
    @NonNull
    @SerializedName("id")
    val mId: Int,

    @SerializedName("title")
    val mTitle: String? = null,

    @SerializedName("vote_average")
    val mVote: Float? = null,

    @SerializedName("poster_path")
    val mPosterPath: String? = null,

    @SerializedName("backdrop_path")
    val mBackdropPath: String? = null,

    @SerializedName("overview")
    val mOverview: String? = null
) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Movie) return false
        return other.mId == this.mId
    }
}
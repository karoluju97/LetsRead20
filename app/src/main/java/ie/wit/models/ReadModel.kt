package ie.wit.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ReadModel(var id: Long = 0, val booktitle: String = "", var author: String = "", var genre: String = "", var year_released: String = "", var summary: String = "" ): Parcelable
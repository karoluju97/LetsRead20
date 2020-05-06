package ie.wit.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ReadModel(
    var uid: String? = "",
                     var booktitle: String = "",
                     var author: String = "",
                     var genre: String = "",
                     var year_released: String = "",
                     var summary: String = "",
                     var profilepic: String = "",
                     var email: String? = "abc@gmail.com")
    : Parcelable
{
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
           "booktitle" to booktitle,
            "author" to author,
            "genre" to genre,
            "year_released" to year_released,
            "summary" to summary,
            "profilepic" to profilepic,
            "email" to email
        )
    }
}

package com.example.movieapp.model

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QueryDocumentSnapshot

data class Movies(

//    @SerializedName("page")
    val page: Int,
//    @SerializedName("results")
    val results: List<Results>

)

data class Results(

//    @SerializedName("id")
    var id: Long,
//    @SerializedName("title")
    val title: String,
//    @SerializedName("overview")
    val overview: String,
//    @SerializedName("poster_path")
    val poster_path: String

) {
    companion object {

        fun fromData(snapshot: QueryDocumentSnapshot): Results {

            return Results(
                id = snapshot.id.toLong(),
                title = snapshot.data["original_title"] as String,
                overview = snapshot.data["overview"] as String,
                poster_path = snapshot.data["posterPath"] as String
            )

        }

        fun fromDocument(doc: DocumentSnapshot): Results {

            return Results(
                id = doc.id.toLong(),
                title = doc["original_title"] as String,
                overview = doc["overview"] as String,
                poster_path = doc["posterPath"] as String
            )

        }

    }

}
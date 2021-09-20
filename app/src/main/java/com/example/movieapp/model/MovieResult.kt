package com.example.movieapp.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QueryDocumentSnapshot

data class MovieResult(
    val page: Int,
    val results: List<Movie>
)

@Entity
data class Movie(
    @PrimaryKey
    var pkMovie: Long? = 0,
    var userEmail: String? = null,
    val id: Long,
    val title: String,
    val overview: String,
    val poster_path: String,
    val vote_average: Float,
//    @Embedded
    val genres: List<Genre>?

) {
//    constructor(): this( "", 0L, "", "", "", 0F, listOf<Genre>())
//    companion object {
//
//        fun fromData(snapshot: QueryDocumentSnapshot): Movie {
//
//            return Movie(
//                id = snapshot.id.toLong(),
//                title = snapshot.data["original_title"] as String,
//                overview = snapshot.data["overview"] as String,
//                poster_path = snapshot.data["posterPath"] as String,
//                vote_average = snapshot.data["vote_average"] as Float,
//                genres = snapshot.data["genres"] as List<Genre>
//            )
//
//        }
//
//        fun fromDocument(doc: DocumentSnapshot): Movie {
//
//            return Movie(
//                id = doc.id.toLong(),
//                title = doc["original_title"] as String,
//                overview = doc["overview"] as String,
//                poster_path = doc["posterPath"] as String,
//                vote_average = doc["vote_average"] as Float,
//                genres = doc["genres"] as List<Genre>
//            )
//
//        }
//
//    }

}

@Entity
data class Genre(
    @PrimaryKey(autoGenerate = true)
    var pkGenre: Long? = 0,
    val id: Int,
    val name: String
) {
//    constructor(): this(0, 0, "")
}

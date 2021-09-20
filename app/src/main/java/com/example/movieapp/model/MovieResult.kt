package com.example.movieapp.model

data class MovieResult(
    val page: Int,
    val results: List<Movie>
)

data class Movie(

    var id: Long,
    val title: String,
    val overview: String,
    val poster_path: String,
    val vote_average: Float,
    val genres: List<Genre>?

) {
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

data class Genre(
    val id: Int,
    val name: String
)

data class GenreResult(

    val genres: List<Genre>?
)
package xerus.ratings

typealias Rating = Float

interface Ratable {
	
	val id: Int
	
	fun getRating(): Rating
	fun getRating(id: Int): Rating
	
	fun updateRating(other: Ratable, change: Rating)
	fun updateRating(change: Rating)
	
}
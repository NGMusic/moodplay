typealias Rating = Float

interface Ratable {
	
	val id: Int
	
	fun getRating(): Rating
	fun getRating(id: Int): Rating
	
	
	
}
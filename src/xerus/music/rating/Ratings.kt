import xerus.util.MutableFloat
import xerus.util.Storage

abstract class RatingsHelper {
	
	abstract val defaultRating: Rating
	
	abstract fun calculateRating(r1: Ratings, r2: Ratings): Rating
	abstract fun getRatableById(id: Int): Ratings?
	
	companion object {
		lateinit var instance: RatingsHelper
	}
	
}

/** Requires an instance of [RatingsHelper] at [RatingsHelper.instance] to work */
class Ratings(override val id: Int) : Ratable {
	
	private val ratings: Storage<MutableFloat> = Storage(MutableFloat(helper.defaultRating))
	
	override fun getRating(): Rating = ratings.getOrDefault(id).toFloat()
	override fun getRating(id: Int): Rating = getRatingInternal(id).toFloat()
	
	private fun getRatingInternal(id: Int): MutableFloat = ratings[id] ?: run {
		val rating = helper.getRatableById(id)?.let { other ->
			helper.calculateRating(this, other).also { r -> other.ratings.set(id, r) }
		} ?: helper.defaultRating
		ratings[id] = MutableFloat(rating)
		rating
	}
	
	private fun editRating(id: Int, change: Float) {
		val f = getRatingInternal(id)
		// apply a function to softly cap off the sides
		val temp = f.toFloat() / 8 - 1
		f.add((-temp * temp + 1) * change)
	}
	
	fun updateRating(s: Ratings, change: Float) {
		editRating(s.id, change)
		s.editRating(id, change / 2)
		logger.finer("$this updated Rating to $s by $change")
	}
	
	fun updateRating(change: Float) {
		editRating(id, change)
		logger.finer("$this updated Rating by $change")
	}
	
	fun initRatings(line: ByteArray) {
		ratings.ensureCapacity(line.size)
		line.mapTo(ratings) {
			MutableFloat((it.toInt() and 0x0FF) / 16f)
		}
	}
	
	fun serialiseRatings(): ByteArray {
		val res = ByteArray(ratings.size)
		for (i in ratings.indices)
			res[i] = (16 * getRating(i)).toByte()
		return res
	}
	
	companion object {
		val logger = LoggerFactory.getLogger(Rating::class.java)
		val helper = RatingsHelper.instance
	}
	
}
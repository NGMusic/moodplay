package xerus.ratings

import org.slf4j.LoggerFactory
import xerus.util.MutableFloat
import xerus.util.Storage

abstract class AbstractRatingsHelper {
	
	abstract val defaultRating: Rating
	
	abstract fun calculateRating(r1: Ratings, r2: Ratings): Rating
	abstract fun getRatableById(id: Int): Ratings?
	
	companion object {
		lateinit var instance: AbstractRatingsHelper
	}
	
}

/** requires [AbstractRatingsHelper.instance] to be initialized */
class Ratings(override val id: Int) : Ratable {
	
	private val ratings: Storage<MutableFloat> = Storage(MutableFloat(helper.defaultRating))
	
	override fun getRating(): Rating = ratings.getOrDefault(id).toFloat()
	override fun getRating(id: Int): Rating = getRatingInternal(id).toFloat()
	
	private fun getRatingInternal(id: Int): MutableFloat = ratings[id] ?: run {
		val rating = helper.getRatableById(id)?.let { other ->
			MutableFloat(helper.calculateRating(this, other)).also { other.ratings[id] = it }
		} ?: MutableFloat(helper.defaultRating)
		ratings[id] = rating
		rating
	}
	
	private fun editRating(id: Int, change: Rating) {
		val f = getRatingInternal(id)
		// apply a function to softly cap off the sides
		val temp = f.toFloat() / 8 - 1
		f.add((-temp * temp + 1) * change)
	}
	
	override fun updateRating(other: Ratable, change: Rating) {
		editRating(other.id, change)
		(other as? Ratings)?.editRating(id, change / 2)
		logger.trace("$this updated xerus.ratings.Rating to $other by $change")
	}
	
	override fun updateRating(change: Rating) {
		editRating(id, change)
		logger.trace("$this updated xerus.ratings.Rating by $change")
	}
	
	fun deserialize(line: ByteArray) {
		ratings.clear()
		ratings.ensureCapacity(line.size)
		for (byte in line)
			ratings.add(MutableFloat((byte.toInt() and 0x0FF) / 16f))
	}
	
	fun serialize(): ByteArray {
		val res = ByteArray(ratings.size)
		for (i in ratings.indices)
			res[i] = (16 * getRating(i)).toByte()
		return res
	}
	
	companion object {
		val logger = LoggerFactory.getLogger(Rating::class.java)
		inline val helper
			get() = AbstractRatingsHelper.instance
	}
	
}
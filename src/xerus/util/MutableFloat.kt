package xerus.util

class MutableFloat(private var value: Float = 0f) : Number(), Comparable<MutableFloat> {
	
	//-----------------------------------------------------------------------
	/**
	 * Checks whether the float value is the special NaN value.
	 *
	 * @return true if NaN
	 */
	val isNaN: Boolean
		get() = java.lang.Float.isNaN(value)
	
	/**
	 * Checks whether the float value is infinite.
	 *
	 * @return true if infinite
	 */
	val isInfinite: Boolean
		get() = java.lang.Float.isInfinite(value)
	
	/**
	 * Increments this instance's value by 1; this method returns the value associated with the instance
	 * immediately prior to the increment operation. This method is not thread safe.
	 *
	 * @return the value associated with the instance before it was incremented
	 * @since 3.5
	 */
	val andIncrement: Float
		get() {
			val last = value
			value++
			return last
		}
	
	/**
	 * Decrements this instance's value by 1; this method returns the value associated with the instance
	 * immediately prior to the decrement operation. This method is not thread safe.
	 *
	 * @return the value associated with the instance before it was decremented
	 * @since 3.5
	 */
	val andDecrement: Float
		get() {
			val last = value
			value--
			return last
		}
	
	/**
	 * Constructs a new MutableFloat with the specified value.
	 *
	 * @param value  the initial value to store
	 */
	constructor(value: Number) : this(value.toFloat())
	
	/**
	 * Constructs a new MutableFloat parsing the given string.
	 *
	 * @param value  the string to parse
	 */
	constructor(value: String) : this(value.toFloat())
	
	
	/**
	 * Sets the value.
	 *
	 * @param value  the value to set
	 */
	fun setValue(value: Float) {
		this.value = value
	}
	
	/**
	 * Sets the value from any Number instance.
	 *
	 * @param value  the value to set
	 */
	fun setValue(value: Number) {
		this.value = value.toFloat()
	}
	
	/** Increments the value. */
	fun increment() {
		value++
	}
	
	/**
	 * Increments this instance's value by 1; this method returns the value associated with the instance
	 * immediately after the increment operation. This method is not thread safe.
	 *
	 * @return the value associated with the instance after it is incremented
	 */
	fun incrementAndGet(): Float {
		value++
		return value
	}
	
	/** Decrements the value. */
	fun decrement() {
		value--
	}
	
	/**
	 * Decrements this instance's value by 1; this method returns the value associated with the instance
	 * immediately after the decrement operation. This method is not thread safe.
	 *
	 * @return the value associated with the instance after it is decremented
	 * @since 3.5
	 */
	fun decrementAndGet(): Float {
		value--
		return value
	}
	
	/**
	 * Adds a value to the value of this instance.
	 *
	 * @param operand  the value to add, not null
	 * @since Commons Lang 2.2
	 */
	fun add(operand: Float) {
		this.value += operand
	}
	
	/**
	 * Adds a value to the value of this instance.
	 *
	 * @param operand  the value to add
	 */
	fun add(operand: Number) {
		this.value += operand.toFloat()
	}
	
	/**
	 * Subtracts a value from the value of this instance.
	 *
	 * @param operand  the value to subtract
	 * @since Commons Lang 2.2
	 */
	fun subtract(operand: Float) {
		this.value -= operand
	}
	
	/**
	 * Subtracts a value from the value of this instance.
	 *
	 * @param operand  the value to subtract, not null
	 * @throws NullPointerException if the object is null
	 * @since Commons Lang 2.2
	 */
	fun subtract(operand: Number) {
		this.value -= operand.toFloat()
	}
	
	/**
	 * Increments this instance's value by `operand`; this method returns the value associated with the instance
	 * immediately after the addition operation. This method is not thread safe.
	 *
	 * @param operand the quantity to add, not null
	 * @return the value associated with this instance after adding the operand
	 * @since 3.5
	 */
	fun addAndGet(operand: Float): Float {
		this.value += operand
		return value
	}
	
	/**
	 * Increments this instance's value by `operand`; this method returns the value associated with the instance
	 * immediately after the addition operation. This method is not thread safe.
	 *
	 * @param operand the quantity to add
	 * @return the value associated with this instance after adding the operand
	 */
	fun addAndGet(operand: Number): Float {
		this.value += operand.toFloat()
		return value
	}
	
	/**
	 * Increments this instance's value by `operand`; this method returns the value associated with the instance
	 * immediately prior to the addition operation. This method is not thread safe.
	 *
	 * @param operand the quantity to add, not null
	 * @return the value associated with this instance immediately before the operand was added
	 * @since 3.5
	 */
	fun getAndAdd(operand: Float): Float {
		val last = value
		this.value += operand
		return last
	}
	
	/**
	 * Increments this instance's value by `operand`; this method returns the value associated with the instance
	 * immediately prior to the addition operation. This method is not thread safe.
	 *
	 * @param operand the quantity to add, not null
	 * @throws NullPointerException if `operand` is null
	 * @return the value associated with this instance immediately before the operand was added
	 * @since 3.5
	 */
	fun getAndAdd(operand: Number): Float {
		val last = value
		this.value += operand.toFloat()
		return last
	}
	
	/**
	 * Returns the value of this MutableFloat as an int.
	 *
	 * @return the numeric value represented by this object after conversion to type int.
	 */
	override fun toInt(): Int {
		return value.toInt()
	}
	
	/**
	 * Returns the value of this MutableFloat as a long.
	 *
	 * @return the numeric value represented by this object after conversion to type long.
	 */
	override fun toLong(): Long {
		return value.toLong()
	}
	
	/**
	 * Returns the value of this MutableFloat as a float.
	 *
	 * @return the numeric value represented by this object after conversion to type float.
	 */
	override fun toFloat(): Float {
		return value
	}
	
	/**
	 * Returns the value of this MutableFloat as a double.
	 *
	 * @return the numeric value represented by this object after conversion to type double.
	 */
	override fun toDouble(): Double {
		return value.toDouble()
	}
	
	override fun toByte(): Byte = value.toByte()
	
	override fun toChar(): Char = value.toChar()
	
	override fun toShort(): Short = value.toShort()
	
	/**
	 * Compares this object against some other object. The result is `true` if and only if the argument is
	 * not `null` and is a `Float` object that represents a `float` that has the
	 * identical bit pattern to the bit pattern of the `float` represented by this object. For this
	 * purpose, two float values are considered to be the same if and only if the method
	 * [Float.floatToIntBits]returns the same int value when applied to each.
	 *
	 *
	 * Note that in most cases, for two instances of class `Float`,`f1` and `f2`,
	 * the value of `f1.equals(f2)` is `true` if and only if <blockquote>
	 *
	 * <pre>
	 * f1.floatValue() == f2.floatValue()
	</pre> *
	 *
	</blockquote> *
	 *
	 *
	 * also has the value `true`. However, there are two exceptions:
	 *
	 *  * If `f1` and `f2` both represent `Float.NaN`, then the
	 * `equals` method returns `true`, even though `Float.NaN==Float.NaN` has
	 * the value `false`.
	 *  * If `f1` represents `+0.0f` while `f2` represents `-0.0f`,
	 * or vice versa, the `equal` test has the value `false`, even though
	 * `0.0f==-0.0f` has the value `true`.
	 *
	 * This definition allows hashtables to operate properly.
	 *
	 * @param other  the object to compare with, null returns false
	 * @return `true` if the objects are the same; `false` otherwise.
	 * @see java.lang.Float.floatToIntBits
	 */
	override fun equals(other: Any?): Boolean {
		return other is MutableFloat && java.lang.Float.floatToIntBits(other.value) == java.lang.Float.floatToIntBits(value)
	}
	
	/**
	 * Returns a suitable hash code for this mutable.
	 *
	 * @return a suitable hash code
	 */
	override fun hashCode(): Int {
		return java.lang.Float.floatToIntBits(value)
	}
	
	//-----------------------------------------------------------------------
	/**
	 * Compares this mutable to another in ascending order.
	 *
	 * @param other  the other mutable to compare to, not null
	 * @return negative if this is less, zero if equal, positive if greater
	 */
	override fun compareTo(other: MutableFloat): Int {
		return java.lang.Float.compare(this.value, other.value)
	}
	
	//-----------------------------------------------------------------------
	/**
	 * Returns the String value of this mutable.
	 *
	 * @return the mutable value as a string
	 */
	override fun toString(): String {
		return value.toString()
	}
	
}


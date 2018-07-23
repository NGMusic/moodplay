package xerus.util

import java.util.*
import java.util.function.Consumer

/**
 * An ArrayList that will grow automatically instead of throwing an
 * [ArrayIndexOutOfBoundsException]
 *
 * No remove operations supported
 */
class Storage<E> constructor(
		/** required for [getOrDefault]  */
		private val defaultVal: E? = null) : Collection<E> {
	
	private val _elements = ArrayList<E?>(256)
	
	/** the index of the last non-null item  */
	override var size: Int = 0
		private set
	/** reports how many elements are actually in this storage  */
	var trueSize: Int = 0
		private set
	
	private var modCount = 0
	
	/**
	 * replaces the specified element if it already exists<br></br>
	 * if this Storage is not yet big enough, it will get expanded by adding nulls
	 * @param element if null, this instantly returns null
	 */
	operator fun set(index: Int, element: E?): E? {
		if (element == null)
			return null
		_elements.ensureCapacity(index + 1)
		for (s in index - _elements.size downTo 0) {
			_elements.add(null)
		}
		if (_elements.set(index, element) == null)
			trueSize++
		updateSize(true)
		return null
	}
	
	fun add(element: E): Boolean {
		trueSize++
		return updateSize(_elements.add(element))
	}
	
	private fun updateSize(update: Boolean): Boolean {
		if (update) {
			val oldsize = size
			size = _elements.size
			while (size > 0 && get(size - 1) == null)
				size--
			if (oldsize != size)
				modCount++
			return true
		}
		return false
	}
	
	fun clear() {
		_elements.clear()
		size = 0
		trueSize = 0
		modCount = 0
	}
	
	override fun contains(element: E) = _elements.contains(element)
	
	operator fun get(index: Int): E? = if (size > index) _elements[index] else null
	
	fun getUnsafe(index: Int): E = get(index)!!
	
	fun getOrDefault(index: Int): E = get(index) ?: defaultVal ?: throw IllegalStateException("No defaultVal set!")
	
	fun has(index: Int): Boolean = size > index && _elements[index] != null
	
	/** returns an iterator over this collection that excludes null elements and does not support removal  */
	override fun iterator(): Iterator<E> = StorageItr()
	
	/** returns the iterator of the enclosed ArrayList  */
	fun fullIterator() = _elements.iterator()
	
	/**
	 * modified version of [ArrayList.Itr], that skips null elements
	 *
	 * does not support removal
	 */
	private inner class StorageItr : Iterator<E> {
		/** index of next element to return */
		private var cursor: Int = 0
		private var expectedModCount = modCount
		private val size: Int = this@Storage.size
		
		private var advanced: Boolean = false
		
		override fun hasNext() = advance()
		
		override fun next(): E {
			checkForComodification()
			if (!advance())
				throw NoSuchElementException()
			advanced = false
			return get(cursor)!!
		}
		
		private fun advance(): Boolean {
			if (advanced)
				return true
			cursor++
			while (get(cursor) == null) {
				cursor++
				if (cursor >= size)
					return false
			}
			advanced = true
			return true
		}
		
		override fun forEachRemaining(consumer: Consumer<in E>) {
			Objects.requireNonNull(consumer)
			var i = cursor
			if (i >= size)
				return
			while (i != size && modCount == expectedModCount) {
				val element = get(i++)
				if (element != null)
					consumer.accept(element)
			}
			// update once at end of iteration to reduce heap write traffic
			cursor = i
			checkForComodification()
		}
		
		internal fun checkForComodification() {
			if (modCount != expectedModCount)
				throw ConcurrentModificationException()
		}
	}
	
	fun ensureCapacity(capacity: Int) = _elements.ensureCapacity(capacity)
	
	override fun containsAll(elements: Collection<E>) = _elements.containsAll(elements)
	
	override fun isEmpty() = size == 0
	
}

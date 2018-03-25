package xerus.util

import java.util.*
import java.util.function.Consumer

// FIXME custom implementation as List

/**
 * An ArrayList that will grow automatically instead of throwing an
 * [ArrayIndexOutOfBoundsException]
 *
 * No remove operations supported
 */
class Storage<E> constructor(
		/** required for [getOrDefault]  */
		private val defaultVal: E? = null) : ArrayList<E?>(256), Iterable<E> {
	
	/** the index of the last non-null item  */
	override var size: Int = 0
		private set
	/** reports how many elements are actually in this storage  */
	var trueSize: Int = 0
		private set
	
	/**
	 * replaces the specified element if it already exists<br></br>
	 * if this Storage is not yet big enough, it will get expanded by adding nulls
	 * @param element if null, this instantly returns null
	 */
	override operator fun set(index: Int, element: E?): E? {
		if (element == null)
			return null
		super.ensureCapacity(index + 1)
		for (s in index - super.size downTo 0) {
			super.add(null)
		}
		if (super.set(index, element) == null)
			trueSize++
		updateSize(true)
		return null
	}
	
	/** shifts nothing, redirects to [set]  */
	override fun add(index: Int, element: E?) {
		set(index, element)
	}
	
	override fun add(element: E?): Boolean {
		trueSize++
		return updateSize(super.add(element))
	}
	
	override fun addAll(elements: Collection<E?>): Boolean {
		trueSize += elements.size
		return updateSize(super.addAll(elements))
	}
	
	private fun updateSize(update: Boolean): Boolean {
		if (update) {
			size = super.size
			while (size > 0 && get(size - 1) == null)
				size--
			return true
		}
		return false
	}
	
	override fun clear() {
		super.clear()
		size = 0
		trueSize = 0
	}
	
	override fun get(index: Int): E? = if (size > index) super.get(index) else null
	
	fun getUnsafe(index: Int): E = get(index)!!
	
	fun getOrDefault(index: Int): E = get(index) ?: defaultVal ?: throw IllegalStateException("No defaultVal set")
	
	fun has(index: Int): Boolean = size > index && super.get(index) != null
	
	/** returns an iterator over this collection that excludes null elements and does not support removal  */
	override fun iterator(): MutableIterator<E> = StorageItr()
	
	/** returns the iterator from the enclosed ArrayList  */
	fun fullIterator() = super.iterator()
	
	/**
	 * modified version of [ArrayList.Itr], that skips null elements
	 *
	 * does not support removal
	 */
	private inner class StorageItr : MutableIterator<E> {
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
		
		override fun remove() =
				throw UnsupportedOperationException("This is a Storage Iterator!")
		
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
	
	override fun removeAt(index: Int): E = throw UnsupportedOperationException()
	override fun remove(element: E?) = throw UnsupportedOperationException()
	override fun removeAll(elements: Collection<E?>) = throw UnsupportedOperationException()
	override fun retainAll(elements: Collection<E?>) = throw UnsupportedOperationException()
	
}

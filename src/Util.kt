import java.nio.file.Paths

class Util {
    companion object{
        fun readLines(file: String):List<String>{
            return Paths.get(file).toFile().readLines()
        }
    }
}

// insert the number into asc sorted array
fun insertPos(v:Int, sortL: List<Int>, hi:Int = sortL.size - 1, lo:Int = 0):Pair<Int,Int>{
    val h = sortL[hi]
    val l = sortL[lo]

    if (hi - lo <= 1)
        return l to h

    if (h <= v)
        return h to h
    if (l >= v)
        return l to l

    val mi = (hi+lo)/2
    val m = sortL[mi]
    if (m >= v)
        return insertPos(v, sortL, mi, lo)
    else
        return insertPos(v, sortL, hi, mi)
}

fun <T>Iterable<Iterable<T>>.transpose():List<List<T>> = this.foldIndexed(listOf<List<T>>()){
        index, acc, list ->
    if (index == 0)
        list.map { listOf(it) }
    else
        list.mapIndexed { index0, i -> acc[index0] + listOf(i)  }
}

fun String.splitBySpace():List<String> = this.trim().split("""\s+""".toRegex())

fun <K,V> Map<K,Iterable<V>>.merge(map: Map<K,Iterable<V>>): Map<K,List<V>> {
    return (this.keys + map.keys).map {
        it to
        this.getOrDefault(it, listOf()) + map.getOrDefault(it, listOf())
    }.toMap()
}

fun <T> Iterable<T>.frequency():Map<T, Long> = this.fold(mutableMapOf()){
    acc, t ->
        acc[t] = acc.getOrDefault(t, 0) + 1
        acc
}

fun <K, V, T> Map<K, Map<T, V>>.merges(map: Map<K, Map<T, V>>): Map<K, Map<T, V>> {
    return this.keys.union(map.keys).map {
        it to this.getOrDefault(it, mapOf()) + map.getOrDefault(it, mapOf())
    }.toMap()
}

fun <K> Map<K,Long>.mergeValue(map: Map<K, Long>):Map<K,Long> {
    return (this.keys + map.keys).associateWith {
        (this.getOrDefault(it, 0L) + map.getOrDefault(it, 0L))
    }
}

fun <T>List<T>.splitWith(sep:(T)->Boolean):List<List<T>> {
    val res = mutableListOf<List<T>>()
    var start = 0
    (0 until this.size).forEach {
        val v = this[it]
        if (sep(v) || this.size -1 == it){
            if (it > 0){
                res += listOf(this.subList(start, if (this.size - 1 == it) it+1 else it))
            }
            start = it + 1
        }
    }

    return res
}

fun <T>List<T>.permutations():List<List<T>> {
    if (this.size <= 1)
        return listOf(this)

    return this.foldIndexed(listOf()){
        i, acc, e ->
        acc + (this.take(i) + this.drop(i+1)).permutations().map { listOf(e) + it }
    }


}
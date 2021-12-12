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
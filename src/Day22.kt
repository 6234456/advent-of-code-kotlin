import java.lang.Error
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

data class Scope(val start : Long, val end: Long) {
    override fun equals(other: Any?): Boolean {
       return other is Scope && other.start == start  && other.end == end
    }

    operator fun contains(other: Scope): Boolean{
        return this.start <= other.start && end >= other.end
    }

    override fun toString(): String {
        if (this == EMPTY)
            return "Empty"
       return "$start..$end"
    }

    fun overlapped(other: Scope):Boolean{
        if (other == EMPTY) return false
        if (other.start > end) return false
        if (start > other.end) return false

        return true
    }

    operator fun plus(other: Scope): Segment{
        if (this == EMPTY)
            return Segment(listOf(other))

        if (!overlapped(other))
            return Segment(listOf(this, other))

        return Segment(listOf(Scope(min(this.start, other.start), max(this.end, other.end))))
    }

   operator fun minus(other: Segment):Segment{
       return Segment(listOf(this)) - other
   }

    operator fun minus(other: Scope):Segment{
        if (!overlapped(other))
            return Segment(listOf(this))

        if (this in other)
            return Segment(listOf(EMPTY))

        if (other in this){
            if (this.start == other.start)
                return Segment(listOf(Scope(other.end + 1, end)))

            if (this.end == other.end)
                return Segment(listOf(Scope(this.start, other.start - 1)))
            return Segment(listOf(Scope(this.start, other.start-1), Scope(other.end+1, end)))
        }

        if (this.start < other.start)
            return Segment(listOf(Scope(this.start, other.start - 1)))

        return Segment(listOf(Scope(other.end + 1, this.end)))
    }

    operator fun minus(other: String): Segment = minus(Scope.of(other))

    fun intersect(other: Scope): Scope {
        if (!overlapped(other))
            return EMPTY

        return Scope(max(start, other.start), min(end, other.end))
    }

    fun intersect(other: String): Scope{
        return intersect(Scope.of(other))
    }

    fun size():Long{
        if (this == EMPTY)
            return 0L
        return end - start + 1
    }
    companion object{
        val EMPTY = Scope(0L, 0L)
        fun of(string: String):Scope{
            val v = string.split("..").map { it.trim().toInt().toLong() }
            return Scope(v.first(), v.last())
        }
    }
}

data class Segment(val list: List<Scope>){
    override fun toString(): String {
       return list.toString()
    }

    fun size():Long{
        return list.fold(0L){acc, scope ->
            acc + scope.size()
        }
    }

    fun intersect(other: Segment):Segment{
        return Segment(this.list.fold(listOf<Scope>()){
            acc, scope ->
                acc + other.list.map { scope.intersect(it) }
        }.filter { it != Scope.EMPTY })
    }

    fun intersect(other: Scope):Segment{
        return intersect(Segment(listOf(other)))
    }

    fun intersect(other: String):Segment{
        return intersect(Scope.of(other))
    }


    operator fun minus(other: Scope):Segment{
        return Segment(list.map { (it - other).list }.fold<List<Scope>, List<Scope>>(listOf()){ acc, scopes ->  acc + scopes }.filter { it != Scope.EMPTY })
    }

    operator fun minus(other:Segment):Segment{
        return other.list.fold(this){
            acc, scope ->
                this - scope
        }
    }

    operator fun plus(other: Scope):Segment{
        return Segment(list + list.fold(Segment(listOf(other))) {
            acc, scope ->
             acc - scope
        }.list)
    }

    operator fun plus(other: String):Segment{
        return plus(Scope.of(other))
    }


    operator fun minus(other: String):Segment = minus(Scope.of(other))
}

fun main() {
   println(Scope.of("1..99") - "1..10" - "22..50")
    println(Scope.of("1..99") - Segment(listOf(Scope.of("1..10"), Scope.of("22..50"))))
    println(Segment(listOf(Scope.of("1..10"), Scope.of("22..50"))) - Scope.of("9..40"))


    var t = 0
    Util.readLines("src/Test22.txt").fold(Triple<Segment, Segment, Segment>(
        Segment(listOf(Scope.EMPTY)),
        Segment(listOf(Scope.EMPTY)),
        Segment(listOf(Scope.EMPTY))
    )) {
       acc, i ->
        val v = i.splitBySpace()[1].split(",").map { it.substring(2).trim()}
        val s = abs(v[0].split("..")[0].toInt())

        if (s >= 50)
           acc
        else
        when(i.splitBySpace()[0].trim()){
            "on" ->
                Triple(acc.first + v.first(), acc.second + v[1], acc.third + v.last())
            "off" ->
                Triple(acc.first - v.first(), acc.second - v[1], acc.third - v.last())
            else ->
                throw Error()
        }
    }.let {
        println(it.first)
        println(it.second)
        println(it.third)
        println(it.first.size() * it.second.size() * it.second.size())
    }

}
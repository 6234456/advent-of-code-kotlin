data class Beacon(val x:Int, val y:Int, val z:Int){
    fun vector(other:Beacon): Triple<Int, Int, Int>{
        return Triple(x - other.x, y - other.y, z - other.z)
    }

    fun translate(triple: Triple<Int, Int, Int>):Beacon{
        return Beacon(x + triple.first, y + triple.second, z+ triple.third)
    }

    fun change():List<Beacon>{
        return v(this)
    }

    override fun toString(): String {
        return "[$x, $y, $z]"
    }
    companion object{
        fun of(list: List<Int>):Beacon = Beacon(list[0], list[1], list[2])

        fun of(triple: Triple<Int, Int, Int>):Beacon = Beacon(triple.first, triple.second, triple.third)

        private val sign: List<Triple<Int, Int, Int>> = sequence {
            listOf(1, -1).forEach { x ->
                listOf(1, -1).forEach { y ->
                    listOf(1, -1).forEach { z ->
                        yield(Triple(x, y, z))
                    }
                }
            }}.toList()

        private val v:(Beacon) -> List<Beacon> = {
            sign.map { s ->
                listOf(it.x * s.first, it.y * s.second, it.z * s.third).permutations().map { Beacon.of(it) }
            }.reduce { acc, beacons ->
                acc + beacons
            }

        }

        fun orientation(list: List<Beacon>):List<List<Beacon>>{
            return list.map(v).transpose()
        }
    }
}
fun main(){
    val l = Util.readLines("src/InputDay19.txt").splitWith { it.startsWith("--- scanner") }
        .map { it.filter { it.isNotEmpty() }.map { Beacon.of(it.split(",").map { it.trim().toInt() }) } }

    var notVisited:MutableSet<Int> = (1 until l.size).toMutableSet()
    val scanner: Array<Beacon> = Array(l.size) {Beacon(0,0,0)}

    fun compare(l1: List<Beacon>, l2:List<Beacon>, index:Int): List<Beacon>{
        if (! notVisited.contains(index))
            return l1

        val len1 = l1.size
        val len2 = l2.size
        val res: MutableMap<Triple<Int, Int, Int>, List<Pair<Beacon, Beacon>>> = mutableMapOf()

        (0 until len1).forEach {
            x ->
            (0 until len2).forEach {
                y ->
                 val k = l1[x].vector(l2[y])
                 res.putIfAbsent(k, listOf())
                 res[k] =  res[k]!! + listOf(Pair(l1[x], l2[y]))
            }
        }


        if (res.filterValues { it.size > 2 }.values.isNotEmpty()){
            notVisited.remove(index)

            val diff = res.filterValues { it.size > 2 }.keys.first()
            scanner[index] = Beacon.of(diff)
            return (l1 + res.filterValues { it.size < 3}.values.map { it.first().second.translate(diff) }).toSet().toList()
        }

        return l1
    }



    // linear transformation by  multi -1 / or move x y z
    // x y z  = -x-1
    // vector

    var q1 = l.first()

    while (notVisited.isNotEmpty()){
        q1 = l.foldIndexed(q1){
                index, acc, beacons ->
            if (notVisited.contains(index)){
               Beacon.orientation(beacons).fold(acc) {
                   ac, e ->
                   compare(ac, e, index)
               }
            }
            else
                acc
        }
    }
   // println(q1.sortedBy { it.x }.map { it.x })
    println(q1.size)

    //Q2
    println(
    sequence {
        (scanner.indices).forEach{
            val v1 = scanner[it]
            (scanner.indices).forEach{
                x ->
                if (x != it){
                    val v2 = scanner[x]
                    yield(kotlin.math.abs(v1.x - v2.x) + kotlin.math.abs(v1.y - v2.y)+ kotlin.math.abs(v1.z - v2.z))
                }
            }
        }
    }.maxOf { it }
    )

}
fun main(){
    val l = Util.readLines("src/InputDay06.txt")[0].split(",").map { it.trim().toInt() }

    fun dev(l:List<Int>):List<Int>{
        return l.foldIndexed(l){
                index, acc, i ->
            val res = acc.toMutableList()
            if (i == 0){
                res[index] = 6
                res += listOf(8)
            }else{
                res[index] -= 1
            }
            res
        }
    }

    fun timePassage(n:Int, list: List<Int>):List<Int>{
        var l = list
        repeat(n) {
            l = dev(l)
        }
        return l
    }


    val resMap = (0..8).associateWith { timePassage(80, listOf(it)).size }
    val resQ1 = l.groupBy{it}.mapValues { it.value.size }.entries.fold(0L){
        acc, entry ->
            acc + entry.value * resMap[entry.key]!!
    }

    println(resQ1)


    // Q2
    val resMap2 = (0..8).associateWith { timePassage( 16, listOf(it)) }
    val countFrequency = resMap2.mapValues { it.value.groupBy { it }.mapValues { it.value.size } }

    fun next16(map: MutableMap<Int, Long>): MutableMap<Int, Long>{
        return map.entries.fold(mutableMapOf<Int, Long>()){
                acc , entry ->
            countFrequency[entry.key]!!.entries.forEach {
                if (acc.contains(it.key))
                    acc[it.key] = acc[it.key]!! + it.value * entry.value
                else
                    acc[it.key] = it.value * entry.value
            }
            acc
        }
    }
    val input = l.groupBy{it}.mapValues { it.value.size.toLong() }

    fun timePassage2(n:Int, map0:MutableMap<Int, Long>):MutableMap<Int, Long>{
        var map = map0
        repeat(n) {
            map = next16(map)
        }
        return map
    }
    println(timePassage2(16, input.toMutableMap()).values.fold(0L){acc, l -> acc + l  })
}
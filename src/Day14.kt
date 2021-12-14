fun main(){
    val raw = Util.readLines("src/InputDay14.txt")
    val s = raw.first().trim()
    val cmd = raw.drop(2).map { it.split("->").map { it.trim() } }.map{
        it.first() to it.last()
    }.toMap()


    fun subStringList(string: String):List<String>{
        val l = string.length
        if (l <= 2)
            return listOf(string)

        return (0..l-2).map {
            val k = string.substring(it, it + 2)
            k
        }
    }

    fun insertString(targ: String, insert: List<String>): String{
        val targList = targ.toCharArray().map { it.toString() }

        return targList.dropLast(1).zip(insert).map { it.first + it.second }
            .reduce { acc, s ->  acc + s } + targList.last()
    }

    fun replace(string: String):String{
        return insertString(string, subStringList(string).map { cmd.getOrDefault(it, "") })
    }

    fun frequency(string: String): Map<String, Long>{
        return string.toCharArray().map { it.toString() }.fold(mutableMapOf()){
            acc, s ->
                acc[s] = acc.getOrDefault(s, 0) + 1
            acc
        }
    }

    fun repeatN(s:String, n:Int):String{
        var s0 = s
        repeat(n) {
            s0 = replace(s0)
        }
        return s0
    }

    fun repeat10(s:String):String{
        return repeatN(s, 10)
    }
    val f = frequency(repeat10(s)).values
   // println(frequency(s0))
    println(f.maxOf { it } - f.minOf { it })


    // Q2

    val m1: Map<String, Map<String, Long>> = cmd.mapValues {
        subStringList(repeatN(it.key, 1)).frequency()
    }

    //var resm = mutableMapOf<String, Map<String, Long>>()
    var resm = m1
    repeat(39) {
        resm = resm.mapValues {
            it.value.entries.fold(mapOf()) { acc, entry ->
                acc.mergeValue(
                    if (m1.containsKey(entry.key)) {
                        m1[entry.key]!!.mapValues { it.value * entry.value }
                    } else
                        listOf(entry.key to entry.value).toMap()
                )

            }
        }
    }

    subStringList(s).fold(mapOf<String, Long>()){
        acc, s0 ->
        acc.mergeValue(resm.getOrDefault(s0, mapOf(s0 to 1L)))
    }.apply {
       // println(this)
    }.entries.fold(mapOf<String, Long>()){
        acc, entry ->
        if (entry.key.first() == entry.key.last()){
            acc.mergeValue(mapOf(entry.key.first().toString() to entry.value * 2))
        }else
        acc.mergeValue(entry.key.toCharArray().map { it.toString() }.associateWith { entry.value })
    }.mergeValue(mapOf(s.first().toString() to 1L, s.last().toString() to 1L)).apply{
        // println(this)
    }.mapValues { it.value / 2 }.apply {
        println(this)
    }.values.let {
        println( it.maxOf { it } - it.minOf { it })
    }




}
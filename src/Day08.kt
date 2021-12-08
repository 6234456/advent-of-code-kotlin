fun main(){
    val l = Util.readLines("src/InputDay08.txt")
    val l0 = l.map { it.split("|")[1].trim().splitBySpace() }
    val map = mapOf<Int, Int>(
        2 to 1,
        3 to 7,
        4 to 4,
        7 to 8
    )

    val targ = map.keys

    println(l0.map { it.map { it.length }.count { targ.contains(it) } }.reduce { acc, i -> acc + i  })

    // 6-length
    // 6 !contains 1
    // 9 contains 4
    // another 6-length 0
    // 5-length
    // 9 contains 3
    // 6 contains 5
    // another 5-length 2

    val total = l.map { it.replace("|", "").trim().splitBySpace().map {
        it.toCharArray().sorted().joinToString("")
    } }


    fun parse(l:List<String>):Int{
        val res:Array<String> = Array<String>(10){""}
        res[8] = "abcdefg"

        /**
         * to check the completeness of the information
         *
        val check: ()->Boolean = {l.takeLast(4).all { s-> res.contains(s) }}
        val get: ()->Int = {l.takeLast(4).map { s-> res.indexOf(s) }.fold(0){
             i, acc ->
                acc * 10 + i
        } }
        **/

        l.forEach {
            val len = it.length
            if (map.containsKey(len)){
                res[map[len]!!] = it
            }
        }

        if (res[1].isEmpty() && res[4].isEmpty() && res[7].isEmpty())
            throw Exception("Not Enough Information")

        l.filter { it.length == 6 }.forEach {
            if(res[1].isNotEmpty() && ! it.mContains(res[1])){
                res[6] = it
            }else if (res[4].isNotEmpty() && it.mContains(res[4])){
                res[9] = it
            }else if(res[7].isNotEmpty() && ! it.mContains(res[7])){
                res[6] = it
            } else {
                res[0] = it
            }
        }

        l.filter { it.length == 5 }.forEach {
            if(res[1].isNotEmpty() && it.mContains(res[1])){
                res[3] = it
            }else if (res[9].isNotEmpty() && !res[9].mContains(it)){
                res[2] = it
            }else if(res[6].isNotEmpty() && res[6].mContains(it)){
                res[5] = it
            }
        }

        return l.takeLast(4).map { s-> res.indexOf(s) }.fold(0){acc, i -> acc * 10 + i }
    }

    println(total.map { parse(it) }.reduce { acc, i -> acc + i })
}

fun String.mEquals(s: String): Boolean = (this - s).isEmpty() && (s-this).isEmpty()
fun String.mContains(s:String): Boolean = this.toCharArray().intersect(s.toCharArray().toSet()).joinToString("").mEquals(s)
operator fun String.minus(s: String):String = this.toCharArray().subtract(s.toCharArray().toSet()).joinToString("")
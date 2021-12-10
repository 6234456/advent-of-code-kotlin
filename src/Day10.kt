import java.util.*

fun main(){
    val l = Util.readLines("src/InputDay10.txt").map { it.toCharArray().map {
        it.toString()
    } }

    val m = mapOf<String,String>(
        ">" to "<",
        "]" to "[",
        "}" to "{",
        ")" to "("
    )

    fun judgeStr(strings: List<String>):String{
        val q = LinkedList<String>()

        strings.forEach {
            string ->
            if (m.containsKey(string)){
                if (q.isNotEmpty()){
                    if (q.peek() == m[string]!!)
                        q.remove()
                    else return string
                }else
                    return string
            }else{
                q.push(string)
            }
        }

        return ""
    }

    println( l.map {
       judgeStr(it)
    }.filter { it.isNotEmpty() }.fold(0){
        acc, s ->
        acc + when(s){
            "}" -> 1197
            ")" -> 3
            "]" -> 57
            ">" -> 25137
            else -> throw Exception("Error")
        }
    })

    // Q2

    val m2 = m.map { it.value to it.key }.toMap()

    fun judgeStr2(strings: List<String>):List<String>{
        val q = LinkedList<String>()

        strings.forEach {
                string ->
            if (m.containsKey(string)){
                if (q.isNotEmpty()){
                    if (q.peek() == m[string]!!)
                        q.remove()
                    else return listOf()
                }else
                    return listOf()
            }else{
                q.push(string)
            }
        }

        val res = mutableListOf<String>()

        while (q.isNotEmpty()){
            res.add(m2[q.remove()]!!)
        }

        return res
    }

    fun val2(string: String): Int = when(string){
        ")" -> 1
        "]" -> 2
        "}" -> 3
        ">" -> 4
        else -> throw Exception("Error")
    }

    l.map {
        judgeStr2(it)
    }.filter { it.isNotEmpty() }.map{
        it.fold(0L){
                acc, s ->
            acc * 5 + val2(s)
        }
    }.sorted().apply {
        //println(this)
      //  println(this[(this.size - 1)/2])
        println(this[this.size/2])
    }
}
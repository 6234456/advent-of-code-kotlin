/**
 *  graph
 *  search path with adjacent list
 */

fun main(){
    val l = Util.readLines("src/InputDay12.txt").map { it.split("-").map {
        it.trim()
    }}

    val m1 =  l.groupBy { it.first() }.mapValues { it.value.map { it.last() }}
    val m2 = l.groupBy { it.last() }.mapValues { it.value.map { it.first() } }

    val adj = m1.merge(m2).mapValues {
        if (it.key != "start" && it.key != "end"){
            it.value.toMutableSet().apply {
                this.removeIf {
                    it == "start"
                }
            }
        }else{
            it.value.toSet()
        }
    }
    val startsWithLowerCase: (String) -> Boolean = { """^[a-z]*""".toRegex().matches(it) }

    fun dfs(start: String = "start", visited:Set<String> = setOf()):Int{
        if (start == "end")
            return 1

        return adj[start]!!.fold(0){
            acc, s ->
            if (visited.contains(s)){
                acc
            }else{
                acc + dfs(s, visited + if (startsWithLowerCase(s)) setOf(s) else setOf())
            }
        }
    }

    println(dfs())


    fun dfs2(start: String = "start", visited:Map<String, Int> = mapOf(), depleted:Boolean = false):Int{
        if (start == "end")
            return 1

        return adj[start]!!.fold(0){
                acc, s ->
            val v = visited.getOrDefault(s, 0)
            if (v == 2){
                acc
            }else if(v == 1){
                 if (! depleted){
                     acc + dfs2(s, visited + (s to if (startsWithLowerCase(s)) 1 else 0), true)
                 }else{
                     acc
                 }
            }else{
                acc + dfs2(s, visited + (s to if (startsWithLowerCase(s)) 1 else 0), depleted)
            }
        }
    }

    println(dfs2())
}
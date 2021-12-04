fun main() {
    // Q1
    val l = Util.readLines("src/InputDay04.txt")
    val moves = l[0].split(",").map { it.toInt() }

    val chunkedSize = 5
    val boards = l.drop(2).chunked(chunkedSize + 1).map { it.dropLast(1).map { it.trim().split("""\s+""".toRegex()).map { it.toInt() } } }
    val win: (List<List<Int>>, List<Int>) -> Boolean = {
            b, res ->
            b.any { it.all { res.contains(it) } } || b.foldIndexed(listOf<List<Int>>()){
                index, acc, list ->
                    if (index == 0)
                        list.map { listOf(it) }
                    else
                        list.mapIndexed { index0, i -> acc[index0] + listOf(i)  }
            }.any { it.all { res.contains(it) } }
    }

    val paint: (List<List<Int>>, List<Int>) -> Int = {
        l, res ->
           l.fold(listOf<Int>()){
               acc, list ->  acc + list
           }.filter { !res.contains(it) }.fold(0){
               acc, i ->
               acc + i
           } * res.last()
    }

    moves.forEachIndexed { index, _ ->
        for (i in boards.indices) {
            val v = boards[i]
            val res = moves.subList(0, index+1)
            if(win(v, res)){
                //println(paint(v, res))
                return@forEachIndexed
            }
        }
    }

    //Q2
    val steps = boards.map {
        for (i in moves.indices){
            val res = moves.subList(0, i+1)
            if (win(it, res))
                return@map i
        }
        return@map -1
    }

    val maxStep = steps.maxOf { it }

    val boardIndex = steps.indexOf(maxStep)

    println(paint(boards[boardIndex], moves.subList(0,maxStep + 1)))

}

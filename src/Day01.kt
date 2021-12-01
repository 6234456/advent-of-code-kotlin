fun main() {
    // Q1
    val l = Util.readLines("src/InputDay01.txt").map { it.toInt() }
    // ([1] [0])
    val increases = (l.drop(1).zip(l.dropLast(1)))
        .map { if(it.first - it.second > 0) 1 else 0 }
        .fold(0){ acc, i ->  acc + i }

    println("Q1: total increases $increases")

    // Q2
    val l1 = l.mapIndexed { index, i -> if (index <= l.size - 3) i + l[index+1] + l[index+2] else 0}.dropLast(2)

    val increases1 = (l1.drop(1).zip(l1.dropLast(1)))
        .map { if(it.first - it.second > 0) 1 else 0 }
        .fold(0){ acc, i ->  acc + i }

    println("Q2: total increases $increases1")
}

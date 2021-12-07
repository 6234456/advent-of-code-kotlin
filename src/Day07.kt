import kotlin.math.abs

fun main() {
    val l = Util.readLines("src/InputDay07.txt")[0].split(",").map { it.trim().toInt() }.sorted()//.groupBy { it }.mapValues { it.value.size }

    // Q1
    println(
            l.groupBy { it }.mapValues { it.value.count() }.keys.map {
                            l.fold(0L){acc, i -> acc + abs(it - i)
                        }
            }.minOf { it }
    )


    //Q2
    println(
            l.groupBy { it }.mapValues { it.value.count() }.keys.map {
                l.fold(0L){acc, i ->
                    val v = abs(it - i)
                    acc + (v + 1) * v / 2
                }
            }.minOf { it }
    )

}
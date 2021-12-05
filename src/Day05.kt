import java.lang.Integer.max
import java.lang.Integer.min

fun main() {
    // Q1
    val dimension = 1000
    val l = Util.readLines("src/InputDay05.txt")
    val status = l.map { it.trim().split("->").map { it.split(",").map { it.trim().toInt() } } }
        .filter {
            it[0][1] == it[1][1] || it[0][0] == it[1][0]
        }.fold(Array(dimension){IntArray(dimension){0}}){
            acc, list ->
            val y0 = list[0][1]
            val y1 = list[1][1]
            val x0 = list[0][0]
            val x1 = list[1][0]
            if (y0 == y1){
                for(i in min(x0,x1)..max(x0,x1)){
                    acc[i][y0] += 1
                }
            }else{
                for(i in min(y0,y1)..max(y0,y1)){
                    acc[x0][i] += 1
                }
            }
            acc
        }.fold(0){
            acc, ints ->
                acc + ints.count { it > 1 }
        }

        println(status)


    // Q2
    val status0 = l.map { it.trim().split("->").map { it.split(",").map { it.trim().toInt() } } }
        .fold(Array(dimension){IntArray(dimension){0}}){
                acc, list ->
            val y0 = list[0][1]
            val y1 = list[1][1]
            val x0 = list[0][0]
            val x1 = list[1][0]
            if (y0 == y1){
                for(i in min(x0,x1)..max(x0,x1)){
                    acc[i][y0] += 1
                }
            }else if (x0 == x1){
                for(i in min(y0,y1)..max(y0,y1)){
                    acc[x0][i] += 1
                }
            }else{
                //diagonal
                (if (x0 > x1) (x0.downTo(x1)) else (x0.rangeTo(x1))).zip(
                    if (y0 > y1) (y0.downTo(y1)) else (y0.rangeTo(y1))
                ).forEach {
                    acc[it.first][it.second] += 1
                }
            }
            acc
        }.fold(0){
                acc, ints ->
            acc + ints.count { it > 1 }
        }

    println(status0)
}
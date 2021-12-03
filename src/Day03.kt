import kotlin.math.pow

fun main() {
    val cmds = Util.readLines("src/InputDay03.txt").map { it.toCharArray().map { x -> x.toString().toInt() } }
    val l = cmds.size
    val res = cmds
        .reduce { acc, list ->  acc.zip(list).map { it.first + it.second }}
        .map { if(it > l * 0.5) 1 else 0 }
        .reversed()
        .foldIndexed(0){
            index, acc, i ->
                acc + i * 2.0.pow(index).toInt()
        }

    val res1 = cmds
        .reduce { acc, list ->  acc.zip(list).map { it.first + it.second }}
        .map { if(it > l * 0.5) 0 else 1 }
        .reversed()
        .foldIndexed(0){
                index, acc, i ->
            acc + i * 2.0.pow(index).toInt()
        }

    println("Q1: ${res * res1}")


    // Q2

    fun binaryToDec(input: List<Int>):Int {
        return input.reversed()
            .foldIndexed(0){
                    index, acc, i ->
                acc + i * 2.0.pow(index).toInt()
            }
    }

    fun judge(input: List<List<Int>>, position:Int = 0, mostCommon:Boolean = true):Int{
        if(input.size == 1 || position == input.first().size)
            return binaryToDec(input.first())

        val l1 = input.size * 0.5

        val target = input.reduce { acc, list ->  acc.zip(list).map { it.first + it.second }}
            .map {
                if (mostCommon){
                    if(it >= l1) 1 else 0
                }else{
                    if(it < l1) 1 else 0
                }
            }[position]

        return judge(input.filter { it[position] == target}, position + 1, mostCommon)
    }

    println("Q2: ${judge(cmds) * judge(cmds, mostCommon = false)}")

}
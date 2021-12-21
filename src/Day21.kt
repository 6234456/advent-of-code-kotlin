import java.math.BigInteger
// Q2 with dp
fun main(){
    val p1 = 7
    val p2 = 10
    val moves = generateSequence(6) {
        it + 9
    }

    fun score(pos:Int, move:Int):Int{
        val v = (pos + move).mod(10)
        return when(v){
            0 -> 10
            else -> v
        }
    }
    fun move(p1:Int, p2:Int){

        fun judge(sequence: Sequence<Int>, p1: Int): Pair<Int, Int>{
            var cnt1 = 0
            var t1 = 0
            var p01 = p1
            sequence.forEach {
                if (t1 >= 1000){
                    return cnt1 to t1
                }
                val v = score(p01, it)
                cnt1 += 1
                p01 = v
                t1 += v
            }
            throw Exception("Error")
        }

        fun scoreAt(sequence: Sequence<Int>, p1: Int, n:Int):Int{
            var p01 = p1
           return sequence.take(n).fold(0){
               acc, i ->
                val v = score(p01, i)
               p01 = v
               acc + v
           }
        }


        val j1 = judge(moves.filterIndexed{i,_ -> i % 2 == 0}, p1)
        val j2 = judge(moves.filterIndexed{i,_ -> i % 2 == 1}, p2)
        val p = if (j1.first < j2.first){
            ((j1.first - 1) * 6 + 3)* scoreAt(moves.filterIndexed{i,_ -> i % 2 == 1}, p2, j1.first - 1)
        }else
            ((j2.first - 1) * 6 + 3)* scoreAt(moves.filterIndexed{i,_ -> i % 2 == 0}, p2, j2.first - 1)


        println(j1)
        println(j2)
        println((j1.first - 1) * 6)
        println(scoreAt(moves.filterIndexed{i,_ -> i % 2 == 1}, p2, j1.first - 1))
        println(p)
    }

    // move(p1, p2)//
    // matrix of score starting from the point 1 to 10 with
    val v = sequence<Int> {
        (1..3).forEach {
            x ->
            (1..3).forEach {
                y ->
                (1..3).forEach {
                    z ->
                    yield(x+y+z)
                }
            }
        }
    }.toList().frequency()

    // score to frequency
    val m = (1..10).map { x-> v.map { score(x, it.key) to it.value.toBigInteger() }.toMap()}
    // after certain steps the target reached or exceeded
    fun countPossibilities(startPos: Int, steps: Int, target: Int): BigInteger{
        val branch = m[startPos-1]
        if (steps == 1)
            return branch.getOrDefault(target, BigInteger.ZERO)

        return branch.keys.fold(BigInteger.ZERO){
            acc, i ->
            acc + countPossibilities(i, steps - 1, target - i) * branch[i]!!
        }
    }

    fun cnt(startPos:Int, target: Int = 21, steps: Int = 10): Array<Array<Array<BigInteger>>> {
        val res: Array<Array<Array<BigInteger>>> = Array(steps + 1){
            Array(target + 1){
                Array(10){
                    BigInteger.ZERO
                }
            }
        }

        val branch = m[startPos-1]
       branch.forEach{
           res[1][it.key][it.value.toInt()-1] = it.value
       }

        (1 until steps).forEach {
            x ->
            (1 until target).forEach{
               y ->
                (1..10).forEach {
                    z ->
                if (res[x][y][z-1] != BigInteger.ZERO)
                    m[z-1].forEach{
                        val k = it.key + y
                        res[x+1][if (k > 21) 21 else k][it.key-1] += it.value * res[x][y][z-1]
                    }
                }
            }
        }
        return res
    }


    fun countLT(startPos: Int, steps: Int, target: Int): BigInteger{
        return (0 until target).fold(BigInteger.ZERO){
            acc, i ->
            acc + countPossibilities(startPos, steps, i)
        }
    }



    val B3 = BigInteger.ONE + BigInteger.TWO
    val myPow : (BigInteger, Int) -> BigInteger = {
        bigInteger, i ->
        (1..i).fold(BigInteger.ONE){
            acc, _ ->
            acc * bigInteger
        }
    }

fun q2(p1: Int, p2: Int) {
    val l1 = (1..10).map { countPossibilities(p1, it, 21) }
    val l2 = (1..10).map { countPossibilities(p2, it, 21) }
    val l01 = (1..10).map { countLT(p1, it, 21) }
    val l02 = (1..10).map { countLT(p2, it, 21) }

    val v1 = l1.drop(1).zip(l02).dropLast(1).fold(l1.first()) { acc, pair ->
        acc + pair.first * pair.second
    }
    println(v1)
}
    val l1 = (1..10).map { countPossibilities(4, it, 21) }
    println(l1.reduce { acc, bigInteger -> acc + bigInteger })

    // q2(4,8)

    println((21..50).fold(BigInteger.ZERO) { acc, e ->
      acc + countPossibilities(4, 3,e)
    })

    val c = cnt(4, steps = 15).fold(BigInteger.ZERO){
        acc, arrays ->
            acc + arrays[21].reduce{
                acc0: BigInteger, bigInteger: BigInteger ->
                    acc0 + bigInteger
            }
    }


    val c1 = cnt(4, steps = 15).map { it[21].reduce { acc, bigInteger -> acc + bigInteger } }
    val c2 = cnt(8, steps = 15).map { it[21].reduce { acc, bigInteger -> acc + bigInteger } }
    val c10 = (c1.drop(3))
    val c20 = (c2.dropLast(1).mapIndexed { index, bigInteger -> myPow(B3, index * 3) - bigInteger }.drop(1))

    println(c10.zip(c20).fold(BigInteger.ZERO){ acc, pair -> acc + pair.first* pair.second })



}
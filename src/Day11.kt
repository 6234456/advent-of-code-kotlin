fun main() {
    val l = Util.readLines("src/InputDay11.txt").map {
        it.toCharArray().map {
            it.toString().toInt()
        }.toMutableList()
    }

    fun trigger(i:Int, j:Int){
        l[i][j] += 1

        if (l[i][j] == 10){
            if (i > 0){
                trigger(i-1, j)
                if (j > 0){
                    trigger(i-1, j-1)
                }
                if (j < 9){
                    trigger(i-1, j+1)
                }
            }
            if (i < 9){
                trigger(i+1, j)
                if (j < 9){
                    trigger(i+1, j+1)
                }
                if (j > 0){
                    trigger(i+1, j-1)
                }
            }
            if (j > 0){
                trigger(i, j-1)
            }
            if (j < 9){
                trigger(i, j+1)
            }
        }


    }

    fun increaseBy1(){
        (0..9).forEach {
            i -> (0..9).forEach {
                j ->
                    trigger(i,j)
            }
        }
        (0..9).forEach {
                i -> (0..9).forEach {
                    j ->
                if (l[i][j] > 9){
                    l[i][j] = 0
                }
            }
        }
    }

    println(
    (1..100).fold(0L){
        acc, _ ->
            increaseBy1()
            acc + l.fold(0){
                acc0, ints ->
                    acc0 + ints.count { it == 0 }
            }
    })

    // Q2
    var cnt = 100
    while (true){
        if (l.fold(0){
                    acc0, ints ->
                acc0 + ints.count { it == 0 }
            } == 100)
            break

        increaseBy1()
        cnt += 1
    }

    println(cnt)




}
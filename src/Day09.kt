fun main(){
    val l = Util.readLines("src/InputDay09.txt").map { it.toCharArray().map {
        it.toString().toInt()
    } }

    val r = l.size - 1
    val c = l.first().size - 1

    val res1 = l.mapIndexed { i, ints ->
        ints.mapIndexed { j, e ->
            if (i > 0 && l[i-1][j] <= e)
                return@mapIndexed 0

            if (j > 0 && l[i][j-1] <= e)
                return@mapIndexed 0

            if (i < r && l[i+1][j] <= e)
                return@mapIndexed 0

            if (j < c && l[i][j+1] <= e)
                return@mapIndexed 0

            return@mapIndexed e + 1
        }.reduce { acc, i -> acc + i  }
    }.reduce { acc, i -> acc + i  }

    println(res1)

    // Q2
    val l2 = l.map { it.map { if (it < 9) 0 else -1 }.toMutableList() }.toMutableList()

   fun paint(x:Int, y:Int, order:Int){
       if (l2[y][x] == 0){
           l2[y][x] = order
           if(x < c && l2[y][x+1] == 0){
               paint(x+1, y, order)
           }
           if(x > 0 && l2[y][x-1] == 0){
               paint(x-1, y, order)
           }
           if(y >0 && l2[y-1][x] == 0){
               paint(x, y-1, order)
           }
           if (y < r && l2[y+1][x] == 0){
               paint(x, y+1, order)
           }
       }
   }

    var order:Int = 0

    (0..r).forEach { i->
        (0..c).forEach { j->
            val v = l2[i][j]
            if (v == 0){
                order += 1
                paint(j,i,order)
            }
        }
    }

    println(l2.fold(listOf<Int>()){acc, ints -> acc + ints}.filter { it > 0 }.groupBy { it }
        .values.map { it.size }.sorted().takeLast(3).apply { println(this) }.reduce { acc, i -> acc * i  })






}
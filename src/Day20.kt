import java.awt.DisplayMode

fun main(){
    val l = Util.readLines("src/InputDay20.txt").splitWith { it.isEmpty()}
    val ref = l.first().map {  it.toCharArray().map {
        when (it.toString()) {
            "#" -> 1
            "." -> 0
            else -> throw Exception("Error")
        }
        }
    }.reduce { acc, ints ->  acc + ints }


    val display = l.last().map {
        it.toCharArray().map {
            when (it.toString()) {
                "#" -> 1
                "." -> 0
                else -> throw Exception("Error")
            }
        }
    }

    fun trimPixelV(display: List<List<Int>>, sym:Int = 0) : List<List<Int>> {
        val v = sym
       return display.drop(
        (0 until display.size).first { display[it].any { it == v } }
        ).dropLast(
            display.size - 1 - (display.size-1 downTo 0).first{ display[it].any{ it == v}}
        )
    }

    fun trimPixel(display: List<List<Int>>, sym:Int = 0): List<List<Int>> {
        return  trimPixelV(trimPixelV(display, sym).transpose(), sym).transpose()
    }


    fun enhance(display: List<List<Int>>, sym:Int = 0) : List<List<Int>> {
        val y = display.first().size

        fun darkRows(n: Int = 2, cnt: Int = y):List<List<Int>> = (1..n).map { (1..cnt).map { sym } }
        fun darkCol(n:Int):List<Int> = (1..n).map { sym }

        val margin = 2

        // add black frame
        val area = (darkRows(margin) + display + darkRows(margin)).map { darkCol(margin) + it + darkCol(margin) }
        val res: Array<IntArray> = Array(area.size - 2){ IntArray(area.first().size - 2) }

            (1..area.size - 2).forEach { r ->
                (1..area.first().size - 2).forEach { c ->
                    val index =
                        (area[r - 1].subList(c - 1, c + 2) + area[r].subList(c - 1, c + 2) + area[r + 1].subList(
                            c - 1,
                            c + 2
                        )).reduce { acc, i -> acc * 2 + i }
                    res[r-1][c-1] = ref[index]
                }
            }

        return trimPixel(res.map { it.toList() }.toList(), sym)
    }

    fun enhanceN(display:List<List<Int>>, n:Int, sym:Int = 1):List<List<Int>>{
        var d = display
        (1..n).forEach {
            d = if (it.mod(2) == 1)
              enhance(d)
            else
                enhance(d, sym)
        }
        return d
    }

    val q1  = enhanceN(display, 2, 1).fold(0){acc, ints ->  acc + ints.reduce { acc, i -> acc + i  } }
    println(q1)

    val q = enhanceN(display,50, 1).fold(0){acc, ints ->  acc + ints.reduce { acc, i -> acc + i  } }
    println(q)
}
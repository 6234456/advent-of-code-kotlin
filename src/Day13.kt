import java.lang.Error

data class Dot(val x: Int, val y: Int){
    override fun equals(other: Any?): Boolean {
        return other is Dot && other.x == this.x && other.y == this.y
    }

    override fun toString(): String {
        return "[$x, $y]"
    }

    companion object{
        fun printDots(dots:Set<Dot>){
            val x = dots.maxOf { it.x }
            val y = dots.maxOf { it.y }
            (0..y).forEach {
                y0 ->
                (0..x).forEach {
                    x0->
                    if (dots.contains( Dot(x0, y0)))
                        print("*")
                    else
                        print("-")
                }
                println()
            }
        }
    }
}


fun main(){
    val reg = """^fold.+""".toRegex()
    val num = """^\d+.+""".toRegex()
    val raw = Util.readLines("src/InputDay13.txt")
    val cmd = raw.filter { reg.matches(it) }.map { it.splitBySpace().last() }
    val dots = raw.filter { num.matches(it) }.map { it.trim().split(",").map { it.trim().toInt() }.let {
        Dot(it.first(),it.last())
    } }.toSet()

    val x = dots.maxOf { it.x }
    val y = dots.maxOf { it.y }

    fun foldCmd(c: String, dots:Set<Dot>):Set<Dot>{
        val v = c.trim().split("=")
        val value = v[1].trim().toInt()
        return when(v[0]){
            "x" -> dots.map {
                val x0 = it.x
                if (x0 > value)
                    Dot(value * 2 - x0, it.y)
                else
                    it
            }
            "y" -> dots.map {
                val y0 = it.y
                if (y0 > value)
                    Dot(it.x, value * 2 - y0)
                else
                    it
            }
            else -> throw Error("Error")
        }.filter { it.x >= 0 && it.y >= 0 }.toSet()
    }

    println(foldCmd(cmd[0], dots).size)

    Dot.printDots(cmd.fold(dots){
        acc, s ->
            foldCmd(s, acc)
    })


}


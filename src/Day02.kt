data class Position(var x: Long = 0, var y: Long = 0, var depth: Long = 0){
    fun move(cmd:String){
        val cmds = cmd.split(" ")
        when(cmds[0]){
            "forward" -> x += cmds[1].toLong()
            "down" -> y += cmds[1].toLong()
            "up" -> y -= cmds[1].toLong()
            else -> throw Exception("unknown cmd:$cmd   =>  ${cmds[1]}")
        }
    }

    fun moveWithDepth(cmd:String){
        val cmds = cmd.split(" ")
        val v = cmds[1].toLong()
        when(cmds[0]){
            "forward" -> {
                x += v
                depth += v * y
            }
            "down" -> y += v
            "up" -> y -= v
            else -> throw Exception("unknown cmd:$cmd   =>  ${cmds[1]}")
        }
    }


}

fun main() {

    val cmds = Util.readLines("src/InputDay02.txt")
    // Q1
    val p = Position(0,0)
    cmds.forEach{
        p.move(it)
    }

    println("Q1: ${p.x * p.y}")

    val p1 = Position()
    cmds.forEach{
        p1.moveWithDepth(it)
    }

    println("Q1: ${p1.x * p1.depth}")

}

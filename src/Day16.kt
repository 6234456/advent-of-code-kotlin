import java.math.BigInteger

fun main(){
    val s = """
        0 = 0000
        1 = 0001
        2 = 0010
        3 = 0011
        4 = 0100
        5 = 0101
        6 = 0110
        7 = 0111
        8 = 1000
        9 = 1001
        A = 1010
        B = 1011
        C = 1100
        D = 1101
        E = 1110
        F = 1111
    """.trimIndent()

    val code = s.trim().split("\n").map { it.split(" = ").let { it[0] to it[1] } }.toMap()

    val input = Util.readLines("src/InputDay16.txt")[0].toCharArray().map {
        code[it.toString()]
    }.joinToString("")

    fun bin2dec(string: String):Int = string.toCharArray().map { it.toString().toInt() }.fold(0){
        acc, o ->
            acc * 2 + o
    }

    fun bin2decBig(string: String):BigInteger = string.toCharArray().map { it.toString().toInt() }.fold(BigInteger.ZERO){
            acc, o ->
        acc * BigInteger.TWO + o.toBigInteger()
    }

    fun getVersion(string: String): Int = bin2dec(string.substring(0,3))

    fun parseLiteral(string: String, start: Int):String{
        if(string.substring(start, start+1) == "0")
            return string.substring(start+1, start+5)

        return string.substring(start+1, start+5) + parseLiteral(string, start+5)
    }

    fun lenLiteral(string: String, start:Int):Int {
        var v = start + 6
        while(string.substring(v, v+1) == "1"){
            v += 5
        }
        return v + 5
    }

    fun toNextLen(int: Int):Int{
        return  (4  - int.mod(4)) + int
    }

    fun isLiteral(string: String):Boolean = bin2dec(string.substring(3,6)) == 4



    fun packetLen(string: String, affix: Boolean = true):Int {
        val v = when(bin2dec(string.substring(3,6))){
            4 -> lenLiteral(string, 0)
            else -> {
                val v = when(string.substring(6,7)){
                    "0" -> 15
                    else -> 11
                }
                if (v == 15)
                    bin2dec(string.substring(7, 7 + v)) + 7 + v
                else{
                    var cnt = 7+v
                    val totalCnt = bin2dec(string.substring(7, 7+v))
                    repeat(totalCnt){
                        cnt += packetLen(string.substring(cnt),false)
                    }
                    cnt
                }
            }
        }
        return if (affix) toNextLen(v) else v
    }

    fun parseOperator(string: String, start:Int = 0): List<String> {
        val v = when(string.substring(6+start,7+start)){
            "0" -> 15
            else -> 11
        }
        val ends = start + packetLen(string.substring(start), false)
        var l = mutableListOf<String>(string.substring(start, ends))
        var cnt = 7 + v + start
        while (cnt < ends){
            val len = packetLen(string.substring(cnt), false)
            l.add(string.substring(cnt, cnt + len))
            cnt += len
        }

        return l
    }

    fun paresV(input:String ):Int{
        var ls = parseOperator(input).drop(1)
        return getVersion(input) + ls.fold(0){
           acc, s ->
            if (isLiteral(s))
                acc + getVersion(s)
            else
                acc + paresV(s)
        }
    }

    fun operates(input: String): BigInteger {
        if (isLiteral(input))
            return bin2decBig(parseLiteral(input,6))

        val v = parseOperator(input)
        val value = v.drop(1).map { operates(it) }

        return when(bin2dec(v[0].substring(3,6))){
            0 -> value.reduce { acc, i -> acc + i  }
            1 -> value.reduce { acc, i -> acc * i  }
            2 -> value.minOf { it }
            3 -> value.maxOf { it }
            5 -> if(value.first() > value.last()) BigInteger.ONE else BigInteger.ZERO
            6 -> if(value.first() < value.last()) BigInteger.ONE else BigInteger.ZERO
            7 -> if(value.first() == value.last()) BigInteger.ONE else BigInteger.ZERO
            else -> throw Error()
        }
    }

    println(parseOperator(input))
    println(packetLen(input,false))
    println(paresV(input))

    println(operates(input))

}
import java.lang.Integer.min

fun main(){
    val grid = Util.readLines("src/InputDay15.txt").map { it.trim().toCharArray().map { it.toString().toInt()} }
    val x = grid.size
    val y = grid[0].size

    val dp = (1..x).map { (1..y).map { 0 }.toMutableList() }

    dp[0][1] = grid[0][1]
    dp[1][0] = grid[1][0]

    (2 until x).forEach {
        dp[it][0] = dp[it-1][0] + grid[it][0]
    }
    (2 until y).forEach{
        dp[0][it] = dp[0][it-1] + grid[0][it]
    }

    (1 until x).forEach { i ->
        (1 until y).forEach { j ->
            dp[i][j] = min(dp[i][j-1] , dp[i-1][j]) + grid[i][j]
        }
    }

    println(dp[x-1][y-1])
}
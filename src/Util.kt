import java.net.HttpCookie
import java.net.URI
import java.net.URL
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.file.Paths

class Util {
    companion object{
        fun readLinesFromURL(url: String):List<String>{
            val client = HttpClient.newBuilder().build()
            val request = HttpRequest.newBuilder().uri(URI.create(url)).build()

            val response = client.send(request, HttpResponse.BodyHandlers.ofString())

            return response.body().lines()
        }

        fun readLines(file: String):List<String>{
            return Paths.get(file).toFile().readLines()
        }
    }
}

fun <T>Iterable<Iterable<T>>.transpose():List<List<T>> = this.foldIndexed(listOf<List<T>>()){
        index, acc, list ->
    if (index == 0)
        list.map { listOf(it) }
    else
        list.mapIndexed { index0, i -> acc[index0] + listOf(i)  }
}

fun String.splitBySpace():List<String> = this.trim().split("""\s+""".toRegex())
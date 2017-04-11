import java.io._
import java.net.ServerSocket
import java.util.{Date, Locale}

import scala.collection.mutable.ArrayBuffer

/**
  * @author tominaga
  */
object HTTPServer {
  def main(args: Array[String]): Unit = {
    val serverSocket = new ServerSocket(8000)
    while (true) {
      val socket = serverSocket.accept()
      val url: String = extractUrl(socket.getInputStream)
      val response: String = buildResponse(url)
      socket.getOutputStream.write(response.getBytes)
      socket.close()
    }
  }

  private def extractUrl(input: InputStream): String = {
    val br = new BufferedReader(new InputStreamReader(input))
    val reqestLine = br.readLine()
    val requestArray = reqestLine.split(" ")
    requestArray(1)
  }

  private def buildResponse(url: String): String = {
    println(url)
    val filePath = "../src" + url
    val file = new File(filePath)
    println(file)
    println(file.isFile)
    println(file.exists())
    val contentType = "Content-Type: image/png"
    val date = "%ta, %<td %<tb %<tY %<tT %<tz" formatLocal(Locale.ENGLISH, new Date)
    val contentLength = "Content-Length: " + "0"
    val acceptRange = "Accept-Ranges: bytes"
    
    if (!file.exists() || !file.isFile) {
      val response = new ArrayBuffer[String]
      response += "HTTP/1.1 404 Not Found"
      response += contentType
      response += date
      return response.toList.mkString("\n")
    }

    val fileBr = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"))
    val body = fileBr.lines().toArray.mkString("\n")
    val response = new ArrayBuffer[String]
    response += "HTTP/1.1 200 OK"
    response += contentType
    response += contentLength
    response += acceptRange
    response += date
    response += ""
    response += body
    response.toList.mkString("\n")
  }
}

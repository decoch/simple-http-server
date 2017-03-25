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
    val filePath = "./src" + url
    val file = new File(filePath)
    val contentType = "Content-Type: text/html"
    val date = "%ta, %<td %<tb %<tY %<tT %<tz" formatLocal(Locale.ENGLISH, new Date)

    if (!file.exists() || !file.isFile) {
      val response = new ArrayBuffer[String]
      response += "HTTP/1.1 404 Not Found"
      response += contentType
      response += date
      return response.toList.mkString("\n")
    }

    val fileBr = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)))
    val body = fileBr.lines().toArray.mkString("\n")
    val response = new ArrayBuffer[String]
    response += "HTTP/1.1 200 OK"
    response += contentType
    response += date
    response += ""
    response += body
    response.toList.mkString("\n")
  }
}

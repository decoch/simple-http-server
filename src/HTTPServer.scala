import java.net.ServerSocket
import java.util.{Date, Locale}

import scala.collection.mutable.ArrayBuffer

/**
  * @author tominaga
  */
object HTTPServer {
  def main(args: Array[String]): Unit = {
    val socket = new ServerSocket(8000).accept()
    val out = socket.getOutputStream
    val response = new ArrayBuffer[String]
    response += "HTTP/1.1 200 OK"
    response += "Content-Type: text/html"
    response += "%ta, %<td %<tb %<tY %<tT %<tz" formatLocal(Locale.ENGLISH, new Date)
    response += "Content-Length: 0"
    val result = response.toList.mkString("\n")
    out.write(result.getBytes)
  }
}

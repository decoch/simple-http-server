import java.io.{BufferedReader, InputStreamReader}
import java.net.ServerSocket

import scala.collection.mutable.ArrayBuffer

/**
  * @author tominaga
  */
object HTTPServer {
  def main(args: Array[String]): Unit = {
    val socket = new ServerSocket(8000).accept()
    val br = new BufferedReader(new InputStreamReader(socket.getInputStream))
    var line = ""
    val response = new ArrayBuffer[String]
    while ({ line = br.readLine(); line != null && !line.isEmpty }) {
      response += line
    }
    val result = response.toList.mkString("\n")
    socket.getOutputStream.write(result.getBytes)
  }
}

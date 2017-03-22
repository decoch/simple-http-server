import java.net.ServerSocket

/**
  * @author tominaga
  */
object HTTPServer {
  // 使いそうなパッケージ
  // /Library/Java/JavaVirtualMachines/jdk1.8.0_60.jdk/Contents/Home/src.zip!/java/net/ServerSocket.java
  // 参考にしたURL
  // https://twitter.github.io/scala_school/concurrency.html
  def main(args: Array[String]): Unit = {
    val serverSocket = new ServerSocket(8000)
    val socket = serverSocket.accept()
    val out = socket.getOutputStream
    val response = "Hello Request!"
    out.write(response.getBytes)
    println(response)
  }
}

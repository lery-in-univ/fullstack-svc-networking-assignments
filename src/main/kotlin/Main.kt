fun main(args: Array<String>) {
    if (args.isEmpty()) {
       println("Argument is empty, please pass a arguments client or server")
       return
    }

    val mode = args[0]
    when {
        mode == "server" -> runServer()
        mode == "client" -> runClient()
        else -> println("Invalid arguments: ${mode}")
    }
}
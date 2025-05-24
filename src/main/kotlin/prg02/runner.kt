package prg02

fun run(args: Array<String>) {
    if (args.isEmpty()) {
        println("Argument is empty, please pass a arguments client or server")
        return
    }

    when (val mode = args[0]) {
        "server" -> runServer()
        "client" -> runClient()
        else -> println("Invalid arguments: $mode")
    }
}
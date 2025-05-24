package prg04

import io.grpc.ServerBuilder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import serverstreaming.ServerStreamingGrpcKt
import serverstreaming.Serverstreaming
import java.util.concurrent.Executors

fun makeMessage(message: String): Serverstreaming.Message
    = Serverstreaming.Message.newBuilder().setMessage(message).build()

class ServerStreamingService : ServerStreamingGrpcKt.ServerStreamingCoroutineImplBase() {
    override fun getServerResponse(request: Serverstreaming.Number): Flow<Serverstreaming.Message> {
        val messages = listOf(
            makeMessage("message #1"),
            makeMessage("message #2"),
            makeMessage("message #3"),
            makeMessage("message #4"),
            makeMessage("message #5")
        )

        println("Server processing gRPC server-streaming {${request.value}}")
        return flow {
            messages.forEach { value -> emit(value) }
        }
    }
}

fun runServer() {
    val threadPoolExecutor = Executors.newFixedThreadPool(10)
    val server = ServerBuilder
        .forPort(50051)
        .executor(threadPoolExecutor)
        .addService(ServerStreamingService())
        .build()

    Runtime.getRuntime().addShutdownHook(Thread {
        server.shutdown()
    })

    println("Starting server. Listening on port 50051.")
    server.start()
    server.awaitTermination()
}

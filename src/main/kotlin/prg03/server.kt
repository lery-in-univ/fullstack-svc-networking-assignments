package prg03

import clientstreaming.ClientStreamingGrpcKt
import clientstreaming.Clientstreaming
import io.grpc.ServerBuilder
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.Executors

class ClientStreamingServicer : ClientStreamingGrpcKt.ClientStreamingCoroutineImplBase() {
    override suspend fun getServerResponse(requests: Flow<Clientstreaming.Message>): Clientstreaming.Number {
        println("Server processing gRPC client-streaming.")

        var count = 0
        requests.collect { count++ }
        return Clientstreaming.Number.newBuilder().setValue(count).build()
    }
}

fun runServer() {
    val threadPoolExecutor = Executors.newFixedThreadPool(10)
    val server = ServerBuilder
        .forPort(50051)
        .executor(threadPoolExecutor)
        .addService(ClientStreamingServicer())
        .build()

    Runtime.getRuntime().addShutdownHook(Thread {
        server.shutdown()
    })

    println("Starting server. Listening on port 50051.")
    server.start()
    server.awaitTermination()
}


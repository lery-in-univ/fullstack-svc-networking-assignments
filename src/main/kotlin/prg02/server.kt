package prg02

import bidirectional.BidirectionalGrpcKt
import bidirectional.BidirectionalOuterClass
import io.grpc.ServerBuilder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.concurrent.Executors

class BidirectionalService : BidirectionalGrpcKt.BidirectionalCoroutineImplBase() {
    override fun getServerResponse(requests: Flow<BidirectionalOuterClass.Message>): Flow<BidirectionalOuterClass.Message> {
        println("Server processing gRPC bidirectional streaming.")
        return flow {
            requests.collect {
                value -> emit(value)
            }
        }
    }
}

fun runServer() {
    val threadPoolExecutor = Executors.newFixedThreadPool(10)
    val server = ServerBuilder
        .forPort(50051)
        .executor(threadPoolExecutor)
        .addService(BidirectionalService())
        .build()

    Runtime.getRuntime().addShutdownHook(Thread {
        server.shutdown()
    })

    println("Starting server. Listening on port 50051.")
    server.start()
    server.awaitTermination()
}

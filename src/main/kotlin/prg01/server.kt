package prg01

import HelloGrpc
import MyServiceGrpcKt
import io.grpc.ServerBuilder
import java.util.concurrent.Executors

class MyServiceServicer: MyServiceGrpcKt.MyServiceCoroutineImplBase() {
    override suspend fun myFunction(request: HelloGrpc.MyNumber): HelloGrpc.MyNumber {
        val response = HelloGrpc.MyNumber.newBuilder()
        response.setValue(my_func(request.value))
        return response.build()
    }
}

fun runServer() {
    val threadPoolExecutor = Executors.newFixedThreadPool(10)
    val server = ServerBuilder
        .forPort(50051)
        .executor(threadPoolExecutor)
        .addService(MyServiceServicer())
        .build()

    Runtime.getRuntime().addShutdownHook(Thread {
        server.shutdown()
    })

    println("Starting server. Listening on port 50051.")
    server.start()
    server.awaitTermination()
}

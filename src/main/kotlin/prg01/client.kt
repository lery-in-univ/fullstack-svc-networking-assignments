package prg01

import HelloGrpc
import MyServiceGrpcKt
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.runBlocking

fun runClient() = runBlocking {
    val channel = ManagedChannelBuilder
        .forAddress("localhost", 50051)
        .usePlaintext()
        .build()

    val stub = MyServiceGrpcKt.MyServiceCoroutineStub(channel)

    val request = HelloGrpc.MyNumber.newBuilder()
        .setValue(4)
        .build()

    val response = stub.myFunction(request)

    println("gRPC result: ${response.value}")
}
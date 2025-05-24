package prg04

import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.runBlocking
import serverstreaming.Serverstreaming
import serverstreaming.ServerStreamingGrpcKt.ServerStreamingCoroutineStub

suspend fun recvMessage(stub: ServerStreamingCoroutineStub) {
    val request = Serverstreaming.Number.newBuilder().setValue(5).build()
    val responses = stub.getServerResponse(request)
    responses.collect { println("[server to client] ${it.message}") }
}

fun runClient() = runBlocking {
    val channel = ManagedChannelBuilder
        .forAddress("localhost", 50051)
        .usePlaintext()
        .build()

    val stub = ServerStreamingCoroutineStub(channel)
    recvMessage(stub)

    channel.shutdown()
}

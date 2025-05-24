package prg03

import clientstreaming.ClientStreamingGrpcKt
import clientstreaming.Clientstreaming
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

fun makeMessage(message: String)
        = Clientstreaming.Message.newBuilder().setMessage(message).build()

fun generateMessages(): Flow<Clientstreaming.Message> {
    val messages = listOf(
        makeMessage("message #1"),
        makeMessage("message #2"),
        makeMessage("message #3"),
        makeMessage("message #4"),
        makeMessage("message #5")
    )

    return flow {
        messages.forEach {
            println("[client to server] ${it.message}")
            emit(it)
        }
    }
}

suspend fun sendMessage(stub: ClientStreamingGrpcKt.ClientStreamingCoroutineStub) {
    val response = stub.getServerResponse(generateMessages())
    print("[server to client] ${response.value}")
}

fun runClient() = runBlocking {
    val channel = ManagedChannelBuilder
        .forAddress("localhost", 50051)
        .usePlaintext()
        .build()

    val stub = ClientStreamingGrpcKt.ClientStreamingCoroutineStub(channel)
    sendMessage(stub)

    channel.shutdown()
}
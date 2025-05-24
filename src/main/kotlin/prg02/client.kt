package prg02

import bidirectional.BidirectionalGrpcKt.BidirectionalCoroutineStub
import bidirectional.BidirectionalOuterClass.Message
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

fun makeMessage(message: String)
    = Message.newBuilder().setMessage(message).build()

fun generateMessages(): Flow<Message> {
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

suspend fun sendMessage(stub: BidirectionalCoroutineStub) {
    val responses = stub.getServerResponse(generateMessages())
    responses.collect {
        println("[server to client] ${it.message}")
    }
}

fun runClient() = runBlocking {
    val channel = ManagedChannelBuilder
        .forAddress("localhost", 50051)
        .usePlaintext()
        .build()

    val stub = BidirectionalCoroutineStub(channel)
    sendMessage(stub)

    channel.shutdown()
}
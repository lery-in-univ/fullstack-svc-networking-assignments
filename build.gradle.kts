import com.google.protobuf.gradle.*

group = "org.example"
version = "1.0-SNAPSHOT"

val grpcVersion = "3.25.3"
val grpcKotlinVersion = "1.4.1"
val grpcProtoVersion = "1.62.2"

plugins {
    kotlin("jvm") version "1.9.0"
    application

    id("com.google.protobuf") version "0.9.4"
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("io.grpc:grpc-kotlin-stub:$grpcKotlinVersion")
    implementation("io.grpc:grpc-netty:$grpcProtoVersion")
    implementation("io.grpc:grpc-protobuf:$grpcProtoVersion")
    implementation("io.grpc:protoc-gen-grpc-java:$grpcProtoVersion")
    implementation("com.google.protobuf:protobuf-kotlin:$grpcVersion")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}

sourceSets {
    main {
        proto {
            srcDir("src/main/proto")
        }
    }
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:$grpcVersion"
    }

    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:$grpcProtoVersion"
        }
        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:$grpcKotlinVersion:jdk8@jar"
        }
    }

    generateProtoTasks {
        all().forEach {
            it.plugins {
                id("grpc")
                id("grpckt")
            }
        }
    }
}

application {
    mainClass.set("MainKt")
}

# fullstack-svc-networking-assignments

2025년 1학기에 수강 중인 풀스택서비스네트워킹 강의의 과제를 정리해 둔 레포지토리입니다.

## Build

```shell
./gradlew build
```

## Run

```shell
./gradlew run --args="<server|client>"
```

어떤 프로그램을 실행할지는 [`Main.kt`](./src/main/kotlin/Main.kt) 파일에서 결정합니다. 다른 프로그램을 실행하고 싶으시다면, `Main.kt` 파일에서 `prgNN`의 숫자를 변경해주세요.

인수로 `server`를 넘기는 경우 gRPC 서버를, `client`를 넘기는 경우 gRPC 클라이언트를 동작시킵니다.

## Programs

- [prg01](./src/main/kotlin/prg01): 단순 요청, 단순 응답을 처리합니다.
- [prg02](./src/main/kotlin/prg02): 스트림 요청, 스트림 응답을 처리합니다.
- [prg03](./src/main/kotlin/prg03): 스트림 요청, 단순 응답을 처리합니다.
- [prg04](./src/main/kotlin/prg04): 단순 요청, 스트림 응답을 처리합니다.

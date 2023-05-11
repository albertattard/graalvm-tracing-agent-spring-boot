# GraalVM - Tracing Agent, GraalVM Gradle Plugin, and Spring Boot

A very simple application that uses the GraalVM Gradle Plugin to run the tracing
agent against a very simple Spring Boot application.

In this example, the `processTestAot` Gradle task fails with the following
error.

```
...
> Task :processTestAot FAILED
[native-image-plugin] Instrumenting task with the native-image-agent: processTestAot
[0.144s][warning][jni,resolve] Re-registering of platform native method: jdk.internal.misc.Unsafe.allocateInstance(Ljava/lang/Class;)Ljava/lang/Object; from code in a different classloader
12:34:56.789 [main] INFO org.springframework.test.context.aot.TestClassScanner -- Scanning for Spring test classes in all packages in classpath roots [/Users/aattard/Projects/albertattard/graalvm-tracing-agent-spring-boot/build/classes/java/test]
Exception in thread "main" java.lang.IllegalStateException: Cannot perform AOT processing during AOT run-time execution
        at org.springframework.util.Assert.state(Assert.java:76)
        at org.springframework.test.context.aot.TestContextAotGenerator.processAheadOfTime(TestContextAotGenerator.java:126)
        at org.springframework.test.context.aot.TestAotProcessor.performAotProcessing(TestAotProcessor.java:91)
        at org.springframework.test.context.aot.TestAotProcessor.doProcess(TestAotProcessor.java:72)
        at org.springframework.test.context.aot.TestAotProcessor.doProcess(TestAotProcessor.java:39)
        at org.springframework.context.aot.AbstractAotProcessor.process(AbstractAotProcessor.java:82)
        at org.springframework.boot.test.context.SpringBootTestAotProcessor.main(SpringBootTestAotProcessor.java:63)
...
```

Technology stack used

- GraalVM 22.3.2 and Java 17
- Spring Boot 3.0.6
- GraalVM Gradle Plugin 0.9.21

## Reproduce problem

1. (_Optional_) Test the application

   ```shell
   $ ./gradlew clean check
   ```

   All tests should pass.

2. Use the
   [assisted configuration with tracing agent](https://www.graalvm.org/latest/reference-manual/native-image/guides/use-reachability-metadata-repository-gradle/)

   ```shell
   $ ./gradlew clean -Pagent check
   ```

   **Unfortunately this fails**

   ```
   ...
   > Task :processTestAot FAILED
   [native-image-plugin] Instrumenting task with the native-image-agent: processTestAot
   [0.144s][warning][jni,resolve] Re-registering of platform native method: jdk.internal.misc.Unsafe.allocateInstance(Ljava/lang/Class;)Ljava/lang/Object; from code in a different classloader
   12:34:56.789 [main] INFO org.springframework.test.context.aot.TestClassScanner -- Scanning for Spring test classes in all packages in classpath roots [/Users/aattard/Projects/albertattard/graalvm-tracing-agent-spring-boot/build/classes/java/test]
   Exception in thread "main" java.lang.IllegalStateException: Cannot perform AOT processing during AOT run-time execution
           at org.springframework.util.Assert.state(Assert.java:76)
           at org.springframework.test.context.aot.TestContextAotGenerator.processAheadOfTime(TestContextAotGenerator.java:126)
           at org.springframework.test.context.aot.TestAotProcessor.performAotProcessing(TestAotProcessor.java:91)
           at org.springframework.test.context.aot.TestAotProcessor.doProcess(TestAotProcessor.java:72)
           at org.springframework.test.context.aot.TestAotProcessor.doProcess(TestAotProcessor.java:39)
           at org.springframework.context.aot.AbstractAotProcessor.process(AbstractAotProcessor.java:82)
           at org.springframework.boot.test.context.SpringBootTestAotProcessor.main(SpringBootTestAotProcessor.java:63)
   ...
   ```

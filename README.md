# [phase-verifier](https://github.com/chyccs/phase-verifier)

[![Maven Central](https://img.shields.io/maven-central/v/io.github.chyccs/phase-verifier.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22io.github.chyccs%22%20AND%20a:%22phase-verifier%22)

Java library that enables step-by-step verification using JUnit
(You can do step-by-step testing like a StepVerifier in a reactive environment)




**For Maven:**

```xml
<dependency>
  <groupId>io.github.chyccs</groupId>
  <artifactId>phase-verifier</artifactId>
  <version>x.x.x</version>
  <scope>test</scope>
</dependency>
```



**For Gradle:**

```groovy
testImplementation 'io.github.chyccs:phase-verifier:x.x.x'
```



**How to use:**

```java
public class CookieTests {
    @Test
    public void testSuccess() throws ExecutionException, InterruptedException {
        PhaseVerifier.execute(() -> "butter")
            .expectNot("cream")
            .expect("butter");
    }
}
```


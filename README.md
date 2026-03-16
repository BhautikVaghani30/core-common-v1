# Common Functionality Library

## Description

This project houses a collection of common functionalities and shared components designed to be used across various
microservices within our architecture. By providing a centralized, reusable library, we aim to streamline development,
ensure consistency, and reduce boilerplate code in our microservices.

This library can be included as a dependency (JAR) in any microservice, providing instant access to pre-configured and
optimized modules.

## Features

* **Optimized WebClient Configuration:** Pre-configured `WebClient.Builder` beans for different communication
  scenarios (internal, external, with compression).
* **Centralized Logging:** Standardized Logback configuration for structured (JSON) logging.
* **MDC Context Lifter:** `CoreMdcContextLifter` to propagate MDC context across reactive streams, ensuring consistent
  tracing in asynchronous operations.
* **Aspect-Oriented Programming (AOP):** Foundation for implementing cross-cutting concerns.

## Prerequisites

To build and use this project, you will need:

* Java 21
* Gradle

## Installation

1. **Build the project:**
   ```bash
   ./gradlew build
   ```

2. **Publish to local Maven repository:**
   ```bash
   ./gradlew publishToMavenLocal
   ```
   *(Note: You may need to add the `maven-publish` plugin to your `build.gradle` for this command to work).*

3. **Add as a dependency:**
   In your microservice's `build.gradle`, add the following to your dependencies block:

   ```groovy
   implementation 'com.core.v1:common:1.0.0'
   ```

## Usage

### WebClient

To use a pre-configured `WebClient`, inject it into your Spring components using a qualifier.

**Example:**

```java
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import constant.com.core.township.common.CoreWebClientBuilderTypeConstants;

@Service
public class MyService {

    private final WebClient webClient;

    public MyService(@Qualifier(CoreWebClientBuilderTypeConstants.CORE_WEB_CLIENT_BUILDER) WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://api.example.com").build();
    }

    // ... methods that use the webClient
}
```

### Logging

The logging is configured automatically via `logback-spring.xml`. Simply use a standard SLF4J logger in your classes.

**Example:**

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MyComponent {

    private static final Logger log = LoggerFactory.getLogger(MyComponent.class);

    public void doSomething() {
        log.info("Doing something important...");
    }
}
```

## Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository.
2. Create a new branch (`git checkout -b feature/your-feature-name`).
3. Make your changes.
4. Commit your changes (`git commit -m 'Add some feature'`).
5. Push to the branch (`git push origin feature/your-feature-name`).
6. Open a Pull Request.

## License

This project is licensed under the **Apache 2.0 License**.

## Contact

For questions or suggestions, please open an issue in the repository.

## Version

1.0.0 = LTS
1.1.0 = remove @ComponentScan for i18n to resolve bean conflicts
1.1.1 = added default exception handler for all microservice
1.1.2 = added CoreHeaderFeaturesOutgoingExchange filter and CoreLocalDevelopmentConfig file
1.1.3 = deprecated CoreServiceException to resolve conflict with microservice specific exception
1.1.4 = remove deprecated CoreServiceException and remove globle exception handler// ?stanble version

Implementing context
1.1.5 = 
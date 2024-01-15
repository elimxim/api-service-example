import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    kotlin("jvm") version "1.9.0"
    id("org.springframework.boot") version "3.2.1"
    id("io.spring.dependency-management") version "1.1.4"
    id("org.hidetake.swagger.generator") version "2.19.2"
    java
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-quartz")
    implementation("net.lbruun.springboot:preliquibase-spring-boot-starter:1.5.0")

    implementation("ch.qos.logback:logback-core")
    implementation("org.liquibase:liquibase-core")
    implementation("org.mapstruct:mapstruct:1.5.5.Final")
    implementation("io.swagger:swagger-annotations:1.6.12")
    implementation("com.atlassian.oai:swagger-request-validator-spring-webmvc:2.39.0")

    implementation("org.testcontainers:postgresql")

    compileOnly("org.projectlombok:lombok")
    runtimeOnly("org.postgresql:postgresql")

    swaggerCodegen("io.swagger.codegen.v3:swagger-codegen-cli:3.0.51")

    annotationProcessor("org.projectlombok:lombok")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.assertj:assertj-core")
}

swaggerSources {
    create("drone") {
        setInputFile(file("${rootDir}/src/main/resources/oas/drone-api.yml"))
        code.apply {
            language = "spring"
            configFile = file("${rootDir}/swagger-config.json")
            dependsOn(validation)
            components = listOf("models", "apis")
            additionalProperties = mapOf(
                    Pair("jakarta", "true"),
                    Pair("useTags", "true")
            )
            rawOptions = listOf("--ignore-file-override=${rootDir}/.swagger-codegen-ignore")
        }
    }
}

val swaggerCode = swaggerSources.getByName("drone").code

tasks.compileJava.configure {
    dependsOn(tasks.generateSwaggerCode)
}

tasks.processResources.configure {
    dependsOn(swaggerCode)
}

sourceSets {
    main.get().apply {
        java.srcDir("${swaggerCode.outputDir}/src/main/java")
        resources.srcDir("${swaggerCode.outputDir}/src/main/resources")
    }
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        exceptionFormat = TestExceptionFormat.FULL
        showExceptions = true
        showCauses = true
        showStackTraces = true
        events.addAll(listOf(
                TestLogEvent.FAILED,
                TestLogEvent.PASSED,
                TestLogEvent.SKIPPED,
                TestLogEvent.STARTED,
                TestLogEvent.STANDARD_ERROR,
                TestLogEvent.STANDARD_OUT
        ))
    }
}
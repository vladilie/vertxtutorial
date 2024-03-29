
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Kotlin application project to get you started.
 */

plugins {
    // Apply the Kotlin JVM plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm") version "1.3.41"

    // Apply the application plugin to add support for building a CLI application.
    application

    java // Required by at least JUnit.

}


repositories {
    mavenCentral()
    // Use jcenter for resolving dependencies.
    // You can declare any Maven/Ivy/file repository here.
    jcenter()
}

val junitJupiterVersion = "5.4.1"
val testContainersVersion = "1.11.1"

dependencies {

    implementation("org.apache.commons:commons-compress:1.18")
    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("io.github.jponge:vertx-boot:1.0.0")

    testImplementation("org.assertj:assertj-core:3.10.0")
    testImplementation("org.testcontainers:testcontainers:${testContainersVersion}")
    testImplementation("com.julienviet:childprocess-vertx-ext:1.3.0")

    testImplementation("org.junit.jupiter:junit-jupiter-engine:${junitJupiterVersion}")
    testImplementation("org.junit.jupiter:junit-jupiter-params:${junitJupiterVersion}")
    testImplementation("org.testcontainers:junit-jupiter:${testContainersVersion}")
     // Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    // Use the Kotlin JDK 8 standard library.
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // Use the Kotlin test library.
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    // Use the Kotlin JUnit integration.
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")

    implementation("ch.qos.logback:logback-classic:1.2.3")

    implementation("io.vertx:vertx-core:3.8.3")

//    testCompile("io.vertx:vertx-unit:3.8.3")
    testCompile("org.junit.jupiter:junit-jupiter:5.5.2")
    compile("io.vertx:vertx-web-client:3.8.3")
    testCompile("io.vertx:vertx-junit5:3.8.3")
    compile("io.vertx:vertx-web:3.8.3")

}

application {
    // Define the main class for the application
    mainClassName = "vertxtutorial.AppKt"
}

tasks.named<JavaExec>("run") {
    main = project.properties.getOrDefault("mainClass", "vertxtutorial.AppKt") as String
    classpath = sourceSets["main"].runtimeClasspath
    systemProperties["vertx.logger-delegate-factory-class-name"] = "io.vertx.core.logging.SLF4JLogDelegateFactory"
}

// compile bytecode to java 8 (default is java 6)
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks {
    // Use the built-in JUnit support of Gradle.
    "test"(Test::class) {
        useJUnitPlatform() {
            includeEngines("junit-jupiter")
        }
    }
}

tasks.test {

    val mapKeys = System.getProperties().mapKeys { it.key as String }
    val toMutableMap = mapKeys.toMutableMap()
    toMutableMap["junit.jupiter.conditions.deactivate"] = "*"
    toMutableMap["junit.jupiter.extensions.autodetection.enabled"] = "true"
    toMutableMap["junit.jupiter.testinstance.lifecycle.default"] = "per_class"
    systemProperties(toMutableMap.toMap())

}
package io.vertx.tutorial;

import io.vertx.core.AbstractVerticle
import io.vertx.core.Launcher

class HttpVerticle : AbstractVerticle() {

    override fun start() {
        vertx.createHttpServer()
                .requestHandler { it.response().end("Hi there! by vert.x") }
                .listen(8080) {
                    if (it.succeeded()) {
                        System.out.println("http://localhost:8080/");
                    } else {
                        System.err.println("Failed to listen on port 8080");
                    }
                }
    }
}


fun main() {
    println("Starting the service")
    Launcher.executeCommand("run", HttpVerticle::class.qualifiedName)
}
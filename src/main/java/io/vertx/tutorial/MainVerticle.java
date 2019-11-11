package io.vertx.tutorial;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Launcher;

public class MainVerticle extends AbstractVerticle {

    @Override
    public void start() {
        vertx.createHttpServer()
                .requestHandler(req -> req.response().end("Hi there! by vert.x"))
                .listen(8080, handler -> {
                    if (handler.succeeded()) {
                        System.out.println("http://localhost:8080/");
                    } else {
                        System.err.println("Failed to listen on port 8080");
                    }
                });
    }

    public static void main(final String[] args) {
        Launcher.executeCommand("run", MainVerticle.class.getName());
    }

}

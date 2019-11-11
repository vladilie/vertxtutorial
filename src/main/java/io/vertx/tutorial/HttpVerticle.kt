package io.vertx.tutorial;

import io.netty.handler.codec.http.HttpResponseStatus
import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.Launcher
import io.vertx.core.json.Json
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.BodyHandler
import javax.ws.rs.core.MediaType

class HttpVerticle : AbstractVerticle() {

    fun registerUser(routingContext: RoutingContext) {
        routingContext
                .response()
                .setStatusCode(HttpResponseStatus.CREATED.code())
                .putHeader("content-type", MediaType.APPLICATION_JSON)
                .end(Json.encodePrettily(User("Vlad", "vladilie", "vlad@ilie.com", null, null).asJson()))

    }

    fun createBaseRouter(): Router {
        val baseRouter = Router.router(vertx)
        baseRouter.route("/").handler{
            it.response().putHeader("content-type", MediaType.APPLICATION_JSON)
            it.response().end("The server is up!")
        }
        baseRouter.mountSubRouter("/api", createApiRouter())

        return baseRouter
    }

    fun createApiRouter(): Router {
        val apiRouter = Router.router(vertx)
        apiRouter.route("/user*").handler(BodyHandler.create())
        apiRouter.post("/user").handler(this::registerUser)

        return apiRouter
    }

    override fun start(startFuture: Future<Void>?) {
        vertx.createHttpServer()
                .requestHandler(this.createBaseRouter()::accept)
                .listen(8080) {
                    if (it.succeeded()) {
                        startFuture?.complete()
                    } else {
                        startFuture?.fail(it.cause())
                    }
                }
    }
}


fun main() {
    println("Starting the service")
    Launcher.executeCommand("run", HttpVerticle::class.qualifiedName)
}
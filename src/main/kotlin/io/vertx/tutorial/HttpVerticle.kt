package io.vertx.tutorial

import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.BodyHandler
import org.slf4j.LoggerFactory

class HttpVerticle : AbstractVerticle() {
    var counter:Long = 1
    val logger = LoggerFactory.getLogger(HttpVerticle::class.java)

    override fun start(startPromise: Promise<Void>?) {
        // create a apiRouter to handle the API
        val baseRouter = Router.router(vertx)
        val apiRouter = Router.router(vertx)

        baseRouter.route("/").handler{
            it.response()
                    .putHeader("content-type", "text/plain")
                    .end("!Server is up!")
        }


        apiRouter.route("/user*").handler(BodyHandler.create())
        apiRouter.post("/user").handler{this.registerUser(it)}


        baseRouter.mountSubRouter("/api", apiRouter)

        vertx.createHttpServer()
                .requestHandler(baseRouter)
                .listen(8080);

        logger.info("Open http://localhost:8080/");
        startPromise!!.complete()
    }

    private fun registerUser(context: RoutingContext?) {
        context!!.response()
                .putHeader("content-type", "application/json")
                .end(JsonObject().put("user",
                        JsonObject().put("name", "vlad"))
                        .encodePrettily())
    }


}
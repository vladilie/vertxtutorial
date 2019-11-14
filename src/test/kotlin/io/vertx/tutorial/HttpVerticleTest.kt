package io.vertx.tutorial

import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.client.HttpResponse
import io.vertx.ext.web.client.WebClient
import io.vertx.ext.web.codec.BodyCodec
import io.vertx.junit5.Timeout
import io.vertx.junit5.VertxExtension
import io.vertx.junit5.VertxTestContext
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.concurrent.TimeUnit

@ExtendWith(VertxExtension::class)
class HttpVerticleTest {
    // Deploy the verticle and execute the test methods when the verticle is successfully deployed
    @BeforeEach
    fun prepare(vertx: Vertx, testContext: VertxTestContext) {
        vertx.deployVerticle(HttpVerticle(), testContext.succeeding {
            testContext.completeNow()
        })
    }

    @Test
    @Timeout(value = 1, timeUnit = TimeUnit.SECONDS)
    fun http_server_check_response(vertx: Vertx, testContext: VertxTestContext) {
        val client = WebClient.create(vertx)
        val succeeding =
                testContext.succeeding<HttpResponse<String>> {
                    testContext.verify {
                        assertThat(it.body()).isEqualTo("!Server is up!")
                        testContext.completeNow()
                    }
                }
        client.get(8080, "localhost", "/")
                .`as`(BodyCodec.string())
                .send(succeeding)
    }
    @Test
    @Timeout(value = 1, timeUnit = TimeUnit.SECONDS)
    fun http_post_user(vertx: Vertx, testContext: VertxTestContext) {
        val client = WebClient.create(vertx)
        val succeeding =
                testContext.succeeding<HttpResponse<String>> {
                    testContext.verify {
                        assertThat(JsonObject(it.body())).isEqualTo(JsonObject().put("user", JsonObject().put("name", "vlad")))
                        testContext.completeNow()
                    }
                }
        client.post(8080, "localhost", "/api/user")
                .`as`(BodyCodec.string())
                .send(succeeding)
    }
}

package io.vertx.tutorial;

import io.vertx.core.Vertx
import io.vertx.core.http.HttpClientResponse
import io.vertx.core.http.HttpMethod
import io.vertx.ext.unit.TestContext
import io.vertx.ext.unit.junit.VertxUnitRunner
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

const val DEFAULT_TIMEOUT: Long = 15000;

@RunWith(VertxUnitRunner::class)
class HttpVerticleTest {

    lateinit var vertx: Vertx;

    @Before
    fun setup(context: TestContext) {
        val async = context.async()
        vertx = Vertx.vertx()
        val httpVerticle = HttpVerticle()
        vertx.deployVerticle(httpVerticle) {
            if (it.succeeded()) {
                vertx.createHttpServer().requestHandler(httpVerticle.createBaseRouter()::accept).listen(8080)
                async.complete()
            } else {
                context.fail(it.cause());
            }
        }
    }

    @After
    fun close(context: TestContext) {
        vertx.close(context.asyncAssertSuccess());
    }

    @Test(timeout = DEFAULT_TIMEOUT)
    fun testServerIsUp(context: TestContext) {
        val async = context.async()
        val client = vertx.createHttpClient()
        val request = client.request(HttpMethod.POST, 8080, "localhost", "/")

        request.handler { response: HttpClientResponse ->
            if (response.statusCode() == 200) {
                response.bodyHandler { buffer ->
                    context.assertEquals("The server is up!", buffer.toString());
                    client.close();
                    async.complete();
                }
            }
        }
        request.end()
    }

}


//
//    @Test
//    public void testThatTheUserIsPosted(TestContext tc) {
//        Async async = tc.async();
//        vertx.createHttpClient().postAbs("/api/user", response -> {
//            tc.assertEquals(response.statusCode(), 200);
//            response.bodyHandler(body -> {
//                tc.assertTrue(body.length() > 0);
//                tc.assertEquals(new String(body.getBytes()), "The server is up!");
//                async.complete();
//            });
//        });
//    }

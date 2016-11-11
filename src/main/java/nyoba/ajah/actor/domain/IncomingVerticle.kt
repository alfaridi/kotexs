package nyoba.ajah.actor.domain

import io.vertx.core.AbstractVerticle
import io.vertx.core.Vertx
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.StaticHandler
import org.springframework.stereotype.Component



/**
 * Created by alfaridi on 11/11/16.
 */
@Component
open class IncomingVerticle : AbstractVerticle() {

    @Throws(Exception::class)
    override fun start() {
        val router = Router.router(vertx)
        router.route().handler(StaticHandler.create())
        Vertx.vertx().createHttpServer().requestHandler { req -> req.response().end("Hello World!") }.listen(8080)
    }

}
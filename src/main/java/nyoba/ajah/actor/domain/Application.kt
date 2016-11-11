package nyoba.ajah.actor.domain

import io.vertx.core.Vertx
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean
import java.io.IOException
import javax.annotation.PostConstruct
import javax.persistence.EntityManagerFactory

/**
 * Created by alfaridi on 11/10/16.
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
@EnableCaching
open class Application {

    @Bean
    open fun sessionFactory(emf: EntityManagerFactory): HibernateJpaSessionFactoryBean {
        val factoryBean = HibernateJpaSessionFactoryBean()
        factoryBean.entityManagerFactory = emf
        return factoryBean
    }

    @PostConstruct
    open fun deployVerticle() {
        Vertx.vertx().deployVerticle(IncomingVerticle())
    }

    companion object {
        @JvmStatic fun main(args: Array<String>) {
            SpringApplication.run(Application::class.java, *args)
        }
    }

}

package nyoba.ajah.actor.domain

import nyoba.ajah.actor.domain.common.helpers.SerializationHelper
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.CachingConfigurerSupport
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.interceptor.KeyGenerator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.StringRedisTemplate
import java.time.Duration
import java.util.Calendar

/**
 * Created by alfaridi on 11/11/16.
 */
@Configuration
@EnableCaching
@EnableAutoConfiguration
open class RedisCacheConfigurerSupport : CachingConfigurerSupport() {

    @Bean
    open fun template(factory: RedisConnectionFactory): RedisTemplate<String, String> {
        val template = StringRedisTemplate(factory)
        template.valueSerializer = SerializationHelper()
        template.connectionFactory = factory
        return template
    }

    @Bean
    open fun cacheManager(template: RedisTemplate<Any, Any>): CacheManager {
        val cacheManager = RedisCacheManager(template)
        cacheManager.setDefaultExpiration(Duration.ofMinutes(5).seconds)
        return cacheManager
    }

    @Bean
    override fun keyGenerator(): KeyGenerator {
        return KeyGenerator { any, method, arrayOfAnys -> {
            val key = StringBuilder()
            key.append(any.javaClass.simpleName)
                    .append(".")
                    .append(method.name)

            try {
                for(obj: Any in arrayOfAnys) {
                    val name = obj.toString()
                    key.append(".$name.")
                    when (obj) {
                        is Calendar     -> key.append(obj.timeInMillis)
                        else            -> key.append(obj.toString())
                    }
                }
            } catch (e: Exception) {}
        }}


    }

}
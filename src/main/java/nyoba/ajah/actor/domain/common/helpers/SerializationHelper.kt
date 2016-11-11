package nyoba.ajah.actor.domain.common.helpers

import org.springframework.core.serializer.support.DeserializingConverter
import org.springframework.core.serializer.support.SerializingConverter
import org.springframework.data.redis.serializer.RedisSerializer
import org.springframework.data.redis.serializer.SerializationException
import java.nio.charset.Charset

/**
 * Created by alfaridi on 11/10/16.
 */
class SerializationHelper : RedisSerializer<Any> {

    private val charset: Charset
    private val serializer = SerializingConverter()
    private val deserializer = DeserializingConverter()

    init {
        this.charset = Charset.forName("UTF8")
    }

    override fun deserialize(bytes: ByteArray?): Any? {
        if (bytes == null || bytes.isEmpty()) {
            return null
        } else {
            try {
                return this.deserializer.convert(bytes)
            } catch (var3: Exception) {
                return String(bytes, this.charset)
            }
        }
    }

    override fun serialize(`object`: Any?): ByteArray {
        if (`object` == null) {
            return ByteArray(0)
        } else {
            try {
                if (`object` is String) {
                    return `object`.toByteArray(this.charset)
                } else {
                    return this.serializer.convert(`object`)
                }
            } catch (var3: Exception) {
                throw SerializationException("Cannot serialize", var3)
            }

        }
    }
}
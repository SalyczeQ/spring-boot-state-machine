package eu.dusansalay.statemachine.repository.config

import eu.dusansalay.statemachine.common.PaymentType
import io.r2dbc.postgresql.codec.EnumCodec
import io.r2dbc.postgresql.codec.EnumCodec.Builder.RegistrationPriority
import io.r2dbc.spi.ConnectionFactoryOptions
import io.r2dbc.spi.Option
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.r2dbc.ConnectionFactoryOptionsBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.convert.CustomConversions
import org.springframework.data.r2dbc.convert.EnumWriteSupport
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions
import org.springframework.data.r2dbc.dialect.DialectResolver
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import org.springframework.r2dbc.core.DatabaseClient


@Configuration
@EnableR2dbcRepositories
class R2dbcConfiguration {

    @Bean
    fun connectionFactoryOptionsBuilderCustomizer(): ConnectionFactoryOptionsBuilderCustomizer {
        return ConnectionFactoryOptionsBuilderCustomizer { builder: ConnectionFactoryOptions.Builder ->
            builder.option(
                Option.valueOf("extensions"),
                listOf(
                    EnumCodec.builder()
                        .withEnum("payment_type", PaymentType::class.java)
                        .withRegistrationPriority(RegistrationPriority.FIRST)
                        .build()
                )
            )
            logger.info("Adding enum to R2DBC postgresql extensions: {}", builder)
        }
    }

    @Bean
    fun r2dbcCustomConversions(databaseClient: DatabaseClient): R2dbcCustomConversions {
        val dialect = DialectResolver.getDialect(databaseClient.connectionFactory)
        val converters: MutableList<Any> = ArrayList(dialect.converters)
        converters.addAll(R2dbcCustomConversions.STORE_CONVERTERS)
        return R2dbcCustomConversions(
            CustomConversions.StoreConversions.of(dialect.simpleTypeHolder, converters),
            listOf(
                PaymentTypeWritingConverter()
            )
        )
    }

    companion object {
        val logger = LoggerFactory.getLogger(R2dbcConfiguration::class.java)
    }
}


class PaymentTypeWritingConverter : EnumWriteSupport<PaymentType>()

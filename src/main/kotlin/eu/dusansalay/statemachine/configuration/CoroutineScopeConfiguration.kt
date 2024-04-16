package eu.dusansalay.statemachine.configuration

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CoroutineScopeConfiguration {

    @Bean
    fun coroutineDispatcher(): CoroutineDispatcher =
        Dispatchers.Default

    @Bean
    fun coroutineExceptionHandler(): CoroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            LOGGER.error(throwable.message, throwable)
        }

    @Bean
    fun coroutineScope(
        coroutineDispatcher: CoroutineDispatcher,
        coroutineExceptionHandler: CoroutineExceptionHandler
    ) = CoroutineScope(
        SupervisorJob() +
                coroutineDispatcher +
                coroutineExceptionHandler
    )

    companion object {
        val LOGGER: Logger = LoggerFactory.getLogger(CoroutineScopeConfiguration::class.java)
    }
}

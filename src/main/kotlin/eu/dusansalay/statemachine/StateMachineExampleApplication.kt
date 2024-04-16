package eu.dusansalay.statemachine

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class StateMachineExampleApplication

fun main(args: Array<String>) {
	runApplication<StateMachineExampleApplication>(*args)
}

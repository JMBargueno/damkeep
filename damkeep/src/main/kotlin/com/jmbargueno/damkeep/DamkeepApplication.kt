package com.jmbargueno.damkeep

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Component

@SpringBootApplication
class DamkeepApplication

fun main(args: Array<String>) {
	runApplication<DamkeepApplication>(*args)
}


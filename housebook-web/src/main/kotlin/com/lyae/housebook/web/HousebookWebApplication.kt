package com.lyae.housebook.web

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HousebookWebApplication

fun main(args: Array<String>) {
    runApplication<HousebookWebApplication>(*args)
}

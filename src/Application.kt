package com.codexdroid

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.gson.*
import io.ktor.features.*
import kotlinx.serialization.Serializable
import kotlin.random.Random
import kotlin.random.nextInt


@Serializable
data class Person(var id:Int, var name:String, var email:String, var mobile:String)

@Serializable
data class DataList(var status:Int, var message:String?, var error:String?, var dataList: List<Person>?)

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
fun Application.module() {
    install(ContentNegotiation) {
        gson {
            this.setPrettyPrinting()
            this.setLenient()
        }
    }

    routing {

        val dataList = mutableListOf<Person>()
        repeat(30){ num ->

            var firstName = ""
            var lastName = ""

            repeat(6){
                firstName += Random.nextInt(97..122).toChar()
            }
            repeat(6){
                lastName += Random.nextInt(97..122).toChar()
            }

            val email = "${firstName}@gmail.com"
            val mobile = Random.nextLong(1111111111,9999999999).toString()

            dataList.add(Person(id = num, name = "$firstName $lastName", email = email, mobile = mobile))
        }

        get("/data"){
            call.respond(DataList(HttpStatusCode.OK.value,"All Data Fetch Successfully",null,dataList))
        }
    }
}




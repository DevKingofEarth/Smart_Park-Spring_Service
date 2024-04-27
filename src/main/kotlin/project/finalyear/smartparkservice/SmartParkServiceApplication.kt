package project.finalyear.smartparkservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.annotation.Id
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class SmartParkServiceApplication

fun main(args: Array<String>) {
    runApplication<SmartParkServiceApplication>(*args)
}

@RestController
class Parkinginfo(val seva: Parkingservice) {
    @GetMapping
    fun index(): List<Parkedinfo> = seva.findParkedinfo()

    @PostMapping
    fun display(@RequestBody parkedinfo: Parkedinfo) {
        seva.display(parkedinfo)
    }
}

private fun Parkingservice.findParkedinfo(): List<Parkedinfo> {
    return findParkedinfo()
}


@Service
class Parkingservice(val db: ParkingRepo) {
    fun checkStatus(): List<Parkedinfo> = db.findParkedinfo()

    fun display(parkedinfo: Parkedinfo) {

        db.save(parkedinfo)
    }

}

interface ParkingRepo : CrudRepository<Parkedinfo, String> {

    @Query("select * from Parkedinfo order by id desc")
    fun findParkedinfo(): List<Parkedinfo>

}

@Table("parkinginfo")
data class Parkedinfo(@Id val slot: String?, val vacancy: String?)
data class User(val emailid: String, val password: String)
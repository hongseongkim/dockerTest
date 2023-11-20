package com.example.bootmongo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class BootmongoApplication

@Document
data class City(
        @Id
        var id: String?,
        var name: String,
        var country: String,
        var populationInMillions: Float

)

data class CreateCityDto(
        var name: String,
        var country: String,
        var populationInMillions: Float

) {
    init {
        require(populationInMillions > 0,{"population이 0 이여야합니다"})
    }
}

interface CityRepository : MongoRepository<City, String>

@RestController
@RequestMapping("cities")
class CityController(
        var cityRepository: CityRepository
) {
	@PostMapping
    fun create(@RequestBody request: CreateCityDto): City =
	cityRepository.save(
			City(
					id = null,
					name = request.name,
					country = request.country,
					populationInMillions = request.populationInMillions
			)
	)


}

fun main(args: Array<String>) {
    runApplication<BootmongoApplication>(*args)
}

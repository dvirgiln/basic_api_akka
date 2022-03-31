package co.copper.test.dto

import co.copper.test.model.{Postman, PostmanHeader}
import com.fasterxml.jackson.annotation.{JsonCreator, JsonIgnoreProperties, JsonProperty}

import scala.collection.JavaConverters._
import scala.language.implicitConversions

@JsonCreator()
@JsonIgnoreProperties(ignoreUnknown = true)
case class PostmanDto(
    @JsonProperty("headers") headers: Map[String, String],
    @JsonProperty("url") url: String
)

object PostmanDto {
  implicit def fromDtoToEntity(dto: PostmanDto): Postman = {
    val value = new Postman()
    value.url = dto.url
    value.headers = dto.headers
      .map { entry =>
        val header = new PostmanHeader
        header.key = entry._1
        header.value = entry._2
        header.postman = value
        header
      }
      .toList
      .asJava
    value
  }

  implicit def fromEntityToDto(entity: Postman): PostmanDto = {
    PostmanDto(
      entity.headers.asScala.map(a => a.key -> a.value).toMap,
      entity.url
    )
  }
}

package co.copper.test.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.module.scala.{ClassTagExtensions, DefaultScalaModule}
import org.springframework.context.annotation.{Bean, Primary}
import org.springframework.stereotype.Component

@Component
class ScalaJacksonMapper {

  @Bean
  @Primary
  def buildScalaMapper(): ObjectMapper = {
    JsonMapper
      .builder()
      .addModule(DefaultScalaModule)
      .build() :: ClassTagExtensions
  }

}

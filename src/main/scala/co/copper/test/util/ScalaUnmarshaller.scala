package co.copper.test.util

import akka.http.javadsl.marshallers.jackson.Jackson.unmarshaller
import akka.http.scaladsl.model.HttpEntity
import akka.http.scaladsl.unmarshalling.Unmarshaller
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.module.scala.{ClassTagExtensions, DefaultScalaModule}

import scala.reflect._

object ScalaUnmarshaller {
  val mapper = JsonMapper
    .builder()
    .addModule(DefaultScalaModule)
    .build() :: ClassTagExtensions

  def unmarshall[T](implicit ct: ClassTag[T]): Unmarshaller[HttpEntity, T] = {
    unmarshaller(mapper, ct.runtimeClass.asInstanceOf[Class[T]]).asScala
  }

}

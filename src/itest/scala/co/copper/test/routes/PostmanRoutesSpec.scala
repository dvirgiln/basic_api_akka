package co.copper.test.routes

import akka.actor.ActorSystem
import akka.actor.testkit.typed.scaladsl.ActorTestKit
import akka.http.scaladsl.model.{ContentTypes, HttpRequest, StatusCodes}
import akka.http.scaladsl.testkit.{RouteTestTimeout, ScalatestRouteTest}
import co.copper.test.Application.ctx
import co.copper.test.ApplicationConfiguration
import co.copper.test.dto.PostmanDto
import co.copper.test.util.ScalaUnmarshaller.unmarshall
import com.fasterxml.jackson.databind.ObjectMapper
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.junit.JUnitRunner
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import com.sbuslab.http.directives.SbusDirectives
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.{AnnotationConfigApplicationContext, ComponentScan}
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.annotation.DirtiesContext.ClassMode
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.context.junit4.SpringRunner

import scala.concurrent.duration._
@SpringBootTest
@RunWith(classOf[JUnitRunner])
@ExtendWith(Array(classOf[SpringExtension]))
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class PostmanRoutesSpec extends AnyWordSpec
  with Matchers
  with ScalaFutures
  with ScalatestRouteTest {

  val ctx = new AnnotationConfigApplicationContext(classOf[ApplicationConfiguration])

  val routes: PostmanRoutes = ctx.getBean(classOf[PostmanRoutes])

    lazy val testKit = ActorTestKit()
    implicit def typedSystem = testKit.system
    override def createActorSystem(): akka.actor.ActorSystem =
      testKit.system.classicSystem
  implicit def default(implicit system: ActorSystem) = RouteTestTimeout(5.seconds)

  "Postman Routes" should {
    "return not found status when there are no items in db" in {
      val request = HttpRequest(uri = "/postman")
      request  ~> routes.anonymousRoutes ~> check {
        status should ===(StatusCodes.NotFound)
        contentType should ===(ContentTypes.`application/json`)
      }
    }

    "return and item once something is being post" in {
      import co.copper.test.util.ImplicitConversions._
      val request = Post("/postman")
      request  ~> routes.anonymousRoutes ~> check {
        status should ===(StatusCodes.OK)
        contentType should ===(ContentTypes.`application/json`)
        val result = entityAs[PostmanDto]
        result.headers should not be empty
        result.url should not be empty
      }
    }
  }

}

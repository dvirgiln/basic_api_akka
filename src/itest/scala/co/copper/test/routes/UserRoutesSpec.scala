package co.copper.test.routes

import akka.actor.ActorSystem
import akka.actor.testkit.typed.scaladsl.ActorTestKit
import akka.http.scaladsl.model.{ContentTypes, HttpRequest, StatusCodes}
import akka.http.scaladsl.testkit.{RouteTestTimeout, ScalatestRouteTest}
import co.copper.test.ApplicationConfiguration
import co.copper.test.dto.{PostmanDto, UsersDto}
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.runner.RunWith
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.junit.JUnitRunner
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.annotation.DirtiesContext.ClassMode
import org.springframework.test.context.junit.jupiter.SpringExtension

import scala.concurrent.duration._

@SpringBootTest
@RunWith(classOf[JUnitRunner])
@ExtendWith(Array(classOf[SpringExtension]))
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class UserRoutesSpec extends AnyWordSpec
  with Matchers
  with ScalaFutures
  with ScalatestRouteTest {

  val ctx = new AnnotationConfigApplicationContext(classOf[ApplicationConfiguration])

  val routes: UserRoutes = ctx.getBean(classOf[UserRoutes])

    lazy val testKit = ActorTestKit()
    implicit def typedSystem = testKit.system
    override def createActorSystem(): akka.actor.ActorSystem =
      testKit.system.classicSystem
  implicit def default(implicit system: ActorSystem) = RouteTestTimeout(5.seconds)
  import co.copper.test.util.ImplicitConversions._

  "User Routes" should {
    "return not found status when there is no user in db" in {
      val request = HttpRequest(uri = "/users/1")
      request  ~> routes.anonymousRoutes ~> check {
        status should ===(StatusCodes.NotFound)
        contentType should ===(ContentTypes.`application/json`)
      }
    }

    "save new users in db" in {
      val request = Post("/users")
      request  ~> routes.anonymousRoutes ~> check {
        status should ===(StatusCodes.OK)
        contentType should ===(ContentTypes.`application/json`)
      }
    }

    /*"get all the users" in {
      val request = Post("/postman/users")
      request  ~> routes.anonymousRoutes ~> check {
        status should ===(StatusCodes.OK)
        contentType should ===(ContentTypes.`application/json`)
        val result = entityAs[UsersDto]
        result.users should not be empty
      }
    }*/
  }

}

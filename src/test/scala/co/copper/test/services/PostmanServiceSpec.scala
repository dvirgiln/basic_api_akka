package co.copper.test.services

import co.copper.test.Application.ctx
import co.copper.test.ApplicationConfiguration
import org.scalatest.wordspec.AnyWordSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner
import org.springframework.context.annotation.{AnnotationConfigApplicationContext, ComponentScan}
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.annotation.DirtiesContext.ClassMode
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.context.junit4.SpringRunner

import scala.concurrent.ExecutionContext

@SpringBootTest
@RunWith(classOf[JUnitRunner])
@ExtendWith(Array(classOf[SpringExtension]))
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class PostmanServiceSpec extends AnyWordSpec{

  val ctx = new AnnotationConfigApplicationContext(classOf[ApplicationConfiguration])
  val mapper: ObjectMapper = ctx.getBean(classOf[ObjectMapper])

  "" should {
    "dssdf" in {
      succeed
    }
  }
}

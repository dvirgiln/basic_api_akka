package co.copper.test.routes

import akka.http.scaladsl.model.StatusCodes
import co.copper.test.services.UserService
import com.sbuslab.http.RestRoutes
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import scala.util.{Failure, Success}

@Component
@Autowired
class UserRoutes (
                   userService: UserService
                 ) extends RestRoutes {

  def anonymousRoutes =
    pathPrefix("users") {
      concat(
        path(Segment) { id =>
            get {
              onComplete(userService.getById(id.toLong)) { result =>
                result match {
                  case Failure(exception) => failWith(exception)
                  case Success(None) => complete(StatusCodes.NotFound)
                  case Success(Some(value)) => complete(value)
                }
              }
            }
        }
        ~
          concat(pathEnd {
            post {
              onComplete(userService.post) { result =>
                result match {
                  case Success(None) =>
                    complete(StatusCodes.OK)
                  case Success(Some(error)) => failWith(new Exception(error.message))
                  case Failure(exception)       => failWith(exception)
                }
              }
            }
          })
      )
    }

}
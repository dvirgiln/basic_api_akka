package co.copper.test.routes

import akka.http.scaladsl.model.StatusCodes
import co.copper.test.dto.Error
import co.copper.test.services.PostmanService
import com.sbuslab.http.RestRoutes
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import scala.util.{Failure, Success}

@Component
@Autowired
class PostmanRoutes(
    deribitService: PostmanService
) extends RestRoutes {

  def anonymousRoutes =
    pathPrefix("postman") {
      concat(pathEnd {
        get {
          onComplete(deribitService.get) { result =>
            result match {
              case Success(Some(value)) => complete(value)
              case Success(None)        => complete(StatusCodes.NotFound)
              case Failure(exception)   => failWith(exception)
            }
          }
        } ~
          post {
            onComplete(deribitService.post) { result =>
              result match {
                case Success(Left(Error(statusCode, message))) =>
                  complete(statusCode, message)
                case Success(Right(response)) => complete(response)
                case Failure(exception)       => failWith(exception)
              }
            }
          }
      })
    }

}

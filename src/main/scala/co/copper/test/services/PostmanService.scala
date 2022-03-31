package co.copper.test.services

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpMethods, HttpRequest, HttpResponse, StatusCodes}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer
import co.copper.test.dto.{Error, PostmanDto}
import co.copper.test.model.Postman
import co.copper.test.repositories.PostmanRepository
import com.sbuslab.utils.Logging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import scala.concurrent.{ExecutionContext, Future}

@Service
@Autowired
class PostmanService(repository: PostmanRepository)(implicit
    ec: ExecutionContext,
    system: ActorSystem,
    materializer: ActorMaterializer
) extends Logging {
  private val url = "https://postman-echo.com/get"
  import co.copper.test.util.ImplicitConversions._

  def get: Future[Option[PostmanDto]] = {
    val latest = repository.findFirstByOrderByIdDesc
    Future(latest.map(a => a))
  }

  def post: Future[Either[Error, PostmanDto]] = {
    val req = HttpRequest(uri = url, method = HttpMethods.GET)
    val response: Future[HttpResponse] = Http().singleRequest(req)

    response.flatMap {
      case r @ HttpResponse(StatusCodes.OK, _, _, _) =>
        val dtoFuture = Unmarshal(r.entity).to[PostmanDto]
        dtoFuture.map { dto =>
          val entity: Postman = dto
          val saved = repository.save(entity)
          Right(saved)
        }
      case r @ HttpResponse(statusCode, _, _, _) =>
        Unmarshal(r).to[String].map(a => Left(Error(statusCode.intValue(), a)))
    }
  }

}

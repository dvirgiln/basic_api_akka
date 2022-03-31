package co.copper.test.services

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpMethods, HttpRequest, HttpResponse, StatusCodes}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer
import co.copper.test.dto.external.{RandomUser, RandomUsers}
import co.copper.test.dto.{Error, UserDto}
import co.copper.test.model.User
import co.copper.test.repositories.UserRepository
import com.sbuslab.utils.Logging
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import scala.concurrent.{ExecutionContext, Future}


@Service
@Autowired
class UserService(repository: UserRepository)(implicit
                                              ec: ExecutionContext,
                                              system: ActorSystem,
                                              materializer: ActorMaterializer
) extends Logging {
  lazy val persistUserActor: ActorRef = system.actorOf(Props(new UserPersistActor((repository))), "persistUserActor")
  private val url = "https://randomuser.me/api?results=20"
  import co.copper.test.util.ImplicitConversions._
  private val logger = LoggerFactory.getLogger(classOf[UserPersistActor])

  def getById(id: Long): Future[Option[UserDto]] = {
    val latest = repository.findById(id)
    val value = if(latest.isPresent) Some(latest.get()) else None
    Future(value.map(a => a))
  }

  def post: Future[Option[Error]] = {
    val req = HttpRequest(uri = url, method = HttpMethods.GET)
    val response: Future[HttpResponse] = Http().singleRequest(req)

    response.flatMap {
      case r @ HttpResponse(StatusCodes.OK, _, _, _) =>
        val usersFuture = Unmarshal(r.entity).to[RandomUsers]
        usersFuture.map { users =>
          val entities: List[User] = users.results.map(a => RandomUser.fromExternalToEntity(a))
          entities.foreach(entity => persistUserActor ! SaveUser(entity))
          None
        }
      case r @ HttpResponse(statusCode, _, _, _) =>
        Unmarshal(r).to[String].map(a => Some(Error(statusCode.intValue(), "Error in the response")))
    }
  }
}




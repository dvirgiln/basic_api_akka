package co.copper.test.services

import akka.actor.{Actor, ActorLogging, Props}
import co.copper.test.model.User
import co.copper.test.repositories.UserRepository
import org.slf4j.LoggerFactory

import java.util.UUID

sealed trait UserCommand
case class SaveUser(user: User)

object UserPersistActor {
  final case object GetUsers
}

class UserPersistActor(repository: UserRepository)extends Actor with ActorLogging {
  private val logger = LoggerFactory.getLogger(classOf[UserPersistActor])
  def receive: Receive = {
    case SaveUser(user) =>
      logger.info(s"Saving user $user")
      repository.save(user)
  }

}

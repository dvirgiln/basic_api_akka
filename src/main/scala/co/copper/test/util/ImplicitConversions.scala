package co.copper.test.util

import co.copper.test.dto.external.RandomUsers
import co.copper.test.dto.{PostmanDto, UserDto, UsersDto}
import co.copper.test.util.ScalaUnmarshaller.unmarshall

object ImplicitConversions {
  implicit val postmanResponseUnMarshaller = unmarshall[PostmanDto]

  implicit val userResponseUnMarshaller = unmarshall[UserDto]
  implicit val usersResponseUnMarshaller = unmarshall[UsersDto]



  implicit val externalUsersResponseUnMarshaller = unmarshall[RandomUsers]

}

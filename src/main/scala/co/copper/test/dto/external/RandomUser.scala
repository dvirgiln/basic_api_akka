package co.copper.test.dto.external

import co.copper.test.model.User
import com.fasterxml.jackson.annotation.{JsonCreator, JsonIgnoreProperties, JsonProperty}

@JsonCreator()
@JsonIgnoreProperties(ignoreUnknown = true)
case class UserName(
    @JsonProperty("first") first: String,
    @JsonProperty("last") last: String
)

@JsonCreator()
@JsonIgnoreProperties(ignoreUnknown = true)
case class Login(@JsonProperty("password") password: String)

@JsonCreator()
@JsonIgnoreProperties(ignoreUnknown = true)
case class RandomUser(
    @JsonProperty("name") name: UserName,
    @JsonProperty("email") email: String,
    @JsonProperty("login") login: Login
)

@JsonCreator()
@JsonIgnoreProperties(ignoreUnknown = true)
case class RandomUsers(@JsonProperty("results") results: List[RandomUser])


object RandomUser{
  implicit def fromExternalToEntity(external: RandomUser): User = {
    val user = new User()
    user.firstName = external.name.first
    user.lastName = external.name.last
    user.email = external.email
    user.password = external.login.password
    user
  }
}

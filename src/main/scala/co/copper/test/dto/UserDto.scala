package co.copper.test.dto

import co.copper.test.model.User

case class UserDto(id:Long, first: String, last: String, email: String, password: String)

object UserDto{
  implicit def fromEntityToDto(entity: User): UserDto = {
    UserDto(entity.id, entity.firstName, entity.lastName, entity.email, entity.password)
  }

  implicit def fromDtoToEntity(dto: UserDto): User = {
    val user = new User()
    user.id = dto.id
    user.firstName = dto.first
    user.lastName = dto.last
    user.email = dto.email
    user.password = dto.password
    user
  }
}

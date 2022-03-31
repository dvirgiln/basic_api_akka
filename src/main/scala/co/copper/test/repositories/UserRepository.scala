package co.copper.test.repositories

import co.copper.test.model.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
trait UserRepository extends CrudRepository[User, java.lang.Long]

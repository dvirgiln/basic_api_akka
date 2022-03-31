package co.copper.test.repositories

import co.copper.test.model.Postman
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
@Repository
trait PostmanRepository extends CrudRepository[Postman, java.lang.Long] {
  //@Query(value = "SELECT 1 * FROM postman u order by id desc", nativeQuery = true)
  def findFirstByOrderByIdDesc: Option[Postman]
}

package co.copper.test.model

import javax.persistence._
import javax.validation.constraints.NotEmpty
import scala.beans.BeanProperty

@Entity
@Table(name = "random_user")
class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @BeanProperty
  var id: Long = _

  @BeanProperty()
  @NotEmpty
  @Column(name = "first_name")
  var firstName: String = _

  @BeanProperty
  @NotEmpty
  @Column(name = "last_name")
  var lastName: String = _

  @BeanProperty
  @NotEmpty
  var email: String = _

  @BeanProperty
  @NotEmpty
  var password: String = _


  override def toString = s"User($id, $firstName, $lastName, $email, $password)"
}

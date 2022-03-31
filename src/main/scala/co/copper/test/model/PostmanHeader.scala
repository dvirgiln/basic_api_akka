package co.copper.test.model

import javax.persistence._
import javax.validation.constraints.NotEmpty
import scala.beans.BeanProperty

@Entity
@Table(name = "postman_header")
class PostmanHeader {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @BeanProperty
  var id: Long = _

  @BeanProperty
  @NotEmpty
  var key: String = _

  @BeanProperty
  @NotEmpty
  var value: String = _

  @ManyToOne
  @JoinColumn(name = "postman_id", nullable = false)
  var postman: Postman = _
}

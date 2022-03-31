package co.copper.test.model

import javax.persistence._
import javax.validation.constraints.NotEmpty
import scala.beans.BeanProperty

@Entity
@Table(name = "postman")
class Postman {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @BeanProperty
  var id: Long = _

  @BeanProperty
  @NotEmpty
  var url: String = _

  @OneToMany(
    mappedBy = "postman",
    fetch = FetchType.EAGER,
    cascade = Array(CascadeType.ALL)
  )
  var headers: java.util.List[PostmanHeader] = _
}

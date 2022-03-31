package co.copper.test

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.sbuslab.utils.config.DatabaseConfiguration
import com.typesafe.config.Config
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.{Bean, ComponentScan, Configuration}
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

import scala.concurrent.ExecutionContext

@Configuration
@ComponentScan(Array("co.copper.test"))
@EnableJpaRepositories(Array("com.sbuslab", "co.copper.test"))
class ApplicationConfiguration extends DatabaseConfiguration {
  @Bean
  @Autowired
  def getActorSystem(config: Config) = ActorSystem("copper", config)

  @Bean
  @Autowired
  def getExecutionContext(system: ActorSystem): ExecutionContext =
    system.dispatcher

  @Bean
  @Autowired
  def getActorMaterializer(system: ActorSystem) = ActorMaterializer()(system)

  protected def getDbConfig = getConfig.getConfig("copper.test.db")
}

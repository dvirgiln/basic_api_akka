# test-assessment

Installation requirements:

* JDK ver. 11 and above
* Gradle ver. 5 and above
* Docker (for the postgres container)

To run the service, execute the command `gradle run`.
Run the postgres container with the use of the Makefile.

In this template project, we prepared Spring Java and Scala templates.
To interact with HTTP APIs, we have set up [AsyncHttpClient](https://github.com/AsyncHttpClient/async-http-client). 
To work with databases, we have set up Spring NamedParameterJdbcTemplate.
You can choose any other framework you prefer, but you have to prepare all corresponding assets prior to the beginning of the assessment.

During the online assessment, you will create a small service interacting with an external API, storing some data in a database and providing HTTP endpoints to interact with the service itself.
You will implement this service step by step during the online session. 
**Before** this session, you should set up your environment (IDE, docker, etc). You should also share your screen during the test assessment, so we can discuss and review your actions. 

### THINGS DONE
1. API was replying directly String. There was no Json Conversion.
   1. Normally in Scala projects I use Akka Spray to do the marshalling/unmarshalling to/from json to case class
   2. I adapted the code to use Jackson marshalling, as it is one of the dependencies
   3. Convert the HttpResponse body from String to case class object
   4. The case class contains a Scala Map. By default Jackson does not have any serializer/deserializer for Scala collections
   5. Added dependency `jackson-module-scala` and inject a new ObjectMapper bean in Spring.
   6. Added ImplicitConversions.
2. Persistence:
   1. Initially I was thinking to use Slick, as it is a Scala based library. 
   2. Then looking into the dependencies from `sbuslab` repository I realised that JPA was configured.
      1. As well Flyway it is configured in the class `DatabaseMigration`
      2. I decided to use JPA as everything was setup.
      3. I wanted to avoid write SQL code using the JDBC template.
      4. To make it work I had to debug the code and identify that I needed to add the following line in the config file:
         ``` packages-to-scan = ["co.copper.test.model"]```
3. Tests:
   1. ScalaTest is not added as dependency. 
   2. Add Scala test. An error appears related Lombok:
      ```https://stackoverflow.com/questions/66801256/java-lang-illegalaccesserror-class-lombok-javac-apt-lombokprocessor-cannot-acce```
   3. Then another error: ```Caused by: org.junit.platform.commons.PreconditionViolationException: Cannot create Launcher without at least one TestEngine;```
   4. Added junit dependency for this.
   5. As well this error: ```https://exerror.com/general-error-during-semantic-analysis-unsupported-class-file-major-version-60/#:~:text=major%20version%2060-,To%20Solve%20General%20error%20during%20semantic%20analysis%3A%20Unsupported%20class%20file,Deployment%20%7C%20Build%20Tools%20%7C%20Gradle.```
   6. Upgraded Gradle version.
   7. Included H2 Database
   8. Made the SpringBoot tests to work with scalatests specs. 
   9. To test the routes I used the `akka-http-testkit`

### ENDPOINTS
#### POSTMAN API
1. HTTP Get Latest postman record
`HTTP GET /test/postman`
 
2. HTTP Post lastest Postman record `HTTP POST /test/postman`

#### USERS API
1. HTTP Get User by ID: `HTTP GET /test/users/${id}`
2. HTTP Post to get 20 users: `HTTP POST /test/users`
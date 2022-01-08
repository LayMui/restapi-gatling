package restapi
import com.typesafe.config.ConfigFactory
import io.gatling.core.Predef._
import io.gatling.http.Predef._

import java.util.concurrent.atomic.AtomicInteger
import scala.concurrent.duration._
import scala.language.postfixOps

// import io.gatling.http.config.HttpProtocolBuilder.toHttpProtocol
// import io.gatling.http.request.builder.HttpRequestBuilder.toActionBuilder

class message extends Simulation{
    val config = ConfigFactory.load()
    val httpConf = http.baseUrl(config.getString("MESSAGE_BASE_URL"))
  

object Get {
  val getByID = exec(http("Get Single Message")
      .get("/taqelah/messages/2"))
}  

object Create {

  val messageFeeder = csv("message.csv").random  

  val newMessage = exec(http("Create New Message") // Here's an example of a POST request
       .post("/taqelah/messages/")
       .pause(1)
       .feed(messageFeeder)
       .body(StringBody("""{ "author": "CB", "message": "Speaking in tongue" }""")))
}
    
    val scn = scenario("CRUD on Message")
     .exec(Get.getByID)
    
   
    val user = scenario("Normal_Users") // For user
      .exec(Get.getByID)
      .exec(Create.newMessage)


    setUp(
       user.inject(atOnceUsers(10)),
   // scn.inject(
   //   nothingFor(5.seconds),
   //   atOnceUsers(10),
   //   rampUsersPerSec(150) to 200 during(1 minutes))
  ).protocols(httpConf.inferHtmlResources())


}

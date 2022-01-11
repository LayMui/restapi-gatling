package restapi
import com.typesafe.config.ConfigFactory
import io.gatling.core.Predef._
import io.gatling.http.Predef._

import java.util.concurrent.atomic.AtomicInteger
import scala.concurrent.duration._
import scala.language.postfixOps

class message extends Simulation{
    val config = ConfigFactory.load()
    val httpConf = http.baseUrl(config.getString("MESSAGE_BASE_URL"))
  

object Get {
  val getByID = exec(http("Get Single Message")
      .get("/taqelah/messages/2"))
}  

object Create {  
  val newMessage = exec(http("Create New Message") // Here's an example of a POST request
       .post("/taqelah/messages/")
       .body(StringBody("""{ "author": "CB", "message": "Speaking in tongue" }""")))
}
    
    val messageFeeder = csv("message.csv").random  
    val scn = scenario("CRUD on Message")
     .exec(Get.getByID)
     .feed(messageFeeder)
    
   
    val user = scenario("Normal_Users") // For user
      .exec(Get.getByID)
    //  .exec(Create.newMessage)


    setUp(
       user.inject(atOnceUsers(1)),
   // scn.inject(
   //   nothingFor(5.seconds),
   //   atOnceUsers(10),
   //   rampUsersPerSec(150) to 200 during(1 minutes))
  //).protocols(httpConf.inferHtmlResources())
    ).protocols(httpConf)


}

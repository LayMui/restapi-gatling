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
  
    val scn = scenario("CRUD on Message")

    .exec(http("Get Single Message")
      .get("/taqelah/messages/2"))
    
   

    setUp(
    scn.inject(
      nothingFor(5.seconds),
      atOnceUsers(10),
      rampUsersPerSec(150) to 200 during(1 minutes))
  ).protocols(httpConf.inferHtmlResources())


}

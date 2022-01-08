package restapi
import com.typesafe.config.ConfigFactory
import io.gatling.core.Predef._
import io.gatling.http.Predef._

import java.util.UUID
import java.util.concurrent.atomic.AtomicInteger
import scala.concurrent.duration._
import scala.language.postfixOps
import scala.util.Random

class message extends Simulation{

  val config = ConfigFactory.load()
    val httpConf = http.baseUrl(config.getString("MESSAGE_BASE_URL"))
    .header("Content-Type", "application/json"))


  
  val scn = scenario("CRUD on Message")

    .exec(http("Create Message")
      .post("/taqelah/messages")
      .body(StringBody(_ =>
        s"""
           |{
           |  "author": "Curry Blake",
           |  "message": "Speaking in tongue"
           |  }
           | }
          """.stripMargin)).asJson
      .check(status.is(201)))
   

   

  setUp(
    scn.inject(
      nothingFor(5.seconds),
      atOnceUsers(10),
      rampUsersPerSec(150) to 200 during(1 minutes))
  ).protocols(httpConf.inferHtmlResources())

}

import io.gatling.javaapi.core.*
import io.gatling.javaapi.core.CoreDsl.*
import io.gatling.javaapi.http.HttpDsl.*
import java.time.Duration

class BasicSimulation : Simulation() {
  private val httpProtocol = http
    .baseUrl("http://localhost:8080")
    .contentTypeHeader("application/json")

  private val scn = scenario("Transaction Scenario")
    .exec(
      http("Transaction Request")
        .post("/api/transaction")
        .body(StringBody("""{ "accountID": 1, "amount": 10.0, "currencyName": "EUR", "direction": "IN", "description": "Test" }"""))
        .check(status().shouldBe(201))
        .requestTimeout(Duration.ofMinutes(1))
    )

  init {
    setUp(
//      scn.injectOpen(atOnceUsers(1))
//      scn.injectOpen(atOnceUsers(10))
//      scn.injectOpen(atOnceUsers(100))
      scn.injectOpen(atOnceUsers(500))
//      scn.injectOpen(atOnceUsers(5000))
//      scn.injectOpen(atOnceUsers(10000))
    )
      .protocols(httpProtocol)
  }
}

package videogamedb.scriptfundamentals;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static  io.gatling.javaapi.core.CoreDsl.*;
import static  io.gatling.javaapi.http.HttpDsl.*;

public class MyFirstTest extends Simulation{

    // 1. HTTP Configutation
    private HttpProtocolBuilder httpProtocol = http
            .baseUrl("https://videogamedb.uk/api")
            .acceptHeader("application/json");

    // 2. Scenario Definition
    private ScenarioBuilder scn = scenario("My First Test")
            .exec(http("Get All Games")
                    .get("/videogame"));


    // 3. Load Simulation
    {
        setUp(
                scn.injectOpen(atOnceUsers(1))
        ).protocols(httpProtocol);
    }
}

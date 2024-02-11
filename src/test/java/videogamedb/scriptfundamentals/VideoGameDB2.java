package videogamedb.scriptfundamentals;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class VideoGameDB2 extends Simulation {

    private HttpProtocolBuilder httpProtocol = http
            .baseUrl("https://videogamedb.uk/api")
            .acceptHeader("application/json");

    private static ChainBuilder getAllVideoGames =
            repeat(3).on(
                    exec(http("Get All Video Games")
                    .get("/videogame")
                    .check(status().not(404), status().not(500)))
            );

    private static ChainBuilder getSpecificVideoGame =
            repeat(5,"myCounter").on(exec(http("Get Specific Video Game with id: #{myCounter}")
                    .get("/videogame/#{myCounter}")
                    .check(status().is(200)))
            );

    private ScenarioBuilder scn = scenario("Video Game DB")
            .exec(getAllVideoGames)
            .pause(5)
            .exec(getSpecificVideoGame)
            .pause(5)
            .repeat(2).on(exec(getAllVideoGames)
            );

    {
        setUp(
                scn.injectOpen(atOnceUsers(1))
        ).protocols(httpProtocol);
    }
}

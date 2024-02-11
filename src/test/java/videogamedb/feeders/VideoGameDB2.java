package videogamedb.feeders;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class VideoGameDB2 extends Simulation {
    private HttpProtocolBuilder httpProtocol = http
            .baseUrl("https://videogamedb.uk/api")
            .acceptHeader("application/json");

    private static FeederBuilder.FileBased<Object> jsonFeeder = jsonFile("data/GameJSONFile.json").random();

    private static ChainBuilder getSpecificGame =
            feed(jsonFeeder)
                    .exec(http("Get Video Game with name- #{name}")
                            .get("/videogame/#{id}")
                            .check(jmesPath("name").isEL("#{name}")));

    private ScenarioBuilder scn = scenario("Video Game DB")
            .repeat(10).on(
                    exec(getSpecificGame)
                            .pause(1)
            );

    {
        setUp(
                scn.injectOpen(atOnceUsers(1))
                        .protocols(httpProtocol)
        );
    }
}

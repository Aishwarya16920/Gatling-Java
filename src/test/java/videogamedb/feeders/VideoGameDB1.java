package videogamedb.feeders;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.http.HttpDsl.*;
import static io.gatling.javaapi.core.CoreDsl.*;

public class VideoGameDB1 extends Simulation {

    private HttpProtocolBuilder httpProtocol = http
            .baseUrl("https://videogamedb.uk/api")
            .acceptHeader("application/json");

    private static FeederBuilder.FileBased<String> csvFeeder = csv("data/gameCSVFile.csv").circular();

    private static ChainBuilder getSpecificGame =
            feed(csvFeeder)
                    .exec(http("Get Video Game with name- #{gameName}")
                    .get("/videogame/#{gameId}")
                            .check(jmesPath("name").isEL("#{gameName}")));

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

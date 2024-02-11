package videogamedb.scriptfundamentals;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class VideoGameDB3 extends Simulation{
    private HttpProtocolBuilder httpProtocol = http
            .baseUrl("https://videogamedb.uk/api")
            .acceptHeader("application/json")
            .contentTypeHeader("application/json");

    private static ChainBuilder authenticate =
            exec(http("Authenticate")
                    .post("/authenticate")
                    .body(StringBody(
                            "{\n" +
                                    "  \"password\": \"admin\",\n" +
                                    "  \"username\": \"admin\"\n" +
                                    "}"
                    ))
                    .check(jmesPath("token").saveAs("jwtToken")));

    private static ChainBuilder createNewGame =
            exec(http("Create New Game")
                    .post("/videogame")
                    .header("Authorization", "Bearer #{jwtToken}")
                    .body(StringBody(
                            "{\n" +
                                    "  \"category\": \"Platform\",\n" +
                                    "  \"name\": \"Mario\",\n" +
                                    "  \"rating\": \"Mature\",\n" +
                                    "  \"releaseDate\": \"2012-05-04\",\n" +
                                    "  \"reviewScore\": 85\n" +
                                    "}"
                    )));

    ScenarioBuilder scn = scenario("Video Game DB")
            .exec(authenticate)
            .exec(createNewGame);

    {
        setUp(
                scn.injectOpen(atOnceUsers(1))
                        .protocols(httpProtocol)
        );
    }
}

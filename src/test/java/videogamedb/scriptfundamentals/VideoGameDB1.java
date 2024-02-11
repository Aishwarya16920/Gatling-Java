package videogamedb.scriptfundamentals;

import io.gatling.javaapi.http.*;
import io.gatling.javaapi.core.*;

import java.time.Duration;
import java.util.List;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class VideoGameDB1 extends Simulation{

    private HttpProtocolBuilder httpProtocol = http
            .baseUrl("https://videogamedb.uk/api")
            .acceptHeader("application/json");

    private ScenarioBuilder scn = scenario("Video Game DB- 1")
            .exec(http("Get All Video Games- 1")
                    .get("/videogame")
                    .check(status().is(200))
                    .check(jsonPath("$[?(@.id==1)].name").is("Resident Evil 4"))
                    .check(jmesPath("[? id ==`1`].name").ofList().is(List.of("Resident Evil 4")))
                    .check(jmesPath("[1].id").saveAs("gameId")))
            .pause(5)
            .exec(http("Get Spcific Game- #{gameId}")
                    .get("/videogame/#{gameId}")
                    .check(status().in(200, 201, 202))
                    .check(jmesPath("name").is("Gran Turismo 3"))
                    .check(bodyString().saveAs("responseBody")))
            .pause(1, 10)
            .exec(
                    session -> {
                        System.out.println(session);
                        System.out.println("Game Id set to " + session.getString("gameId"));
                        return session;
                    }
            )
            .exec(
                    session -> {
                        System.out.println("Response Body: " + session.getString("responseBody"));
                        return session;
                    }
            )
            .exec(http("Get All Video Games- 2")
                    .get("/videogame")
                    .check(status().not((404)), status().not(500)))
            .pause(Duration.ofMillis(4000));
    {
        setUp(
                scn.injectOpen(atOnceUsers(1))
        ).protocols(httpProtocol);
    }
}

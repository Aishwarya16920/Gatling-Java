package videogamedb;

import java.time.Duration;
import java.util.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import io.gatling.javaapi.jdbc.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static io.gatling.javaapi.jdbc.JdbcDsl.*;

public class RecordedSimulationProxy extends Simulation {

  private HttpProtocolBuilder httpProtocol = http
    .baseUrl("https://videogamedb.uk")
    .inferHtmlResources(AllowList(), DenyList(".*\\.js", ".*\\.css", ".*\\.gif", ".*\\.jpeg", ".*\\.jpg", ".*\\.ico", ".*\\.woff", ".*\\.woff2", ".*\\.(t|o)tf", ".*\\.png", ".*\\.svg", ".*detectportal\\.firefox\\.com.*"))
    .acceptHeader("*/*")
    .acceptEncodingHeader("gzip, deflate, br")
    .userAgentHeader("PostmanRuntime/7.36.1");
  
  private Map<CharSequence, String> headers_0 = Map.of("Postman-Token", "b14b1e45-192a-442b-97c4-75231c83d77d");
  
  private Map<CharSequence, String> headers_1 = Map.ofEntries(
    Map.entry("Content-Type", "application/json"),
    Map.entry("Postman-Token", "0fec2636-ff08-498b-94b7-a4ab4dda2f90")
  );
  
  private Map<CharSequence, String> headers_2 = Map.ofEntries(
    Map.entry("Content-Type", "application/json"),
    Map.entry("Postman-Token", "e3cc80c2-eff9-4932-854b-21e4b395c228"),
    Map.entry("authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTcwNzEwMzUyMSwiZXhwIjoxNzA3MTA3MTIxfQ.bHBf3yFZUen7Dw7VEUWx7Wi65cK8GAW-ZDN5U0g9B7A")
  );
  
  private Map<CharSequence, String> headers_3 = Map.of("Postman-Token", "e060bbc9-8c4b-48c3-990f-9baf06dd1420");
  
  private Map<CharSequence, String> headers_4 = Map.ofEntries(
    Map.entry("Content-Type", "application/json"),
    Map.entry("Postman-Token", "c210b66b-9b7a-4f07-b60a-d69e55dd9d23"),
    Map.entry("authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTcwNzEwMzUyMSwiZXhwIjoxNzA3MTA3MTIxfQ.bHBf3yFZUen7Dw7VEUWx7Wi65cK8GAW-ZDN5U0g9B7A")
  );
  
  private Map<CharSequence, String> headers_5 = Map.ofEntries(
    Map.entry("Postman-Token", "99571554-440c-4ac5-8965-386b98e4ccee"),
    Map.entry("authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTcwNzEwMzUyMSwiZXhwIjoxNzA3MTA3MTIxfQ.bHBf3yFZUen7Dw7VEUWx7Wi65cK8GAW-ZDN5U0g9B7A")
  );


  private ScenarioBuilder scn = scenario("RecordedSimulationProxy")
    .exec(
      http("request_0")
        .get("/api/videogame")
        .headers(headers_0),
      pause(7),
      http("request_1")
        .post("/api/authenticate")
        .headers(headers_1)
        .body(RawFileBody("videogamedb/recordedsimulationproxy/0001_request.json")),
      pause(53),
      http("request_2")
        .post("/api/videogame")
        .headers(headers_2)
        .body(RawFileBody("videogamedb/recordedsimulationproxy/0002_request.json")),
      pause(9),
      http("request_3")
        .get("/api/videogame/2")
        .headers(headers_3),
      pause(7),
      http("request_4")
        .put("/api/videogame/3")
        .headers(headers_4)
        .body(RawFileBody("videogamedb/recordedsimulationproxy/0004_request.json")),
      pause(3),
      http("request_5")
        .delete("/api/videogame/2")
        .headers(headers_5)
    );

  {
	  setUp(scn.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
  }
}

Idea is to spike on non-standard JSON structures to parse them with Jackson within a Spring Boot app. This is done with a leaning towards Cloud Foundry API responses (initially just the Router API which exposes routing tables).

Features

* Pre Spring Boot 1.4 (JsonJacksonSpringBootPre14Tests)
 * See com.mc.criteria in src/test/java for different scenarios covered
 * In particular, TestCriteriaForCFRouterAPI

* Spring Boot 1.4 (JsonJacksonSpringBootSliceTests)
 * @JsonComponent using JsonComponentModule -> TrivialResponseJsonComponent.java
 * JsonObjectDeserializer.getRequiredNode ->  tbc

* TODO
 * @JsonView

References

* http://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-testing.html#boot-features-testing-spring-boot-applications-testing-autoconfigured-json-tests
* https://github.com/sdeleuze/spring-jackson-demo
* https://spring.io/blog/2014/12/02/latest-jackson-integration-improvements-in-spring

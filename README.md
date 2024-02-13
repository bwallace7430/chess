# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html#initialData=C4S2BsFMAIGEAtIGckCh0AcCGAnUBjEbAO2DnBElIEZVs8RCSzYKrgAmO3AorU6AGVIOAG4jUAEyzAsAIyxIYAERnzFkdKgrFIuaKlaUa0ALQA+ISPE4AXNABWAexDFoAcywBbTcLEizS1VZBSVbbVc9HGgnADNYiN19QzZSDkCrfztHFzdPH1Q-Gwzg9TDEqJj4iuSjdmoMopF7LywAaxgvJ3FC6wCLaFLQyHCdSriEseSm6NMBurT7AFcMaWAYOSdcSRTjTka+7NaO6C6emZK1YdHI-Qma6N6ss3nU4Gpl1ZkNrZwdhfeByy9hwyBA7mIT2KAyGGhuSWi9wuc0sAI49nyMG6ElQQA)

## IntelliJ Support

Open the project directory in IntelliJ in order to develop, run, and debug your code using an IDE.

## Maven Support

You can use the following commands to build, test, package, and run your code.

| Command                    | Description                                     |
| -------------------------- | ----------------------------------------------- |
| `mvn compile`              | Builds the code                                 |
| `mvn package`              | Run the tests and build an Uber jar file        |
| `mvn package -DskipTests`  | Build an Uber jar file                          |
| `mvn install`              | Installs the packages into the local repository |
| `mvn test`                 | Run all the tests                               |
| `mvn -pl shared tests`     | Run all the shared tests                        |
| `mvn -pl client exec:java` | Build and run the client `Main`                 |
| `mvn -pl server exec:java` | Build and run the server `Main`                 |

These commands are configured by the `pom.xml` (Project Object Model) files. There is a POM file in the root of the project, and one in each of the modules. The root POM defines any global dependencies and references the module POM files.

### Running the program using Java

Once you have compiled your project into an uber jar, you can execute it with the following command.

```sh
java -jar client/target/client-jar-with-dependencies.jar

♕ 240 Chess Client: chess.ChessPiece@7852e922
```

### HTTP Endpoints Sequence Diagram
A sequence diagram of handling the HTTP endpoints can be found at the following link:
https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDAEooDmSAzmFMARDQVqhFHXyFiwUhS5ckg4VVHiGU0gHFgAWxRKVuCY2nMAIv2ABBECBRzMAEwsAjYFxQwHzzJnS8Anly4RGjsMAAMAHQAHJjsUBAArtgwAMRowFT+rBzcvPwKaGkA7gAWSGBiiKikALQAfOSUNFAAXDAA2gAKAPJkACoAujAA9InuUAA6aADe45QZugA0MLhyxdAOKyjawEgIAL6YSpQwDTmcPHwCQs2i7VC5PJQAFPNQiygra1wbUFswHZ7BAASkwbEu+Ru+lsZ0a5jAVhsdi47XYKDAAFUJgB1CqlbELHQoN4TT5ghFI2xyOGeZztMgAUQAMoy4P0YO9PjAAGYJbSciaYSnWalcM7nCF5a6FGEodpoRIIBDgp5Q2V3WHnEXIuTtECPfgoQlQUlE5arNy-TYUiyilG0rztACSADkmSwOVzid8rX8AUD9jA3f0eoLaFKrgVFJqPNq7brUTADSgjZZEmBSmaPsTbYj7TTzk7g+7GZ7wzmLcAM6V+hAANboEuhmDVzPChNiiWNSPqmMiWztNu1hvoVWQmX95Ra84nNqtmt1xtoY7NU61c7VPztABM4XC0xmw6X6CO6AcPniSRSqWg0nRMGZEE4RVSZQqVWQflpc-a3T6QyjO4ciFIe3oWj8-pHHOP4ohqA7ysmhqVLI8iCNmnzbLs+xgqh8HTnG8KdiiaIYiaXR+psGG5h2+aJo69LkCybIcpBmy8vyFYwDiAASZaMhWny0VSDobo0eFTvcFYUesmyriBklavUEm3Ah7RUMAyBOJUMnWv8Lxsf8KzvLp-q4XBimETAOpivqyEoOmmbUboeYiYWjTFm6Hocseo5oMZZLEs2YbDsJBbimJTQKapBFDoufnyWhMWiN2TTUJQcWZieK4wZFW4YLu+6Hr5y5nmgF5xAkyRpPEKBNk+7BJMwb7lJUmD5cws5rvOHSmMx-SMsMIzAUl0wlWOuXiRZyWDoC5UqS841oOZ0VyrSNkkTAjzaBANCOVmS2ueFDHtH1rIDRxEACsO3F8SwAlLau6WwHlX4FTAe7hD456XlVN6PA4j55DAWi6OKLUfu1b2dVNz1-hojKASM7DEmN8XLk9LRwipcrtNwJoNeiDjOmgi3o+gK1JWtDQbXqMDoliuL4vt2Vk1lflHfRRaMUyrLsoJQV8ldC6Zrd-EiyOGO0xFXWrbG7TgWIOOxt2v4wLwiRK91tKg3o8swMgPC61wLxgrr1P1NLpFgJYyrG6bYVcx5PPMfzmkIPTxLOqYKzvpUJqYTAzgIKA9YBz6nu6K6gucSjuiO12kXm-r7vG5gycIar3VDnbxJcJj66btDhUHrMqd52VFVXtVqTYIkUDYAgHhwPZINBRDbUdbBcOdL0AxDXHKBo+zy6HoP0e6NB2sNMrakwPjEyEygxOk4diX4SlNPEXTDMmnimYs35bOSxTCeic7DKu16gW6Jd101mL90S9lZ+FrLVP64r6+WVnPca1rz0dbElxkhVMlRdYvHHjRDOBE4RW1bA4BwECoEuVfhFC+JZvKRxQBPDwIYwyDzQalGBUlB7e3TsAlWYk1ZkNMAXF6RcahgBLmPL2phK6-WvGkfwKBlQQGKDAAAUhAJARRdZpGDqHKGTDu4tD-F0TESNB7DxPmgQ8HU4AQAQNAFYtCp6AJntNEBC9KBLxXsfbKlMN5KXgbvJmB9yar0cZzROGDeYsQFrfIW99Ra8XFo9aWqVZ6xU8VrOWmdqHZ3VlATW9CgG6BAQAKxEWgCBGitE6Owd7M2lCImW23kmYAiCTR1ggYrFY6TtFQF0Wwlx586TtExF0UwlgLqDyYhyGYlToAHHDrfAAvKEx+AlaFxNekwkuvhyqcJrvEYA2RECplgMAbAjdCDRlfH7T8Miuo916v1QaowvBjKInRWyyYm54EpK4dwDtAnc1Ogc1sypsHim8dgohDzrJPPdhWN5nF3ifIwWdRGD0XnDj8Osm4-zhahVyow7cH19xTIvEAA

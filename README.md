# HTTP Server

This is a Java implementation of an HTTP server. The functionality of
this server is checked against the [Cob
Spec](https://github.com/8thlight/cob_spec).

## Installation

    git clone git@github.com:Maikon/http_server.git

    cd http-server


Assuming
[Maven](http://maven.apache.org/guides/getting-started/maven-in-five-minutes.html) is installed, in the project directory run:

    mvn clean package

From the `target` directory use the
`http-server-jar-with-dependencies.jar` jar which includes all the
runtime dependencies along with the server implementation:

    java -jar target/http-server-jar-with-dependencies.jar -p <specified-port> -d <cob-spec-directory>

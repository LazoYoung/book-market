# Book Market
This is a web server application backed by Spring boot.

## How to run
For Unix users, please follow these steps.

1. Grant execution permission to gradle wrapper file: `chmod u+x gradlew`
2. Run the application with a gradle task: `./gradlew bootRun`

## Changing server port
1. Locate file `application.properties` under directory `src/main/resources`
2. Find the line `server.port=80` and change it to your preference
3. Restart the application
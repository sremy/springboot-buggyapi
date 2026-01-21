# Buggy API Services

The SpringBoot Buggy API Service, has endpoints designed to simulate Java performance problems for tool testing and analysis.

---

```
SSH: git clone git@github.com:sremy/springboot-buggyapi.git
HTTP: git clone https://github.com/sremy/springboot-buggyapi.git
```

** Pre-Requisite **

To run this, you need the following installed and configured in your path.

1. Java 21 or higher
2. Maven 3.0 or higher

---

## Build the application
Please navigate to the folder and execute the following commands in your terminal.

1. cd springboot-buggyapi
2. mvn clean install


## Run the application
1. Run the class `com.ycrash.springboot.buggy.app.SpringBootBuggyApp` or the jar: `java -jar  target/ycrash.springboot*.jar`
with the JVM options `-Xmx1G -XX:MaxMetaspaceSize=500M 
   -XX:FlightRecorderOptions=stackdepth=128 
   --add-opens=java.base/java.lang=ALL-UNNAMED`
2. Open the application in the browser http://{your-host}:8090/swagger-ui.html to invoke the java performance problems using UI
or curl command example 
curl http://{your-host:8090}/v1/invoke/

Note. You can change the port from default(8090) in the application properties.

### Run the Bookstore service

- http://localhost:8090/v1/invoke/hashcode
- http://localhost:8090/v1/invoke/hashcode/random


### Run the concurrency service
http://localhost:8090/v1/invoke/concurrency


---

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License
[Apache license 2.0](https://www.apache.org/licenses/LICENSE-2.0)


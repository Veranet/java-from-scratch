## Java From Scratch

#### Lesson 1
```shell
javac hello-world-app\src\main\java\halatsiankova\javafromscratch\lesson1\HelloWorld.java -d classes  
java -cp classes halatsiankova.javafromscratch.lesson1.HelloWorld Vera
```
![alt text](hello-world-app/src/main/lesson-1.png "Run the Application")
#### Lesson 7
Clean the project and then build the project’s artifacts using Maven
```shell
mvn clean package
```
Running a JAR file with dependencies using Maven
```shell
java -jar bus-tickets/target/bus-tickets-1.0-SNAPSHOT-jar-with-dependencies.jar
```

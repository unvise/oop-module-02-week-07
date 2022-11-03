# oop-module-02-week-07
Проверка само запускаемого jar-файла
## Maven
Unix
```console
./mvnw clean install && java -jar target/*.jar
```
Windows
```console
.\mvnw clean install ; java -jar target\*.jar
```
## Gradle
Unix
```console
./gradlew clean build && java -jar build/libs/*.jar
```
Windows
```console
.\gradlew clean build ; java -jar build\libs\*.jar
```
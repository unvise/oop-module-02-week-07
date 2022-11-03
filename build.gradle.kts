plugins {
    id("java")
    application
}

group = "com.unvise"
version = "1.0-SNAPSHOT"
val mainClazz = "com.unvise.oop.FibonacciApp"

repositories {
    mavenCentral()
}

application {
    mainModule.set("com.unvise.oop")
    mainClass.set(mainClazz)
}

dependencies {
    implementation("org.jfree:jfreechart:1.5.3")
    implementation("org.apache.commons:commons-math3:3.6.1")
    compileOnly("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.projectlombok:lombok:1.18.24")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
    testLogging {
        events.add(org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED)
        events.add(org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED)
        events.add(org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED)
        events.add(org.gradle.api.tasks.testing.logging.TestLogEvent.STANDARD_OUT)
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.SHORT
        showExceptions = true
        showCauses = true
        showStackTraces = true
    }
}

tasks.withType<Jar> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest {
        attributes["Main-Class"] = mainClazz
    }
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    val sourcesMain = sourceSets.main.get()
    from(sourcesMain.output)
}
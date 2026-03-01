plugins {
    java
    eclipse
    idea
}

group = "com.github.eataborda.api"
version = "1.0-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
}

repositories {
    mavenCentral()
}

val allureVersion = "2.33.0"
val aspectJVersion = "1.9.25.1"
val restAssuredVersion = "6.0.0"
val junitJupiterVersion = "6.0.3"
val logbackVersion = "1.5.32"

val agent: Configuration by configurations.creating {
    isCanBeConsumed = true
    isCanBeResolved = true
}

defaultTasks ("clean", "test")

tasks.test {
    doFirst {
        jvmArgs = listOf("-javaagent:${agent.singleFile}")
    }
    val includedTags = System.getProperty("includeTags")
    val includedTagsSet = (includedTags != null)

    val excludedTags = System.getProperty("excludeTags")
    val excludeTagsSet = (excludedTags != null)

    if (includedTagsSet && excludeTagsSet) {
        useJUnitPlatform {
            includeTags(includedTags)
            excludeTags(excludedTags)
        }
    } else if (includedTagsSet && !excludeTagsSet) {
        useJUnitPlatform {
            includeTags(includedTags)
        }
    } else if (!includedTagsSet && excludeTagsSet) {
        useJUnitPlatform {
            excludeTags(excludedTags)
        }
    } else {
        useJUnitPlatform()
    }
    testLogging.showStandardStreams = true
    ignoreFailures = true
}

tasks.withType<Test> {
    systemProperty("includeTags", System.getProperty("includeTags"))
    systemProperty("excludeTags", System.getProperty("excludeTags"))
    systemProperty("USER", System.getProperty("USER"))
    systemProperty("PASSWORD", System.getProperty("PASSWORD"))
    systemProperty("AUTHORIZATION", System.getProperty("AUTHORIZATION"))
    systemProperty("showAllureAttachments", System.getProperty("showAllureAttachments", "true"))
    systemProperty("junit.jupiter.extensions.autodetection.enabled", true)
}

dependencies {
    agent("org.aspectj:aspectjweaver:${aspectJVersion}")
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitJupiterVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitJupiterVersion")
    testImplementation ("io.rest-assured:rest-assured:$restAssuredVersion")
    testImplementation("io.rest-assured:json-path:$restAssuredVersion")
    testImplementation ("io.rest-assured:json-schema-validator:$restAssuredVersion")
    testImplementation(platform("io.qameta.allure:allure-bom:$allureVersion"))
    testImplementation("io.qameta.allure:allure-junit5:$allureVersion")
    testImplementation("io.qameta.allure:allure-rest-assured:$allureVersion")
    testImplementation("ch.qos.logback:logback-classic:$logbackVersion")
    testImplementation("ch.qos.logback:logback-core:$logbackVersion")
    testImplementation("com.google.code.gson:gson:2.13.2")
    testImplementation("org.assertj:assertj-core:3.27.7")
}
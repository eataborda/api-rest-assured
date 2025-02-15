plugins {
    java
    eclipse
    idea
}

group = "com.github.eataborda.api"
version = "1.0-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(22))
    }
}

repositories {
    mavenCentral()
}

val allureVersion = "2.29.1"
val aspectJVersion = "1.9.22.1"

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
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.11.4")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.11.4")
    testImplementation ("io.rest-assured:rest-assured:5.5.1")
    testImplementation("io.rest-assured:json-path:5.5.1")
    testImplementation ("io.rest-assured:json-schema-validator:5.5.1")
    testImplementation(platform("io.qameta.allure:allure-bom:$allureVersion"))
    testImplementation("io.qameta.allure:allure-junit5:$allureVersion")
    testImplementation("io.qameta.allure:allure-rest-assured:$allureVersion")
    testImplementation("org.assertj:assertj-core:3.27.3")
    testImplementation("ch.qos.logback:logback-classic:1.5.16")
    testImplementation("ch.qos.logback:logback-core:1.5.16")
    testImplementation("com.google.code.gson:gson:2.12.1")
}
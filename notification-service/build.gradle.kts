plugins {
	java
	id("org.springframework.boot") version "3.5.1"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.tracker.ai"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

extra["springCloudVersion"] = "2025.0.0"

val firebaseVersion = "9.1.1"
val gsonVersion = "2.8.9"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
	implementation("org.springframework.kafka:spring-kafka")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.kafka:spring-kafka-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	testCompileOnly("org.projectlombok:lombok")
	implementation("io.micrometer:micrometer-registry-prometheus:1.15.1")

	implementation("com.google.firebase:firebase-admin:$firebaseVersion")
	implementation("com.google.code.gson:gson:$gsonVersion")

	implementation("com.twilio.sdk:twilio:10.9.2")
	implementation("com.twilio.sdk:twilio-java-sdk:6.3.0")
	implementation("org.springframework.kafka:spring-kafka")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

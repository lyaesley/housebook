import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    val kotlinVersion = "1.4.10"
	id("org.springframework.boot") version "2.4.0" apply false
	id("io.spring.dependency-management") version "1.0.10.RELEASE" apply false
	kotlin("jvm") version kotlinVersion
	kotlin("plugin.spring") version kotlinVersion apply false
	kotlin("plugin.jpa") version kotlinVersion apply false
    //추가
    kotlin("kapt") version kotlinVersion
    kotlin("plugin.allopen") version kotlinVersion

}

allOpen {
	annotation("javax.persistence.Entity")
	annotation("javax.persistence.Embeddable")
	annotation("javax.persistence.MappedSuperclass")
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}



allprojects {
	repositories {
		mavenCentral()
	}
}

subprojects {
	apply(plugin = "kotlin")
	apply(plugin = "kotlin-kapt")
	apply(plugin = "kotlin-spring")
	apply(plugin = "kotlin-jpa")
	apply(plugin = "kotlin-allopen")
	apply(plugin = "org.springframework.boot")
	apply(plugin = "io.spring.dependency-management")

	group = "com.lyae"
	version = "0.0.1-SNAPSHOT"
	java.sourceCompatibility = JavaVersion.VERSION_11

	val profile = if (project.hasProperty("profile")) project.property("profile").toString() else "local"

	sourceSets {
		main {
			resources {
				srcDir("src/main/resources")
				srcDir("src/main/resources-${profile}")
			}
		}
	}

	dependencies {
		//DB
		runtimeOnly("com.h2database:h2")

		//jpa
		implementation("org.springframework.boot:spring-boot-starter-data-jpa")

		//kotlin
		implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
		implementation("org.jetbrains.kotlin:kotlin-reflect")
		implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

		// logger
		implementation("org.slf4j:slf4j-api")

		//basic
		implementation("org.springframework.boot:spring-boot-starter")
		implementation("org.springframework.boot:spring-boot-starter-web")
		implementation("org.springframework.boot:spring-boot-starter-actuator")
		implementation("org.springframework.boot:spring-boot-devtools")
		kapt("org.springframework.boot:spring-boot-configuration-processor")
		annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
		testImplementation("org.springframework.boot:spring-boot-starter-test") {
			exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
		}
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}

	/*tasks.getByName<Test>("test"){
		systemProperty("spring.profiles.active", profile)
	}*/

	tasks.withType<KotlinCompile> {
		kotlinOptions {
			freeCompilerArgs = listOf("-Xjsr305=strict")
			jvmTarget = "11"
		}
	}

}

//참고로 :은 디렉토리 path를 얘기합니다. root 프로젝트에서 하위 프로젝트 사이에 계층이 하나 존재하기 때문에 추가하였습니다.
project(":housebook-common") {
	dependencies {
		//DB
		runtimeOnly("com.h2database:h2")

	}

	val jar: Jar by tasks
	val bootJar: BootJar by tasks

	bootJar.enabled = false
	jar.enabled = true
}

project(":housebook-api") {
	dependencies {
		implementation(project(":housebook-common"))

	}
}

project(":housebook-web") {
	dependencies {
		implementation(project(":housebook-common"))
	}
}



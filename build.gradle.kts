import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.2.4.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    id("com.adarshr.test-logger") version "2.0.0"
    id("de.jansauer.printcoverage") version "2.0.0"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
    jacoco
}
group = "hu.neuron"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11
repositories {
    mavenCentral()
}
dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("mysql:mysql-connector-java")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

    implementation("io.github.microutils:kotlin-logging-jvm:2.0.11")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.mockk:mockk:1.9")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.springframework.security:spring-security-test")
}

tasks.jacocoTestReport {
    reports {
        xml.isEnabled = true
    }
}
tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
    }
    withType<Test> {
        useJUnitPlatform()
    }

    check {
        dependsOn("jacocoTestCoverageVerification")
    }

    jacocoTestCoverageVerification {
        violationRules {
            rule {
                element = "CLASS"
                limit {
                    minimum = "0.0".toBigDecimal()
                }
            }
        }
    }

    getByName<JacocoReport>("jacocoTestReport") {
        reports {
            xml.isEnabled = true
            csv.isEnabled = false
            html.isEnabled = false
        }
        afterEvaluate {
            classDirectories.setFrom(files(classDirectories.files.map {
                fileTree(it) {
                    include("**/hu/neuron/**")
                    exclude(
                        "**/hu/neuron/KotlinDemoApplication"
                    )
                }
            }))
        }
    }
}
sourceSets {
    test {
        java {
            setSrcDirs(listOf("src/test/kotlin-intg", "src/test/kotlin-unit"))
        }
        withConvention(org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet::class){
            kotlin.setSrcDirs(listOf("src/test/kotlin-intg", "src/test/kotlin-unit"))
        }
    }
}
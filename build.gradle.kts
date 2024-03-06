plugins {
    kotlin("jvm") version "1.9.0"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        url = uri("https://repo.e-iceblue.cn/repository/maven-public/")
    }
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.apache.pdfbox:pdfbox:2.0.24")
    implementation("e-iceblue:spire.pdf.free:9.12.3")
    implementation("technology.tabula:tabula:1.0.5")
    implementation("org.apache.tika:tika-core:2.2.1")
    implementation("org.apache.tika:tika-parsers:2.2.1")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("MainKt")
}
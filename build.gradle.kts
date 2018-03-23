import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.2.30"
}

java.sourceSets["main"].srcDir("src")

repositories {
    jcenter()
}

dependencies {
    compile(kotlin("stdlib"))
    compile("org.slf4j", "slf4j-api")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

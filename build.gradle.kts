import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	maven
    kotlin("jvm") version "1.2.51"
}

group = "xerus.music"

java.sourceSets["main"].java.srcDir("src")

repositories {
    jcenter()
}

dependencies {
    compile(kotlin("stdlib"))
    compile("org.slf4j", "slf4j-api", "1.7.25")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

plugins {
    `java-library`
    id("de.eldoria.plugin-yml.bukkit") version "0.7.1"
    id("com.gradleup.shadow") version "9.0.0"
}

repositories {
    mavenCentral()
    gradlePluginPortal()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.1-R0.1-SNAPSHOT")
    implementation("org.bstats:bstats-bukkit:3.0.2")
}

group = "uk.firedev"
version = project.property("project-version") as String
description = "Show your held item in chat"
java.sourceCompatibility = JavaVersion.VERSION_21

bukkit {
    name = rootProject.name
    version = project.version.toString()
    main = "uk.firedev.basicshowitem.BasicShowItem"
    apiVersion = "1.21"
    author = "FireML"
    description = project.description.toString()

    permissions {
        register("basicshowitem.use") {
            description = "Allows the player to show their held item in chat"
            default = BukkitPluginDescription.Permission.Default.TRUE
        }
    }
}

tasks {
    build {
        dependsOn(shadowJar)
    }
    shadowJar {
        archiveBaseName.set(project.name)
        archiveVersion.set(project.version.toString())
        archiveClassifier.set("")
        minimize()

        relocate("org.bstats", "uk.firedev.basicshowitem.libs.bstats")
    }
    jar {
        enabled = false
    }
    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }
}

plugins {
    idea
    `java-library`
    id("net.neoforged.moddev.legacyforge") version "2.0.91"
}

val modId = property("mod_id") as String
val minecraft = stonecutter.current.version
val loader = stonecutter.current.project.substringAfterLast('-')
val mixinConfig = "$modId.mixins.json"
val refmap = "$modId.refmap.json"

version = property("mod_version") as String
group = property("mod_group_id") as String

repositories {
    mavenCentral()
}

base {
    archivesName = "$modId-$loader-$minecraft"
}

java.toolchain.languageVersion = JavaLanguageVersion.of(Targets.java(minecraft))

legacyForge {
    version = property("forge_version") as String

    parchment {
        mappingsVersion = property("parchment_mappings_version") as String
        minecraftVersion = property("parchment_minecraft_version") as String
    }

    runs {
        register("client") {
            client()
            findProperty("quickPlay")?.let { programArguments.addAll("--quickPlaySingleplayer", it.toString()) }
        }

        configureEach {
            // A forked JVM is not attached to the console, so on a Japanese Windows it falls back to CP932.
            systemProperty("file.encoding", "UTF-8")
            systemProperty("stdout.encoding", "UTF-8")
            systemProperty("stderr.encoding", "UTF-8")
            logLevel = org.slf4j.event.Level.DEBUG
        }
    }

    mods {
        register(modId) {
            sourceSet(sourceSets.main.get())
        }
    }
}

mixin {
    add(sourceSets.main.get(), refmap)
    config(mixinConfig)
}

dependencies {
    annotationProcessor("org.spongepowered:mixin:0.8.5:processor")
    compileOnly("org.jetbrains:annotations:24.1.0")
    testImplementation("org.junit.jupiter:junit-jupiter:${property("junit_version")}")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

sourceSets.test {
    compileClasspath += sourceSets.main.get().compileClasspath
    runtimeClasspath += sourceSets.main.get().runtimeClasspath
}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    manifest.attributes("MixinConfigs" to mixinConfig)
}

val modMetadata = mapOf(
    "minecraft_version" to minecraft,
    "minecraft_version_range" to property("minecraft_version_range"),
    "loader_dependency_range" to property("loader_dependency_range"),
    "loader_version_range" to property("loader_version_range"),
    "mod_id" to modId,
    "mod_name" to property("mod_name"),
    "mod_license" to property("mod_license"),
    "mod_version" to property("mod_version"),
    "mod_authors" to property("mod_authors"),
    "mod_description" to property("mod_description"),
    "mixin_java" to "JAVA_${Targets.java(minecraft)}",
    "resource_pack_format" to Targets.resourcePackFormat(minecraft),
    "data_pack_format" to Targets.dataPackFormat(minecraft),
)

val generateModMetadata = tasks.register<ProcessResources>("generateModMetadata") {
    inputs.properties(modMetadata)
    expand(modMetadata)
    from(rootProject.file("src/main/templates/$loader"))
    into("build/generated/sources/modMetadata")
}
sourceSets.main {
    resources.srcDir(generateModMetadata)
}
legacyForge.ideSyncTask(generateModMetadata)

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-Xlint:deprecation")
}

idea {
    module {
        isDownloadSources = true
        isDownloadJavadoc = true
    }
}

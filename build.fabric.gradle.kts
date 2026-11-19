import groovy.json.JsonOutput

plugins {
    `java-library`
    id("dev.architectury.loom") version "1.17.493"
}

val modId = property("mod_id") as String
val minecraft = stonecutter.current.version
val loader = stonecutter.current.project.substringAfterLast('-')

version = property("mod_version") as String
group = property("mod_group_id") as String

base {
    archivesName = "$modId-$loader-$minecraft"
}

java.toolchain.languageVersion = JavaLanguageVersion.of(Targets.java(minecraft))

repositories {
    maven("https://maven.parchmentmc.org/")
    maven("https://maven.terraformersmc.com/releases/")
    mavenCentral()
}

dependencies {
    minecraft("com.mojang:minecraft:$minecraft")
    mappings(loom.layered {
        officialMojangMappings()
        parchment("org.parchmentmc.data:parchment-${property("parchment_minecraft_version")}:${property("parchment_mappings_version")}@zip")
    })
    modImplementation("net.fabricmc:fabric-loader:${property("fabric_loader_version")}")
    modCompileOnly("com.terraformersmc:modmenu:${property("modmenu_version")}") {
        isTransitive = false
    }
    // Mod Menu needs Fabric API; both are only in the dev client, so the config screen can be opened there.
    modLocalRuntime("com.terraformersmc:modmenu:${property("modmenu_version")}") {
        isTransitive = false
    }
    modLocalRuntime("net.fabricmc.fabric-api:fabric-api:${property("fabric_api_version")}") {
        exclude(group = "net.fabricmc", module = "fabric-loader")
    }
    compileOnly("org.jetbrains:annotations:24.1.0")
    testImplementation("org.junit.jupiter:junit-jupiter:${property("junit_version")}")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    if (Targets.java(minecraft) < 16) {
        annotationProcessor("com.pkware.jabel:jabel-javac-plugin:1.0.1-2")
        compileOnly("com.pkware.jabel:jabel-javac-plugin:1.0.1-2")
        testAnnotationProcessor("com.pkware.jabel:jabel-javac-plugin:1.0.1-2")
        testCompileOnly("com.pkware.jabel:jabel-javac-plugin:1.0.1-2")
    }
}

loom {
    mixin {
        useLegacyMixinAp = true
        defaultRefmapName = "$modId.refmap.json"
        add(sourceSets.main.get(), "$modId.refmap.json")
    }
}

tasks.test {
    useJUnitPlatform()
}

val modMetadata = mapOf(
    "minecraft_version_range" to property("minecraft_version_range"),
    "loader_version_range" to property("loader_version_range"),
    "java_version" to Targets.java(minecraft),
    "mixin_java" to "JAVA_${Targets.java(minecraft)}",
    "mod_id" to modId,
    "mod_name" to property("mod_name"),
    "mod_license" to property("mod_license"),
    "mod_version" to property("mod_version"),
    "mod_authors" to JsonOutput.toJson(property("mod_authors")),
    "mod_description" to JsonOutput.toJson(property("mod_description")),
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

tasks.withType<JavaCompile>().configureEach {
    // Jabel lets the source use newer syntax while the bytecode stays at the Java this Minecraft runs on.
    javaCompiler = javaToolchains.compilerFor { languageVersion = JavaLanguageVersion.of(21) }
    sourceCompatibility = "21"
    options.release = Targets.java(minecraft)
    options.encoding = "UTF-8"
    options.compilerArgs.add("-Xlint:deprecation")
}

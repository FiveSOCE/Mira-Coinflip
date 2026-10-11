plugins { java }
group = "com.fivesoce.mira"
version = "0.1.0-dev"
repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://jitpack.io")
}
dependencies {
    compileOnly("io.papermc.paper:paper-api:26.2.build.+")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7") {
        exclude(group = "org.bukkit", module = "bukkit")
    }
}
java { toolchain.languageVersion.set(JavaLanguageVersion.of(25)) }
tasks.jar { archiveBaseName.set("Mira-Coinflip-26.2") }

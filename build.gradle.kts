plugins { java }
group = "com.fivesoce.mira"
version = "0.1.0-dev"
repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://jitpack.io")
}
dependencies {
    compileOnly("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7")
}
java { toolchain.languageVersion.set(JavaLanguageVersion.of(17)) }
tasks.jar { archiveBaseName.set("Mira-Coinflip-1.18") }

plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

repositories {
    google()
    mavenCentral()
    maven { setUrl("https://jitpack.io") }
    maven {
        name = "backbaseRepo"
        url = uri("https://repo.backbase.com/repo")
        credentials(PasswordCredentials::class)
    }
    gradlePluginPortal()
}

val navigationVersion = thirdPartyLibs.versions.navigation.get()

dependencies {
    implementation(gradleApi())
    // buildSrc kotlin-dsl (Gradle 8.13) needs older AGP/Kotlin than catalog-third-parties.
    implementation("com.android.tools.build:gradle:8.9.1")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.1.10")
    implementation("com.karumi:shot:6.1.0")
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.23.8")
    implementation("dev.drewhamilton.poko:poko-gradle-plugin:0.18.2")
    implementation("androidx.navigation:navigation-safe-args-gradle-plugin:$navigationVersion")
    // Not published in catalog-third-parties; required by dependency-updates convention plugin.
    implementation("com.github.ben-manes:gradle-versions-plugin:0.51.0")
}

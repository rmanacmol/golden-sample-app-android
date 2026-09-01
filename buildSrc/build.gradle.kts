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
    // catalog-third-parties pins AGP 8.13 / Kotlin 2.2 / Poko 0.19, which Gradle 8.13 kotlin-dsl cannot compile in buildSrc.
    implementation("com.android.tools.build:gradle:8.9.1")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.1.10")
    implementation(thirdPartyLibs.karumi.shot)
    implementation(thirdPartyLibs.detekt)
    implementation("dev.drewhamilton.poko:poko-gradle-plugin:0.18.2")
    implementation("androidx.navigation:navigation-safe-args-gradle-plugin:$navigationVersion")
    // Not in catalog-third-parties; required by the dependency-updates convention plugin.
    implementation("com.github.ben-manes:gradle-versions-plugin:0.51.0")
}

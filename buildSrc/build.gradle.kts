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
    implementation(thirdPartyLibs.agp)
    implementation(thirdPartyLibs.kotlin.gradle.plugin)
    implementation(thirdPartyLibs.karumi.shot)
    implementation(thirdPartyLibs.detekt)
    implementation(thirdPartyLibs.poko)
    implementation("androidx.navigation:navigation-safe-args-gradle-plugin:$navigationVersion")
    // Not in catalog-third-parties; required by the dependency-updates convention plugin.
    implementation("com.github.ben-manes:gradle-versions-plugin:0.51.0")
}

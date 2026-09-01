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

val navigationVersion = thirdPartyLibs.findVersion("navigation").get().requiredVersion

dependencies {
    implementation(gradleApi())
    implementation(thirdPartyLibs.findLibrary("agp").get())
    implementation(thirdPartyLibs.findLibrary("kotlin-gradle-plugin").get())
    implementation(thirdPartyLibs.findLibrary("karumi-shot").get())
    implementation(thirdPartyLibs.findLibrary("detekt").get())
    implementation(thirdPartyLibs.findLibrary("poko").get())
    implementation("androidx.navigation:navigation-safe-args-gradle-plugin:$navigationVersion")
    // Not published in catalog-third-parties; required by dependency-updates convention plugin.
    implementation("com.github.ben-manes:gradle-versions-plugin:0.51.0")
}

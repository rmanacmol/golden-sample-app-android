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

dependencies {
    implementation(gradleApi())
    implementation(buildPlugins.agp)
    implementation(buildPlugins.kotlin.gradle.plugin)
    implementation(buildPlugins.karumi.gradle.plugin)
    implementation(buildPlugins.detekt.gradle.plugin)
    implementation(buildPlugins.gradle.versions.gradle.plugin)
    implementation(buildPlugins.androidx.navigation.safe.args.gradle.plugin)
    implementation(buildPlugins.poko.gradle.plugin)
}

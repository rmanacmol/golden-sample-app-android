rootProject.name = "buildSrc"

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://jitpack.io") }
        maven {
            name = "backbaseRepo"
            url = uri("https://repo.backbase.com/repo")
            credentials(PasswordCredentials::class)
        }
    }
    versionCatalogs {
        create("thirdPartyLibs") {
            from("com.backbase.android.platform:catalog-third-parties:2026.03.01")
        }
    }
}

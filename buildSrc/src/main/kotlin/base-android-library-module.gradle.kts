import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("com.android.library")
    kotlin("android")
    id("dev.drewhamilton.poko")
}

internal val Project.thirdPartyLibs: VersionCatalog
    get() =
        project.extensions.getByType<VersionCatalogsExtension>().named("thirdPartyLibs")

android {
    compileSdk = Version.compileSdk
    defaultConfig {
        minSdk = Version.minSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        compilerOptions { jvmTarget.set(JvmTarget.JVM_17) }
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = false
            isReturnDefaultValues = true
        }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_17.toString()
        }
        execution = "ANDROIDX_TEST_ORCHESTRATOR"
    }

    // JUnit 5 will bundle in files with identical paths; exclude them
    packaging {
        resources.excludes.addAll(
            listOf(
                "META-INF/DEPENDENCIES",
                "META-INF/NOTICE",
                "META-INF/LICENSE",
                "META-INF/NOTICE",
                "META-INF/INDEX.LIST",
                "META-INF/AL2.0",
                "META-INF/LGPL2.1",
                "META-INF/ASL2.0",
                "META-INF/licenses/ASM",
                "META-INF/*.kotlin_module",
                "**/*.txt",
                "**/*.xml",
                "**/*.md",
                "**/*.properties",
            )
        )
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
    maxParallelForks = (Runtime.getRuntime().availableProcessors() / 2).coerceAtLeast(1)
    minHeapSize = "256m"
    maxHeapSize = "1024m"
    reports {
        html.required.set(false)
        junitXml.required.set(false)
    }
}

dependencies {
    implementation(thirdPartyLibs.findLibrary("androidx-appcompat").get())
    implementation(thirdPartyLibs.findLibrary("androidx-core-ktx").get())
    implementation(thirdPartyLibs.findLibrary("androidx-lifecycle-runtimeKtx").get())
    implementation(thirdPartyLibs.findLibrary("koin-android").get())
    implementation(thirdPartyLibs.findLibrary("androidx-constraintLayout").get())
    implementation(thirdPartyLibs.findLibrary("material").get())
    implementation(thirdPartyLibs.findLibrary("androidx-swipeRefreshLayout").get())

    testImplementation(thirdPartyLibs.findLibrary("assertj-core").get())
    testImplementation(thirdPartyLibs.findLibrary("junit-jupiter").get())
    testImplementation(thirdPartyLibs.findLibrary("coroutines-test").get())
    testImplementation(thirdPartyLibs.findLibrary("mockK").get())
    testRuntimeOnly(thirdPartyLibs.findLibrary("junit-jupiter-engine").get())
    testRuntimeOnly(thirdPartyLibs.findLibrary("junit-jupiter-launcher").get())
}
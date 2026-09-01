plugins {
    id(backbase.plugins.feature.android.module.get().pluginId)
    id(thirdPartyLibs.plugins.kotlin.parcelize.get().pluginId)
    id(backbase.plugins.configured.detekt.get().pluginId)
    id(backbase.plugins.jacoco.codecoverage.get().pluginId)
    id("shot")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.backbase.accounts_journey"
    defaultConfig {
        testApplicationId = "com.backbase.accounts_journey.test"
        testInstrumentationRunner = "com.karumi.shot.ShotTestRunner"
        testInstrumentationRunnerArguments["clearPackageData"] = "true"
        testInstrumentationRunnerArguments["useTestStorageService"] = "true"
    }
    buildTypes {
        debug {
            enableAndroidTestCoverage = true
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }
    testOptions {
        targetSdk = Version.compileSdk
        execution = "ANDROIDX_TEST_ORCHESTRATOR"
        animationsDisabled = true
    }
}

dependencies {
    implementation(thirdPartyLibs.androidx.navigation.fragmentKtx)
    implementation(thirdPartyLibs.androidx.navigation.uiKtx)

    testImplementation(projects.testData)
    testImplementation(projects.accountsTestData)

    // Backbase libraries
    implementation(platform(backbase.bom))
//    implementation(midTier.bundles.common)
    implementation(foundationLibs.bundles.bomOutput)
    implementation(midTierLibs.bundles.bomOutput)

    coreLibraryDesugaring(thirdPartyLibs.coreLibraryDesugaring)

    testImplementation(thirdPartyLibs.androidx.core.testing)

    androidTestImplementation(projects.accountsTestData)
    androidTestImplementation(projects.testData)
    androidTestImplementation(thirdPartyLibs.bundles.testing.android)

    androidTestUtil(thirdPartyLibs.androidx.test.orchestrator)
}

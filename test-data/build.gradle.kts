plugins {
    id(backbase.plugins.base.android.library.module.get().pluginId)
    id(backbase.plugins.configured.detekt.get().pluginId)
}

android {
    namespace = "com.backbase.android.test_data"
}

dependencies {
    implementation(platform(backbase.bom))
    implementation(foundationLibs.bundles.bomOutput)
    implementation(midTierLibs.bundles.bomOutput)
    implementation(clientLibs.bundles.bomOutput)

    implementation(libs.coroutinesTest)
    implementation(platform(libs.junit.bom))
    implementation(libs.junit.jupiter)

    implementation(libs.espresso)
}

plugins {
    id("io.gitlab.arturbosch.detekt")
}

internal val Project.thirdPartyLibs: VersionCatalog
    get() =
        project.extensions.getByType<VersionCatalogsExtension>().named("thirdPartyLibs")

detekt {
    toolVersion = thirdPartyLibs.findVersion("detekt").get().requiredVersion
    buildUponDefaultConfig = true // preconfigure defaults
    allRules = false // activate all available (even unstable) rules.
    config.setFrom("$rootDir/config/golden-sample-app-detekt.yml") // point to your custom config defining rules to run, overwriting default behavior
    parallel = true
    autoCorrect = true
    ignoredVariants = listOf("release")
}

dependencies {
    detektPlugins(thirdPartyLibs.findLibrary("detekt-formatting").get())
}
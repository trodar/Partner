//import org.jetbrains.kotlin.js.inline.util.aliasArgumentsIfNeeded

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.jetbrains.kapt)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.trodar.test"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        testInstrumentationRunner = "com.trodar.test.HiltTestRunner"
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
    kotlinOptions {
        jvmTarget = libs.versions.androidjvmTarget.get()
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlinCompiler.get()
    }
    packaging {
        resources.excludes.add("META-INF/*")
    }


}
configurations.all {
    resolutionStrategy {
        force(libs.hamcrest)
        force(libs.hamcrest.core)
        force(libs.hamcrest.library)
    }
}
dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)

    implementation(projects.features.preach)
    implementation(projects.features.domain)
    implementation(projects.features.report)
    implementation(projects.core.commonImpl)
    implementation(projects.core.room)
    implementation(libs.androidx.material3)
    implementation(libs.places)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.compose.runtime)

    testImplementation(libs.junit)
    testImplementation(libs.androidx.compose.test.junit4)
    testImplementation(libs.mockk)
    androidTestImplementation(libs.mockk)
    androidTestImplementation(libs.junit.test.ext)
    androidTestImplementation(libs.junit.test.ext.ktx)
    androidTestImplementation(libs.kaspersky.android.components.kaspresso)
    androidTestImplementation(libs.kaspersky.android.components.kaspresso.compose)
    androidTestImplementation(libs.androidx.compose.test.junit4)
    androidTestImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.hamcrest)
    androidTestImplementation(libs.hamcrest.core)
    androidTestImplementation(libs.hamcrest.library)
    debugImplementation(libs.androidx.compose.test.manifest)
    debugImplementation(libs.mockk.android)
    kaptAndroidTest(libs.hilt.compiler)
    kaptAndroidTest(libs.androidx.test.uiautomator)
}
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.jetbrains.kapt)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.trodar.jwpartner"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId="com.trodar.jwpartner"
        minSdk = libs.versions.minSdk.get().toInt()
        versionCode = 1
        versionName = "0.59.8"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        resources.excludes.add("/META-INF/{AL2.0,LGPL2.1}")
    }
    kapt {
        correctErrorTypes = true
    }

    hilt {
        enableAggregatingTask = true
    }
}

dependencies {



    //implementation(libs.androidx.activity)
    implementation(libs.androidx.activity.compose)

    implementation(libs.exyte)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.compose.material3)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)
    implementation(libs.hilt.android)
    kapt (libs.hilt.compiler)

    implementation(projects.core.theme)
    implementation(projects.core.commonImpl)
    implementation(projects.features.preach)
    implementation(projects.features.report)
    implementation(projects.features.notepad)
    implementation(projects.navigation)
    androidTestImplementation(testFixtures (projects.navigation))

    testImplementation(libs.junit)
    testImplementation(libs.androidx.compose.test.junit4)
    androidTestImplementation(libs.junit.test.ext)
    androidTestImplementation(libs.kaspersky.android.components.kaspresso)
    androidTestImplementation(libs.kaspersky.android.components.kaspresso.compose)
    androidTestImplementation(libs.androidx.compose.test.junit4)
    debugImplementation(libs.androidx.compose.test.manifest)
//    testImplementation 'junit:junit:4.13.2'
//    androidTestImplementation 'androidx.test.ext:junit:1.1.5'
//    androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'
//
//    androidTestImplementation platform('androidx.compose:compose-bom:2023.06.01')
//    androidTestImplementation 'androidx.compose.ui:ui-test-junit4'
//
//    debugImplementation 'androidx.compose.ui:ui-tooling'
//    debugImplementation 'androidx.compose.ui:ui-test-manifest'
}
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.jetbrains.kapt)
    alias(libs.plugins.hilt)
    id("kotlin-parcelize")
    //alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "com.trodar.room"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles ("consumer-rules.pro")

        kapt {
            arguments{
                arg("room.schemaLocation", "$projectDir/room_schemas")
            }
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
    kapt {
        correctErrorTypes = true
    }

    hilt {
        enableAggregatingTask = true
    }
}

dependencies {
    implementation(libs.hilt.android)
    //implementation(libs.androidx.monitor)
    kapt (libs.hilt.compiler)

    implementation (libs.androidx.room.runtime)
    implementation (libs.androidx.room.ktx)
    kapt (libs.androidx.room.compiler)

    implementation(projects.core.commonImpl)
    implementation(projects.core.theme)

    testImplementation(libs.junit)
    androidTestImplementation(libs.junit.test.ext)
    testImplementation(libs.androidx.test.core)
    testImplementation(libs.androidx.arch.core.test)
    androidTestImplementation(libs.androidx.test.runner)
}
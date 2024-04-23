plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kapt)
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.vukasinprvulovic.network"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(project(":core:utils"))
    implementation(project(":core:application"))
    implementation(project(":core:configuration"))

    implementation(libs.hilt)
    kapt(libs.hilt.compiler)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    implementation(libs.ktor.client)
    implementation(libs.ktor.content.negotiation)
    implementation(libs.ktor.json.serialization)
    implementation(libs.kotlinx.serialization)
    implementation(libs.ktor.logging)

    testImplementation(libs.junit)
    testImplementation(libs.ktor.client.test)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.kotest.assertion)
}
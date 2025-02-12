plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.jetbrains.kotlinx.kover)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.github.common"
    compileSdk = libs.versions.androidCompileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.androidMinSdk.get().toInt()

        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

}

dependencies {
    implementation(project(":arch"))

    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.material3)
    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.coroutines.core)

    // Test
    testImplementation(libs.junit)
    testImplementation(libs.test.coroutines)
    testImplementation(libs.mockk)
    testImplementation(libs.kotest)
    testImplementation(libs.turbine)
}

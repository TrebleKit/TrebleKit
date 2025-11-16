plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = NameSpaceConfig.HYBRID

    compileSdk {
        version = release(version = AppConfig.COMPILE_SDK_VERSION)
    }

    defaultConfig {
        minSdk {
            version = release(version = AppConfig.MIN_SDK_VERSION)
        }

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        ndk {
            abiFilters += listOf("armeabi-v7a", "arm64-v8a", "x86_64")
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
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(21)
        }
    }
}

dependencies {
    implementation(project(":flutter"))
    implementation(project(":common"))
    implementation(project(":base"))
    implementation(project(":resources"))
    implementation(platform(libs.insert.koin.bom))
    implementation(libs.insert.koin.core)
    implementation(libs.kongzue.baseframework)
    implementation(libs.androidx.appcompat)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
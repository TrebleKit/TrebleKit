plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = NameSpaceConfig.AIDL

    compileSdk {
        version = release(version = AppConfig.COMPILE_SDK_VERSION) // 目标Android16
    }

    defaultConfig {
        minSdk {
            version = release(version = AppConfig.MIN_SDK_VERSION) // 最低Android13
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

    buildFeatures {
        aidl = true
    }
}
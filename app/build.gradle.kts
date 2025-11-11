plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = NameSpaceConfig.APP

    compileSdk {
        version = release(version = AppConfig.COMPILE_SDK_VERSION)
    }

    defaultConfig {

        applicationId = AppConfig.APPLICATION_ID

        minSdk {
            version = release(version = AppConfig.MIN_SDK_VERSION)
        }
        targetSdk {
            version = release(version = AppConfig.TARGET_SDK_VERSION)
        }

        versionCode = AppConfig.VERSION_CODE
        versionName = AppConfig.VERSION_NAME

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        ndk {
            abiFilters += listOf("armeabi-v7a", "arm64-v8a", "x86_64")
        }
    }
    dependenciesInfo {
        includeInApk = false
        includeInBundle = false
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile(
                    "proguard-android-optimize.txt",
                ),
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
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(project(":flutter"))

    implementation(project(":hybrid"))
    implementation(project(":aidl"))
    implementation(project(":base"))
    implementation(project(":common"))

    implementation("com.google.accompanist:accompanist-coil:0.15.0")
    implementation(libs.accompanist.drawablepainter)

    implementation(libs.kongzue.baseframework)
    implementation(libs.kongzue.dialogx)
    implementation(libs.kongzue.dialogx.material3)
    implementation(libs.lsposed.hiddenapibypass)
    implementation(libs.rikka.shizuku.api)
    implementation(libs.rikka.shizuku.provider)
    implementation(libs.kyant.capsule)
    implementation(libs.kyant.backdrop)
    implementation(libs.blankj.utilcodex)
    implementation(platform(libs.insert.koin.bom))
    implementation(libs.insert.koin.core)
    implementation(libs.insert.koin.android)
    implementation(libs.insert.koin.compose)



    implementation(libs.material)
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.viewpager2)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.play.services.base)
//    implementation(libs.androidx.navigation.compose)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)

    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
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
        buildConfig = true
    }
}

dependencies {
    implementation(project(":hybrid"))
    implementation(project(":aidl"))
    implementation(project(":base"))
    implementation(project(":common"))
    implementation(project(":plugin"))

    implementation(project(":utils"))
    implementation(project(":provider"))
    implementation(project(":ui"))

    implementation(libs.kongzue.baseframework)

    implementation(libs.kongzue.dialogx)
    implementation(libs.kongzue.dialogx.material3)

    implementation(libs.lsposed.hiddenapibypass)

    implementation(libs.rikka.shizuku.api)



    implementation(libs.blankj.utilcodex)

    implementation(platform(libs.insert.koin.bom))
    implementation(libs.insert.koin.core)
    implementation(libs.insert.koin.android)




    implementation(libs.material)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    implementation(libs.play.services.base)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
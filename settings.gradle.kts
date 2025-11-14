// 设置项目名称
rootProject.name = "TrebleKit"

pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://storage.googleapis.com/download.flutter.io")
        maven(url = "https://jitpack.io")
    }
}

// 检查是否存在构建脚本
// 不存在就执行 flutter pub get
rootProject.projectDir.resolve(
    relative = ".android/include_flutter.groovy"
).let { flutter ->
    if (flutter.exists()) {
        apply(from = flutter)
        return@let flutter
    } else {
        throw GradleException(
            "未找到Flutter生成的构建脚本",
        )
    }
}

// 包含项目模块
include(":app")
include(":aidl")
include(":hybrid")
include(":base")
include(":common")
include(":plugin")
include(":resources")
include(":utils")
include(":ui")
include(":engine")
include(":provider")

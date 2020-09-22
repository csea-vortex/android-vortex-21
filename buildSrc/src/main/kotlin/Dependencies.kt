object AndroidSdk {
    const val compileSdk = 30
    const val minSdk = 21
    const val targetSdk = 30

    const val versionCode = 1
    const val versionName = "1.0.0"
}

object Versions {
    const val gradle = "4.0.1"
    const val kotlin = "1.4.0"
    const val coreKtx = "1.3.1"
    const val appcompat = "1.2.0"
    const val constraintLayout = "2.0.1"
    const val lifecycle = "2.2.0"
    const val navigation = "2.3.0"

    /* test */
    const val junit = "4.12"
    const val junitExt = "1.1.2"
    const val espressoCore = "3.3.0"
}

object BuildPlugins {
    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.gradle}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"

    const val androidApplication = "com.android.application"
    const val kotlinAndroid = "kotlin-android"
    const val kotlinAndroidExtensions = "kotlin-android-extensions"
}

object Libs {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"

    const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"
    const val lifecycleViewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
}

object TestLibs {
    const val junit = "junit:junit:${Versions.junit}"
    const val junitExt = "androidx.test.ext:junit:${Versions.junitExt}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
}

object ModuleLibs {
    object AppModule {
        val implementations = listOf(
            Libs.kotlin, Libs.coreKtx, Libs.appcompat,
            Libs.constraintLayout,
            Libs.lifecycleExtensions, Libs.lifecycleViewModelKtx
        )
        val testImplementations = listOf(TestLibs.junit)
        val androidTestImplementations = listOf(
            TestLibs.junitExt, TestLibs.espressoCore
        )
    }
}


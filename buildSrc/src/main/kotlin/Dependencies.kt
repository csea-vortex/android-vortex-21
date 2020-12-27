object AndroidSdk {
    const val compileSdk = 30
    const val minSdk = 21
    const val targetSdk = 30

    const val versionCode = 2
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
    const val material = "1.3.0-alpha02"
    const val circularImageView = "3.1.0"
    const val retrofit = "2.9.0"
    const val security = "1.1.0-alpha02"
    const val picasso = "2.71828"
    const val dagger2 = "2.27"

    /* test */
    const val junit = "4.12"
    const val junitExt = "1.1.2"
    const val espressoCore = "3.3.0"

    const val playCore = "1.8.0"
    const val playCoreKtx = "1.8.1"

    const val okhttp = "3.10.0"
    const val okhttpDownloader = "1.1.0"
    const val okhttpLogger = "4.9.0"

    const val cardView = "1.0.0"
    const val legacySupport ="1.0.0"
}

object BuildPlugins {
    const val navigationSafeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}"
    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.gradle}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"

    const val androidApplication = "com.android.application"
    const val kotlinAndroid = "kotlin-android"
    const val kotlinAndroidExtensions = "kotlin-android-extensions"
    const val kotlinKapt = "kotlin-kapt"
    const val navigationSafeArgsPlugin = "androidx.navigation.safeargs.kotlin"
}

object Libs {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"

    const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"
    const val lifecycleViewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val lifecycleLiveDataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    const val lifecycleCompiler = "androidx.lifecycle:lifecycle-compiler:${Versions.lifecycle}"
    const val lifecycleCommon = "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycle}"

    const val navigationFragmentKtx = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val navigationUIKtx = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"

    const val material = "com.google.android.material:material:${Versions.material}"

    const val circularImageView = "de.hdodenhof:circleimageview:${Versions.circularImageView}"

    const val playCore = "com.google.android.play:core:${Versions.playCore}"
    const val playCoreKtx = "com.google.android.play:core-ktx:${Versions.playCoreKtx}"

    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitGsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"

    const val security = "androidx.security:security-crypto:${Versions.security}"

    const val picasso = "com.squareup.picasso:picasso:${Versions.picasso}"

    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    const val okhttpDownloader = "com.jakewharton.picasso:picasso2-okhttp3-downloader:${Versions.okhttpDownloader}"
    const val okhttplogger = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttpLogger}"

    const val dagger = "com.google.dagger:dagger:${Versions.dagger2}"
    const val daggerAndroid = "com.google.dagger:dagger-android:${Versions.dagger2}"
    const val daggerAndroidAndroidProcessor = "com.google.dagger:dagger-android-processor:${Versions.dagger2}"
    const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger2}"
    const val cardView = "androidx.cardview:cardview:${Versions.cardView}"
    const val kotlinJetbrains = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    const val legacySupport = "androidx.legacy:legacy-support-v4:${Versions.legacySupport}"
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
            Libs.lifecycleExtensions, Libs.lifecycleViewModelKtx,
            Libs.lifecycleLiveDataKtx, Libs.lifecycleCommon,
            Libs.navigationFragmentKtx, Libs.navigationUIKtx,
            Libs.material,
            Libs.circularImageView,
            Libs.playCore,
            Libs.playCoreKtx,
            Libs.retrofit,
            Libs.retrofitGsonConverter,
            Libs.security,
            Libs.picasso,
            Libs.okhttp,
            Libs.okhttpDownloader,
            Libs.okhttplogger,
            Libs.dagger, Libs.daggerAndroid,
            Libs.cardView,
            Libs.kotlinJetbrains,
            Libs.legacySupport
        )
        val kapts = listOf(
            Libs.lifecycleCompiler,
            Libs.daggerCompiler, Libs.daggerAndroidAndroidProcessor
        )
        val testImplementations = listOf(TestLibs.junit)
        val androidTestImplementations = listOf(
            TestLibs.junitExt, TestLibs.espressoCore
        )
    }
}


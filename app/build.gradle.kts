plugins {
    id(BuildPlugins.androidApplication)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinAndroidExtensions)
    id(BuildPlugins.kotlinKapt)
    id(BuildPlugins.navigationSafeArgsPlugin)
    id(BuildPlugins.googleServicesPlugin)
    id(BuildPlugins.crashAnalyticsGradlePlugin)
}

android {
    compileSdkVersion(AndroidSdk.compileSdk)
    defaultConfig {
        applicationId = "edu.nitt.vortex2021"
        minSdkVersion(AndroidSdk.minSdk)
        targetSdkVersion(AndroidSdk.targetSdk)
        versionCode = AndroidSdk.versionCode
        versionName = AndroidSdk.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8

    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    ModuleLibs.AppModule.implementations.forEach(::implementation)
    ModuleLibs.AppModule.kapts.forEach(::kapt)
    ModuleLibs.AppModule.testImplementations.forEach(::testImplementation)
    ModuleLibs.AppModule.androidTestImplementations.forEach(::androidTestImplementation)
}
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "br.com.rodrigoamora.transitorio"
    compileSdk = 34

    defaultConfig {
        applicationId = "br.com.rodrigoamora.transitorio"
        minSdk = 26
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")

    //AppCompat
    implementation("androidx.appcompat:appcompat:1.6.1")

    //MaterialDesign
    implementation("com.google.android.material:material:1.11.0")

    //ConstraintLayout
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    //KOIN
    implementation("io.insert-koin:koin-android:3.4.1")

    //Navigation Fragment
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")

    // OKHTTP
    implementation("com.squareup.okhttp3:logging-interceptor:3.13.1")

    // RETROFIT
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    ///// LIBS FOR TEST /////
    //jUnit
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")

    //Espresso
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //KOIN
    testImplementation("io.insert-koin:koin-test:3.4.1")
    testImplementation("io.insert-koin:koin-test-junit4:3.4.1")
}
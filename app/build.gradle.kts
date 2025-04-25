plugins {
    id("com.android.application")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "br.com.rodrigoamora.transitorio"
    compileSdk = 34

    defaultConfig {
        applicationId = "br.com.rodrigoamora.transitorio"

        minSdk = 31
        targetSdk = 34

        versionCode = 44
        versionName = "4.1.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            isDebuggable = true
            isMinifyEnabled = false

            buildConfigField("String", "BASE_URL_API", "\"https://dados.mobilidade.rio/gps/\"")
            buildConfigField("String", "DATABASE_NAME", "\"transitorio_database_dev.db\"")
        }

        release {
            isMinifyEnabled = false

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            buildConfigField("String", "BASE_URL_API", "\"https://dados.mobilidade.rio/gps/\"")
            buildConfigField("String", "DATABASE_NAME", "\"transitorio_database.db\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        buildConfig = true
        viewBinding = true
    }

    secrets {
        // Optionally specify a different file name containing your secrets.
        // The plugin defaults to "local.properties"
        propertiesFileName = "secrets.properties"

        // A properties file containing default secret values. This file can be
        // checked in version control.
        defaultPropertiesFileName = "local.properties"

        // Configure which keys should be ignored by the plugin by providing regular expressions.
        // "sdk.dir" is ignored by default.
        ignoreList.add("keyToIgnore") // Ignore the key "keyToIgnore"
        ignoreList.add("sdk.*")       // Ignore all keys matching the regexp "sdk.*"
    }
}

dependencies {
    //AndroidX
    implementation("androidx.core:core-ktx:1.13.1")

    //AppCompat
    implementation("androidx.appcompat:appcompat:1.7.0")

    //ConstraintLayout
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    //KOIN
    implementation("io.insert-koin:koin-android:3.4.1")

    //GOOGLE-PLAY-SERVICES
    implementation("com.google.android.gms:play-services-location:21.3.0")
    implementation("com.google.android.gms:play-services-maps:19.0.0")

    //LIFECYCLE
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.3")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.3")

    //MaterialDesign
    implementation("com.google.android.material:material:1.12.0")

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
    androidTestImplementation("androidx.test.ext:junit:1.2.1")

    //Espresso
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

    //KOIN
    testImplementation("io.insert-koin:koin-test:3.4.1")
    testImplementation("io.insert-koin:koin-test-junit4:3.4.1")
}
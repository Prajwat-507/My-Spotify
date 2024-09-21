plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.myspotify"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.myspotify"
        minSdk = 24
        targetSdk = 34
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

    buildFeatures{
        viewBinding=true;
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation ("androidx.activity:activity:1.9.2")
    implementation("com.google.firebase:firebase-database-ktx:21.0.0")
    implementation("com.google.firebase:firebase-firestore:25.1.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

    //Automatically adjusts dimensions of text according to screes size
    implementation ("com.intuit.sdp:sdp-android:1.1.1")
    implementation ("com.intuit.ssp:ssp-android:1.1.1")

    //Fragment navigation
    implementation ("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation ("androidx.navigation:navigation-ui-ktx:2.7.7")

    // AndroidX lifecycle

    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.2.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.3.0-beta01") // what?
    implementation ("androidx.lifecycle:lifecycle-extensions:2.2.0")
    // alternatively - just ViewModel
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.4")

    //Firebase Auth
    implementation("com.google.firebase:firebase-auth:23.0.0")

    //Edge to Edge
    implementation("androidx.activity:activity-ktx:1.9.1")

    //Retrofit
    implementation ("com.squareup.retrofit2:retrofit:(2.11.0)")

    //Json Convertor
    implementation ("com.squareup.retrofit2:converter-gson:2.11.0")

    //Picasso
    implementation ("com.squareup.picasso:picasso:2.8")

    //Exo player
    implementation("androidx.media3:media3-exoplayer:1.4.1")
    implementation("androidx.media3:media3-exoplayer-dash:1.4.1")
    implementation("androidx.media3:media3-ui:1.4.1")

    //Bottom navigation Bar
    implementation ("nl.joery.animatedbottombar:library:1.1.0")

    //Custom Progress Bar
    implementation ("com.github.ybq:Android-SpinKit:1.4.0")

    // FirebaseUI for Cloud Firestore
    implementation ("com.firebaseui:firebase-ui-firestore:8.0.2")
}
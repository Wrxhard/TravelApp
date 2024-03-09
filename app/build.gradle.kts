plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")

}

android {
    namespace = "com.wrxhard.ftravel"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.wrxhard.ftravel"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf(
                    "room.schemaLocation" to "$projectDir/schemas",
                    "room.incremental" to "true"
                )
            }
        }
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
        dataBinding = true
        mlModelBinding = true
    }
}

dependencies {

    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("org.tensorflow:tensorflow-lite-support:0.1.0")
    implementation("org.tensorflow:tensorflow-lite-metadata:0.1.0")
    implementation("org.tensorflow:tensorflow-lite-gpu:2.3.0")
    //Room
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$room_version")
    kapt("androidx.room:room-compiler:$room_version")

    //Gson
    val gson_version = "2.10.1"
    implementation("com.google.code.gson:gson:$gson_version")

    val work_version = "2.9.0"
    // Kotlin + coroutines
    implementation("androidx.work:work-runtime-ktx:$work_version")

    val nav_version = "2.7.6"
    // Nav
    implementation("androidx.navigation:navigation-runtime-ktx:$nav_version")
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")

    //Coroutine
    val coroutine_version="1.7.1"
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutine_version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutine_version")

    //Lifecycle
    val lifecycle_version = "2.7.0"
    // LiveData
    //noinspection GradleDependency
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version")

    // ViewModel
    //noinspection GradleDependency
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")

    // Feature module Support
    implementation("androidx.navigation:navigation-dynamic-features-fragment:$nav_version")

    //Responsive
    implementation("com.intuit.sdp:sdp-android:1.1.0")
    implementation("com.intuit.ssp:ssp-android:1.1.0")

    //DI
    val hilt_version="2.50"
    implementation("com.google.dagger:hilt-android:$hilt_version")
    kapt("com.google.dagger:hilt-compiler:$hilt_version")

    //Okhttp
    val okhttp_version="4.12.0"
    implementation("com.squareup.okhttp3:okhttp:$okhttp_version")

    val retrofit_version="2.9.0"
    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:$retrofit_version")
    // Retrofit with Scalar Converter
    implementation("com.squareup.retrofit2:converter-scalars:$retrofit_version")
    implementation("com.squareup.retrofit2:converter-gson:$retrofit_version")


    //Glide
    val glide_version="4.12.0"
    implementation("com.github.bumptech.glide:glide:$glide_version")
    //Background Blur
    implementation("com.github.Dimezis:BlurView:version-2.0.3")
    // Import tflite dependencies
    implementation("org.tensorflow:tensorflow-lite:2.4.0")
    // The GPU delegate library is optional. Depend on it as needed.
    implementation("org.tensorflow:tensorflow-lite-gpu:2.3.0")
    implementation("org.tensorflow:tensorflow-lite-support:0.1.0")
    //Google Map
    implementation("com.google.android.gms:play-services-maps:18.2.0")

    //Google Auth
    implementation("com.google.android.gms:play-services-auth:21.0.0")

    //Google ML kit Translate
    implementation("com.google.mlkit:translate:17.0.2")


    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
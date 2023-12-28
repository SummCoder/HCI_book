plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.hci_book"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.hci_book"
        minSdk = 33
        targetSdk = 33
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
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.drawerlayout:drawerlayout:1.2.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("androidx.recyclerview:recyclerview:1.2.1")
    implementation ("io.github.youth5201314:banner:2.2.2")
    implementation ("com.makeramen:roundedimageview:2.3.0")


    implementation ("com.github.bumptech.glide:glide:4.3.1")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation ("com.google.android.material:material:1.1.0")
}
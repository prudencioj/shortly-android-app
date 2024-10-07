plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.jprudencio.shortly"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.jprudencio.shortly"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField("String", "SHRTCO_API_URL", "\"https://api.shrtco.de/v2/\"")
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
        compose = true
        buildConfig = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.hilt.android)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    ksp(libs.dagger.compiler)
    ksp(libs.hilt.android.compiler)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp3.logging.interceptor)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

/*

plugins {
    id 'com.android.application'
    id 'org.jetbrains.kotlin.android'
    id 'kotlin-kapt'
    id 'dagger.hilt.android.plugin'
}

android {
    compileSdk 34

    defaultConfig {
        applicationId "com.jprudencio.shortly"
        minSdk 23
        targetSdk 34
        versionCode 1
        versionName "1.0"

        testInstrumentationRunner "com.jprudencio.shortly.ShortlyTestRunner"
        vectorDrawables {
            useSupportLibrary true
        }

        buildConfigField "String", "SHRTCO_API_URL", "\"https://api.shrtco.de/v2/\""
    }

    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = '1.8'
    }
    buildFeatures {
        compose true
    }
    composeOptions {
        kotlinCompilerExtensionVersion "${composeCompiler_version}"
    }
    packagingOptions {
        resources {
            excludes += '/META-INF/{AL2.0,LGPL2.1}'
        }
    }
    namespace 'com.jprudencio.shortly'
}

dependencies {
    implementation 'androidx.core:core-ktx:1.13.1'
    implementation 'androidx.lifecycle:lifecycle-runtime-ktx:2.8.6'

    // Compose
    implementation 'androidx.activity:activity-compose:1.9.2'
    implementation "androidx.compose.ui:ui:$compose_version"
    implementation "androidx.compose.ui:ui-tooling-preview:$compose_version"
    implementation "androidx.compose.foundation:foundation-layout:$compose_version"
    implementation 'androidx.navigation:navigation-compose:2.8.2'
    implementation 'com.google.accompanist:accompanist-systemuicontroller:0.25.1'

    // Hilt
    implementation "com.google.dagger:hilt-android:$hilt_version"
    kapt "com.google.dagger:hilt-android-compiler:$hilt_version"
    implementation "androidx.hilt:hilt-navigation-compose:1.2.0"

    // Room
    implementation "androidx.room:room-runtime:$room_version"
    kapt "androidx.room:room-compiler:$room_version"
    implementation "androidx.room:room-ktx:$room_version"

    // Material design
    implementation 'com.google.android.material:material:1.12.0'
    implementation "androidx.compose.material:material:$compose_version"

    // Retrofit
    implementation 'com.squareup.retrofit2:retrofit:2.11.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
    implementation 'com.squareup.okhttp3:logging-interceptor:4.12.0'

    debugImplementation "androidx.compose.ui:ui-tooling:$compose_version"
    debugImplementation "androidx.compose.ui:ui-test-manifest:$compose_version"

    testImplementation 'junit:junit:4.13.2'
    testImplementation "androidx.test:core:1.6.1"
    testImplementation "io.mockk:mockk:1.13.10"
    testImplementation 'org.jetbrains.kotlinx:kotlinx-coroutines-test:1.9.0'

    androidTestImplementation 'androidx.test.ext:junit:1.2.1'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.6.1'
    androidTestImplementation "androidx.compose.ui:ui-test-junit4:$compose_version"

    androidTestImplementation "com.google.dagger:hilt-android-testing:$hilt_version"
    kaptAndroidTest "com.google.dagger:hilt-android-compiler:$hilt_version"
}
*/
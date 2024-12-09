plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.hilt)
    id("kotlin-kapt")
    id ("kotlin-android")
    id ("kotlin-parcelize")
    id("maven-publish")
}

android {
    namespace = "com.dbng.domain"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

publishing{
    publications {
        create<MavenPublication>("release") {
            groupId = "com.dbng.domainlayer"
            artifactId = "recipedomain"
            version = "1.0"
            println("Test aar file "+layout.buildDirectory.file("outputs/aar/domain-release.aar"))
            artifact(layout.buildDirectory.file("outputs/aar/domain-release.aar"))

        }
    }
    repositories{
        maven {
            name = "recipepackages"
            url = uri("https://maven.pkg.github.com/nagarajudbng/RecipeDomain-Layer")
            credentials{
                username = System.getenv("GITHUB_USERNAME")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}
dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(project(":core"))
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    implementation(libs.hilt.android)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    kapt(libs.hilt.android.compiler)
    implementation(libs.hilt.navigation.compose)


    testImplementation (libs.mockito.kotlin)
    testImplementation (libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.core)
    androidTestImplementation(libs.kotlin.mockito.kotlin)
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.5.3")
        classpath("com.google.gms:google-services:4.3.15")
        classpath("com.android.tools.build:gradle:8.1.0")


    }
}





plugins {
    id("com.android.application") version "8.1.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
    id("com.android.library") version "8.1.0" apply false
}



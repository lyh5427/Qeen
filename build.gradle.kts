// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        //hilt
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.10")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.48")
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
}
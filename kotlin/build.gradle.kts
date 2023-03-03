plugins {
    kotlin("multiplatform") version "1.8.0"
}

group = "tech.eritquearcus"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        withJava()
    }
    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    val nativeTarget = when {
        hostOs == "Mac OS X" -> macosX64("native")
        hostOs == "Linux" -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    nativeTarget.apply {
        compilations.all {
            cinterops {
                this.create("lib")
            }
        }
        binaries {
            executable {
                entryPoint = "main"
            }
        }
    }
    sourceSets {
        val nativeMain by getting
        val nativeTest by getting
    }
}
tasks.getByName("compileKotlinNative") {
    this.dependsOn("clean")
}
afterEvaluate {
//    tasks.named<org.jetbrains.kotlin.gradle.tasks.KotlinNativeCompile>("compileKotlinNative").apply {
//        this.configure {
//            this.kotlinOptions.freeCompilerArgs = listOf("-Xllvm-variant=D:\\msys64\\mingw64")
//        }
//    }
//    tasks.named<org.jetbrains.kotlin.gradle.tasks.KotlinNativeLink>("linkDebugExecutableNative").apply {
//        this.configure {
//            this.kotlinOptions.freeCompilerArgs =  listOf("-Xllvm-variant=D:\\msys64\\mingw64")
//        }
//    }
}
tasks.named<org.jetbrains.kotlin.gradle.tasks.KotlinNativeCompile>("compileKotlinNative").apply {
    this.configure {
        var llvm = "LLVM_REPLACE_FOR_GITHUB_WORKFLOW"
        if(llvm.startsWith("LLVM_REPLACE")){
            llvm = "D:\\msys64\\mingw64"
        }
        this.kotlinOptions.freeCompilerArgs = listOf("-Xllvm-variant=$llvm")
    }
}
//tasks.named<org.jetbrains.kotlin.gradle.tasks.KotlinNativeLink>("linkDebugExecutableNative").apply {
//    this.configure {
//        this.kotlinOptions.freeCompilerArgs = listOf("-Xllvm-variant=D:\\msys64\\mingw64")
//    }
//}
tasks.getByName("runDebugExecutableNative") {
    this.dependsOn("clean")
}
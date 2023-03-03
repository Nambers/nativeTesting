import kotlinx.cinterop.memScoped

fun main() {
    println("Hello, Kotlin/Native!")
    memScoped {
        lib.test("Hello, Kotlin/Native!")
    }
}
import kotlinx.cinterop.memScoped

fun main() {
    println("Hello, Kotlin/Native!")
    memScoped {
        lib.lib.lib.test("Hello, Kotlin/Native!")
    }
}
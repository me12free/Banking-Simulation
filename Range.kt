fun main() {
    // if else condition
    val noOfFish = 60
println("1.Using 'if else'")
    if (noOfFish == 0) {
        println("There is no fish.")
    } else if (noOfFish >= 60) {
        println("There is little fish.")
    }
    else {
        println("There is a lot of fish.")
    }

    //Range and "when" statement
    println("2. Using 'when'")
when(noOfFish) {
    0 -> println("There is no fish.")
    in 1..60 -> println("There is little fish.")
    else -> println("There is a lot of fish.")
}
}
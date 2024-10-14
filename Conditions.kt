fun main(){
    val passMark = 60
    val Mark:String = "A"

    // if else statements
    if (passMark >= 60) {
        println("Passed")
    }
    else {
        println("Failed")
    }

    //condition 2
    if (passMark >= 80) {
        println("Student got $Mark")// String interpolation
    } else {
        println("Student got $passMark")
    }
}
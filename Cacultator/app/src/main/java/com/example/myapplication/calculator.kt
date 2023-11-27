package com.example.myapplication

fun main() {
    val num1 = readLine()!!.toDouble()
    val oper = readLine()!!.toString()
    val num2 = readLine()!!.toDouble()

    when (oper) {
        "+" -> {
            val plusResult = num1 + num2
            println("덧셈 결과는 ${plusResult}입니다")
        }

        "-" -> {
            val minusResult = num1 - num2
            println("뺄셈 결과는 ${minusResult}입니다")
        }

        "*" -> {
            val multipleResult = num1 * num2
            println("곱셈 결과는 ${multipleResult}입니다")
        }

        "/" -> {
            val divideResult = num1 / num2
            println("나눗셈 결과는 ${divideResult}입니다")
        }

        else -> {
            println("올바른 연산자를 입력해 주세요(+,-,*,/ 만 인식 합니다)")
        }

    }
}
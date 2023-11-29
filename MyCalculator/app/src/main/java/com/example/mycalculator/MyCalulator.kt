package com.example.mycalculator

import java.util.concurrent.AbstractExecutorService

fun main() {

    println("숫자와 연산자를 입력해주세요.")
    val num1 = readLine()!!.toDouble()
    val oper = readLine()!!.toString()
    val num2 = readLine()!!.toDouble()

    val calc = MyCalulator()

    println("계산 결과 ${calc.operate(num1, oper, num2)}")

}
class MyCalulator {
    fun operate(num1:Double,oper:String,num2:Double): String {

        var add = AddOperation()
        var sub = SubtrackOperation()
        var mul = MultiplyOperation()
        var div = DivideOperaion()
        var abs = AbstractOperatin()
        var result : String

        when(oper) {
            "+"->result = (add.operate(num1, num2))
            "-"->result = (sub.operate(num1, num2))
            "*"->result = (mul.operate(num1, num2))
            "/"->result = (div.operate(num1, num2))
            "%"->result = (num1 % num2).toString()
            else->result = (abs.operate(num1, num2))
        }
        return result
    }
}
class AddOperation {
    fun operate(num1: Double,num2: Double) : String {

        var result : String
        result = (num1 + num2).toString()
        return  result
    }
}
class SubtrackOperation {
    fun operate(num1: Double,num2: Double) : String {

        var result : String
        result = (num1 - num2).toString()
        return  result
    }
}
class MultiplyOperation {
    fun operate(num1: Double,num2: Double) : String {

        var result : String
        result = (num1 * num2).toString()
        return  result
    }
}
class DivideOperaion {
    fun operate(num1: Double,num2: Double) : String {

        var result : String
        result = (num1 / num2).toString()
        return  result
    }
}
class AbstractOperatin {
    fun operate(num1: Double, num2: Double) : String {

        var result : String
        result = "올바른 연산자를 입력해야 합니다"
        return  result
    }
}
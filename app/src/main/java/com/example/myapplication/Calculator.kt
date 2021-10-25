package com.example.myapplication

class Calculator {

    companion object {
        var num1: Int? = null
        var num2: Int? = null
        var operation: String = ""

        fun calculate(): Int? {
            var res: Int? = null
            if (num1 != null && num2 != null) {
                when (operation) {
                    "+" -> res = num1!! + num2!!
                    "-" -> res = num1!! - num2!!
                    "*" -> res = num1!! * num2!!
                    "/" -> res = num1!! / num2!!
                }
            }
            return res
        }

    }
}
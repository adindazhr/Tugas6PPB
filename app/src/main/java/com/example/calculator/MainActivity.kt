package com.example.calculator

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.lang.Exception


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var num1 by remember { mutableStateOf("0") }
            var num2 by remember { mutableStateOf("0") }
            var result by remember { mutableStateOf("") }

            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TextField(
                    value = num1,
                    onValueChange = { num1 = it },
                    label = { Text("Number 1") }
                )

                TextField(
                    value = num2,
                    onValueChange = { num2 = it },
                    label = { Text("Number 2") }
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OperationButton("Add") {
                        performOperation(num1.toInt(), num2.toInt(), Operation.ADD) { result = it.toString() }
                    }

                    OperationButton("Subtract") {
                        performOperation(num1.toInt(), num2.toInt(), Operation.SUBTRACT) { result = it.toString() }
                    }

                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    OperationButton("Multiply") {
                        performOperation(num1.toInt(), num2.toInt(), Operation.MULTIPLY) { result = it.toString() }
                    }

                    OperationButton("Divide") {
                        performOperation(num1.toInt(), num2.toInt(), Operation.DIVIDE) {
                            if (it == Int.MIN_VALUE) {
                                result = "Error: Division by zero"
                            } else {
                                result = it.toString()
                            }
                        }
                    }
                }

                Text(text = result)
            }
        }
    }

    enum class Operation {
        ADD, SUBTRACT, MULTIPLY, DIVIDE
    }

    @Composable
    private fun OperationButton(text: String, onClick: () -> Unit) {
        Button(onClick = onClick) {
            Text(text)
        }
    }

    private fun performOperation(num1: Int, num2: Int, operation: Operation, callback: (Int) -> Unit) {
        try {
            val result = when (operation) {
                Operation.ADD -> num1 + num2
                Operation.SUBTRACT -> num1 - num2
                Operation.MULTIPLY -> num1 * num2
                Operation.DIVIDE -> if (num2 != 0) num1 / num2 else Int.MIN_VALUE
            }
            callback(result)
        } catch (e: Exception) {
            callback(Int.MIN_VALUE)
        }
    }
}
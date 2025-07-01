package com.example.androidtodo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoApp()
        }
    }
}

class TodoViewModel : ViewModel() {
    private val _tasks = mutableStateListOf<String>()
    val tasks: List<String> = _tasks

    fun addTask(task: String) {
        _tasks.add(task)
    }

    fun removeTask(task: String) {
        _tasks.remove(task)
    }
}

@Composable
fun TodoApp(todoViewModel: TodoViewModel = viewModel()) {
    var text by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("New Task") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = {
                if (text.isNotEmpty()) {
                    todoViewModel.addTask(text)
                    text = ""
                }
            }) {
                Text("Add")
            }
        }

        LazyColumn {
            items(todoViewModel.tasks) { task ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = task)
                    Button(onClick = { todoViewModel.removeTask(task) }) {
                        Text("Delete")
                    }
                }
            }
        }
    }
}

package com.example.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    // lateinit 意思是晚点才会init，一开始不会
    private lateinit var todoAdapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 利用空list 创建adapter
        todoAdapter = TodoAdapter(mutableListOf())
        rvTodoItems.adapter = todoAdapter
        rvTodoItems.layoutManager = LinearLayoutManager(this)
        // click add button 应该怎么办
        btnAddTodo.setOnClickListener {
            // 接受edit box里面的text，并且转化成string
            val todoTitle = etTodoTitle.text.toString()
            // 检查是否为空
            if (todoTitle.isNotEmpty()) {
                // 不为空 就创建新todo item
                val todo = Todo(todoTitle)
                // 添加进adapter
                todoAdapter.addTodo(todo)
                // 清空edit box
                etTodoTitle.text.clear()
            }
        }
        btnDeleteDoneTodos.setOnClickListener {
            todoAdapter.deleteDoneTodos()
        }
    }
}
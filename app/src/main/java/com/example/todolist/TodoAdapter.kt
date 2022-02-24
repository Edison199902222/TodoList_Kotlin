package com.example.todolist

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_todo.view.*

class TodoAdapter(
    private val todos: MutableList<Todo>
): RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {
    // to do list view 的holder
    class TodoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    // will create the view holder,xml 经过转换成kotlin class
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            // Instantiates a layout XML file into its corresponding View  objects.
            // 把layout 转换成 kotlin的class，使得在code 里面可以使用
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_todo,
                parent,
                false
            )
        )
    }
    // 新加一个todo item
    fun addTodo(todo: Todo) {
        todos.add(todo)
        // 加完以后只是加进list，我们还需要显示在界面上
        notifyItemInserted(todos.size - 1)
    }

    fun deleteDoneTodos() {
        // 检查每一个元素，如果checked 是true，就remove
        todos.removeAll{ todo ->
            todo.isChecked
        }
        notifyDataSetChanged()
    }
    // 如果check是true的话，就有划线的效果
    private fun toggleStrikeThrough(tvTodoTitle: TextView, isChecked: Boolean) {
        if (isChecked) {
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags or STRIKE_THRU_TEXT_FLAG
        } else {
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
        }
    }
    // bind the data from todo list to views of our list
    // 就是真正绑定的function
    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        // 得到想要绑定的todo item
        val curTodo = todos[position]
        // 开始绑定
        holder.itemView.apply{
            // tvTodoTitle 和 cbDone 都是item to do xml file 里面的小部件
            tvTodoTitle.text = curTodo.title
            cbDone.isChecked = curTodo.isChecked
            toggleStrikeThrough(tvTodoTitle, curTodo.isChecked)
            // box也需要绑定，并且会自动更新
            cbDone.setOnCheckedChangeListener{_, isChecked ->
                toggleStrikeThrough(tvTodoTitle, isChecked)
                curTodo.isChecked = !curTodo.isChecked
            }
        }
    }
    // return amount of item in list
    override fun getItemCount(): Int {
        return todos.size
    }
}
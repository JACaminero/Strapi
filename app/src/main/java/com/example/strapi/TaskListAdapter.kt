package com.example.strapi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_layout.view.*

class TaskListAdapter(val onClickListener: (Product) -> Unit) : RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>() {

    private var taskList = emptyList<Product>()
    class TaskViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.row_layout, parent, false)

        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentItem = taskList[position]
        holder.itemView.name.text = "Nombre = ${currentItem.name}"
        holder.itemView.upc.text = "upc = ${currentItem.upc}"
        holder.itemView.precio.text = "precio = ${currentItem.price}"

        holder.itemView.setOnClickListener {
            onClickListener(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    fun setData(tasks: List<Product>) {
        this.taskList = tasks
        notifyDataSetChanged()
    }
}
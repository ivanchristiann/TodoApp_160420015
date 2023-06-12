package ac.id.ubaya.todoapp_160420015.view

import ac.id.ubaya.todoapp_160420015.R
import ac.id.ubaya.todoapp_160420015.model.Todo
import ac.id.ubaya.todoapp_160420015.viewmodel.DetailTodoViewModel
import ac.id.ubaya.todoapp_160420015.viewmodel.ListTodoViewModel
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_create_todo.*
import kotlinx.android.synthetic.main.layout_todo_item.view.*

class TodoListAdapter(private val viewModelStoreOwner: ViewModelStoreOwner, val todoList:ArrayList<Todo>, val adapterOnClick: (Any)->Unit):RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>() {
    class TodoViewHolder(var view:View): RecyclerView.ViewHolder(view)
    private lateinit var viewModel: DetailTodoViewModel
    private lateinit var viewModelList: ListTodoViewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.layout_todo_item, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int){
        var checktask = holder.view.findViewById<CheckBox>(R.id.checkTask)
        var priority = ""
        if (todoList[position].priority == 1) priority = "Low"
        if (todoList[position].priority == 2) priority = "Medium"
        if (todoList[position].priority == 3) priority = "High"

        checktask.text = "[$priority] ${todoList[position].title}"

        holder.view.imageButtonEdit.setOnClickListener {
            val action = TodoListFragmentDirections.actionEditTodo(todoList[position].uuid)
            Navigation.findNavController(it).navigate(action)
        }

//        holder.view.checkTask.setOnCheckedChangeListener { compoundButton, isChecked ->
//            if(isChecked == true) {
//                adapterOnClick(todoList[position])
//            }
//        }

        holder.view.checkTask.setOnCheckedChangeListener { compoundButton, isChecked ->
            if(isChecked == true) {
                viewModel = ViewModelProvider(viewModelStoreOwner).get(DetailTodoViewModel::class.java)
                viewModel.changeisdone(0,todoList[position].uuid)

                viewModelList = ViewModelProvider(viewModelStoreOwner).get(ListTodoViewModel::class.java)
                viewModelList.refresh()
            }
        }

    }

    override fun getItemCount() = todoList.size

    fun updateTodoList(newTodoList: List<Todo>) {
        todoList.clear()
        todoList.addAll(newTodoList)
        notifyDataSetChanged()
    }

}







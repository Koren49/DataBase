package com.example.databaseapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(private var userList: List<User>, private val onUserActionListener: OnUserActionListener) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    // The receieved view is row_user.xml
    class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var userId: TextView = view.findViewById(R.id.user_id)
        var userFirstName: TextView = view.findViewById(R.id.user_first_name)
        var userLastName: TextView = view.findViewById(R.id.user_last_name)
        var rootLayout: ConstraintLayout = view.findViewById(R.id.root_layout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_user, parent, false)
        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.userId.text = user.id.toString()
        holder.userFirstName.text = user.firstName
        holder.userLastName.text = user.lastName
        holder.rootLayout.setOnLongClickListener {
            onUserActionListener.onDeleteUser(user)
            true
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

}

interface OnUserActionListener {
    fun onUserClicked(user: User)
    fun onDeleteUser(user: User)
    fun onSwipedUser(user: User)
}
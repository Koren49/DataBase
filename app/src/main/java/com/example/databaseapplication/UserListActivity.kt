package com.example.databaseapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView


// Koren wants to work on a feature. koren will do the following:
// 1. Create a feature branch
// 2. work on his feature
// 3. once done -> QA
// 4. create a pull request to the main branch (master)




class UserListActivity: AppCompatActivity(), OnUserActionListener{
    val mainViewModel: MainViewModel by viewModels()

    val swipeToDeleteCallBack = object : SwipeToDeleteCallBack() {
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            mainViewModel.removeUserInIndex(position, this@UserListActivity)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)
        println("yoyo onCreate")

        val userRecyclerView = findViewById<RecyclerView>(R.id.userListRecycler)
      //  mainViewModel.setContext(this)
        mainViewModel.userListLiveData.observe(this){
            val userAdapter = UserAdapter(it,this@UserListActivity)
            userRecyclerView.adapter = userAdapter
            userRecyclerView.addItemDecoration(
                DividerItemDecoration(
                    this,
                    DividerItemDecoration.VERTICAL
                )
            )
            val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallBack)
            itemTouchHelper.attachToRecyclerView(userRecyclerView)
        }
        mainViewModel.getUsersFromDataBase(this)
    }

    override fun onUserClicked(user: User) {
        TODO("Not yet implemented")
    }


    override fun onDeleteUser(user: User) {
        mainViewModel.removeUser(user, this)

    }

    override fun onSwipedUser(user: User) {
        mainViewModel.onSwipedUser(user, this)

    }


}





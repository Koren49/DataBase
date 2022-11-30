package com.example.databaseapplication

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// Rules regarding Coroutines (viewModelScope):
// 1. Every time you do a network operation (communication with Firebase, with Server etc..) -> do it inside a coroutine
// 2. Every time you do a databse operation - do it inside a coroutine
// 3. My personal preference - Every functino in the viewmodel that does logic, I like to put in a coroutine in order to not burden on the main thread (UI thread)
class MainViewModel(): ViewModel() {

    val userListLiveData = MutableLiveData<List<User>>()

    fun getUsersFromDataBase(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val database = AppDatabase.getDatabaseInstance(context)
            val userList = database.userDao().getAllUsers()

            userListLiveData.postValue(userList)

            // Notify all observers of userListLiveData that there is a new data -> userList

        }
    }

    fun addUserToDatabase(user: User, context: Context){
        viewModelScope.launch(Dispatchers.IO){
            val database = AppDatabase.getDatabaseInstance(context)
            database.userDao().insertUser(user)
            val userList = database.userDao().getAllUsers()
            userListLiveData.postValue(userList)
        }
    }

    fun onUserDeleted(user: User, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val database = AppDatabase.getDatabaseInstance(context)
            val userList = database.userDao().getAllUsers()
            Log.d("YOYO", "property deleted")
            AlertDialog.Builder(context)
                .setTitle("Delete entry")
                .setMessage("Are you sure you want to delete this entry?") // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes,
                    DialogInterface.OnClickListener { dialog, which ->
                        database.userDao().delete(user)
                        userListLiveData.postValue(userList)
                    }) // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .show()
        }

    }

    fun removeUserInIndex(index: Int, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = userListLiveData.value?.get(index) ?: return@launch
            val database = AppDatabase.getDatabaseInstance(context)
            database.userDao().delete(user)
            val userList = database.userDao().getAllUsers()
            userListLiveData.postValue(userList)
        }
    }
    fun removeUser(user: User, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val database = AppDatabase.getDatabaseInstance(context)
            database.userDao().delete(user)
            val userList = database.userDao().getAllUsers()
            userListLiveData.postValue(userList)
        }
    }


    fun onSwipedUser(user: User, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val database = AppDatabase.getDatabaseInstance(context)
            database.userDao().delete(user)
            val userList = database.userDao().getAllUsers()
            userListLiveData.postValue(userList)
        }
    }

        // Main Thread
    //  viewModelScope.launch(Dispatchers.IO) -> creates a background thread to run our code
}

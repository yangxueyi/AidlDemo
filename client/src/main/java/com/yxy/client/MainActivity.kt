package com.yxy.client

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.Looper
import android.os.RemoteException
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.yxy.service.Book
import com.yxy.service.BookController
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    private val TAG = "MainActivity"

    private var bookController: BookController? = null

    private var connected: Boolean = false

    private var bookList: List<Book>? = null

    private val serviceConnection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            bookController = BookController.Stub.asInterface(service)
            connected = true
        }

        override fun onServiceDisconnected(name: ComponentName) {
            connected = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindService()
        btn_getBookList.setOnClickListener {
            try {
                if (bookController != null) {
                    bookList = bookController!!.bookList
                    for (book in  bookList!!) {
                        Log.e(TAG, book.name)
                    }
                }
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }
        btn_addBook_inOut.setOnClickListener {

            try {
                val book = Book("InOut")
                val addBookInOut = bookController!!.addBookInOut(book)
                Log.e(TAG, "向服务器以InOut方式添加了一本新书")
                Log.e(TAG, "新书名：$addBookInOut")
                Toast.makeText(this, addBookInOut, Toast.LENGTH_SHORT).show()

            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        if (connected) {
            unbindService(serviceConnection)
        }
    }

    private fun bindService() {
        val intent = Intent()
        intent.setPackage("com.yxy.service")
        intent.action = "com.yxy.service.action"
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }
}

package com.yxy.service

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import java.util.ArrayList

/**
 * Created by YangXueYi
 * Time : jajaying on 2019/12/10 14:54
 */
@SuppressLint("Registered")
class AIDLService : Service() {

     var lists: MutableList<Book>? = null

    private val stub = object : BookController.Stub() {

        override fun getBookList(): List<Book>? {
            for (i in lists!!){
                Log.e("1111", i.name)
            }
            return lists
        }

        override fun addBookInOut(book: Book): String? {
            book.name = "服务器改了新书的名字 InOut"
            lists?.add(book)
            return book.name
        }

    }

    override fun onCreate() {
        super.onCreate()
        lists = ArrayList()
        initData()
    }

    private fun initData() {
        for (i in 0..9) {
            lists?.add(Book("红楼梦$i"))
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        return stub
    }
}

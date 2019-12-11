package com.yxy.service

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by YangXueYi
 * Time : jajaying on 2019/12/10 13:51
 */
class Book : Parcelable {

    var name: String? = null

    constructor(name: String) {
        this.name = name
    }

    override fun toString(): String {
        return "book name：" + name!!
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(this.name)
    }

    fun readFromParcel(dest: Parcel) {
        name = dest.readString()
    }

    protected constructor(`in`: Parcel) {
        this.name = `in`.readString()
    }

    companion object CREATOR : Parcelable.Creator<Book> {
        override fun createFromParcel(parcel: Parcel): Book {
            return Book(parcel)
        }

        override fun newArray(size: Int): Array<Book?> {
            return arrayOfNulls(size)
        }
    }

}


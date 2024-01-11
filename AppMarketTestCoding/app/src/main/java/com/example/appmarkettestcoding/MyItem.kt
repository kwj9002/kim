package com.example.appmarkettestcoding

import android.os.Parcel
import android.os.Parcelable

data class MyItem(
    val Image: Int,
    val ItemTitle: String,
    val ItemDetail: String,
    val SellerName: String,
    val Price: Int,
    val Address: String,
    var InterestCnt: Int,
    val ChatCnt: Int,
    var isLike: Boolean
)  : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()?: "",
        parcel.readString()?: "",
        parcel.readString()?: "",
        parcel.readInt(),
        parcel.readString()?: "",
        parcel.readInt(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(Image)
        parcel.writeString(ItemTitle)
        parcel.writeString(ItemDetail)
        parcel.writeString(SellerName)
        parcel.writeInt(Price)
        parcel.writeString(Address)
        parcel.writeInt(InterestCnt)
        parcel.writeInt(ChatCnt)
        parcel.writeByte(if (isLike) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MyItem> {
        override fun createFromParcel(parcel: Parcel): MyItem {
            return MyItem(parcel)
        }

        override fun newArray(size: Int): Array<MyItem?> {
            return arrayOfNulls(size)
        }
    }
}

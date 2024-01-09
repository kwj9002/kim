package com.example.appmarkettestcoding

import android.os.Parcel
import android.os.Parcelable

data class MyItem(
    var aImage: Int,
    val aImageName: String,
    val aProName: String,
    val aProIntro: String,
    val aSeller: String,
    val aPrice: String,
    val aAddress: String,
    var aGood: String = "0",
    val aChatting: String,
    var isLiked: Boolean = false
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "0",
        parcel.readString() ?: "",
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(aImage)
        parcel.writeString(aImageName)
        parcel.writeString(aProName)
        parcel.writeString(aProIntro)
        parcel.writeString(aSeller)
        parcel.writeString(aPrice)
        parcel.writeString(aAddress)
        parcel.writeString(aGood)
        parcel.writeString(aChatting)
        parcel.writeByte(if (isLiked) 1 else 0)
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

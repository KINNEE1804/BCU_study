package com.example.bcu_study.data.local

import androidx.room.TypeConverter

class ColorListConveter {

    //save data to the database
    @TypeConverter
    fun fromColorList(coloList: List<Int>): String{
        return coloList.joinToString(","){it.toString()}
    }

    //take data from datapool
    @TypeConverter
    fun toColorList(colorListString: String): List<Int>{
        return colorListString.split(",").map { it.toInt() }
    }
}
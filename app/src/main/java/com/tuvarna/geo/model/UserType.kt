package com.tuvarna.geo.model





class UserType {

    private var isBlocked: Boolean = false
    private var type = ""

    constructor(type: String){
        this.type = type
    }

    fun getType(): String{
        return type
    }

    fun setType(type: String){
        this.type = type
    }



}

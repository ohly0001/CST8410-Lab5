package com.example.lab5

data class Repository(var uname:String = "") {
    companion object {
        private var instance = Repository()
        fun getInstance() :Repository { return instance }
    }
}

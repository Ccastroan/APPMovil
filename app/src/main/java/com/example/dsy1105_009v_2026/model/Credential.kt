package com.example.dsy1105_009v_2026.model

data class Credential (val username:String, val password:String)
{
    companion object{
        val Admin= Credential(username = "admin", password = "123")
    }
}
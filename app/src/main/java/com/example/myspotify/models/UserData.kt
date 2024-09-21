package com.example.myspotify.models


import com.google.firebase.Timestamp

data class UserData(val trackTitle: String?=null,
    val artistName: String?=null,
    val coverImg: String?=null,
    val duration: String?=null,
    val userId: String?=null,
    val created: Timestamp?=null,
    val preview: String?=null
    ) {}
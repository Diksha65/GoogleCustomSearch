package com.example.dunzoassignment.model

import java.io.Serializable


data class FinalObject (
    val thumbnailsrc : String?,
    val src : String?,
    val title : String?,
    val link : String?,
    val snippet : String?,
    var isExpanded : Boolean
) : Serializable
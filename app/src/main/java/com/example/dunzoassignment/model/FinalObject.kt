package com.example.dunzoassignment.model

import java.io.Serializable


data class FinalObject (
    val thumbnailsrc : String?,
    val src : String?,
    val title : String?,
    val link : String?,
    val description : String?,
    var isExpanded : Boolean
) : Serializable
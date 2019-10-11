package com.example.simplefinancereport.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import java.util.*

@Entity
data class Report (
    @Id var id: Long = 0,
    var name: String? = null,
    var quantity: Long = 0,
    var date: Date = Calendar.getInstance().time
)
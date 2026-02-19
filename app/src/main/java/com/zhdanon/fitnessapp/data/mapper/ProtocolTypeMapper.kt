package com.zhdanon.fitnessapp.data.mapper

import com.zhdanon.fitnessapp.domain.models.workouts.ProtocolType

fun ProtocolType.toBackendString(): String =
    when (this) {
        ProtocolType.STANDARD -> "Standard"
        ProtocolType.AMRAP -> "AMRAP"
        ProtocolType.AFAP -> "AFAP"
        ProtocolType.EMOM -> "EMOM"
        ProtocolType.TABATA -> "TABATA"
    }

fun String.toProtocolType(): ProtocolType =
    when (this) {
        "Standard" -> ProtocolType.STANDARD
        "AMRAP" -> ProtocolType.AMRAP
        "AFAP" -> ProtocolType.AFAP
        "EMOM" -> ProtocolType.EMOM
        "TABATA" -> ProtocolType.TABATA
        else -> ProtocolType.STANDARD
    }
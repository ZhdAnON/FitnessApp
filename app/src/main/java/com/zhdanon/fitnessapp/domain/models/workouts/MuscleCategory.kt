package com.zhdanon.fitnessapp.domain.models.workouts

sealed class MuscleCategory(val title: String, val muscles: List<Muscle>) {

    object Shoulders : MuscleCategory(
        "Плечи",
        listOf(
            Muscle("передняя дельта"),
            Muscle("средняя дельта"),
            Muscle("задняя дельта")
        )
    )

    object Arms : MuscleCategory(
        "Руки",
        listOf(
            Muscle("бицепс"),
            Muscle("трицепс"),
            Muscle("предплечья"),
            Muscle("запястья")
        )
    )

    object Back : MuscleCategory(
        "Спина",
        listOf(
            Muscle("широчайшие"),
            Muscle("трапециевидная"),
            Muscle("ромбовидная"),
            Muscle("большие круглые мышцы"),
            Muscle("малые круглые мышцы"),
            Muscle("разгибатели спины")
        )
    )

    object Legs : MuscleCategory(
        "Ноги",
        listOf(
            Muscle("ягодицы"),
            Muscle("квадрицепс"),
            Muscle("задняя поверхность бедра"),
            Muscle("отводящие мышцы"),
            Muscle("приводящие мышцы"),
            Muscle("икроножные")
        )
    )

    object Core : MuscleCategory(
        "Кор",
        listOf(
            Muscle("прямая мышца живота"),
            Muscle("поперечная мышца живота"),
            Muscle("косые мышцы")
        )
    )

    companion object {
        val all = listOf(Shoulders, Arms, Back, Legs, Core)
    }
}

data class Muscle(val title: String)
package com.example.musictheory.data

enum class Tonality(val signCount: Int, val signType: String, val parallTon: String) {
    C(0, "Пусто", "a"),
    G(1, "Диезы", "e"),
    D(2, "Диезы", "h"),
    a(0, "Пусто", "C"),
    e(1, "Диезы", "G"),
    B(2, "Бемоли", "g")


}
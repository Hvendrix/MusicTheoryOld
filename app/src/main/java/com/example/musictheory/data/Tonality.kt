package com.example.musictheory.data

enum class Tonality(val signCount: Int, val signType: String, val parallTon: String) {
    C(0, "Пусто", "a"),
    a(0, "Пусто", "C"),
    G(1, "Диезы", "e"),
    D(2, "Диезы", "h"),
    A(3, "Диезы", "fis"),
    E(4, "Диезы", "cis"),
    H(5, "Диезы", "gis"),
    Fis(6, "Диезы", "dis"),
    e(1, "Диезы", "G"),
    h(2, "Диезы", "D"),
    fis(3, "Диезы", "A"),
    cis(4, "Диезы", "E"),
    B(2, "Бемоли", "g"),
    Es(3, "Бемоли", "c"),



}
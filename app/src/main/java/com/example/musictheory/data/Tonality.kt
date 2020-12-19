package com.example.musictheory.data

enum class Tonality(val signCount: Int, val signType: String, val mollDur: String, val parallTon: String, val rusName: String, val harmNote:String, var parallTonRef: Tonality?) {
//    C(0, "Пусто", "a", "До Мажор"),
//    a(0, "Пусто", "C", "Ля Минор"),
    G(1, "Диезы",  "dur","e", "соль мажор", "ми-бемоль", null),
    D(2, "Диезы", "dur","h", "ре мажор", "си-бемоль", null),
    A(3, "Диезы", "dur","fis", "ля мажор", "фа-бекар", null),
    E(4, "Диезы", "dur","cis", "ми мажор", "до-бекар", null),
//    H(5, "Диезы", "gis", "си мажор"),
//    Fis(6, "Диезы", "dis", "фа-диез мажор"),
    e(1, "Диезы", "moll","G", "ми минор", "ре-диез", null),
    h(2, "Диезы", "moll","D", "си минор", "ля-диез", null),
    fis(3, "Диезы", "moll","A", "фа-диез минор", "ми-диез", null),
    cis(4, "Диезы", "moll","E", "до-диез минор", "си-диез", null),
    B(2, "Бемоли", "dur","g", "си-бемоль мажор", "си-бемоль", null),
    Es(3, "Бемоли", "dur","c", "ми-бемоль мажор", "до-бемоль", null),
    As(4, "Бемоли", "dur","f", "ля-бемоль мажор", "фа-бемоль", null),
    g(2, "Бемоли", "moll", "B", "соль минор", "фа-диез", null),
    c(3, "Бемоли", "moll","Es", "до минор", "си-бекар", null),
    f(4, "Бемоли", "moll","As", "фа минор", "ми-бекар", null);


    companion object{
        init{
            change()
        }
        fun change(){
            values().forEach { ton ->
                ton.parallTonRef = valueOf(ton.parallTon)
            }
        }
    }

}
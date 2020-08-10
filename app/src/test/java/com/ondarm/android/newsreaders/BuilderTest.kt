package com.ondarm.android.newsreaders

fun main() {

    val nutritionFacts = NutritionFacts.Builder(3, 4)
        .build()

}

class NutritionFacts private constructor(builder: Builder) {
    private val servingSize: Int
    private val servings: Int
    private val calories: Int
    private val fat: Int
    private val sodium: Int
    private val carbohydrate: Int

    init {
        servingSize = builder.servingSize
        servings = builder.servings
        calories = builder.calories
        fat = builder.fat
        sodium = builder.sodium
        carbohydrate = builder.carbohydrate
    }

    class Builder(
        // Required parameters(필수 인자)
        internal val servingSize: Int, internal val servings: Int
    ) {
        // Optional parameters - initialized to default values(선택적 인자는 기본값으로 초기화)
        internal var calories = 0
        internal var fat = 0
        internal var carbohydrate = 0
        internal var sodium = 0
        fun calories(`val`: Int): Builder {
            calories = `val`
            return this // 이렇게 하면 . 으로 체인을 이어갈 수 있다.
        }

        fun fat(`val`: Int): Builder {
            fat = `val`
            return this
        }

        fun carbohydrate(`val`: Int): Builder {
            carbohydrate = `val`
            return this
        }

        fun sodium(`val`: Int): Builder {
            sodium = `val`
            return this
        }

        fun build(): NutritionFacts {
            return NutritionFacts(this)
        }

    }


}
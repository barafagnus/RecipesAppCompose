package ru.vysokov.recipesappcompose.data.repository

import ru.vysokov.recipesappcompose.data.model.CategoryDto
import ru.vysokov.recipesappcompose.data.model.IngredientDto
import ru.vysokov.recipesappcompose.data.model.RecipeDto


object RecipesRepositoryStub {
    fun getCategories(): List<CategoryDto> = categories

    fun getRecipesByCategoryId(categoryId: Int): List<RecipeDto> {
        return when (categoryId) {
            0 -> burgerRecipes
            else -> emptyList()
        }
    }

    private val categories = listOf(
        CategoryDto(0, "Бургеры", "Рецепты всех популярных видов бургеров", "burger.png"),
        CategoryDto(1, "Десерты", "Самые вкусные рецепты десертов специально для вас", "dessert.png"),
        CategoryDto(2, "Пицца", "Пицца на любой вкус и цвет. Лучшая подборка для тебя", "pizza.png"),
        CategoryDto(3, "Рыба", "Печеная, жареная, сушеная, любая рыба на твой вкус", "fish.png"),
        CategoryDto(4, "Супы", "От классики до экзотики: мир в одной тарелке", "soup.png"),
        CategoryDto(5, "Салаты", "Хрустящий калейдоскоп под соусом вдохновения", "salad.png")
    )

    private val burgerRecipes = listOf(
        RecipeDto(
            id = 0,
            title = "Классический бургер с говядиной",
            ingredients = listOf(
                IngredientDto("0.5", "кг", "говяжий фарш"),
                IngredientDto("1.0", "шт", "луковица, мелко нарезанная"),
                IngredientDto("2.0", "зубч", "чеснок, измельченный"),
                IngredientDto("4.0", "шт", "булочки для бургера"),
                IngredientDto("4.0", "шт", "листа салата"),
                IngredientDto("1.0", "шт", "помидор, нарезанный кольцами"),
                IngredientDto("2.0", "ст. л.", "горчица"),
                IngredientDto("2.0", "ст. л.", "кетчуп"),
                IngredientDto("по вкусу", "", "соль и черный перец")
            ),
            method = listOf(
                "1. В глубокой миске смешайте говяжий фарш, лук, чеснок, соль и перец. Разделите фарш на 4 равные части и сформируйте котлеты.",
                "2. Разогрейте сковороду на среднем огне. Обжаривайте котлеты с каждой стороны в течение 4-5 минут или до желаемой степени прожарки.",
                "3. В то время как котлеты готовятся, подготовьте булочки. Разрежьте их пополам и обжарьте на сковороде до золотистой корочки.",
                "4. Смазать нижние половинки булочек горчицей и кетчупом, затем положите лист салата, котлету, кольца помидора и закройте верхней половинкой булочки.",
                "5. Подавайте бургеры горячими с картофельными чипсами или картофельным пюре."
            ),
            imageUrl = "burger-hamburger.png"
        ),
        RecipeDto(
            id = 1,
            title = "Чизбургер с беконом",
            ingredients = listOf(
                IngredientDto("0.4", "кг", "говяжий фарш"),
                IngredientDto("4.0", "шт", "ломтика бекона"),
                IngredientDto("4.0", "шт", "ломтика сыра чеддер"),
                IngredientDto("4.0", "шт", "булочки для бургера"),
                IngredientDto("1.0", "шт", "помидор, нарезанный"),
                IngredientDto("по вкусу", "", "майонез и кетчуп")
            ),
            method = listOf(
                "1. Обжарьте бекон на сковороде до хрустящей корочки, отложите на бумажное полотенце.",
                "2. Сформируйте из фарша 4 котлеты, обжарьте с каждой стороны по 4 минуты.",
                "3. За минуту до готовности положите на каждую котлету по ломтику сыра, чтобы он расплавился.",
                "4. Соберите бургер: булочка, майонез, котлета с сыром, бекон, помидор, кетчуп.",
                "5. Подавайте горячими."
            ),
            imageUrl = "burger-cheeseburger.png"
        )
    )
}
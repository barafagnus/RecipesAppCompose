package fixtures

import ru.vysokov.recipesappcompose.data.model.CategoryDto

object CategoryTestFixtures {
    fun createCategoryDto(
        id: Int = 1,
        title: String = "Бургеры",
        description: String = "Всякие там бургерсы",
        imageUrl: String = "burger.jpg"
    ) = CategoryDto(
        id = id,
        title = title,
        description = description,
        imageUrl = imageUrl
    )

    fun createCategoryDtoList(count: Int = 3) =
        List(count) { index ->
            createCategoryDto(id = index, title = "Рецепт ${index + 1}")
        }
}
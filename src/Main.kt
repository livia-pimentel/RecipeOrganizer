fun main() {
    val recipeManagement = RecipeManagement("src/recipes.json")

    println("Existing Recipes:")
    recipeManagement.getAllRecipes().forEach { recipe ->
        println("Name: ${recipe.name}")
        println("Ingredients: ")
        recipe.ingredients.forEach { ingredients, quantaty ->
            println("- $ingredients: $quantaty")
        }
        println("Instructions: ${recipe.instructions}")
        println("Category: ${recipe.category}")
        println("--------------------")
    }

}

fun main() {
    val recipeManagement = RecipeManagement("src/recipes.json")

    while (true) {
        println("\nRecipe Organizer Menu:")
        println("1. View Recipes")
        println("2. Add Recipe")
        println("3. Delete Recipe")
        println("4. Exit")
        print("Enter your choice: ")

        when (readLine()?.toIntOrNull()) {
            1 -> viewRecipes(recipeManagement)
            2 -> addRecipe(recipeManagement)
            3 -> deleteRecipe(recipeManagement)
            4 -> return
            else -> println("Invalid choice. Please try again.")
        }
    }
}

fun viewRecipes(recipeManagement: RecipeManagement) {
    println("\nExisting Recipes:")
    recipeManagement.getAllRecipes().forEach { recipe ->
        println("Name: ${recipe.name}")
        println("Ingredients: ")
        recipe.ingredients.forEach { ingredient, quantity ->
            println("- $ingredient: $quantity")
        }
        println("Instructions: ")
        recipe.instructions.split("\n").forEach { instruction -> // Split instructions into lines
            println("- $instruction")
        }
        println("Category: ${recipe.category}")
        println("--------------------")
    }
}

fun addRecipe(recipeManagement: RecipeManagement) {
    print("Name: ")
    val name = readLine() ?: ""

    print("Ingredients (Ingredient: Quantity, Ingredient: Quantity): ")
    val ingredientsInput = readLine() ?: ""

    print("Instructions: ")
    val instructions = readLine() ?: ""

    print("Category: ")
    val category = readLine() ?: ""

    recipeManagement.addRecipe(name, ingredientsInput, instructions, category)
}

fun deleteRecipe(recipeManagement: RecipeManagement) {
    viewRecipes(recipeManagement) // Show the recipes for the user to choose which delete
    println("Enter the name of the recipe to delete: ")
    val recipeName = readLine() ?: ""
    recipeManagement.deleteRecipe(recipeName)
}
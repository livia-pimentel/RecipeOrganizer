import java.io.DataInput

fun main() {
    val recipeManagement = RecipeManagement("src/recipes.json")

//    println("Existing Recipes:")
//    recipeManagement.getAllRecipes().forEach { recipe ->
//        println("Name: ${recipe.name}")
//        println("Ingredients: ")
//        recipe.ingredients.forEach { ingredients, quantaty ->
//            println("- $ingredients: $quantaty")
//        }
//        println("Instructions: ${recipe.instructions}")
//        println("Category: ${recipe.category}")
//        println("--------------------")
//    }

    println("\n Enter details for the new recipe")
    print("Name: ")
    val name = readLine() ?: ""
    print("Ingredients (Ingredient: Quantity): ")
    val ingredientsInput = readLine() ?: ""
    print("Instructions: ")
    val instructions = readLine() ?: ""
    print("category: ")
    val category = readLine() ?: ""

    // Pass ingredientsInput as String to addRecipe
    recipeManagement.addRecipe(name, ingredientsInput, instructions, category)

    println("\nUpdate Recipes")
    recipeManagement.getAllRecipes().forEach { recipe ->
        println("Name: ${recipe.name}")
        println("Ingredients: ")
        recipe.ingredients.forEach { ingredient, quantity ->
            println("- $ingredient: $quantity")
        }
        println("Instructions: ${recipe.instructions}")
        println("category: ${recipe.category}")
        println("--------------------")
    }
}

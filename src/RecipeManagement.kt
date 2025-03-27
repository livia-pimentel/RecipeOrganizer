import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

class RecipeManagement (private val filePath: String){

    private val gson = Gson()
    private val recipes: MutableList<Recipe> = mutableListOf() // Mutable list to storage recipes

    init {
        loadRecipes() // Load recipes from JSON file
    }

    // Loads recipes from the JSON file
    private fun loadRecipes() {
        try {
            val json = File(filePath).readText() // Read the JSON file content
            val recipeListType = object : TypeToken<List<Recipe>>() {}.type // Define the type of the list
            recipes.addAll(gson.fromJson(json, recipeListType)) // Convert JSON to list of Recipe objects
            println("Recipes loaded successfully!")
        } catch (e: Exception) {
            println("Error loading recipes: ${e.message}")
        }
    }

    fun getAllRecipes(): List<Recipe> {
        return recipes.toList() // Return an immutable copy of the recipes list
    }

    // Parses the ingredients input string into a map.
    private fun parseIngredients(input: String): Map<String, String> {
        val ingredients = mutableMapOf<String, String>()
        val pairs = input.split(",")
        for (pair in pairs) {
            val parts = pair.trim().split(":")
            if (parts.size == 2) {
                ingredients[parts[0].trim()] = parts[1].trim()
            }
        }
        return ingredients
    }

    // Saves the list of recipes to the JSON file.
    private fun saveRecipes() {
        try {
            val json = Gson().toJson(recipes)
            File(filePath).writeText(json)
            println("Recipes saved successfully!")
        } catch (e: Exception) {
            println("Error saving recipes: ${e.message}")
        }
    }

    // Adds a new recipe to the list and saves it to the JSON file.
    fun addRecipe(name: String, ingredientsInput: String, instructionsInput: String, category: String) {
        if (name.isBlank() || ingredientsInput.isBlank() || instructionsInput.isBlank() || category.isBlank()) {
            println("All fields are required. Please provide valid input.")
            return
        }

        val ingredients = parseIngredients(ingredientsInput)
        val instructions = instructionsInput.split(".").map { it.trim() }.filter { it.isNotEmpty() }.joinToString("\n") // Join instructions with newlines
        val formattedName = formatInput(name)
        val formattedIngredients = ingredients.mapKeys { formatInput(it.key) }
        val formattedCategory = formatInput(category)

        val recipe = Recipe(formattedName, formattedIngredients, instructions, formattedCategory)
        recipes.add(recipe)
        saveRecipes()
        println("Recipe '$formattedName' added successfully!")
    }

    // Formats the input string by trimming whitespace, capitalizing the first letter, and lowercasing the rest.
    private fun formatInput(input: String): String {
        val trimmedWhitespace = input.trim() // Remove leading and trailing whitespace
        return if (isValidInput(trimmedWhitespace)) { // Validate input
            if (trimmedWhitespace.isNotEmpty()) {
                trimmedWhitespace.substring(0, 1).uppercase() + trimmedWhitespace.substring(1).lowercase() // Capitalize first letter and lowercase the rest
            } else {
                "" // Return empty string if input is empty
            }
        } else {
            println("Invalid input: '$input'. Only letters and numbers are allowed.")
            "" // Return empty string if input is invalid
        }
    }

    // Valid Input function
    private fun isValidInput(input: String): Boolean {
        return input.matches(Regex("[a-zA-Z0-9 .]+"))// Check if input contains only letters, numbers, and spaces
    }

    // Delete function
    fun deleteRecipe(recipeName: String) {
        val recipeToDelete = recipes.find { it.name.equals(recipeName, ignoreCase = true ) } // finds the name in the collection and ignores uppercase/lowercase letters
        if (recipeToDelete != null) {
            recipes.remove(recipeToDelete)
            saveRecipes()
            println("Recipe $recipeName deleted successfully!")
        } else {
            println("Recipe '$recipeName' not found.")
        }
    }

}
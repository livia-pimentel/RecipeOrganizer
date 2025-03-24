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

    // Adds a new recipe to the list and saves it to the JSON file.
    fun addRecipe (name: String, ingredients: Map<String, String>, instructions: String, category: String) {
        if(name.isBlank() || ingredients.isEmpty() || instructions.isBlank() || category.isBlank()) {
            println("All fields are required. Please provide valid input.")
            return // Exit the function if any field is blank
        }

        val formattedName = formatInput(name)
        val formattedIngredients = ingredients.mapKeys { formatInput(it.key) }
        val formattedInstructions = formatInput(instructions)
        val formattedCategory = formatInput(category)

        val recipe = Recipe(formattedName, formattedIngredients, formattedInstructions, formattedCategory)
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

    private fun isValidInput(input: String): Boolean {
        return input.matches(Regex("[a-zA-Z0-9 ]+")) // Check if input contains only letters, numbers, and spaces
    }

}
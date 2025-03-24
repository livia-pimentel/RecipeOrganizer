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
        val formattedName = formatInput(name) // Format the recipe name
        val formattedIngredients = ingredients.mapKeys { formattedName(it.key) } // Format ingredient names
        val formattedInstructions = formatInput(instructions) // Format instructions
        val formattedCategory = formatInput(category) // Format category

        val recipe = Recipe(formattedName,formattedIngredients, formattedInstructions, formattedCategory) // Create a new Recipe object
        recipes.add(recipe) // Add the recipe to the list
        saveRecipe() // Save to JSON file
        println("Recipe '$formattedName' added successfully!")
    }

}
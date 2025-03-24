import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.nio.file.Path

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

}
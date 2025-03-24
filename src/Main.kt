// Class to storage data
data class Recipe (
    val name: String, // Recipe name
    val ingredients: Map<String, String>, // Ingredient names and quantities
    val instructions: String, // Preparation instructions
    val category: String, // Recipe category
)

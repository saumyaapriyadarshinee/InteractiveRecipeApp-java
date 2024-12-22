import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class Recipe {
    String name;
    String category;
    List<String> ingredients;
    List<String> instructions;
    String nutritionInfo;
    double rating;
    int ratingCount;
    List<String> comments;
    String cookingTime; // Added to filter by time
    String difficultyLevel; // Added to filter by difficulty level

    public Recipe(String name, String category, List<String> ingredients, List<String> instructions, String nutritionInfo, String cookingTime, String difficultyLevel) {
        this.name = name;
        this.category = category;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.nutritionInfo = nutritionInfo;
        this.rating = 0;
        this.ratingCount = 0;
        this.comments = new ArrayList<>();
        this.cookingTime = cookingTime;
        this.difficultyLevel = difficultyLevel;
    }

    public void displayRecipe() {
        System.out.println("Recipe: " + name);
        System.out.println("Category: " + category);
        System.out.println("Cooking Time: " + cookingTime);
        System.out.println("Difficulty: " + difficultyLevel);
        System.out.println("Ingredients: " + String.join(", ", ingredients));
        System.out.println("Instructions: ");
        for (String instruction : instructions) {
            System.out.println("- " + instruction);
        }
        System.out.println("Nutrition Info: " + nutritionInfo);
        System.out.println("Rating: " + (ratingCount > 0 ? rating / ratingCount : "No ratings yet"));
        if (!comments.isEmpty()) {
            System.out.println("Comments:");
            comments.forEach(comment -> System.out.println("- " + comment));
        }
    }

    public void addRating(double newRating) {
        rating = (rating * ratingCount + newRating) / (ratingCount + 1);
        ratingCount++;
    }

    public void addComment(String comment) {
        comments.add(comment);
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getCookingTime() {
        return cookingTime;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public String getNutritionInfo() {
        return nutritionInfo;
    }
}

class User {
    String username;
    String password;
    List<Recipe> favoriteRecipes;
    boolean loggedIn;
    String fullName;
    boolean isAdmin;  // Admin flag

    public User(String username, String password, String fullName, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.favoriteRecipes = new ArrayList<>();
        this.loggedIn = false;
        this.fullName = fullName;
        this.isAdmin = isAdmin;
    }

    public void addFavoriteRecipe(Recipe recipe) {
        favoriteRecipes.add(recipe);
        System.out.println(recipe.getName() + " has been added to your favorites!");
    }

    public void displayFavorites() {
        if (favoriteRecipes.isEmpty()) {
            System.out.println("No favorite recipes yet.");
        } else {
            System.out.println("Your Favorite Recipes:");
            for (Recipe recipe : favoriteRecipes) {
                System.out.println("- " + recipe.getName());
            }
        }
    }

    public void login(String password) {
        if (this.password.equals(password)) {
            loggedIn = true;
            System.out.println("Logged in successfully!");
        } else {
            System.out.println("Invalid password.");
        }
    }

    public void logout() {
        loggedIn = false;
        System.out.println("Logged out successfully.");
    }

    public void updateProfile(String newPassword, String newFullName) {
        this.password = newPassword;
        this.fullName = newFullName;
        System.out.println("Profile updated successfully.");
    }

    public String getFullName() {
        return fullName;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}

public class InteractiveRecipeApp {
    private static List<Recipe> recipes = new ArrayList<>();
    private static Map<String, User> users = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);
    private static User currentUser;

    public static void main(String[] args) {
        loadSampleRecipes();
        loadSampleUsers();

        // User authentication
        System.out.println("Welcome to the Interactive Recipe App!");
        while (true) {
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int authChoice = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            if (authChoice == 1) {
                loginUser();
            } else if (authChoice == 2) {
                registerUser();
            } else {
                break;
            }

            // After login, show the main menu
            if (currentUser != null) {
                while (true) {
                    System.out.println("\nWelcome " + currentUser.username + "!");
                    System.out.println("1. View All Recipes");
                    System.out.println("2. Search Recipe by Name");
                    System.out.println("3. Add Recipe to Favorites");
                    System.out.println("4. View Favorite Recipes");
                    System.out.println("5. Rate a Recipe");
                    System.out.println("6. Comment on a Recipe");
                    System.out.println("7. Generate Shopping List");
                    System.out.println("8. Edit Recipe");
                    System.out.println("9. Delete Recipe");
                    System.out.println("10. Update Profile");
                    System.out.println("11. Log Out");
                    if (currentUser.isAdmin()) {
                        System.out.println("12. Add Recipe (Admin)");
                        System.out.println("13. Delete Recipe (Admin)");
                    }
                    System.out.println("14. Exit");
                    System.out.print("Choose an option: ");
                    int choice = scanner.nextInt();
                    scanner.nextLine();  // Clear buffer

                    switch (choice) {
                        case 1:
                            viewAllRecipes();
                            break;
                        case 2:
                            searchRecipeByName();
                            break;
                        case 3:
                            addRecipeToFavorites();
                            break;
                        case 4:
                            currentUser.displayFavorites();
                            break;
                        case 5:
                            rateRecipe();
                            break;
                        case 6:
                            commentOnRecipe();
                            break;
                        case 7:
                            generateShoppingList();
                            break;
                        case 8:
                            editRecipe();
                            break;
                        case 9:
                            deleteRecipe();
                            break;
                        case 10:
                            updateProfile();
                            break;
                        case 11:
                            currentUser.logout();
                            currentUser = null;
                            break;
                        case 12:
                            if (currentUser.isAdmin()) {
                                addNewRecipeAdmin();
                            }
                            break;
                        case 13:
                            if (currentUser.isAdmin()) {
                                deleteRecipeAdmin();
                            }
                            break;
                        case 14:
                            System.out.println("Thank you for using the app! Goodbye.");
                            saveRecipesToFile();
                            return;
                        default:
                            System.out.println("Invalid choice, please try again.");
                    }

                    if (!currentUser.loggedIn) {
                        break;
                    }
                }
            }
        }
    }

    private static void loadSampleRecipes() {
        List<String> ingredients1 = Arrays.asList("Eggs", "Milk", "Butter", "Salt");
        List<String> instructions1 = Arrays.asList("Beat the eggs.", "Add milk and whisk.", "Cook in a pan with butter.");
        Recipe recipe1 = new Recipe("Scrambled Eggs", "Breakfast", ingredients1, instructions1, "Calories: 200, Fat: 14g, Protein: 12g", "10 mins", "Easy");

        List<String> ingredients2 = Arrays.asList("Chicken", "Garlic", "Lemon", "Olive oil", "Salt");
        List<String> instructions2 = Arrays.asList("Marinate chicken with garlic, lemon, and olive oil.", "Grill until cooked.");
        Recipe recipe2 = new Recipe("Grilled Chicken", "Dinner", ingredients2, instructions2, "Calories: 300, Fat: 10g, Protein: 30g", "30 mins", "Medium");

        List<String> ingredients3 = Arrays.asList("Flour", "Sugar", "Eggs", "Butter", "Baking powder");
        List<String> instructions3 = Arrays.asList("Mix ingredients.", "Pour batter into a pan and bake.");
        Recipe recipe3 = new Recipe("Vanilla Cake", "Dessert", ingredients3, instructions3, "Calories: 250, Fat: 12g, Protein: 4g", "45 mins", "Hard");

        recipes.add(recipe1);
        recipes.add(recipe2);
        recipes.add(recipe3);
    }

    private static void loadSampleUsers() {
        users.put("john_doe", new User("john_doe", "password123", "John Doe", false));
        users.put("admin", new User("admin", "admin123", "Admin User", true));  // Admin user
    }

    private static void saveRecipesToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("recipes.ser"))) {
            oos.writeObject(recipes);
            System.out.println("Recipes have been saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving recipes: " + e.getMessage());
        }
    }

    private static void loginUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (users.containsKey(username)) {
            currentUser = users.get(username);
            currentUser.login(password);
        } else {
            System.out.println("User not found.");
        }
    }

    private static void registerUser() {
        System.out.print("Enter new username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter full name: ");
        String fullName = scanner.nextLine();

        // Default: Regular user
        User newUser = new User(username, password, fullName, false);
        users.put(username, newUser);
        System.out.println("User registered successfully! You can now log in.");
    }

    private static void viewAllRecipes() {
        System.out.println("\nAll Recipes:");
        for (Recipe recipe : recipes) {
            recipe.displayRecipe();
        }
    }

    private static void searchRecipeByName() {
        System.out.print("Enter recipe name: ");
        String name = scanner.nextLine().toLowerCase();
        List<Recipe> filteredRecipes = recipes.stream()
                .filter(recipe -> recipe.getName().toLowerCase().contains(name))
                .collect(Collectors.toList());

        if (filteredRecipes.isEmpty()) {
            System.out.println("No recipes found.");
        } else {
            filteredRecipes.forEach(Recipe::displayRecipe);
        }
    }

    private static void addRecipeToFavorites() {
        System.out.print("Enter recipe name to add to favorites: ");
        String name = scanner.nextLine();

        Optional<Recipe> recipeOptional = recipes.stream()
                .filter(recipe -> recipe.getName().equalsIgnoreCase(name))
                .findFirst();

        if (recipeOptional.isPresent()) {
            currentUser.addFavoriteRecipe(recipeOptional.get());
        } else {
            System.out.println("Recipe not found.");
        }
    }

    private static void rateRecipe() {
        System.out.print("Enter recipe name to rate: ");
        String name = scanner.nextLine();

        Optional<Recipe> recipeOptional = recipes.stream()
                .filter(recipe -> recipe.getName().equalsIgnoreCase(name))
                .findFirst();

        if (recipeOptional.isPresent()) {
            Recipe recipe = recipeOptional.get();
            System.out.print("Enter rating (1-5): ");
            double rating = scanner.nextDouble();
            scanner.nextLine();  // Clear buffer
            recipe.addRating(rating);
            System.out.println("Rating added successfully!");
        } else {
            System.out.println("Recipe not found.");
        }
    }

    private static void commentOnRecipe() {
        System.out.print("Enter recipe name to comment on: ");
        String name = scanner.nextLine();

        Optional<Recipe> recipeOptional = recipes.stream()
                .filter(recipe -> recipe.getName().equalsIgnoreCase(name))
                .findFirst();

        if (recipeOptional.isPresent()) {
            Recipe recipe = recipeOptional.get();
            System.out.print("Enter comment: ");
            String comment = scanner.nextLine();
            recipe.addComment(comment);
            System.out.println("Comment added successfully!");
        } else {
            System.out.println("Recipe not found.");
        }
    }

    private static void generateShoppingList() {
        System.out.println("Generate Shopping List for which recipe?");
        String name = scanner.nextLine();
        Optional<Recipe> recipeOptional = recipes.stream()
                .filter(recipe -> recipe.getName().equalsIgnoreCase(name))
                .findFirst();
        
        if (recipeOptional.isPresent()) {
            Recipe recipe = recipeOptional.get();
            System.out.println("Shopping List: ");
            recipe.ingredients.forEach(ingredient -> System.out.println("- " + ingredient));
        } else {
            System.out.println("Recipe not found.");
        }
    }

    private static void editRecipe() {
        if (currentUser.isAdmin()) {
            System.out.print("Enter recipe name to edit: ");
            String name = scanner.nextLine();
            Optional<Recipe> recipeOptional = recipes.stream()
                    .filter(recipe -> recipe.getName().equalsIgnoreCase(name))
                    .findFirst();

            if (recipeOptional.isPresent()) {
                Recipe recipe = recipeOptional.get();
                System.out.print("Enter new cooking time: ");
                String cookingTime = scanner.nextLine();
                System.out.print("Enter new difficulty level: ");
                String difficultyLevel = scanner.nextLine();
                recipe.cookingTime = cookingTime;
                recipe.difficultyLevel = difficultyLevel;
                System.out.println("Recipe updated successfully!");
            } else {
                System.out.println("Recipe not found.");
            }
        } else {
            System.out.println("Only admins can edit recipes.");
        }
    }

    private static void deleteRecipe() {
        if (currentUser.isAdmin()) {
            System.out.print("Enter recipe name to delete: ");
            String name = scanner.nextLine();
            Optional<Recipe> recipeOptional = recipes.stream()
                    .filter(recipe -> recipe.getName().equalsIgnoreCase(name))
                    .findFirst();

            if (recipeOptional.isPresent()) {
                recipes.remove(recipeOptional.get());
                System.out.println("Recipe deleted successfully!");
            } else {
                System.out.println("Recipe not found.");
            }
        } else {
            System.out.println("Only admins can delete recipes.");
        }
    }

    private static void updateProfile() {
        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine();
        System.out.print("Enter new full name: ");
        String newFullName = scanner.nextLine();
        currentUser.updateProfile(newPassword, newFullName);
    }

    private static void addNewRecipeAdmin() {
        if (currentUser.isAdmin()) {
            System.out.print("Enter recipe name: ");
            String name = scanner.nextLine();
            System.out.print("Enter recipe category: ");
            String category = scanner.nextLine();
            System.out.print("Enter ingredients (comma-separated): ");
            String ingredientsInput = scanner.nextLine();
            List<String> ingredients = Arrays.asList(ingredientsInput.split(","));
            System.out.print("Enter instructions (comma-separated): ");
            String instructionsInput = scanner.nextLine();
            List<String> instructions = Arrays.asList(instructionsInput.split(","));
            System.out.print("Enter nutrition info: ");
            String nutritionInfo = scanner.nextLine();
            System.out.print("Enter cooking time: ");
            String cookingTime = scanner.nextLine();
            System.out.print("Enter difficulty level: ");
            String difficultyLevel = scanner.nextLine();

            Recipe newRecipe = new Recipe(name, category, ingredients, instructions, nutritionInfo, cookingTime, difficultyLevel);
            recipes.add(newRecipe);
            System.out.println("New recipe added successfully!");
        }
    }

    private static void deleteRecipeAdmin() {
        if (currentUser.isAdmin()) {
            System.out.print("Enter recipe name to delete: ");
            String name = scanner.nextLine();
            Optional<Recipe> recipeOptional = recipes.stream()
                    .filter(recipe -> recipe.getName().equalsIgnoreCase(name))
                    .findFirst();

            if (recipeOptional.isPresent()) {
                recipes.remove(recipeOptional.get());
                System.out.println("Recipe deleted successfully!");
            } else {
                System.out.println("Recipe not found.");
            }
        }
    }
}

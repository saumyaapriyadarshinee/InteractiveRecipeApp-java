# InteractiveRecipeApp-java
The Interactive Recipe App is a Java-based application designed for users to browse, search, rate, comment on, and save their favorite recipes. The app allows both regular users and admins to interact with the recipe database. Regular users can search for recipes, rate them, add them to their favorites, and leave comment.
 The app supports advanced features such as filtering recipes based on category, cooking time, difficulty, and nutritional information. Users can also generate a shopping list based on selected recipes.

The app includes features for user authentication, user profile management, and managing a collection of recipes. It provides a simple and interactive interface to help users find and explore new recipes, save their favorites, and enhance their cooking experience.

Features:
User Authentication: Register, login, and manage user profiles.
Recipe Management: View, search, rate, comment, and add recipes to favorites.
Admin Functions: Admins can add, edit, and delete recipes.
Recipe Filters: Search by recipe name, category, cooking time, difficulty, and nutritional info.
Shopping List: Generate shopping lists based on selected recipes.
User Comments and Ratings: Rate recipes and leave comments for other users.
Usage:
Login:

Use the default username and password below to log in:
Username: john_doe
Password: password123
Alternatively, create a new user via the registration process.
Admin Login:

For admin privileges, use the following credentials:
Username: admin
Password: admin123
Explore Recipes:

Once logged in, users can view all recipes, search by name, or filter recipes by category, cooking time, or difficulty.
Add to Favorites:

Users can add recipes to their favorites list for easy access later.
Rate and Comment:

Users can rate recipes from 1 to 5 stars and leave comments for others to read.
Admin Functions:

Admins can add, edit, and delete recipes, ensuring the recipe database is always up-to-date.
Installation:
Clone the repository to your local machine using the command:

bash
Copy code
git clone <repository_url>
Compile and Run the Program:

Navigate to the directory where the file is located and compile the code:
Copy code
javac InteractiveRecipeApp.java
Run the application:
Copy code
java InteractiveRecipeApp
Dependencies:
Java Development Kit (JDK) 8 or higher is required to compile and run the application.
Notes:
The app uses file serialization (recipes.ser) to save the recipes data. Make sure the app has the necessary permissions to create files in its directory.
You can extend the functionality by adding more features such as multi-user support, recipe sharing, and more.
Example Credentials:
Admin Login:
Username: admin
Password: admin123
User Login:
Username: john_doe
Password: password123
License:
This project is open-source. Feel free to modify and use it for educational or personal purposes.

package com.maven.cookbook.model;

public class FoodDTO{
    private Food food;
    private String username;
    private String difficultyName;
    private String mealTypeType;
    private String cuisineType;

    public FoodDTO(Food food, String username, String difficultyName,
            String mealTypeType, String cuisineType) {
        this.food = food;
        this.username = username;
        this.difficultyName = difficultyName;
        this.mealTypeType = mealTypeType;
        this.cuisineType = cuisineType;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDifficultyName() {
        return difficultyName;
    }

    public void setDifficultyName(String difficultyName) {
        this.difficultyName = difficultyName;
    }

    public String getMealTypeType() {
        return mealTypeType;
    }

    public void setMealTypeType(String mealTypeType) {
        this.mealTypeType = mealTypeType;
    }

    public String getCuisineType() {
        return cuisineType;
    }

    public void setCuisineType(String cuisineType) {
        this.cuisineType = cuisineType;
    }
}

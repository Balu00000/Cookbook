package com.maven.cookbook.model;

public class UserDTO{
    private User user;
    private Integer postedFood;
    private Integer favouriteFood;

    public UserDTO(User user, Integer postedFood, Integer favouriteFood) {
        this.user = user;
        this.postedFood = postedFood;
        this.favouriteFood = favouriteFood;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getPostedFood() {
        return postedFood;
    }

    public void setPostedFood(Integer postedFood) {
        this.postedFood = postedFood;
    }
    
    public Integer getFavouriteFood() {
        return favouriteFood;
    }

    public void setFavouriteFood(Integer favouriteFood) {
        this.favouriteFood = favouriteFood;
    }
}

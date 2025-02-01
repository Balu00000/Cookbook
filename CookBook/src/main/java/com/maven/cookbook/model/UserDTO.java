package com.maven.cookbook.model;

public class UserDTO{
    private User user;
    private Integer postedFood;

    public UserDTO(User user, Integer postedFood) {
        this.user = user;
        this.postedFood = postedFood;
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
}

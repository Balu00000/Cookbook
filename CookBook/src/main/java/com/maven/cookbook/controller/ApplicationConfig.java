package com.maven.cookbook.controller;

import java.util.Set;
import javax.ws.rs.core.Application;

@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.maven.cookbook.controller.CuisineController.class);
        resources.add(com.maven.cookbook.controller.DietaryController.class);
        resources.add(com.maven.cookbook.controller.DifficultyController.class);
        resources.add(com.maven.cookbook.controller.FavouriteController.class);
        resources.add(com.maven.cookbook.controller.FoodController.class);
        resources.add(com.maven.cookbook.controller.MealTypeController.class);
        resources.add(com.maven.cookbook.controller.UserController.class);
        resources.add(com.maven.cookbook.filters.CORSFilter.class);
    }
}

-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 03, 2025 at 08:35 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `cookbook`
--
CREATE DATABASE IF NOT EXISTS `cookbook` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `cookbook`;

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `addAllergen` (IN `typeIN` VARCHAR(255) CHARSET utf8mb4, IN `effectIN` VARCHAR(255) CHARSET utf8mb4)   INSERT INTO `allergen`(
    `allergen`.`type`,
    `allergen`.`effect`
)VALUES (
    typeIN,
    effectIN
)$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `addCuisine` (IN `typeIN` VARCHAR(255) CHARSET utf8mb4)   INSERT INTO `cuisine`(
    `cuisine`.`type`
)VALUES(
    typeIN
)$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `addDietary` (IN `typeIN` VARCHAR(255) CHARSET utf8mb4)   INSERT INTO `dietary`(
    `dietary`.`type`
)VALUES(
    typeIN
)$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `addDifficulty` (IN `nameIN` VARCHAR(255) CHARSET utf8mb4, IN `equipmentIN` VARCHAR(255) CHARSET utf8mb4)   INSERT INTO `difficulty`
VALUES(
    null,
    nameIN,
    equipmentIN
)$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `addFavourite` (IN `userIdIN` INT(11), IN `foodIdIN` INT(11))   BEGIN
INSERT INTO `favourite`(
    `favourite`.`user_id`, 
    `favourite`.`food_id`
)VALUES (
    userIdIN,
    foodIdIN
);
set @rat = 0;
SELECT `food`.`rating` INTO @rat FROM `food` WHERE `food`.`id` = foodIdIN;
UPDATE `food` SET `food`.`rating` = @rat + 1 WHERE `food`.`id` = foodIdIN;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `addFood` (IN `nameIN` VARCHAR(255) CHARSET utf8mb4, IN `imageIN` TEXT, IN `descriptionIN` TEXT, IN `preptimeIN` VARCHAR(100) CHARSET utf8mb4, IN `userIdIN` INT(11), IN `instructionsIN` TEXT, IN `difficultyIdIN` INT(11), IN `mealTypeIdIN` INT(11), IN `cuisineIdIN` INT(11), IN `ingredientsIN` TEXT)   BEGIN
INSERT INTO `food`( 
    `name`,
    `image`,
    `description`,
    `prep_time`,
    `user_id`,
    `instructions`,
    `difficulty_id`,
    `meal_type_id`,
    `cuisine_id`
)
VALUES(
    nameIN,
    imageIN,
    descriptionIN,
    preptimeIN,
    userIdIN,
    instructionsIN,
    difficultyIdIN,
    mealTypeIdIN,
    cuisineIdIN
);

SET @foodId = LAST_INSERT_ID();

WHILE LOCATE(';', ingredientsIN) > 0 DO
	SET @ingredient = SUBSTRING_INDEX(ingredientsIN, ';', 1);
	SET @ingredientName = TRIM(SUBSTRING_INDEX(@ingredient, ',', 1));
    SET @amount = SUBSTRING_INDEX(SUBSTRING_INDEX(@ingredient, ',', -2), ',', 1);
    SET @measurement = SUBSTRING_INDEX(@ingredient, ',', -1);
    
    SET @ingredientId = NULL;
    SET @allergenId = NULL;
    
    SELECT `id` INTO @ingredientId FROM `ingredient` WHERE `name` = @ingredientName;
    SELECT `id` INTO @allergenId FROM `allergen` WHERE `type` = @ingredientName;
    IF @allergenId IS NULL THEN 
        SET @allergenId = 0;
    END IF;
    SELECT @ingredientName, @ingredientId, @allergenId;
    
    INSERT INTO `recipe` (`food_id`, `ingredient_id`, `amount`, `measurement`) 
    VALUES (@foodId, @ingredientId, @amount, @measurement);
    INSERT INTO `food_x_allergen` (`food_id`, `allergen_id`) 
    VALUES (@foodId, @allergenId);
    SET ingredientsIN = SUBSTRING(ingredientsIN, LOCATE(';', ingredientsIN) + 1);
END WHILE;
IF TRIM(ingredientsIN) != '' THEN
	SET @ingredientName = TRIM(SUBSTRING_INDEX(ingredientsIN, ',', 1));
    SET @amount = SUBSTRING_INDEX(SUBSTRING_INDEX(ingredientsIN, ',', -2), ',', 1);
    SET @measurement = SUBSTRING_INDEX(ingredientsIN, ',', -1);
    
    SET @ingredientId = NULL;
    SET @allergenId = NULL;
    
    SELECT `id` INTO @ingredientId FROM `ingredient` WHERE `name` = @ingredientName;
    SELECT `id` INTO @allergenId FROM `allergen` WHERE `type` = @ingredientName;
    IF @allergenId IS NULL THEN 
        SET @allergenId = 0;
    END IF;
    SELECT @ingredientName, @ingredientId, @allergenId;
    
	INSERT INTO `recipe` (`food_id`, `ingredient_id`, `amount`, `measurement`) 
    VALUES (@foodId, @ingredientId, @amount, @measurement);
    INSERT INTO `food_x_allergen` (`food_id`, `allergen_id`) 
    VALUES (@foodId, @allergenId);
END IF;

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `addFoodAllergen` (IN `foodIdIN` INT(11), IN `allergenIdIN` INT(11))   INSERT INTO `food_x_allergen`(
    `food_x_allergen`.`allergen_id`, 
    `food_x_allergen`.`food_id`
)VALUES(
    allergenIdIN,
    foodIdIN
)$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `addFoodbutWorse` (IN `nameIN` VARCHAR(255) CHARSET utf8mb4, IN `imageIN` TEXT, IN `descriptionIN` TEXT CHARSET utf8mb4, IN `userIdIN` INT(11), IN `instructionsIN` TEXT CHARSET utf8mb4, IN `difficultyIdIN` INT(11), IN `mealTypeIdIN` INT(11), IN `cuisineIdIN` INT(11), IN `allergenIdIN` INT(11))   BEGIN

INSERT INTO `food`( 
    `food`.`name`,
    `food`.`image`,
    `food`.`description`,
    `food`.`user_id`,
    `food`.`instructions`,
    `food`.`difficulty_id`,
    `food`.`meal_type_id`,
    `food`.`cuisine_id`,
    `food`.`added_at`,
    `food`.`is_deleted`,
    `food`.`deleted_at`
)
VALUES(
    nameIN,
    imageIN,
    descriptionIN,
    userIdIN,
    instructionsIN,
    difficultyIdIN,
    mealTypeIdIN,
    cuisineIdIN,
	CURRENT_TIMESTAMP(),
    0,
    null
);

SET @fodId =(SELECT id FROM `food` WHERE `food`.`name` = nameIN);

INSERT INTO `food_x_allergen` (
    `food_x_allergen`.`allergen_id`, 
    `food_x_allergen`.`food_id`
)VALUES (
    allergenIdIN, @fodId
);

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `addFoodDietary` (IN `dietaryIdIN` INT(11), IN `foodIdIN` INT(11))   INSERT INTO `food_x_dietary`(
    `food_x_dietary`.`dietary_id`,
    `food_x_dietary`.`food_id`
)VALUES(
    dietaryIdIN,
    foodIdIN
)$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `addIngredient` (IN `nameIN` VARCHAR(255) CHARSET utf8mb4, IN `proteinIN` FLOAT(10), IN `carbIN` FLOAT(10), IN `fatIN` FLOAT(10), IN `cholesterolIN` FLOAT(10), IN `fiberIN` FLOAT(10))   INSERT INTO `ingredient`
VALUES(
    null,
    nameIN,
    proteinIN,
    carbIN,
    fatIN,
    cholesterolIN,
    fiberIN
)$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `addMealType` (IN `typeIN` VARCHAR(255) CHARSET utf8mb4)   INSERT INTO `meal_type`(
    `meal_type`.`type`
)VALUES(
    typeIN
)$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `addRecipe` (IN `foodIdIN` INT(11), IN `ingredientIdIN` INT(11), IN `amountIN` INT(11), IN `measurementIN` VARCHAR(255) CHARSET utf8mb4)   INSERT INTO `recipe`
VALUES(
    null,
    foodIdIN,
    ingredientIdIN,
    amountIN,
    measurementIN
)$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteAllergen` (IN `idIN` INT(11))   DELETE FROM `allergen` WHERE `allergen`.`id` = idIN$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteCuisine` (IN `idIN` INT(11))   DELETE FROM `cuisine` WHERE `cuisine`.`id` = idIN$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteDietary` (IN `idIN` INT(11))   DELETE FROM `dietary` WHERE `dietary`.`id` = idIN$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteDifficulty` (IN `idIN` INT)   DELETE FROM `difficulty` WHERE `difficulty`.`id` = idIN$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteFavourite` (IN `userIdIN` INT(11), IN `foodIdIN` INT(11))   BEGIN
DELETE FROM `favourite` WHERE `favourite`.`food_id` = foodIdIN AND `favourite`.`user_id` = userIdIN;

set @rat = 0;
SELECT `food`.`rating` INTO @rat FROM `food` WHERE `food`.`id` = foodIdIN;
UPDATE `food` SET `food`.`rating` = @rat - 1 WHERE `food`.`id` = foodIdIN;

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteFoodAllergen` (IN `idIN` INT(11))   DELETE FROM `food_x_allergen` WHERE `food_x_allergen`.`id` = idIN$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteFoodById` (IN `idIN` INT(11))   UPDATE `food`
SET `food`.`is_deleted` = 1,
`food`.`deleted_at` = CURRENT_TIMESTAMP
WHERE `food`.`id` = idIN AND `food`.`is_deleted` = 0$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteFoodDietary` (IN `idIN` INT(11))   DELETE FROM `food_x_dietary` WHERE `food_x_dietary`.`id` = idIN$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteIngredient` (IN `idIN` INT)   DELETE FROM `ingredient` WHERE `ingredient`.`id` = idIN$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteMealType` (IN `idIN` INT(11))   DELETE FROM `meal_type` WHERE `meal_type`.`id` = idIN$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteRecipe` (IN `idIN` INT(11))   DELETE FROM `recipe` WHERE `recipe`.`id` = idIN$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteUserById` (IN `idIN` INT(11))   UPDATE `user`
SET `user`.`is_deleted` = 1,
`user`.`deleted_at`= CURRENT_TIMESTAMP
WHERE `user`.`id` = idIN AND `user`.`is_deleted` = 0$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllCuisine` ()   SELECT * FROM `cuisine`$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllDietary` ()   SELECT * FROM `dietary`$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllDifficulty` ()   SELECT `id`, `name` FROM `difficulty`$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllergen` (IN `idIN` INT(11))   SELECT * FROM `allergen` WHERE `allergen`.`id` = idIN$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllFood` ()   SELECT 
	`food`.`id`, 
    `food`.`name` AS "Food Name",
    `food`.`image`, 
    `food`.`description`,
    `food`.`prep_time`,
    `user`.`username`, 
    `food`.`rating`,
	`food`.`instructions`, 
    `difficulty`.`name` AS Difficulty, 
    `meal_type`.`type` AS "Meal Type", 
    `cuisine`.`type` AS Cuisine, 
    `food`.`added_at` 
FROM `food`
LEFT JOIN `user` ON `food`.`user_id` = `user`.`id`
LEFT JOIN `difficulty` ON `food`.`difficulty_id` = `difficulty`.`id`
LEFT JOIN `meal_type` ON `food`.`meal_type_id` = `meal_type`.`id`
LEFT JOIN `cuisine` ON `food`.`cuisine_id` = `cuisine`.`id`
WHERE `food`.`is_deleted` = 0$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllMealType` ()   SELECT * FROM `meal_type`$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllUser` ()   SELECT * FROM `user`$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getCuisine` (IN `idIN` INT(11))   SELECT * FROM `cuisine` WHERE `cuisine`.`id` = idIN$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getDietary` (IN `idIN` INT(11))   SELECT * FROM `dietary` WHERE `dietary`.`id` = idIN$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getDifficulty` (IN `idIN` INT(11))   SELECT * FROM `difficulty` WHERE `difficulty`.`id` = idIN$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getFavouriteByUser` (IN `idIN` INT(11))   SELECT `food`.* FROM `food`
LEFT JOIN `favourite` ON `favourite`.`food_id` = `food`.`id`
WHERE `favourite`.`user_id` = idIN$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getFoodAllergen` (IN `idIN` INT(11))   SELECT * FROM `food_x_allergen` WHERE `food_x_allergen`.`id` = idIN$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getFoodAllergenByFood` (IN `idIN` INT(11))   SELECT * FROM `food_x_allergen` WHERE `food_x_allergen`.`food_id` = idIN$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getFoodByAddedAt` ()   SELECT 
	`food`.`id`, 
    `food`.`name` AS "Food Name",
    `food`.`image`, 
    `food`.`description`,
    `food`.`prep_time`,
    `user`.`username`, 
    `food`.`rating`,
	`food`.`instructions`, 
    `difficulty`.`name` AS Difficulty, 
    `meal_type`.`type` AS "Meal Type", 
    `cuisine`.`type` AS Cuisine, 
    `food`.`added_at` 
FROM `food`
LEFT JOIN `user` ON `food`.`user_id` = `user`.`id`
LEFT JOIN `difficulty` ON `food`.`difficulty_id` = `difficulty`.`id`
LEFT JOIN `meal_type` ON `food`.`meal_type_id` = `meal_type`.`id`
LEFT JOIN `cuisine` ON `food`.`cuisine_id` = `cuisine`.`id`
WHERE `food`.`is_deleted` = 0 ORDER BY `food`.`added_at` DESC$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getFoodByCuisine` (IN `idIN` INT(11))   SELECT 
	`food`.`id`, 
    `food`.`name` AS "Food Name",
    `food`.`image`, 
    `food`.`description`,
    `food`.`prep_time`,
    `user`.`username`, 
    `food`.`rating`,
	`food`.`instructions`, 
    `difficulty`.`name` AS Difficulty, 
    `meal_type`.`type` AS "Meal Type", 
    `cuisine`.`type` AS Cuisine, 
    `food`.`added_at` 
FROM `food`
LEFT JOIN `user` ON `food`.`user_id` = `user`.`id`
LEFT JOIN `difficulty` ON `food`.`difficulty_id` = `difficulty`.`id`
LEFT JOIN `meal_type` ON `food`.`meal_type_id` = `meal_type`.`id`
LEFT JOIN `cuisine` ON `food`.`cuisine_id` = `cuisine`.`id`
WHERE `food`.`cuisine_id` = idIN AND `food`.`is_deleted` = 0$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getFoodByDietary` (IN `idIN` INT(11))   SELECT 
	`food`.`id`, 
    `food`.`name` AS "Food Name",
    `food`.`image`, 
    `food`.`description`,
    `food`.`prep_time`,
    `user`.`username`, 
    `food`.`rating`,
	`food`.`instructions`, 
    `difficulty`.`name` AS Difficulty, 
    `meal_type`.`type` AS "Meal Type", 
    `cuisine`.`type` AS Cuisine, 
    `food`.`added_at` 
FROM `food`
LEFT JOIN `user` ON `food`.`user_id` = `user`.`id`
LEFT JOIN `difficulty` ON `food`.`difficulty_id` = `difficulty`.`id`
LEFT JOIN `meal_type` ON `food`.`meal_type_id` = `meal_type`.`id`
LEFT JOIN `cuisine` ON `food`.`cuisine_id` = `cuisine`.`id`
LEFT JOIN `food_x_dietary` ON `food`.`id` = `food_x_dietary`.`food_id`
WHERE `food_x_dietary`.`dietary_id` = idIN AND `food`.`is_deleted` = 0$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getFoodByDifficulty` (IN `idIN` INT)   SELECT 
	`food`.`id`, 
    `food`.`name` AS "Food Name",
    `food`.`image`, 
    `food`.`description`,
    `food`.`prep_time`,
    `user`.`username`, 
    `food`.`rating`,
	`food`.`instructions`, 
    `difficulty`.`name` AS Difficulty, 
    `meal_type`.`type` AS "Meal Type", 
    `cuisine`.`type` AS Cuisine, 
    `food`.`added_at` 
FROM `food`
LEFT JOIN `user` ON `food`.`user_id` = `user`.`id`
LEFT JOIN `difficulty` ON `food`.`difficulty_id` = `difficulty`.`id`
LEFT JOIN `meal_type` ON `food`.`meal_type_id` = `meal_type`.`id`
LEFT JOIN `cuisine` ON `food`.`cuisine_id` = `cuisine`.`id`
WHERE `food`.`difficulty_id` = idIN AND `food`.`is_deleted` = 0$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getFoodById` (IN `idIN` INT(11))   SELECT 
	`food`.`id`, 
    `food`.`name` AS "Food Name",
    `food`.`image`, 
    `food`.`description`,
    `food`.`prep_time`,
    `user`.`username`, 
    `food`.`rating`,
	`food`.`instructions`, 
    `difficulty`.`name` AS Difficulty, 
    `meal_type`.`type` AS "Meal Type", 
    `cuisine`.`type` AS Cuisine, 
    `food`.`added_at` 
FROM `food`
LEFT JOIN `user` ON `food`.`user_id` = `user`.`id`
LEFT JOIN `difficulty` ON `food`.`difficulty_id` = `difficulty`.`id`
LEFT JOIN `meal_type` ON `food`.`meal_type_id` = `meal_type`.`id`
LEFT JOIN `cuisine` ON `food`.`cuisine_id` = `cuisine`.`id`
WHERE `food`.`id` = idIN AND `food`.`is_deleted` = 0$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getFoodByIngredientId` (IN `idIN` INT(11))   SELECT 
	`food`.`id`, 
    `food`.`name` AS "Food Name",
    `food`.`image`, 
    `food`.`description`,
    `food`.`prep_time`,
    `user`.`username`, 
    `food`.`rating`,
	`food`.`instructions`, 
    `difficulty`.`name` AS Difficulty, 
    `meal_type`.`type` AS "Meal Type", 
    `cuisine`.`type` AS Cuisine, 
    `food`.`added_at` 
FROM `food`
LEFT JOIN `user` ON `food`.`user_id` = `user`.`id`
LEFT JOIN `difficulty` ON `food`.`difficulty_id` = `difficulty`.`id`
LEFT JOIN `meal_type` ON `food`.`meal_type_id` = `meal_type`.`id`
LEFT JOIN `cuisine` ON `food`.`cuisine_id` = `cuisine`.`id`
LEFT JOIN `recipe` ON `food`.`id` = `recipe`.`food_id`
WHERE `recipe`.`ingredient_id` = idIN$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getFoodByIngredients` (IN `ingredientsIN` VARCHAR(255) CHARSET utf8mb4)   BEGIN
    DECLARE ingredient VARCHAR(255);
    DECLARE pos INT DEFAULT 1;
    DECLARE next_pos INT DEFAULT 0;
    DECLARE length INT DEFAULT 0;

    CREATE TEMPORARY TABLE IF NOT EXISTS TempIngredients (ingredient_name VARCHAR(255));

    SET length = CHAR_LENGTH(ingredientsIN);

    WHILE pos <= length DO
        SET next_pos = LOCATE(',', ingredientsIN, pos);

        IF next_pos = 0 THEN
            SET ingredient = TRIM(SUBSTRING(ingredientsIN, pos));
            SET pos = length + 1;
        ELSE
            SET ingredient = TRIM(SUBSTRING(ingredientsIN, pos, next_pos - pos));
            SET pos = next_pos + 1;
        END IF;

        IF ingredient <> '' THEN
            INSERT INTO TempIngredients (ingredient_name) VALUES (ingredient);
        END IF;
    END WHILE;

    SELECT 
    `food`.`id`, 
    `food`.`name` AS "Food Name",
    `food`.`image`, 
    `food`.`description`,
    `food`.`prep_time`,
    `user`.`username`, 
    `food`.`rating`,
    `food`.`instructions`, 
    `difficulty`.`name` AS Difficulty, 
    `meal_type`.`type` AS "Meal Type", 
    `cuisine`.`type` AS Cuisine, 
    `food`.`added_at` 
	FROM `food`
    LEFT JOIN `recipe` ON `food`.`id` = `recipe`.`food_id`
    LEFT JOIN `ingredient` ON `recipe`.`ingredient_id` = `ingredient`.`id`
    LEFT JOIN `user` ON `food`.`user_id` = `user`.`id`
	LEFT JOIN `difficulty` ON `food`.`difficulty_id` = `difficulty`.`id`
	LEFT JOIN `meal_type` ON `food`.`meal_type_id` = `meal_type`.`id`
	LEFT JOIN `cuisine` ON `food`.`cuisine_id` = `cuisine`.`id`
    JOIN TempIngredients ti ON `ingredient`.`name` = ti.ingredient_name
    WHERE `food`.`is_deleted` = 0
    GROUP BY `food`.`id`
    HAVING COUNT(DISTINCT ti.ingredient_name) = (SELECT COUNT(*) FROM TempIngredients);

    DROP TEMPORARY TABLE TempIngredients;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getFoodByMealType` (IN `idIN` INT(11))   SELECT
	`food`.`id`, 
    `food`.`name` AS "Food Name",
    `food`.`image`, 
    `food`.`description`,
    `food`.`prep_time`,
    `user`.`username`, 
    `food`.`rating`,
	`food`.`instructions`, 
    `difficulty`.`name` AS Difficulty, 
    `meal_type`.`type` AS "Meal Type", 
    `cuisine`.`type` AS Cuisine, 
    `food`.`added_at` 
FROM `food`
LEFT JOIN `user` ON `food`.`user_id` = `user`.`id`
LEFT JOIN `difficulty` ON `food`.`difficulty_id` = `difficulty`.`id`
LEFT JOIN `meal_type` ON `food`.`meal_type_id` = `meal_type`.`id`
LEFT JOIN `cuisine` ON `food`.`cuisine_id` = `cuisine`.`id`
WHERE `food`.`meal_type_id` = idIN AND `food`.`is_deleted` = 0$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getFoodByRandom` ()   SELECT 
	`food`.`id`, 
    `food`.`name` AS "Food Name",
    `food`.`image`, 
    `food`.`description`,
    `food`.`prep_time`,
    `user`.`username`, 
    `food`.`rating`,
	`food`.`instructions`, 
    `difficulty`.`name` AS Difficulty, 
    `meal_type`.`type` AS "Meal Type", 
    `cuisine`.`type` AS Cuisine, 
    `food`.`added_at` 
FROM `food`
LEFT JOIN `user` ON `food`.`user_id` = `user`.`id`
LEFT JOIN `difficulty` ON `food`.`difficulty_id` = `difficulty`.`id`
LEFT JOIN `meal_type` ON `food`.`meal_type_id` = `meal_type`.`id`
LEFT JOIN `cuisine` ON `food`.`cuisine_id` = `cuisine`.`id`
WHERE `food`.`is_deleted` = 0 ORDER BY RAND() LIMIT 1$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getFoodByRating` ()   SELECT 
	`food`.`id`, 
    `food`.`name` AS "Food Name",
    `food`.`image`, 
    `food`.`description`,
    `food`.`prep_time`,
    `user`.`username`, 
    `food`.`rating`,
	`food`.`instructions`, 
    `difficulty`.`name` AS Difficulty, 
    `meal_type`.`type` AS "Meal Type", 
    `cuisine`.`type` AS Cuisine, 
    `food`.`added_at` 
FROM `food`
LEFT JOIN `user` ON `food`.`user_id` = `user`.`id`
LEFT JOIN `difficulty` ON `food`.`difficulty_id` = `difficulty`.`id`
LEFT JOIN `meal_type` ON `food`.`meal_type_id` = `meal_type`.`id`
LEFT JOIN `cuisine` ON `food`.`cuisine_id` = `cuisine`.`id`
WHERE `food`.`is_deleted` = 0 ORDER BY `food`.`rating` DESC$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getFoodByUser` (IN `idIN` INT(11))   SELECT 
	`food`.`id`, 
    `food`.`name` AS "Food Name",
    `food`.`image`, 
    `food`.`description`,
    `food`.`prep_time`,
    `user`.`username`, 
    `food`.`rating`,
	`food`.`instructions`, 
    `difficulty`.`name` AS Difficulty, 
    `meal_type`.`type` AS "Meal Type", 
    `cuisine`.`type` AS Cuisine, 
    `food`.`added_at` 
FROM `food`
LEFT JOIN `user` ON `food`.`user_id` = `user`.`id`
LEFT JOIN `difficulty` ON `food`.`difficulty_id` = `difficulty`.`id`
LEFT JOIN `meal_type` ON `food`.`meal_type_id` = `meal_type`.`id`
LEFT JOIN `cuisine` ON `food`.`cuisine_id` = `cuisine`.`id`
WHERE `food`.`user_id` = idIN AND `food`.`is_deleted` = 0$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getFoodDietary` (IN `idIN` INT(11))   SELECT * FROM `food_x_dietary` WHERE `food_x_dietary`.`id` = idIN$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getFoodDietaryByFood` (IN `idIN` INT)   SELECT * FROM `food_x_dietary` WHERE `food_x_dietary`.`food_id` = idIN$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getIngredient` (IN `idIN` INT(11))   SELECT * FROM `ingredient` WHERE `ingredient`.`id` = idIN$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getMealType` (IN `idIN` INT(11))   SELECT * FROM `meal_type` WHERE `meal_type`.`id` = idIN$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getRecipeByFoodId` (IN `idIN` INT(11))   SELECT * FROM `recipe` WHERE `recipe`.`food_id` = idIN$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getRecipeByFoodIdButBetter` (IN `idIN` INT(11))   SELECT `name` FROM `recipe` 
LEFT JOIN `ingredient` ON `recipe`.`ingredient_id` = `ingredient`.`id`
WHERE `recipe`.`food_id` = idIN$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getUserProfileInformation` (IN `idIN` INT(11))   SELECT `user`.`username`, 
		`user`.`image`, 
        `user`.`created_at`,
       (SELECT COUNT(`food`.`id`) FROM `food` WHERE `food`.`user_id` = idIN) AS "Recipes Posted",
       (SELECT COUNT(`favourite`.`id`) FROM `favourite` WHERE `favourite`.`user_id` = idIN) AS "Favourites"
FROM `user`
WHERE `user`.`id` = idIN$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `isUserExists` (IN `emailIN` VARCHAR(255) CHARSET utf8mb4, OUT `resultOUT` BOOLEAN)   set resultOUT = EXISTS(
	SELECT `user`.`id`
    FROM `user`
    WHERE `user`.`email` = emailIN
)$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `login` (IN `emailIN` VARCHAR(255) CHARSET utf8mb4, IN `passwordIN` VARCHAR(255) CHARSET utf8mb4)   SELECT * from `user` where `user`.`email` = emailIN AND `user`.`password` = SHA1(passwordIN)$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `registerAdmin` (IN `usernameIN` VARCHAR(255) CHARSET utf8mb4, IN `imageIN` TEXT, IN `emailIN` VARCHAR(255), IN `passwordIN` VARCHAR(20))   INSERT INTO `user`(
	`user`.`username`,
    `user`.`image`,
    `user`.`email`,
    `user`.`password`,
    `user`.`is_admin`
)VALUES (
    userNameIN,
    imageIN,
    emailIN,
    sha1(passwordIN),
    1
)$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `registerUser` (IN `usernameIN` VARCHAR(255) CHARSET utf8mb4, IN `imageIN` TEXT, IN `emailIN` VARCHAR(255) CHARSET utf8mb4, IN `passwordIN` VARCHAR(20) CHARSET utf8mb4)   INSERT INTO `user`(
	`user`.`username`,
    `user`.`image`,
    `user`.`email`,
    `user`.`password`,
    `user`.`is_admin`
)VALUES (
    usernameIN,
    imageIN,
    emailIN,
    sha1(passwordIN),
    0
)$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `searchByIngredient` (IN `ingredientsIN` TEXT CHARSET utf8mb4)   BEGIN
    -- Temporary table to store input ingredients
    CREATE TEMPORARY TABLE TempIngredients (ingredient_name VARCHAR(255));
    
    -- Splitting the input list into individual ingredients
    SET @sql = CONCAT('INSERT INTO TempIngredients (ingredient_name) ',
                      'SELECT TRIM(value) FROM JSON_TABLE(CONCAT("[\"", REPLACE(ingredientsIN, ",", "\",\""), "\"]"), "$") AS jt(value)');
    PREPARE stmt FROM @sql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
    
    -- Find recipes that contain all the specified ingredients
    SELECT r.recipe_id, r.recipe_name
    FROM recipes r
    JOIN recipe_ingredients ri ON r.recipe_id = ri.recipe_id
    JOIN TempIngredients ti ON ri.ingredient_name = ti.ingredient_name
    GROUP BY r.recipe_id
    HAVING COUNT(DISTINCT ti.ingredient_name) = (SELECT COUNT(*) FROM TempIngredients);
    
    -- Drop the temporary table
    DROP TEMPORARY TABLE TempIngredients;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `updateAllergen` (IN `idIN` INT(11), IN `typeIN` VARCHAR(255) CHARSET utf8mb4, IN `effectIN` VARCHAR(255) CHARSET utf8mb4)   UPDATE `allergen` 
SET `allergen`.`type` = typeIN, 
`allergen`.`effect` = effectIN
WHERE `allergen`.`id` = idIN$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `updateCuisine` (IN `idIN` INT(11), IN `typeIN` VARCHAR(255) CHARSET utf8mb4)   UPDATE `cuisine`
SET `cuisine`.`type` = typeIN
WHERE `cuisine`.`id` = idIN$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `updateDietary` (IN `idIN` INT(11), IN `typeIN` VARCHAR(255) CHARSET utf8mb4)   UPDATE `dietary`
SET `dietary`.`type` = typeIN
WHERE `dietary`.`id` = idIN$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `updateDifficulty` (IN `idIN` INT(11), IN `nameIN` VARCHAR(255) CHARSET utf8mb4, IN `equipmentIN` VARCHAR(255) CHARSET utf8mb4)   UPDATE `difficulty`
SET `difficulty`.`name` = nameIN,
`difficulty`.`equipment` = equipmentIN
WHERE `difficulty`.`id` = idIN$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `updateFavourite` (IN `idIN` INT(11), IN `foodIdIN` INT(11), IN `userIdIN` INT(11))   UPDATE `favourite`
SET `favourite`.`food_id` = foodIdIN,
`favourite`.`user_id` = userIdIN
WHERE `favourite`.`id` = idIN$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `updateFood` (IN `idIN` INT(11), IN `nameIN` VARCHAR(255) CHARSET utf8mb4, IN `imageIN` TEXT, IN `descriptionIN` VARCHAR(255) CHARSET utf8mb4, IN `userIdIN` INT(11), IN `ratingIN` FLOAT, IN `instructionsIN` VARCHAR(255) CHARSET utf8mb4, IN `difficultyIdIN` INT(11), IN `mealTypeIdIN` INT(11), IN `cuisineIdIN` INT(11))   UPDATE `food`
SET `food`.`name` = nameIN,
`food`.`image` = imageIN,
`food`.`description`= descriptionIN,
`food`.`user_id` = userIdIN,
`food`.`rating` = ratingIN,
`food`.`instructions` = instructionsIN,
`food`.`difficulty_id` = difficultyIdIN,
`food`.`meal_type_id` = mealTypeIdIN,
`food`.`cuisine_id` = cuisineIdIN
WHERE `food`.`id` = idIN$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `updateFoodAllergen` (IN `idIN` INT(11), IN `allergenIdIN` INT(11), IN `foodIdIN` INT(11))   UPDATE `food_x_allergen`
SET `food_x_allergen`.`allergen_id` = allergenIdIN,
`food_x_allergen`.`food_id` = foodIdIN
WHERE `food_x_allergen`.`id` = idIN$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `updateFoodDietary` (IN `idIN` INT(11), IN `dietaryIdIN` INT(11), IN `foodIdIN` INT(11))   UPDATE `food_x_dietary`
SET `food_x_dietary`.`dietary_id` = dietaryIdIN,
`food_x_dietary`.`food_id` = foodIdIN
WHERE `food_x_dietary`.`id` = idIN$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `updateFoodImage` (IN `idIN` INT(11), IN `imageIN` TEXT CHARSET utf8mb4)   UPDATE `food`
SET `food`.`image` = imageIN
WHERE `food`.`id` = idIN$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `updateIngredient` (IN `idIN` INT(11), IN `nameIN` VARCHAR(255) CHARSET utf8mb4, IN `proteinIN` FLOAT, IN `carbIN` FLOAT, IN `fatIN` FLOAT, IN `cholesterolIN` FLOAT, IN `fiberIN` FLOAT)   UPDATE `ingredient`
SET `ingredient`.`name` = nameIN,
`ingredient`.`protein` = proteinIN,
`ingredient`.`carb` = carbIN,
`ingredient`.`fat` = fatIN,
`ingredient`.`cholesterol` = cholesterolIN,
`ingredient`.`fiber`= fiberIN
WHERE `ingredient`.`id` = idIN$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `updateMealType` (IN `idIN` INT(11), IN `typeIN` VARCHAR(255) CHARSET utf8mb4)   UPDATE `meal_type`
SET `meal_type`.`type` = typeIN
WHERE `meal_type`.`id` = idIN$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `updateRecipe` (IN `idIN` INT(11), IN `foodIdIN` INT(11), IN `ingredientIdIN` INT(11), IN `amountIN` INT(11), IN `measurementIN` VARCHAR(255) CHARSET utf8mb4)   UPDATE `recipe`
SET `recipe`.`food_id` = foodIdIN,
`recipe`.`ingredient_id` = ingredientIdIN,
`recipe`.`amount` = amountIN,
`recipe`.`measurement` = measurementIN
WHERE `recipe`.`id` = idIN$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `updateUser` (IN `idIN` INT(11), IN `usernameIN` VARCHAR(255) CHARSET utf8mb4, IN `imageIN` TEXT CHARSET utf8mb4, IN `emailIN` VARCHAR(255) CHARSET utf8mb4, IN `passwordIN` VARCHAR(255) CHARSET utf8mb4, IN `isAdminIN` TINYINT(1))   UPDATE `user`
SET `user`.`username` = usernameIN,
`user`.`image` = imageIN,
`user`.`email`= emailIN,
`user`.`password` = SHA1(passwordIN),
`user`.`is_admin` = isAdminIN
WHERE `user`.`id` = idIN$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `updateUserImage` (IN `idIN` INT(11), IN `imageIN` TEXT CHARSET utf8mb4)   UPDATE `user`
SET `user`.`image` = imageIN
WHERE `user`.`id` = idIN$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `allergen`
--

CREATE TABLE IF NOT EXISTS `allergen` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(255) DEFAULT NULL,
  `effect` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `allergen`
--

INSERT INTO `allergen` (`id`, `type`, `effect`) VALUES
(1, 'Peanuts', 'Anaphylaxis and swelling, sometimes vomiting.'),
(2, 'Wheat', 'Eczema, Hives, asthma, hay fever, oral allergy syndrome, angioedema, abdominal cramps, Celiac disease, diarrhea, anemia, nausea, vomiting.'),
(3, 'Milk', 'Skin rash, hives, vomiting, diarrhea, constipation, stomach pain, flatulence, colitis, nasal congestion, dermatitis, blisters, migraine, anaphylaxis '),
(4, 'Balsam of Peru', 'Redness, swelling, itching, inflammation and soreness of the mouth or tongue, inflammation, rash, or painful erosion of the lips,hand eczema, conjunctivitis, and blisters. '),
(5, 'Buckwheat', 'Asthma, rhinitis, pruritus, gastrointestinal disturbances, urticaria, angioedema, shock, anaphylaxis.'),
(6, 'Celery', 'Abdominal pain, nausea, vomiting, oral allergy syndrome, neck or facial swelling, severe asthma symptoms, exercise induced anaphylaxis, potentially fatal anaphylactic shocks.'),
(7, 'Egg', 'Anaphylaxis, swelling, sometimes flatulence and vomiting.'),
(8, 'Fish', 'Respiratory reactions, Anaphylaxis, oral allergy syndrome, sometimes vomiting.'),
(9, 'Fruit', 'Mild itching, rash, generalized urticaria, oral allergy syndrome, abdominal pain, vomiting, anaphylaxis.'),
(10, 'Garlic', 'Dermatitis, rhinitis, asthma, urticaria, asymmetrical pattern of fissure, thickening/shedding of the outer skin layers, rarely anaphylaxis.'),
(11, 'Oats', 'Dermatitis, respiratory problems, anaphylaxis.'),
(12, 'Maize', 'Hives, pallor, confusion, dizziness, stomach pain, swelling, vomiting, indigestion, diarrhea, cough, tightness in throat, wheezing, shortness of breath, anaphylaxis.'),
(13, 'Mustard', 'Eczema, Rash, Hives, Facial swelling, Other skin reactions, Oral allergy syndrome, Conjunctivitis, Wheezing, Abdominal pain, Diarrhea, Nausea, Vomiting, Acid Reflux, Dizziness, Asthma, Chest pain, Respiratory problems, Anaphylaxis.'),
(14, 'Poultry Meat', 'Hives, swelling of, or under the dermis, nausea, vomiting, diarrhea, severe oral allergy syndrome, shortness of breath, rarely anaphylactic shock.'),
(15, 'Red Meat', 'Hives, swelling, stomach pain, nausea, vomiting, dizziness, fainting, shortness of breath, anaphylaxis.'),
(16, 'Rice', 'Sneezing, runny nose, itching, stomachache, eczema. '),
(17, 'Sesame', 'Possible respiratory, skin, and gastrointestinal reactions which can trigger serious systemic anaphylactic responses.'),
(18, 'Shellfish', 'Respiratory symptoms, Anaphylaxis, oral allergy syndrome, gastrointestinal symptoms, rhinitis, conjunctivitis.'),
(19, 'Soy', 'Anaphylaxis, asthma exacerbation, rhinitis, allergic conjunctivitis, hives, atopic dermatitis, swelling of, or under the dermis, diarrhea, nausea, vomiting.'),
(20, 'Sulfites', 'Hives, rash, redness of skin, headache (particular frontal), burning behind eyes, asthma-like breathing difficulties, anaphylaxis.'),
(21, 'Tartrazine', 'Skin irritation, hives, rash.'),
(22, 'Tree nut', 'Anaphylaxis, swelling, rash, hives, sometimes vomiting.'),
(24, 'Mushroom', 'Rash, itches, hives, sneezing, dizziness, anaphylaxis.'),
(25, 'Pepper', 'Swelling, itching, hives, anaphylaxis. ');

-- --------------------------------------------------------

--
-- Table structure for table `cuisine`
--

CREATE TABLE IF NOT EXISTS `cuisine` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(255) DEFAULT NULL COMMENT 'Italian, Mexican, etc',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `cuisine`
--

INSERT INTO `cuisine` (`id`, `type`) VALUES
(1, 'Italian'),
(2, 'Mexican'),
(3, 'Indian'),
(4, 'Chinese'),
(5, 'French'),
(6, 'Hungarian'),
(7, 'Lithuanian'),
(8, 'Slovakian'),
(9, 'American');

-- --------------------------------------------------------

--
-- Table structure for table `dietary`
--

CREATE TABLE IF NOT EXISTS `dietary` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(255) DEFAULT NULL COMMENT 'Vegan, Vegetarian, Gluten-Free, etc',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `dietary`
--

INSERT INTO `dietary` (`id`, `type`) VALUES
(1, 'Vegan'),
(2, 'Vegetarian'),
(3, 'Gluten-Free'),
(4, 'Keto'),
(5, 'Paleo'),
(6, 'Carnivore');

-- --------------------------------------------------------

--
-- Table structure for table `difficulty`
--

CREATE TABLE IF NOT EXISTS `difficulty` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `equipment` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `difficulty`
--

INSERT INTO `difficulty` (`id`, `name`, `equipment`) VALUES
(1, 'Easy', 'Basic equipment'),
(2, 'Medium', 'Intermediate equipment'),
(3, 'Hard', 'Advanced equipment');

-- --------------------------------------------------------

--
-- Table structure for table `favourite`
--

CREATE TABLE IF NOT EXISTS `favourite` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `food_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `favourite`
--

INSERT INTO `favourite` (`id`, `user_id`, `food_id`) VALUES
(1, 2, 3),
(2, 3, 10),
(3, 4, 1),
(4, 4, 6),
(5, 4, 7),
(6, 1, 4),
(7, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `food`
--

CREATE TABLE IF NOT EXISTS `food` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `image` text DEFAULT NULL,
  `description` text DEFAULT NULL,
  `prep_time` varchar(255) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `rating` int(11) NOT NULL DEFAULT 0,
  `instructions` text DEFAULT NULL,
  `difficulty_id` int(11) DEFAULT NULL,
  `meal_type_id` int(11) DEFAULT NULL,
  `cuisine_id` int(11) DEFAULT NULL,
  `added_at` timestamp NULL DEFAULT current_timestamp(),
  `is_deleted` tinyint(1) DEFAULT 0,
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `food`
--

INSERT INTO `food` (`id`, `name`, `image`, `description`, `prep_time`, `user_id`, `rating`, `instructions`, `difficulty_id`, `meal_type_id`, `cuisine_id`, `added_at`, `is_deleted`, `deleted_at`) VALUES
(1, 'Spaghetti Bolognese', 'https://img.taste.com.au/5qlr1PkR/taste/2016/11/spaghetti-bolognese-106560-1.jpeg', 'A classic Italian pasta dish with a rich meat sauce.', '60', 3, 2, 'Bring a large pot of salted water to a boil. §Add the spaghetti and cook according to the package instructions (usually 8-10 minutes), until al dente.  §Drain the pasta and set it aside. §Heat 2 tablespoons of olive oil in a large pan over medium heat. §Add 1 finely chopped onion and 2 minced garlic cloves, and sauté until the onion becomes soft and translucent. §Add 500g of ground beef to the pan and cook until browned, breaking the meat up with a wooden spoon as it cooks. §Stir in 1 grated carrot and 1 chopped celery stalk, and cook for 5 minutes until the vegetables soften. §Add 400g of canned crushed tomatoes, 1 tablespoon of tomato paste, 1 teaspoon of sugar, and a splash of red wine (optional) to the pan. §Season with salt, pepper, 1 teaspoon of dried oregano, and 1 teaspoon of dried basil. §Stir everything together and bring the sauce to a simmer. §Lower the heat and let the sauce simmer gently for 20-30 minutes, stirring occasionally. §Taste and adjust the seasoning as needed. Stir in 2 tablespoons of cream or milk for a richer sauce (optional). §Serve the Bolognese sauce over the cooked spaghetti. §Top with freshly grated Parmesan cheese and fresh basil, if desired.', 2, 3, 1, '2023-09-15 12:00:00', 0, NULL),
(2, 'Birria Tacos', 'https://www.isabeleats.com/wp-content/uploads/2024/04/birria-tacos-recipe-5.jpg', 'Birria is a mainstay of Mexican cuisine, a stew that originated in the state of Jalisco traditionally made from goat, beef, or lamb.', '45', 3, 0, 'Sprinkle the chuck roast and short ribs all over with 2 tablespoons salt. Combine roast, ribs, and adobo in a large nonreactive bowl and toss to mix. §Cover and chill for at least 4 hours or up to 24 hours. §Preheat oven to 149°C. §Transfer adobo mixture to a large (9 1/2-quart) Dutch oven and add 8 cups of water. §Bring to a simmer, uncovered, over medium heat, stirring occasionally. §Cover with lid and place in preheated oven. §Bake until meat is fork-tender for about 4 hours. §Remove chuck roast and short ribs from braising broth and transfer to a large bowl, cover with aluminum foil to keep warm. §Return broth in Dutch oven to heat over medium and cook, uncovered, skimming off fat as needed, until reduced to about 8 cups, 15 to 20 minutes. §Season broth with salt to taste. §Shred meat, discard bones and toss with 1 1/2 cups of the broth. §Stir together onion, cilantro, and remaining 1/4 teaspoon salt in a small bowl, set aside. §Heat a large nonstick electric griddle to 204°C or a large (12-inch) cast-iron skillet over medium-high. Using a paper towel dipped in canola oil, lightly grease griddle. If using fresh tortillas, stack two tortillas, and use tongs to dip them together into adobo broth. (If using packaged tortillas, dip one tortilla per taco.) §Place stacked tortillas on griddle, top with 1/4 cup meat. Repeat with as many tortilla stacks as will comfortably fit on griddle. Cook until bottom tortilla is lightly browned and crispy for 1 to 2 minutes. §Fold tacos in half, gently pressing with a spatula. Transfer to a serving plate. Repeat process with oil, adobo broth, remaining tortillas, and remaining meat. §Serve tacos hot with onion-cilantro mixture, lime wedges, and remaining adobo broth for dipping or sipping.', 1, 2, 2, '2023-09-16 10:30:00', 1, '2025-02-17 09:28:37'),
(3, 'Highwayman Dumpling', 'https://c8.alamy.com/comp/HPF4PR/georgian-dumplings-khinkali-with-meat-tomato-spicy-sauce-satsebeli-HPF4PR.jpg', 'These dumplings are usually served with spätzle or crest pasta.', '80', 4, 0, 'The chicken breast is ground and seasoned, then eggs are added, and small, walnut-sized meatballs are formed. §The onion is finely chopped and sautéed in oil until translucent.  §Then, the chopped mushrooms are added and seasoned. Once the mixture comes to a boil, the prepared meatballs are placed into the pan and simmered on low heat for 20–25 minutes. §Any evaporated liquid is replaced with a little water to ensure a rich sauce. §It is served with dumplings or rooster comb-shaped pasta. §For those who prefer, sour cream can also be added on top.', 2, 2, 6, '2024-10-03 11:34:33', 0, NULL),
(4, 'Savoy cabbage stew', 'https://images.immediate.co.uk/production/volatile/sites/30/2020/08/recipe-image-legacy-id-7952_10-69a55af.jpg', 'Bits of crinkly Savoy cabbage and potato chunks crowd this creamy and mild vegetable stew flavored with caraway seeds. Using tender new potatos and a few tablespoons of sour cream to finish turn this everyday dish into a luscious treat.', '60', 4, 1, 'Cut the cabbage into small pieces, then scald with hot water. §Boil the cabbage pieces with salt, black pepper and onion. §Cut the garlic into small pieces along with the caraway then add it to the cabbage. §Once the cabbage cooks half-way through add the diced potato and nutmeg. §When the cabbage has fully boiled, make a roux with some ground chili peppers, flour and oil then mix with the cabbage while still boiling. §The recipe can be eaten with fried eggs, beef stew or in itself. ', 1, 2, 6, '2024-09-20 11:54:05', 0, NULL),
(5, 'Cabbage hajtóka', 'https://i.pinimg.com/736x/c3/ef/43/c3ef4316b95341c152cc557da0c57564.jpg', 'A cabbage filled pastry.', '80', 4, 0, 'It is made from leavened dough. §The risen dough is rolled out flat with a rolling pin. §Freshly grated cabbage is sautéed in lard until it turns half-brown. §It is seasoned with salt and pepper. §The cabbage is then sprinkled over the rolled-out dough. §The dough is rolled up and baked in a tray until it turns red.', 1, 4, 6, '2024-10-03 10:52:39', 0, NULL),
(6, 'Potato Pancakes', 'https://images.ctfassets.net/j9gt1m2cyvgh/79oXPEv7zHjCMBVRyFbjtj/6b0ffbf6ce152fc31c01549ba5a20300/492777782_GTY_RF_PotatoPancakes-1.jpg', 'These special pancakes are made from grated potato and flour, fried in lard or oil', '30', 4, 1, 'We grate the raw potato and season it with salt and pepper to taste, then mix in the crushed garlic and enough flour to form a batter with the consistency of dumpling dough. §In a frying pan, we heat a thin layer of oil—about a finger’s width. §We spoon in one tablespoon of the mixture and press it out to about a finger’s thickness. §We fry it until both sides turn golden brown, adding more oil occasionally. §It is delicious on its own and inexpensive, but you can also spread it with sour cream or sprinkle it with grated cheese.', 1, 2, 9, '2024-10-03 11:09:03', 0, NULL),
(7, 'Garlic Soup', 'https://www.foodandwine.com/thmb/8bW6GuSOVuRP-XtcKZr3k4QyN-o=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/Garlic-Soup-FT-RECIPE1023-db4ccdae3c4d43f1ad7fd0c287293f81.jpg', 'The hungarian rendition of the classic garlic soup.', '45', 4, 1, 'Cut one potato into sticks and place them in approximately 2 liters of water. §Salt the water, pour in 1 liter of tomato juice, and add celery greens. §Bring the mixture to a boil. §Meanwhile, grate one medium carrot and 3–4 garlic cloves. §Fry them in lard until browned. §Once browned, add 1 teaspoon of crushed paprika to the fried mixture and incorporate it into the soup. §Simmer for about 30 minutes until the potato is cooked. §Add grated egg pasta or vermicelli (grated egg pasta is recommended for extra flavor). Optionally, allow each serving to be seasoned with cold tomato juice to taste.', 2, 2, 6, '2024-10-03 11:17:46', 0, NULL),
(8, 'Grandma\'s grilled fish', 'https://www.masalakorb.com/wp-content/uploads/2016/08/Grilled-Fish-Indian-Recipe-V5.jpg', 'Grandma\'s beloved grilled fish. ', '20', 4, 0, 'Thoroughly clean the carp and season with salt. Set aside briefly. §In a bowl, mix the flour and paprika to create a uniform coating. §Toss the salted fish in this mixture until evenly covered. §Heat a generous layer of oil or lard in a deep frying pan until it is very hot. §Using a suitable tool (such as a slotted spoon or spatula), carefully lower the coated fish into the oil. §Fry until the fish turn golden and crispy on all sides, adding more oil if necessary. §Serve the crispy fried carp with a side of boiled potatoes or rice. §For an alternative, grill the fish instead of frying for a lighter version. §Some prefer the grilled style for its distinct flavor.\r\n', 1, 2, 6, '2024-10-03 11:41:36', 0, NULL),
(9, 'Key Lime Pie', 'https://www.southernliving.com/thmb/D_qY7JcMIHihUf6GDOFf1lQpua0=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/Southern-Living_Key_Lime_Pie_013-7739d9ed7e5e41f18b57f299e8a7953c.jpg', 'This is an American desert made from limes. Key lime pie dates back to the late 1800s in the Florida Keys. Modern refrigeration wasn’t available at the time, so fresh milk wasn’t a common commodity. Instead, canned milk was widely used.', '60', 3, 1, 'Heat the oven to 160C fan 140C gas §Whizz 300g oatmeal biscuits to crumbs in a food processor (or put in a strong plastic bag and bash with a rolling pin). §Mix with 110g melted butter and press into the base and up the sides of a 22cm loose-based tart tin. Bake in the oven for 10 minutes. Remove and cool. §Put 3 medium egg yolks in a large bowl and whisk for a minute with electric beaters. §Add a can of condensed milk and whisk for 3 minutes, then add the finely grated zest and juice of 4 limes and whisk again for 3 minutes. §Pour the filling into the cooled base then put back in the oven for 15 minutes. Cool then chill for at least 6 hours or overnight if you like. When you are ready to serve, carefully remove the pie from the tin and put on a serving plate. §To decorate, softly whip together 300ml double cream and 1 tbsp icing sugar. §Dollop or pipe the cream onto the top of the pie and finish with extra lime zest.', 2, 5, 9, '2024-10-22 10:43:43', 0, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `food_x_allergen`
--

CREATE TABLE IF NOT EXISTS `food_x_allergen` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `food_id` int(11) NOT NULL,
  `allergen_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `food_x_dietary`
--

CREATE TABLE IF NOT EXISTS `food_x_dietary` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dietary_id` int(11) DEFAULT NULL,
  `food_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `ingredient`
--

CREATE TABLE IF NOT EXISTS `ingredient` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `protein` float DEFAULT NULL,
  `carb` float DEFAULT NULL,
  `fat` float DEFAULT NULL,
  `cholesterol` float DEFAULT NULL,
  `fiber` float DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `ingredient`
--

INSERT INTO `ingredient` (`id`, `name`, `protein`, `carb`, `fat`, `cholesterol`, `fiber`) VALUES
(1, 'Flour', 10, 76, 1, 0, 2.7),
(2, 'Egg', 6, 0.3, 4.2, 163.7, 0),
(3, 'Milk', 3.4, 5, 1, 5, 0),
(4, 'Cabbage', 12, 53, 0.9, 0, 23),
(5, 'Garlic', 0.19, 1, 0.02, 0, 0.06),
(6, 'Red Onion', 1.2, 10, 0, 0, 1.9),
(7, 'Potato', 3, 26, 0, 0, 2),
(8, 'Caraway', 19.77, 49.9, 14.59, 0, 38),
(9, 'Salt', 0, 0, 0, 0, 0),
(10, 'Black Pepper', 10.95, 64.81, 3.26, 0, 26.5),
(11, 'Nutmeg', 6, 49, 36, 0, 20.8),
(12, 'Chili Pepper', 1.9, 9, 0.4, 0, 1.5),
(13, 'Cooking Oil', 0, 0, 100, 0, 0),
(14, 'Yeast', 8, 18, 1.9, 0, 8),
(15, 'Lard', 0, 0, 100, 95, 0),
(16, 'Sugar', 0, 100, 0, 0, 0),
(17, 'Sour cream', 2.1, 2.9, 20, 52, 0),
(18, 'Tomato Juice', 0.8, 4.2, 0.1, 0, 0.4),
(19, 'Celery', 0.8, 3.6, 0.2, 0, 1.9),
(20, 'Carrot', 0.7, 7.6, 0.2, 0, 2.8),
(21, 'Vermicelli', 0.1, 82, 0.1, 0, 3.9),
(22, 'Haricot Beans', 6, 13, 0.7, 0, 11),
(23, 'Smoked Loin', 23, 0, 19, 86, 0),
(24, 'Matchstick Pasta', 5, 25, 1.1, 33, 0),
(25, 'White Onion', 1.4, 10, 0.2, 0, 1.4),
(26, 'Ground Nutmeg', 6, 49, 36, 0, 20.8),
(27, 'Vinegar', 0, 0, 0, 0, 0),
(28, 'Dried Bread Roll', 11, 52, 6, 4, 2),
(29, 'Parsley', 3, 6, 0.8, 0, 3.3),
(30, 'Chicken Breast', 31, 0, 3.6, 85, 0),
(31, 'Mushroom', 3.1, 3.3, 0.3, 0, 1),
(32, 'Pepper', 1, 5.4, 0.2, 0, 0.9),
(33, 'Water', 0, 0, 0, 0, 0),
(34, 'Pork Ribs', 23, 0, 28, 84, 0),
(35, 'Parsnip', 0.9, 10, 0.2, 0, 2.8),
(36, 'Cascabel Chili', 12.9, 63.6, 6.4, 0, 0.9),
(37, 'Ground Paprika', 14, 54, 13, 0, 35),
(38, 'Dried Pepper', 1.9, 9, 0.4, 0, 1.5),
(39, 'Common Carp', 17.8, 0, 5.6, 66, 0);

-- --------------------------------------------------------

--
-- Table structure for table `meal_type`
--

CREATE TABLE IF NOT EXISTS `meal_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(255) DEFAULT NULL COMMENT 'Breakfast, Lunch, Dinner, Snack, Dessert',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `meal_type`
--

INSERT INTO `meal_type` (`id`, `type`) VALUES
(1, 'Breakfast'),
(2, 'Lunch'),
(3, 'Dinner'),
(4, 'Snack'),
(5, 'Dessert');

-- --------------------------------------------------------

--
-- Table structure for table `recipe`
--

CREATE TABLE IF NOT EXISTS `recipe` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `food_id` int(11) DEFAULT NULL,
  `ingredient_id` int(11) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  `measurement` varchar(255) DEFAULT NULL COMMENT 'gram, db, etc',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `image` text NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `is_admin` tinyint(1) DEFAULT 0,
  `created_at` timestamp NULL DEFAULT current_timestamp(),
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0,
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `username`, `image`, `email`, `password`, `is_admin`, `created_at`, `is_deleted`, `deleted_at`) VALUES
(1, 'John Doe', 'john.png', 'john@citromail.com', 'cbfdac6008f9cab4083784cbd1874f76618d2a97', 0, '2023-09-10 10:00:00', 1, '2025-02-17 07:49:54'),
(2, 'Jane Smith', 'Jane.png', 'jane@hotmail.com', '79ef18bb9e529a91d8941c52b9d47aa71ab1443c', 0, '2023-09-12 06:30:00', 1, '2025-02-17 09:09:56'),
(3, 'Balogh Gergely Daniel', 'gergo.png', 'balogh.gergely.daniel@simonyiszki.org', 'd98f69fa1f7cc99d196cbd9d2d47820c5cd317f2', 1, '2024-09-17 10:28:37', 0, NULL),
(4, 'Kasza David', 'billy.png', 'kasza.david@simonyiszki.org', '7110eda4d09e062aa5e4a390b0a572ac0d2c0220', 1, '2024-09-20 11:29:23', 0, NULL),
(5, 'Ron', 'Ron.png', 'RonWeasel@gmail.com', 'a1c6b74e19c25d2764d20ba7243177c605d5d2a8', 0, '2025-01-03 12:14:41', 0, NULL),
(6, 'Bob', 'Bob.png', 'BreadBob@citromail.com', '9cec8cf728a07d718434d7e87bb43f8643f95494', 1, '2025-01-09 14:17:52', 0, NULL),
(7, 'bobby', 'boby.png', 'boly.gobl@mole.ruf', '7e6f54ed1399fd40d227375c4dcefa01e15b103d', 0, '2025-02-17 08:13:21', 0, NULL);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

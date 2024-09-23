-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 13, 2024 at 02:41 PM
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

-- --------------------------------------------------------

--
-- Table structure for table `allergen`
--

CREATE TABLE `allergen` (
  `id` int(11) NOT NULL,
  `type` varchar(255) DEFAULT NULL,
  `effect` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `allergen`
--

INSERT INTO `allergen` (`id`, `type`, `effect`) VALUES
(1, 'Peanuts', 'Anaphylaxis'),
(2, 'Gluten', 'Celiac disease'),
(3, 'Dairy', 'Lactose intolerance');

-- --------------------------------------------------------

--
-- Table structure for table `allergen_x_food`
--

CREATE TABLE `allergen_x_food` (
  `id` int(11) NOT NULL,
  `allergen_id` int(11) DEFAULT NULL,
  `food_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `allergen_x_food`
--

INSERT INTO `allergen_x_food` (`id`, `allergen_id`, `food_id`) VALUES
(1, 2, 1),
(2, 3, 3);

-- --------------------------------------------------------

--
-- Table structure for table `cuisine`
--

CREATE TABLE `cuisine` (
  `id` int(11) NOT NULL,
  `type` varchar(255) DEFAULT NULL COMMENT 'Italian, Mexican, etc'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `cuisine`
--

INSERT INTO `cuisine` (`id`, `type`) VALUES
(1, 'Italian'),
(2, 'Mexican'),
(3, 'Indian'),
(4, 'Chinese'),
(5, 'French');

-- --------------------------------------------------------

--
-- Table structure for table `dietary`
--

CREATE TABLE `dietary` (
  `id` int(11) NOT NULL,
  `type` varchar(255) DEFAULT NULL COMMENT 'Vegan, Vegetarian, Gluten-Free, etc'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `dietary`
--

INSERT INTO `dietary` (`id`, `type`) VALUES
(1, 'Vegan'),
(2, 'Vegetarian'),
(3, 'Gluten-Free'),
(4, 'Keto'),
(5, 'Paleo');

-- --------------------------------------------------------

--
-- Table structure for table `difficulty`
--

CREATE TABLE `difficulty` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `equipment` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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

CREATE TABLE `favourite` (
  `id` int(11) NOT NULL,
  `food_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `favourite`
--

INSERT INTO `favourite` (`id`, `food_id`, `user_id`) VALUES
(1, 1, 1),
(2, 3, 2);

-- --------------------------------------------------------

--
-- Table structure for table `food`
--

CREATE TABLE `food` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `image` text DEFAULT NULL,
  `description` blob DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `rating` float DEFAULT NULL,
  `instructions` text DEFAULT NULL,
  `difficulty_id` int(11) DEFAULT NULL,
  `meal_type_id` int(11) DEFAULT NULL,
  `cuisine_id` int(11) DEFAULT NULL,
  `added_at` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `food`
--

INSERT INTO `food` (`id`, `name`, `image`, `description`, `user_id`, `rating`, `instructions`, `difficulty_id`, `meal_type_id`, `cuisine_id`, `added_at`, `is_deleted`, `deleted_at`) VALUES
(1, 'Spaghetti Bolognese', 'spaghetti.png', 0x4120636c6173736963204974616c69616e2070617374612064697368207769746820612072696368206d6561742073617563652e, 1, 4.5, '1. Boil the Spaghetti: Bring a large pot of salted water to a boil. Add the spaghetti and cook according to the package instructions (usually 8-10 minutes), until al dente. Drain the pasta and set it aside. \n\n2. Prepare the Meat Sauce: Heat 2 tablespoons of olive oil in a large pan over medium heat. Add 1 finely chopped onion and 2 minced garlic cloves, and sauté until the onion becomes soft and translucent. Add 500g of ground beef to the pan and cook until browned, breaking the meat up with a wooden spoon as it cooks. \n\n3. Add the Vegetables: Stir in 1 grated carrot and 1 chopped celery stalk, and cook for 5 minutes until the vegetables soften. \n\n4. Cook the Sauce: Add 400g of canned crushed tomatoes, 1 tablespoon of tomato paste, 1 teaspoon of sugar, and a splash of red wine (optional) to the pan. Season with salt, pepper, 1 teaspoon of dried oregano, and 1 teaspoon of dried basil. Stir everything together and bring the sauce to a simmer. Lower the heat and let the sauce simmer gently for 20-30 minutes, stirring occasionally. \n\n5. Final Touches: Taste and adjust the seasoning as needed. Stir in 2 tablespoons of cream or milk for a richer sauce (optional). Serve the Bolognese sauce over the cooked spaghetti. Top with freshly grated Parmesan cheese and fresh basil, if desired.', 2, 3, 1, '2023-09-15 14:00:00', 0, NULL),
(2, 'Tacos', 'tacos.png', 0x4d65786963616e20746f7274696c6c61732066696c6c6564207769746820737069636564206d6561742c20766567657461626c65732c20616e64207361756365732e, 2, 4, '1. Prepare the Meat Filling: Heat 2 tablespoons of vegetable oil in a skillet over medium heat. Add 500g of ground beef and cook until browned, breaking it up with a spatula. Drain excess fat if necessary. \n\n2. Season the Meat: Stir in 1 packet of taco seasoning (or your own mix of chili powder, cumin, paprika, garlic powder, and other spices). Add 1/4 cup of water to the pan, stir, and simmer until the sauce thickens (about 5 minutes). \n\n3. Warm the Tortillas: Warm soft corn or flour tortillas in a dry skillet over medium heat for about 1 minute on each side or wrap them in foil and warm in the oven at 180°C (350°F) for 5-10 minutes. \n\n4. Prepare the Toppings: Chop fresh vegetables like lettuce, tomatoes, onions, and cilantro. Grate some cheddar cheese and prepare additional toppings such as sour cream, salsa, or guacamole. \n\n5. Assemble the Tacos: Spoon the seasoned meat into a warm tortilla. Add toppings such as lettuce, diced tomatoes, cheese, sour cream, salsa, and guacamole. Fold the tortilla and serve.', 1, 2, 2, '2023-09-16 12:30:00', 0, NULL),
(3, 'Pancakes', 'pancakes.png', 0x466c756666792070616e63616b657320736572766564207769746820737972757020616e64206275747465722e, 1, 4.8, '1. Prepare the Batter: In a large mixing bowl, whisk together 1 ½ cups of flour, 1 tablespoon of sugar, 1 teaspoon of baking powder, and a pinch of salt. In another bowl, beat 1 egg and add 1 cup of milk and 2 tablespoons of melted butter. Pour the wet ingredients into the dry ingredients and gently whisk until combined. Don\'t overmix. \n\n2. Heat the Griddle: Heat a non-stick skillet or griddle over medium heat and lightly grease with butter or oil. \n\n3. Cook the Pancakes: Pour about 1/4 cup of batter onto the griddle for each pancake. Cook until bubbles form on the surface and the edges look set, about 2-3 minutes. Flip the pancake and cook for another 1-2 minutes. \n\n4. Serve: Serve the pancakes warm with butter, syrup, and toppings like fresh fruit or whipped cream.', 1, 1, 5, '2023-09-17 09:00:00', 0, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `food_x_dietary`
--

CREATE TABLE `food_x_dietary` (
  `id` int(11) NOT NULL,
  `dietary_id` int(11) DEFAULT NULL,
  `food_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `food_x_dietary`
--

INSERT INTO `food_x_dietary` (`id`, `dietary_id`, `food_id`) VALUES
(1, 3, 1),
(2, 1, 3);

-- --------------------------------------------------------

--
-- Table structure for table `ingredient`
--

CREATE TABLE `ingredient` (
  `id` int(11) NOT NULL,
  `neve` varchar(255) DEFAULT NULL,
  `protein` varchar(255) DEFAULT NULL,
  `carb` varchar(255) DEFAULT NULL,
  `fat` varchar(255) DEFAULT NULL,
  `cholesterol` varchar(255) DEFAULT NULL,
  `fiber` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `ingredient`
--

INSERT INTO `ingredient` (`id`, `neve`, `protein`, `carb`, `fat`, `cholesterol`, `fiber`) VALUES
(1, 'Flour', '3g', '76g', '1g', '0mg', '2g'),
(2, 'Egg', '6g', '1g', '5g', '186mg', '0g'),
(3, 'Milk', '8g', '12g', '5g', '24mg', '0g');

-- --------------------------------------------------------

--
-- Table structure for table `meal_type`
--

CREATE TABLE `meal_type` (
  `id` int(11) NOT NULL,
  `type` varchar(255) DEFAULT NULL COMMENT 'Breakfast, Lunch, Dinner, Snack, Dessert'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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

CREATE TABLE `recipe` (
  `id` int(11) NOT NULL,
  `food_id` int(11) DEFAULT NULL,
  `ingredient_id` int(11) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  `measurement` varchar(255) DEFAULT NULL COMMENT 'gram, db, etc'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `image` text DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `username`, `image`, `email`, `password`, `created_at`, `is_deleted`, `deleted_at`) VALUES
(1, 'john_doe', 'image1.png', 'john@example.com', 'password123', '2023-09-10 12:00:00', 0, NULL),
(2, 'jane_smith', 'image2.png', 'jane@example.com', 'securepass', '2023-09-12 08:30:00', 0, NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `allergen`
--
ALTER TABLE `allergen`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `allergen_x_food`
--
ALTER TABLE `allergen_x_food`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `cuisine`
--
ALTER TABLE `cuisine`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `dietary`
--
ALTER TABLE `dietary`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `difficulty`
--
ALTER TABLE `difficulty`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `favourite`
--
ALTER TABLE `favourite`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `food`
--
ALTER TABLE `food`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `food_x_dietary`
--
ALTER TABLE `food_x_dietary`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `ingredient`
--
ALTER TABLE `ingredient`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `meal_type`
--
ALTER TABLE `meal_type`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `recipe`
--
ALTER TABLE `recipe`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

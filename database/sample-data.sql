-- Extra sample data for the Food Delivery App
-- Run once, after schema.sql, as the project user. Running it twice adds duplicates.
SET DEFINE OFF

-- ===== 8 new restaurants =====
INSERT INTO restaurants (name, address, cuisine) VALUES ('Bengal Kitchen', 'Strand Road, Chandannagar', 'Bengali');
INSERT INTO restaurants (name, address, cuisine) VALUES ('Dragon Wok', 'GT Road, Chinsurah', 'Chinese');
INSERT INTO restaurants (name, address, cuisine) VALUES ('Taco Corner', 'Park Street, Kolkata', 'Mexican');
INSERT INTO restaurants (name, address, cuisine) VALUES ('Burger Barn', 'Station Road, Serampore', 'Fast Food');
INSERT INTO restaurants (name, address, cuisine) VALUES ('Biryani House', 'Esplanade, Kolkata', 'Biryani');
INSERT INTO restaurants (name, address, cuisine) VALUES ('Sweet Tooth', 'College Street, Kolkata', 'Desserts');
INSERT INTO restaurants (name, address, cuisine) VALUES ('Tandoori Nights', 'Barrackpore Trunk Road, Barrackpore', 'North Indian');
INSERT INTO restaurants (name, address, cuisine) VALUES ('Green Bowl', 'Salt Lake, Kolkata', 'Healthy');

-- ===== Bengal Kitchen =====
INSERT INTO food_items (restaurant_id, name, price) SELECT id, 'Kosha Mangsho', 220 FROM restaurants WHERE name = 'Bengal Kitchen';
INSERT INTO food_items (restaurant_id, name, price) SELECT id, 'Shorshe Ilish', 280 FROM restaurants WHERE name = 'Bengal Kitchen';
INSERT INTO food_items (restaurant_id, name, price) SELECT id, 'Chingri Malai Curry', 260 FROM restaurants WHERE name = 'Bengal Kitchen';
INSERT INTO food_items (restaurant_id, name, price) SELECT id, 'Luchi with Alur Dum', 120 FROM restaurants WHERE name = 'Bengal Kitchen';
INSERT INTO food_items (restaurant_id, name, price) SELECT id, 'Rice with Dal and Begun Bhaja', 140 FROM restaurants WHERE name = 'Bengal Kitchen';
INSERT INTO food_items (restaurant_id, name, price) SELECT id, 'Mishti Doi', 60 FROM restaurants WHERE name = 'Bengal Kitchen';

-- ===== Dragon Wok =====
INSERT INTO food_items (restaurant_id, name, price) SELECT id, 'Veg Hakka Noodles', 140 FROM restaurants WHERE name = 'Dragon Wok';
INSERT INTO food_items (restaurant_id, name, price) SELECT id, 'Chicken Fried Rice', 170 FROM restaurants WHERE name = 'Dragon Wok';
INSERT INTO food_items (restaurant_id, name, price) SELECT id, 'Chilli Chicken', 190 FROM restaurants WHERE name = 'Dragon Wok';
INSERT INTO food_items (restaurant_id, name, price) SELECT id, 'Veg Manchurian', 150 FROM restaurants WHERE name = 'Dragon Wok';
INSERT INTO food_items (restaurant_id, name, price) SELECT id, 'Hot and Sour Soup', 110 FROM restaurants WHERE name = 'Dragon Wok';
INSERT INTO food_items (restaurant_id, name, price) SELECT id, 'Spring Rolls', 120 FROM restaurants WHERE name = 'Dragon Wok';

-- ===== Taco Corner =====
INSERT INTO food_items (restaurant_id, name, price) SELECT id, 'Chicken Tacos', 180 FROM restaurants WHERE name = 'Taco Corner';
INSERT INTO food_items (restaurant_id, name, price) SELECT id, 'Veg Burrito', 170 FROM restaurants WHERE name = 'Taco Corner';
INSERT INTO food_items (restaurant_id, name, price) SELECT id, 'Nachos with Cheese', 150 FROM restaurants WHERE name = 'Taco Corner';
INSERT INTO food_items (restaurant_id, name, price) SELECT id, 'Cheese Quesadilla', 190 FROM restaurants WHERE name = 'Taco Corner';
INSERT INTO food_items (restaurant_id, name, price) SELECT id, 'Churros', 110 FROM restaurants WHERE name = 'Taco Corner';

-- ===== Burger Barn =====
INSERT INTO food_items (restaurant_id, name, price) SELECT id, 'Classic Veg Burger', 120 FROM restaurants WHERE name = 'Burger Barn';
INSERT INTO food_items (restaurant_id, name, price) SELECT id, 'Crispy Chicken Burger', 160 FROM restaurants WHERE name = 'Burger Barn';
INSERT INTO food_items (restaurant_id, name, price) SELECT id, 'Cheese Fries', 110 FROM restaurants WHERE name = 'Burger Barn';
INSERT INTO food_items (restaurant_id, name, price) SELECT id, 'Onion Rings', 100 FROM restaurants WHERE name = 'Burger Barn';
INSERT INTO food_items (restaurant_id, name, price) SELECT id, 'Grilled Sandwich', 130 FROM restaurants WHERE name = 'Burger Barn';
INSERT INTO food_items (restaurant_id, name, price) SELECT id, 'Chocolate Shake', 140 FROM restaurants WHERE name = 'Burger Barn';

-- ===== Biryani House =====
INSERT INTO food_items (restaurant_id, name, price) SELECT id, 'Chicken Biryani', 200 FROM restaurants WHERE name = 'Biryani House';
INSERT INTO food_items (restaurant_id, name, price) SELECT id, 'Mutton Biryani', 280 FROM restaurants WHERE name = 'Biryani House';
INSERT INTO food_items (restaurant_id, name, price) SELECT id, 'Veg Biryani', 160 FROM restaurants WHERE name = 'Biryani House';
INSERT INTO food_items (restaurant_id, name, price) SELECT id, 'Egg Biryani', 170 FROM restaurants WHERE name = 'Biryani House';
INSERT INTO food_items (restaurant_id, name, price) SELECT id, 'Chicken Kebab', 180 FROM restaurants WHERE name = 'Biryani House';
INSERT INTO food_items (restaurant_id, name, price) SELECT id, 'Boondi Raita', 40 FROM restaurants WHERE name = 'Biryani House';

-- ===== Sweet Tooth =====
INSERT INTO food_items (restaurant_id, name, price) SELECT id, 'Rasgulla (2 pieces)', 80 FROM restaurants WHERE name = 'Sweet Tooth';
INSERT INTO food_items (restaurant_id, name, price) SELECT id, 'Sandesh (2 pieces)', 90 FROM restaurants WHERE name = 'Sweet Tooth';
INSERT INTO food_items (restaurant_id, name, price) SELECT id, 'Gulab Jamun (2 pieces)', 70 FROM restaurants WHERE name = 'Sweet Tooth';
INSERT INTO food_items (restaurant_id, name, price) SELECT id, 'Chocolate Brownie', 120 FROM restaurants WHERE name = 'Sweet Tooth';
INSERT INTO food_items (restaurant_id, name, price) SELECT id, 'Ice Cream Sundae', 130 FROM restaurants WHERE name = 'Sweet Tooth';
INSERT INTO food_items (restaurant_id, name, price) SELECT id, 'Baked Cheesecake', 160 FROM restaurants WHERE name = 'Sweet Tooth';

-- ===== Tandoori Nights =====
INSERT INTO food_items (restaurant_id, name, price) SELECT id, 'Butter Chicken', 240 FROM restaurants WHERE name = 'Tandoori Nights';
INSERT INTO food_items (restaurant_id, name, price) SELECT id, 'Tandoori Chicken (half)', 260 FROM restaurants WHERE name = 'Tandoori Nights';
INSERT INTO food_items (restaurant_id, name, price) SELECT id, 'Paneer Tikka', 210 FROM restaurants WHERE name = 'Tandoori Nights';
INSERT INTO food_items (restaurant_id, name, price) SELECT id, 'Dal Makhani', 170 FROM restaurants WHERE name = 'Tandoori Nights';
INSERT INTO food_items (restaurant_id, name, price) SELECT id, 'Garlic Naan', 50 FROM restaurants WHERE name = 'Tandoori Nights';
INSERT INTO food_items (restaurant_id, name, price) SELECT id, 'Jeera Rice', 100 FROM restaurants WHERE name = 'Tandoori Nights';

-- ===== Green Bowl =====
INSERT INTO food_items (restaurant_id, name, price) SELECT id, 'Greek Salad', 150 FROM restaurants WHERE name = 'Green Bowl';
INSERT INTO food_items (restaurant_id, name, price) SELECT id, 'Quinoa Veg Bowl', 190 FROM restaurants WHERE name = 'Green Bowl';
INSERT INTO food_items (restaurant_id, name, price) SELECT id, 'Grilled Paneer Wrap', 170 FROM restaurants WHERE name = 'Green Bowl';
INSERT INTO food_items (restaurant_id, name, price) SELECT id, 'Sprouts Chaat', 100 FROM restaurants WHERE name = 'Green Bowl';
INSERT INTO food_items (restaurant_id, name, price) SELECT id, 'Fresh Fruit Bowl', 120 FROM restaurants WHERE name = 'Green Bowl';
INSERT INTO food_items (restaurant_id, name, price) SELECT id, 'Oats Banana Smoothie', 130 FROM restaurants WHERE name = 'Green Bowl';

-- ===== More items for the two original restaurants =====
INSERT INTO food_items (restaurant_id, name, price) SELECT id, 'Dal Tadka', 120 FROM restaurants WHERE name = 'Spice Garden';
INSERT INTO food_items (restaurant_id, name, price) SELECT id, 'Mutton Curry', 260 FROM restaurants WHERE name = 'Spice Garden';
INSERT INTO food_items (restaurant_id, name, price) SELECT id, 'Butter Naan', 40 FROM restaurants WHERE name = 'Spice Garden';
INSERT INTO food_items (restaurant_id, name, price) SELECT id, 'Jeera Rice', 90 FROM restaurants WHERE name = 'Spice Garden';

INSERT INTO food_items (restaurant_id, name, price) SELECT id, 'Farmhouse Pizza', 260 FROM restaurants WHERE name = 'Pizza Point';
INSERT INTO food_items (restaurant_id, name, price) SELECT id, 'Pepperoni Pizza', 280 FROM restaurants WHERE name = 'Pizza Point';
INSERT INTO food_items (restaurant_id, name, price) SELECT id, 'Garlic Bread', 110 FROM restaurants WHERE name = 'Pizza Point';
INSERT INTO food_items (restaurant_id, name, price) SELECT id, 'Pasta Alfredo', 200 FROM restaurants WHERE name = 'Pizza Point';
INSERT INTO food_items (restaurant_id, name, price) SELECT id, 'Choco Lava Cake', 120 FROM restaurants WHERE name = 'Pizza Point';

COMMIT;
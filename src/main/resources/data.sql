INSERT INTO ROLE(role_id, role) VALUES (1, 'ROLE_ADMIN');
INSERT INTO ROLE(role_id, role) VALUES (2, 'ROLE_SELLER');
INSERT INTO ROLE(role_id, role) VALUES (3, 'ROLE_BUYER');

INSERT into ADDRESS (address_id, street, city, state, zip) values (1, '1000 N Court st', 'Fairfield', 'IA', '52556');
INSERT into ADDRESS (address_id, street, city, state, zip) values (2, '2000 N Court st', 'Fairfield', 'IA', '52557');
INSERT into ADDRESS (address_id, street, city, state, zip) values (3, '3000 N Court st', 'Fairfield', 'IA', '52558');
INSERT into ADDRESS (address_id, street, city, state, zip) values (4, '4000 N Court st', 'Fairfield', 'IA', '52559');
INSERT into ADDRESS (address_id, street, city, state, zip) values (5, '5000 N Court st', 'Fairfield', 'IA', '52559');
INSERT into ADDRESS (address_id, street, city, state, zip) values (6, '6000 N Court st', 'Fairfield', 'IA', '52559');

INSERT into USER (user_id, firstName, lastName, username, email, password, active, address_id) values (1, 'Han', 'Truong', 'admin', 'hantruongth@gmail.com', '$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.', 1, 1);
INSERT into USER (user_id, firstName, lastName, username, email, password, active, address_id) values (2, 'Tuyen', 'Le', 'seller', 'ttle@mum.edu','$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.', 1, 2);
INSERT into USER (user_id, firstName, lastName, username, email, password, active, address_id) values (3, 'Thu', 'Truong', 'buyer', 'natruong@mum.edu','$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.', 1, 3);
INSERT into USER (user_id, firstName, lastName, username, email, password, active, address_id) values (4, 'Van', 'Le', 'tuyenseller','tnle@mum.edu', '$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.', 1, 4);
INSERT into USER (user_id, firstName, lastName, username, email, password, active, address_id) values (5, 'Han', 'Truong', 'hantruong','thtruong@mum.edu',  '$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.', 1, 5);
INSERT into USER (user_id, firstName, lastName, username, email, password, active, address_id) values (6, 'Van', 'Le', 'vanbuyer','tnle@mum.edu', '$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.', 1, 6);

INSERT INTO SELLER_FOLLOWER(seller_id, follower_id) VALUES (2, 3);
INSERT INTO SELLER_FOLLOWER(seller_id, follower_id) VALUES (2, 6);
INSERT INTO SELLER_FOLLOWER(seller_id, follower_id) VALUES (2, 5);

INSERT INTO SELLER_FOLLOWER(seller_id, follower_id) VALUES (4, 3);
INSERT INTO SELLER_FOLLOWER(seller_id, follower_id) VALUES (4, 6);

insert into user_role(user_id, role_id) values (1, 1);
insert into user_role(user_id, role_id) values (2, 2);
insert into user_role(user_id, role_id) values (3, 3);
insert into user_role(user_id, role_id) values (4, 2);
insert into user_role(user_id, role_id) values (5, 3);
insert into user_role(user_id, role_id) values (6, 3);

INSERT INTO BRAND(id, name, description) values(1,'Amado','Amado');
INSERT INTO BRAND(id, name, description) values(2,'Ikea','Ikea');
INSERT INTO BRAND(id, name, description) values(3,'Furniture Inc','Furniture Inc');
INSERT INTO BRAND(id, name, description) values(4,'The factory','The factory');
INSERT INTO BRAND(id, name, description) values(5,'Artdeco','Artdeco');

INSERT INTO CATEGORY(id, name, description) values(1,'Chairs','Chairs');
INSERT INTO CATEGORY(id, name, description) values(2,'Beds','Beds');
INSERT INTO CATEGORY(id, name, description) values(3,'Accesories','Accesories');
INSERT INTO CATEGORY(id, name, description) values(4,'Furniture','Furniture');
INSERT INTO CATEGORY(id, name, description) values(5,'Home Deco','Home Deco');
INSERT INTO CATEGORY(id, name, description) values(6,'Dressings','Dressings');
INSERT INTO CATEGORY(id, name, description) values(7,'Tables','Tables');

INSERT INTO PRODUCT(id, name, brand_id, category_id, price, description, status, seller_id) values (1, 'Modern Chair', 1,1, 180,'A chair is a piece of furniture for one person to sit on, with a back and four legs. Black Steel Folding Chair by Plastic Development Group. This durable chair has a double hinges and welded cross braces that will support 225 lbs.', true, 2);
INSERT INTO PRODUCT(id, name, brand_id, category_id, price, description, status, seller_id) values (2, 'Minimalistic Plant Pot', 2,1, 350,'These high-quality, low-maintenance chairs are ideal for any gathering both indoor and outdoor. Featuring durable steel frames and solid construction, they''ll last for years to come. Coordinate with our resin tables to create your own set.', true, 2);
INSERT INTO PRODUCT(id, name, brand_id, category_id, price, description, status, seller_id) values (3, 'Modern Chair', 2,3, 240,'Modern style meets comfortable design in the Barrel Chair from Project 62™. The upholstered chair has a barrel backrest that curves into armrests, a wide foam cushioned seat, back and sides, and cylindrical tapered legs.', true, 2);
INSERT INTO PRODUCT(id, name, brand_id, category_id, price, description, status, seller_id) values (4, 'Plant Pot', 1,5, 430,'A perfect option for your master suite, living room or study, this Alford Rolled Arm Tufted Chair from Threshold™ rounds out your seating space in sophisticated style.', true, 2);
INSERT INTO PRODUCT(id, name, brand_id, category_id, price, description, status, seller_id) values (5, 'Small Table', 4,7, 430,'Dark black frame contrasts perfectly with the near-neutral tone of the wicker chair, adding a pop of contrast to your seating area.', true, 2);
INSERT INTO PRODUCT(id, name, brand_id, category_id, price, description, status, seller_id) values (6, 'Night Stand', 3,1, 450,'Sit back, relax and rock your worries away with the Alston Wood Porch Rocking Chair from Cambridge Casual. Thanks to the durable weather-resistant construction.', true, 2);
INSERT INTO PRODUCT(id, name, brand_id, category_id, price, description, status, seller_id) values (7, 'Modern Rocking Chair', 2,1, 250,'A perfect option for your master suite, living room or study, this Alford Rolled Arm Tufted Chair from Threshold™ rounds out your seating space in sophisticated style.', true, 2);
INSERT INTO PRODUCT(id, name, brand_id, category_id, price, description, status, seller_id) values (8, 'Home Deco', 2,5, 150,'Dark black frame contrasts perfectly with the near-neutral tone of the wicker chair, adding a pop of contrast to your seating area.', true, 4);
INSERT INTO PRODUCT(id, name, brand_id, category_id, price, description, status, seller_id) values (9, 'Metallic Chair', 4,1, 490,'Sit back, relax and rock your worries away with the Alston Wood Porch Rocking Chair from Cambridge Casual. Thanks to the durable weather-resistant construction.', true, 4);

INSERT INTO IMAGE(id, name) values(1,'1.jpg');
INSERT INTO IMAGE(id, name) values(2,'2.jpg');
INSERT INTO IMAGE(id, name) values(3,'3.jpg');
INSERT INTO IMAGE(id, name) values(4,'4.jpg');
INSERT INTO IMAGE(id, name) values(5,'5.jpg');
INSERT INTO IMAGE(id, name) values(6,'6.jpg');
INSERT INTO IMAGE(id, name) values(7,'7.jpg');
INSERT INTO IMAGE(id, name) values(8,'8.jpg');
INSERT INTO IMAGE(id, name) values(9,'9.jpg');

INSERT INTO IMAGE(id, name) values(10,'pro-big-1.jpg');
INSERT INTO IMAGE(id, name) values(11,'pro-big-2.jpg');
INSERT INTO IMAGE(id, name) values(12,'pro-big-3.jpg');
INSERT INTO IMAGE(id, name) values(13,'pro-big-4.jpg');

INSERT INTO IMAGE(id, name) values(14,'product1.jpg');
INSERT INTO IMAGE(id, name) values(15,'product2.jpg');
INSERT INTO IMAGE(id, name) values(16,'product3.jpg');
INSERT INTO IMAGE(id, name) values(17,'product4.jpg');
INSERT INTO IMAGE(id, name) values(18,'product5.jpg');
INSERT INTO IMAGE(id, name) values(19,'product6.jpg');

INSERT INTO IMAGE(id, name) values(20, 'saleoff.png');

INSERT INTO PRODUCT_IMAGE(product_id, image_id) values(1,1);
INSERT INTO PRODUCT_IMAGE(product_id, image_id) values(1,10);

INSERT INTO PRODUCT_IMAGE(product_id, image_id) values(2,2);
INSERT INTO PRODUCT_IMAGE(product_id, image_id) values(2,11);

INSERT INTO PRODUCT_IMAGE(product_id, image_id) values(3,3);
INSERT INTO PRODUCT_IMAGE(product_id, image_id) values(3,12);

INSERT INTO PRODUCT_IMAGE(product_id, image_id) values(4,4);
INSERT INTO PRODUCT_IMAGE(product_id, image_id) values(4,13);

INSERT INTO PRODUCT_IMAGE(product_id, image_id) values(5,5);
INSERT INTO PRODUCT_IMAGE(product_id, image_id) values(5,14);

INSERT INTO PRODUCT_IMAGE(product_id, image_id) values(6,6);
INSERT INTO PRODUCT_IMAGE(product_id, image_id) values(7,7);
INSERT INTO PRODUCT_IMAGE(product_id, image_id) values(8,8);
INSERT INTO PRODUCT_IMAGE(product_id, image_id) values(9,9);

INSERT INTO REVIEW(ID, rating, message, show, user_id) values (1, 5, 'this is so good product. I have been using for many years', true, 1);
INSERT INTO REVIEW(ID, rating, message, show, user_id) values (2, 1, 'Not good, product design is so old.', false, 2);
INSERT INTO REVIEW(ID, rating, message, show, user_id) values (3, 2, 'It seems good product but the quality is actually not like that', false, 5);
INSERT INTO REVIEW(ID, rating, message, show, user_id) values (4, 1, 'I do not like it because its material is too bad', false, 3);

INSERT INTO PRODUCT_REVIEW(product_id, review_id) values (1, 1);
INSERT INTO PRODUCT_REVIEW(product_id, review_id) values (1, 2);
INSERT INTO PRODUCT_REVIEW(product_id, review_id) values (2, 3);
INSERT INTO PRODUCT_REVIEW(product_id, review_id) values (2, 4);

INSERT INTO PAYMENT(id, payment) VALUES (1, 'CASH');
INSERT INTO PAYMENT(id, payment) VALUES (2, 'PAYPAL');
INSERT INTO PAYMENT(id, payment) VALUES (3, 'POINT');

INSERT INTO ORDER_STATUS(id, status) VALUES (1, 'Processing');
INSERT INTO ORDER_STATUS(id, status) VALUES (2, 'Shipped');
INSERT INTO ORDER_STATUS(id, status) VALUES (3, 'OnTheWay');
INSERT INTO ORDER_STATUS(id, status) VALUES (4, 'Delivered');
INSERT INTO ORDER_STATUS(id, status) VALUES (5, 'Cancelled');
INSERT INTO ORDER_STATUS(id, status) VALUES (6, 'Returned');
INSERT INTO ORDER_STATUS(id, status) VALUES (7, 'Successful');

INSERT INTO ADVERTISEMENT(id, title, description, show, image_id) VALUES (1, 'SALE OFF 50%', 'Buy ONE and get ONE for free. This offer is valid til 2030', true, 20);

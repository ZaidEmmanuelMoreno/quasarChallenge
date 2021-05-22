insert into ship_location(name, x_coordinate, y_coordinate) values ('kenobi', -500, -200);
insert into ship_location(name, x_coordinate, y_coordinate) values ('skywalker', 100, -100);
insert into ship_location(name, x_coordinate, y_coordinate) values ('sato', 500, 100);
insert into users(username,password) values ('admin','$2a$10$Fbj03SfEm1Z5ayDxk4ytHeuSPtkVKRU5h.2ymHNVwlZeGyJxqmFRG');
insert into roles(name) values ('ADMIN');
insert into roles(name) values ('USER');
insert into users_roles(user_id, role_id) values (1, 1);
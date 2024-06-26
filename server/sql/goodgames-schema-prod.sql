drop database if exists good_games;
create database good_games;
use good_games;

-- create tables and relationships
create table `user` (
    user_id int primary key auto_increment,
    `name` varchar(25) not null,
    email varchar(250) not null,
    `password` varchar(100) not null,
    `role` varchar(15) not null
);

create table game (
	game_id int primary key auto_increment,
    bgg_id int not null,
    `name` varchar(250) not null
);

create table review (
	review_id int primary key auto_increment,
    `text` varchar(1000) not null,
    rating int not null check(rating >= 1 AND rating <= 10),
    user_id int not null,
    game_id int not null,
    constraint fk_review_user_id
        foreign key (user_id)
        references `user`(user_id),
	constraint fk_review_game_id
        foreign key (game_id)
        references game(game_id)
);

create table location (
	location_id int primary key auto_increment,
    `name` varchar(100) not null,
    address varchar(100) not null,
    city varchar(100) not null,
    state varchar(2) not null check(char_length(state)=2),
    postal_code varchar(50) not null
);

create table reservation (
	reservation_id int primary key auto_increment,
    `date` date not null,
    `time` time not null,
    location_id int not null,
    host_id int not null,
	constraint fk_reservation_location_id
        foreign key (location_id)
        references location(location_id),
	constraint fk_reservation_host_id
        foreign key (host_id)
        references `user`(user_id)    
);

create table reservation_attendee (
	reservation_attendee_id int primary key auto_increment,
	reservation_id int not null,
    user_id int not null,
	constraint fk_reservation_attendee_location_id
        foreign key (reservation_id)
        references reservation(reservation_id),
	constraint fk_reservation_attendee_user_id
        foreign key (user_id)
        references `user`(user_id)        
);

insert into `user` (`name`, email, `password`, `role`)
	values 
		("Kevin", "kevin@kevin.com", "kevin", "ADMIN"),
        ("Miguel", "miguel@miguel.com", "miguel", "ADMIN"),
		("Headly", "headley@headley.com", "headley", "ADMIN"),
        ("Rosales", "rosales@rosales.com", "rosales", "USER"),
		("Brissett", "brissett@brissett.com", "brisset", "USER"),
        ("Dias", "dias@dias.com", "dias", "USER");
        
insert into game (bgg_id, `name`)
	values
		(2536, "Vabanque"),
        (1988, "Outlaw Trail: The Western Game"),
        (13, "CATAN"),
		(110308, "7 Wonders: Catan");
        
insert into review (`text`, rating, user_id, game_id)
	values
		("I loved this game!", 10, 1, 1),
        ("Nope, not for me, don't play it.", 1, 6, 1),
        ("I tried it and I liked it, I guess", 7, 2, 2),
        ("Fun, but not for me.", 6, 5, 2),
		("I tried it and I liked it, I guess", 5, 3, 3),
        ("Fun, but not for me.", 5, 5, 3),
        ("Love love love this game", 9, 1, 4);
        
insert into location (`name`, address, city, state, postal_code)
	values 
		("Magic & Mayhem", "100 Main Place", "Yonkers", "NY", "00000"),
        ("Games Place", "12 Games Way", "Detroit", "MI", "11111"),
        ("The Spot", "123 Avenue Q", "Dallas", "TX", "22222"),
        ("Dungeons and Draggins", "45 East Shire Lane", "Charlotte", "NC", "33333");
        
insert into reservation (`date`, `time`, location_id, host_id)
	values
		("2024-06-01", "18:00:00", 1, 1),
		("2024-07-15", "19:30:00", 2, 2),
		("2024-07-01", "18:00:00", 3, 3),
		("2024-06-01", "18:00:00", 3, 4),
		("2024-07-01", "18:00:00", 1, 1),
		("2024-07-01", "17:30:00", 2, 1),
        ("2022-04-13", "13:00:00", 1, 1);
        
insert into reservation_attendee (reservation_id, user_id)
	values
		(1, 1),
        (2, 1),
        (3, 1),
        (4, 2),
        (4, 3),
        (4, 4);

SET SQL_SAFE_UPDATES = 0;

-- update game g
-- 	inner join 
-- 		(select game_id, avg(rating) as avgrating
-- 			from review r
--             group by game_id
-- 		) r
--         on r.game_id = g.game_id
-- 	set g.avg_rating = r.avgrating;

SET SQL_SAFE_UPDATES = 1;

-- testing for aggregate join of average ratings on a specific game.
-- select * from game;

-- testing some joins of tables across the DB
-- select u.`name`, g.`name`, r.`text`
-- 	from user u
--     inner join review r on r.user_id = u.user_id
--     inner join game g on g.game_id = r.game_id
--     where u.user_id = 1;
	
    
    
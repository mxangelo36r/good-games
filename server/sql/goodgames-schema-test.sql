drop database if exists good_games_test;
create database good_games_test;
use good_games_test;

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
    rating int not null check(rating >= 0 AND rating <= 10),
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

delimiter //
create procedure set_known_good_state()
begin
	SET SQL_SAFE_UPDATES = 0;
	delete from reservation_attendee;
    alter table reservation_attendee auto_increment = 1;
    delete from reservation;
    alter table reservation auto_increment = 1;
    delete from review;
    alter table review auto_increment = 1;
    delete from location;
    alter table location auto_increment = 1;
	delete from game;
    alter table game auto_increment = 1;
	delete from `user`;
	alter table `user` auto_increment = 1;

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
        ("Nope, not for me, don't play it.", 0, 6, 1),
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
		("2022-04-13", "13:00:00", 1, 1);
        
insert into reservation_attendee (reservation_id, user_id)
	values
		(1, 1),
        (2, 1),
        (3, 1),
        (4, 2),
        (4, 3),
        (4, 4);

-- update game g
-- 	inner join 
-- 		(select game_id, avg(rating) as avgrating
-- 			from review r
--             group by game_id
-- 		) r
--         on r.game_id = g.game_id
-- 	set g.avg_rating = r.avgrating;

	SET SQL_SAFE_UPDATES = 1;
    
end //

delimiter ;

call set_known_good_state;

-- testing for aggregate join of average ratings on a specific game.
select * from game;

-- testing some joins of tables across the DB
select u.`name`, g.`name`, r.`text`
	from user u
    inner join review r on r.user_id = u.user_id
    inner join game g on g.game_id = r.game_id
    where u.user_id = 1;
    
-- testing delete of location, needs multiple steps
-- select l.`name`, l.location_id from reservation_attendee
-- 	inner join reservation r on r.reservation_id = reservation_attendee.reservation_id
-- 	inner join location l on l.location_id = r.reservation_id
--     where l.location_id = 1;
-- delete reservation_attendee from reservation_attendee 
-- 	inner join reservation r on r.reservation_id = reservation_attendee.reservation_id
--     inner join location l on l.location_id = r.reservation_id
--     where l.location_id = 1;
-- delete from reservation where location_id = 1;
-- delete from location where location_id = 1;
-- select l.`name`, l.location_id from reservation_attendee
-- 	inner join reservation r on r.reservation_id = reservation_attendee.reservation_id
-- 	inner join location l on l.location_id = r.reservation_id
--     where l.location_id = 1;
-- select * from location;

-- Used to test reutnring avg rating for a specifc game_id
-- select game_id, avg(rating) as avgrating
-- 	from review r
--     where game_id = 1;

-- Test to get location for each reservation
-- select r.reservation_id, r.`date`, r.`time`, r.location_id, r.host_id, l.`name`, l.address, l.city, l.state, l.postal_code, u.`name` as host_name
--                 from reservation r
--                 inner join location l on l.location_id = r.location_id
--                 inner join `user` u on u.user_id = r.host_id
--                 where r.`date` >= curdate()
--                 order by r.`date`;

-- Test to get reviews by game_id
select r.review_id, r.`text`, r.rating, r.user_id, r.game_id, g.bgg_id, g.`name` as game_name, u.`name` as user_name
	from review r
    inner join game g on g.game_id = r.game_id
    inner join user u on u.user_id = r.user_id
    where r.review_id = 1;
    
select r.review_id, r.`text`, r.rating, r.user_id, r.game_id, u.`name` as user_name, g.`name` as game_name, g.bgg_id
	from review r
    inner join `user` u on u.user_id = r.user_id
    inner join game g on g.game_id = r.game_id;
    
select r.review_id, r.game_id, g.`name` as game_name, r.text, u.`name` as user_name, avg(r.rating) as avg_rating 
	from review r
    inner join game g on g.game_id = r.game_id
    group by r.game_id
    order by avg_rating;
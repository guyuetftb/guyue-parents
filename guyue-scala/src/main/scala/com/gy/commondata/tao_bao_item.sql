CREATE TABLE `user_tao_bao_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(64) DEFAULT '',
  `cid` int(11) DEFAULT '0',
  `location_state` char(64) DEFAULT '',
  `location_city` char(64) DEFAULT '',
  `price` double DEFAULT '0',
  `num` int(8) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8


insert into user_tao_bao_item (title,cid,location_state,location_city,price,num)
values
('91币10元/魔域/官方秒充/JYT自动充值/可倍数提交', 50007913,'上海', '上海', 9.3, 1000),
('世纪天成按元充/冲锋岛/官方秒充/JYT自动充值', 50007477 ,'上海' ,'上海', 0.98, 1000),
('世纪天成按元充/反恐精英/官方秒充/JYT自动充值', 50007433,'上海' ,'上海', 0.98, 1000),
('世纪天成按元充/跑跑卡丁车/官方秒充/JYT自动充值', 50007437 ,'上海' ,'上海', 0.98, 7313),
('久游休闲卡10元/GT劲舞团2/官方秒充/JYT自动充值', 50007445 ,'上海' ,'上海', 9.2, 1000),
('久游休闲卡10元/仙剑OL/官方秒充/电脑充值/',  50007599 ,'上海' ,'上海', 9.2, 1000),
('久游休闲卡30元/GT劲舞团2/官方秒充/JYT自动充值', 50007445 ,'上海' ,'上海', 27.5, 1000),
('久游休闲卡10元/劲舞团/官方秒充/电脑充值/',  50007435 ,'上海' ,'上海', 9.2, 975),
('久游休闲卡10元/超级舞者/官方秒充/电脑充值/',  50007416 ,'上海' ,'上海', 9.2, 1000),
('久游休闲卡30元/仙剑OL/官方秒充/JYT自动充值', 50007599 ,'上海' ,'上海', 27.5, 1000),
('久游休闲卡30元/劲舞团/官方秒充/JYT自动充值', 50007435 ,'上海' ,'上海', 27.5, 975),
('久游休闲卡30元/超级舞者/官方秒充/JYT自动充值', 50007416 ,'上海' ,'上海', 27.5, 1000),
('人人网人人豆10元校内豆10元电脑充值[请输入人人网数字ID]', 50024863 ,'上海', '上海', 9.4 ,1000)
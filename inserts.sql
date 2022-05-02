--Labels
INSERT INTO kora7392.Label VALUES('BLBORE',      'Black Book Records',    '2017-08-25');
INSERT INTO kora7392.Label VALUES('CATREL',      'Catch & Release',       '2018-05-13');
INSERT INTO kora7392.Label VALUES('HELREC',      'Heldeep Records',       '2015-09-22');
INSERT INTO kora7392.Label VALUES('TERUND',      'Terminal Underground',  '2020-03-15');
INSERT INTO kora7392.Label VALUES('DIRWOR',      'Dirty Workz',           '2016-04-13');
INSERT INTO kora7392.Label VALUES('CONFES',      'CONFESSION',            '2013-06-02');
INSERT INTO kora7392.Label VALUES('REPMAR',      'Repopulate Mars',       '2018-10-25');

--Artists
INSERT INTO kora7392.Artist VALUES('CRLA820808',         'Chris Lake',       'Chris',    'Lake',                     '1982-08-08');
INSERT INTO kora7392.Artist VALUES('PAFI861105',         'FISHER',           'Paul',     'Fisher',                   '1986-11-05');
INSERT INTO kora7392.Artist VALUES('OLHE950201',         'Oliver Heldens',   'Oliver',   'Heldens',                  '1995-02-01');
INSERT INTO kora7392.Artist VALUES('TOCU981027',         'CURBI',            'Toby',     'Curwen-Bingley',           '1998-10-27');
INSERT INTO kora7392.Artist VALUES('PASK951030',         'San Pacho',        'Patrik',   'Skalec',                   '1995-10-30');
INSERT INTO kora7392.Artist VALUES('MARO930603',         'MATRODA',          'Matija',   'Rodic',                    '1993-06-03');
INSERT INTO kora7392.Artist VALUES('DABI950913',         'Cloonee',          'Dave',     'Bissett',                  '1995-09-13');
INSERT INTO kora7392.Artist VALUES('KOBA830530',         'Coone',            'Koen',     'Bauweraerts',              '1983-05-30');
INSERT INTO kora7392.Artist VALUES('FRVA890419',         'Hard Driver',      'Freek',    'Van Kempen',               '1989-04-19');
INSERT INTO kora7392.Artist VALUES('HAMO990728',         'Hasse De Moor',    'Hasse',    'De Moor',                  '1999-07-28');
INSERT INTO kora7392.Artist VALUES('MAJO850512',         'Tchami',           'Martin',   'Joseph Léonard Bresso',    '1985-05-12');
INSERT INTO kora7392.Artist VALUES('DAHA971130',         'Endor',            'Daniel',   'Hardingham',               '1997-11-30');
INSERT INTO kora7392.Artist VALUES('MIHE900516',         'Mike Cervello',    'Michael',  'Herssens',                 '1990-05-16');

--Catalogs
INSERT INTO kora7392.Catalog VALUES(DEFAULT, 'BLACK BOOK ID''S: CHAPTER 3',       'EP');      --1
INSERT INTO kora7392.Catalog VALUES(DEFAULT, 'Loosing Sleep',                     DEFAULT);   --2
INSERT INTO kora7392.Catalog VALUES(DEFAULT, 'Holla',                             DEFAULT);   --3
INSERT INTO kora7392.Catalog VALUES(DEFAULT, 'Just Feels Tight',                  DEFAULT);   --4
INSERT INTO kora7392.Catalog VALUES(DEFAULT, 'The Fish Is Back!',                 'EP');      --5
INSERT INTO kora7392.Catalog VALUES(DEFAULT, 'Legends Of The Elite',              'ALBUM');   --6
INSERT INTO kora7392.Catalog VALUES(DEFAULT, 'ADHD',                              DEFAULT);   --7
INSERT INTO kora7392.Catalog VALUES(DEFAULT, 'Alcoholic',                         DEFAULT);   --8
INSERT INTO kora7392.Catalog VALUES(DEFAULT, 'How''s Your Evening So Far?',       DEFAULT);   --9
INSERT INTO kora7392.Catalog VALUES(DEFAULT, 'Amor',                              DEFAULT);   --10
INSERT INTO kora7392.Catalog VALUES(DEFAULT, 'Deja Vu',                           DEFAULT);   --11
INSERT INTO kora7392.Catalog VALUES(DEFAULT, 'Ruff Cutz',                         'EP');      --12

--Records
INSERT INTO kora7392.Record VALUES('BLBORE0001',     'How''s Your Evening So Far?',                  9, 'BLBORE', '00:06:47', '2020-01-06', '2020-01-17', 'House',         1.99);
INSERT INTO kora7392.Record VALUES('BLBORE0002',     'Holla',                                        3, 'BLBORE', '00:05:11', '2021-07-20', '2021-08-04', 'Tech House',    1.99);
INSERT INTO kora7392.Record VALUES('BLBORE0003',     '400',                                          1, 'BLBORE', '00:04:53', '2022-03-13', '2022-03-25', 'Tech House',    1.99);
INSERT INTO kora7392.Record VALUES('BLBORE0004',     'Snake Charmer',                                1, 'BLBORE', '00:07:03', '2022-03-13', '2022-03-25', 'Tech House',    2.99);

INSERT INTO kora7392.Record VALUES('CATREL0001',     'Just Feels Tight',                             4, 'CATREL', '00:03:14', '2021-08-06', '2021-08-17', 'Tech House',    1.99);
INSERT INTO kora7392.Record VALUES('CATREL0002',     'Palm Beach Banga',                             5, 'CATREL', '00:04:53', '2022-02-13', '2022-02-25', 'Tech House',    2.49);
INSERT INTO kora7392.Record VALUES('CATREL0003',     'Its A Killa',                                  5, 'CATREL', '00:05:05', '2022-03-06', '2022-03-18', 'Tech House',    2.49);

INSERT INTO kora7392.Record VALUES('HELREC0001',     'ADHD',                                         7, 'HELREC', '00:03:47', '2019-02-16', '2019-02-27', 'House',         1.69);
INSERT INTO kora7392.Record VALUES('HELREC0002',     'Alcoholic',                                    8, 'HELREC', '00:03:22', '2021-06-12', '2021-06-23', 'House',         1.29);
INSERT INTO kora7392.Record VALUES('HELREC0003',     'Loosing Sleep',                                2, 'HELREC', '00:02:58', '2021-10-14', '2021-10-25', 'House',         1.29);

INSERT INTO kora7392.Record VALUES('TERUND0001',     'Amor',                                         10,'TERUND', '00:04:15', '2022-04-03', '2022-04-14', 'House',         1.99);

INSERT INTO kora7392.Record VALUES('DIRWOR0001',     'By The Sword',                                 6, 'DIRWOR', '00:03:03', '2022-02-25', '2022-03-12', 'Hardstyle',     0.99);
INSERT INTO kora7392.Record VALUES('DIRWOR0002',     'Hellraiser',                                   6, 'DIRWOR', '00:04:22', '2022-02-25', '2022-03-12', 'Hardstyle',     0.99);
INSERT INTO kora7392.Record VALUES('DIRWOR0003',     'Insanity',                                     6, 'DIRWOR', '00:02:58', '2022-02-25', '2022-03-12', 'Hardstyle',     0.99);

INSERT INTO kora7392.Record VALUES('CONFES0001',     'Deja Vu',                                      11,'CONFES', '00:04:28', '2022-02-12', '2022-04-29', 'Techno',        1.69);

INSERT INTO kora7392.Record VALUES('REPMAR0001',     'Pump It Up',                                   12,'REPMAR', '00:06:28', '2020-09-13', '2020-09-25', 'Tech House',    2.49);
INSERT INTO kora7392.Record VALUES('REPMAR0002',     'El Cuervo',                                    12,'REPMAR', '00:04:59', '2022-03-03', '2022-03-14', 'Tech House',    2.69);

--Record_Artists
INSERT INTO kora7392.RecordArtist VALUES('BLBORE0001',     'CRLA820808');
INSERT INTO kora7392.RecordArtist VALUES('BLBORE0002',     'DABI950913');
INSERT INTO kora7392.RecordArtist VALUES('BLBORE0003',     'CRLA820808');
INSERT INTO kora7392.RecordArtist VALUES('BLBORE0004',     'CRLA820808');
INSERT INTO kora7392.RecordArtist VALUES('BLBORE0004',     'PAFI861105');

INSERT INTO kora7392.RecordArtist VALUES('CATREL0001',     'PAFI861105');
INSERT INTO kora7392.RecordArtist VALUES('CATREL0002',     'PAFI861105');
INSERT INTO kora7392.RecordArtist VALUES('CATREL0003',     'PAFI861105');

INSERT INTO kora7392.RecordArtist VALUES('HELREC0001',     'TOCU981027');
INSERT INTO kora7392.RecordArtist VALUES('HELREC0001',     'HAMO990728');
INSERT INTO kora7392.RecordArtist VALUES('HELREC0002',     'TOCU981027');
INSERT INTO kora7392.RecordArtist VALUES('HELREC0002',     'HAMO990728');
INSERT INTO kora7392.RecordArtist VALUES('HELREC0003',     'TOCU981027');

INSERT INTO kora7392.RecordArtist VALUES('TERUND0001',     'PASK951030');
INSERT INTO kora7392.RecordArtist VALUES('TERUND0001',     'MARO930603');

INSERT INTO kora7392.RecordArtist VALUES('DIRWOR0001',     'KOBA830530');
INSERT INTO kora7392.RecordArtist VALUES('DIRWOR0001',     'FRVA890419');
INSERT INTO kora7392.RecordArtist VALUES('DIRWOR0002',     'FRVA890419');
INSERT INTO kora7392.RecordArtist VALUES('DIRWOR0003',     'KOBA830530');

INSERT INTO kora7392.RecordArtist VALUES('CONFES0001',     'TOCU981027');
INSERT INTO kora7392.RecordArtist VALUES('CONFES0001',     'MIHE900516');

INSERT INTO kora7392.RecordArtist VALUES('REPMAR0001',     'DAHA971130');
INSERT INTO kora7392.RecordArtist VALUES('REPMAR0002',     'DAHA971130');

--Orders
INSERT INTO kora7392.Order VALUES(DEFAULT, 'BLBORE0001',     'user1', '2020-01-06', '2020-01-17');
INSERT INTO kora7392.Order VALUES(DEFAULT, 'BLBORE0001',     'user2', NULL,         '2020-03-15');
INSERT INTO kora7392.Order VALUES(DEFAULT, 'BLBORE0002',     'user1', '2021-07-20', '2022-08-04');
INSERT INTO kora7392.Order VALUES(DEFAULT, 'BLBORE0003',     'user1', '2022-03-14', '2022-03-25');
INSERT INTO kora7392.Order VALUES(DEFAULT, 'BLBORE0003',     'user4', NULL,         '2022-03-29');
INSERT INTO kora7392.Order VALUES(DEFAULT, 'BLBORE0003',     'user3', NULL,         '2022-03-28');
INSERT INTO kora7392.Order VALUES(DEFAULT, 'BLBORE0003',     'user5', NULL,         '2022-03-30');
INSERT INTO kora7392.Order VALUES(DEFAULT, 'BLBORE0004',     'user1', NULL,         '2022-03-30');
INSERT INTO kora7392.Order VALUES(DEFAULT, 'BLBORE0004',     'user7', NULL,         '2022-04-02');
INSERT INTO kora7392.Order VALUES(DEFAULT, 'BLBORE0004',     'user6', NULL,         '2022-04-01');

INSERT INTO kora7392.Order VALUES(DEFAULT, 'CATREL0001',     'user1', '2021-08-14', '2021-08-17');
INSERT INTO kora7392.Order VALUES(DEFAULT, 'CATREL0002',     'user1', '2022-02-15', '2022-02-25');
INSERT INTO kora7392.Order VALUES(DEFAULT, 'CATREL0002',     'user4', NULL,         '2022-03-01');
INSERT INTO kora7392.Order VALUES(DEFAULT, 'CATREL0003',     'user4', '2022-03-15', '2022-03-18');
INSERT INTO kora7392.Order VALUES(DEFAULT, 'CATREL0003',     'user1', NULL,         '2022-03-28');
INSERT INTO kora7392.Order VALUES(DEFAULT, 'CATREL0003',     'user5', NULL,         '2022-04-01');
INSERT INTO kora7392.Order VALUES(DEFAULT, 'CATREL0003',     'user6', '2022-03-17', '2022-03-18');

INSERT INTO kora7392.Order VALUES(DEFAULT, 'HELREC0001',     'user5', '2019-02-16', '2019-02-27');
INSERT INTO kora7392.Order VALUES(DEFAULT, 'HELREC0001',     'user2', NULL,         '2020-03-30');
INSERT INTO kora7392.Order VALUES(DEFAULT, 'HELREC0002',     'user3', '2021-06-20', '2021-06-23');
INSERT INTO kora7392.Order VALUES(DEFAULT, 'HELREC0002',     'user2', NULL,         '2021-09-03');
INSERT INTO kora7392.Order VALUES(DEFAULT, 'HELREC0002',     'user8', NULL,         '2021-08-24');
INSERT INTO kora7392.Order VALUES(DEFAULT, 'HELREC0002',     'user7', '2021-06-18', '2021-06-23');
INSERT INTO kora7392.Order VALUES(DEFAULT, 'HELREC0003',     'user1', '2021-10-14', '2021-10-25');
INSERT INTO kora7392.Order VALUES(DEFAULT, 'HELREC0003',     'user8', NULL,         '2021-10-30');
INSERT INTO kora7392.Order VALUES(DEFAULT, 'HELREC0003',     'user3', NULL,         '2021-12-05');

INSERT INTO kora7392.Order VALUES(DEFAULT, 'TERUND0001',     'user1', '2022-04-10', '2022-04-14');
INSERT INTO kora7392.Order VALUES(DEFAULT, 'TERUND0001',     'user6', '2022-04-09', '2022-04-14');

INSERT INTO kora7392.Order VALUES(DEFAULT, 'DIRWOR0001',     'user2', '2022-02-25', '2022-03-12');
INSERT INTO kora7392.Order VALUES(DEFAULT, 'DIRWOR0003',     'user8', NULL,         '2022-03-29');
INSERT INTO kora7392.Order VALUES(DEFAULT, 'DIRWOR0003',     'user1', '2022-02-25', '2022-03-12');
INSERT INTO kora7392.Order VALUES(DEFAULT, 'DIRWOR0003',     'user4', '2022-02-28', '2022-03-12');

INSERT INTO kora7392.Order VALUES(DEFAULT, 'CONFES0001',     'user1', '2022-02-12', NULL);
INSERT INTO kora7392.Order VALUES(DEFAULT, 'CONFES0001',     'user3', '2022-03-09', NULL);

INSERT INTO kora7392.Order VALUES(DEFAULT, 'REPMAR0001',     'user1', '2020-09-13', '2020-09-25');
INSERT INTO kora7392.Order VALUES(DEFAULT, 'REPMAR0001',     'user7', NULL,         '2020-11-03');

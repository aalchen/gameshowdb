CREATE TABLE DeveloperName(
                              lead_developer VARCHAR(50),
                              website VARCHAR(50),
                              name VARCHAR(50) PRIMARY KEY
);

CREATE TABLE Company(
                        name VARCHAR(50) PRIMARY KEY,
                        contact_info VARCHAR(100) UNIQUE
);

CREATE TABLE Venue(
                      name VARCHAR(50) PRIMARY KEY,
                      address VARCHAR(50),
                      capacity INTEGER
);


CREATE TABLE AwardCeremony(
                              viewer_count INTEGER,
                              award_ceremony_date Date PRIMARY KEY,
                              venue_name VARCHAR(50),
                              FOREIGN KEY (venue_name) REFERENCES Venue(name)
                                  ON DELETE SET NULL
);

CREATE TABLE LivestreamUrl(
                              url VARCHAR(100),
                              language VARCHAR(50),
                              name VARCHAR(50),
                              awardceremony_date Date,
                              PRIMARY KEY (name, language, awardceremony_date),
                              FOREIGN KEY (awardceremony_date) REFERENCES AwardCeremony(award_ceremony_date)
                                  ON DELETE SET NULL
);

CREATE TABLE Staff(
                      phone_number VARCHAR(20),
                      name VARCHAR(50),
                      id INTEGER PRIMARY KEY,
                      role VARCHAR(50)
);


CREATE TABLE DeveloperCountry(
                                 name VARCHAR(50) PRIMARY KEY,
                                 country VARCHAR(50),
                                 CONSTRAINT devcountry_fk
                                     FOREIGN KEY (name) REFERENCES DeveloperName(name)
                                         ON DELETE CASCADE
);

CREATE TABLE VideoGame(
                          title VARCHAR(128),
                          year INTEGER,
                          genre VARCHAR(50),
                          developer_name VARCHAR(50),
                          PRIMARY KEY (title, year),
                          CONSTRAINT videogame_fk
                              FOREIGN KEY (developer_name) REFERENCES DeveloperName(name)
                                  ON DELETE CASCADE
);

CREATE TABLE Award(
                      name VARCHAR(50),
                      prize INTEGER,
                      award_date Date,
                      videogame_title VARCHAR(50),
                      videogame_year INTEGER,
                      PRIMARY KEY (award_date, name),
                      FOREIGN KEY(award_date) REFERENCES AwardCeremony(award_ceremony_date)
                          ON DELETE CASCADE,
                      FOREIGN KEY(videogame_title, videogame_year) REFERENCES VideoGame(Title, Year)
                          ON DELETE SET NULL
);

CREATE TABLE LivestreamViewerCount(
                                      language VARCHAR(50),
                                      viewer_count INTEGER,
                                      name VARCHAR(50),
                                      awardceremony_date Date,
                                      PRIMARY KEY (language, name, awardceremony_date),
                                      FOREIGN KEY (name, language, awardceremony_date) REFERENCES LivestreamUrl(name, language, awardceremony_date)
                                          ON DELETE CASCADE
);


CREATE TABLE VideoGame_DLC(
                              title VARCHAR(50),
                              year INTEGER,
                              videogame_title VARCHAR(50),
                              videogame_year INTEGER,
                              PRIMARY KEY (title, year, videogame_title, videogame_year),
                              FOREIGN KEY (videogame_title, videogame_year) REFERENCES VideoGame(title, year)
                                  ON DELETE CASCADE
);



CREATE TABLE CommunityAward(
                               votes INTEGER,
                               deadline Date NOT NULL,
                               award_name VARCHAR(50),
                               award_date Date,
                               PRIMARY KEY (award_name, award_date),
                               FOREIGN KEY (award_name, award_date) REFERENCES Award(name, award_date)
                                   ON DELETE CASCADE
);

CREATE TABLE SponsoredAward(
                               sponsor VARCHAR(50),
                               award_name VARCHAR(50),
                               award_date Date,
                               PRIMARY KEY (award_name, award_date),
                               FOREIGN KEY (award_name, award_date) REFERENCES Award(name, award_date)
                                   ON DELETE CASCADE
);

CREATE TABLE Staff_AwardCeremony(
                                    staff_id INTEGER,
                                    awardceremony_date Date,
                                    PRIMARY KEY(staff_id, awardceremony_date),
                                    FOREIGN KEY(staff_id) REFERENCES Staff(id),
                                    FOREIGN KEY(awardceremony_date) REFERENCES AwardCeremony(award_ceremony_date)
);


CREATE TABLE Sponsors(
                         company_name VARCHAR(50),
                         awardceremony_date Date,
                         money INTEGER NOT NULL,
                         PRIMARY KEY(company_name, awardceremony_date),
                         FOREIGN KEY(company_name) REFERENCES Company(name),
                         FOREIGN KEY(awardceremony_date) REFERENCES AwardCeremony(award_ceremony_date)
);

INSERT INTO Venue VALUES ('Microsoft Theater', '777 Chick Hearn Ct Los Angeles California USA', 7100);
INSERT INTO Venue VALUES ('Zappos Theater', '3667 S Las Vegas Blvd Las Vegas USA', 7000);
INSERT INTO Venue VALUES ('Nokia Theater Live', '230 Bird Barn Ct Los Angeles California', 3500);
INSERT INTO Venue VALUES ('Sven Theater', '3101 Sizzlin Road  Los Angeles California', 7000);
INSERT INTO Venue VALUES ('The AXIS Theater', '3330 Palm Avenue Los Angeles California', 7100);

INSERT INTO AwardCeremony VALUES (2601, '2013-12-09', 'Zappos Theater');
INSERT INTO AwardCeremony VALUES (3087, '2014-12-10', 'Sven Theater');
INSERT INTO AwardCeremony VALUES (8900, '2019-12-02', 'Nokia Theater Live');
INSERT INTO AwardCeremony VALUES (13423, '2020-12-11', 'The AXIS Theater');
INSERT INTO AwardCeremony VALUES (33683, '2021-12-09', 'Microsoft Theater');
INSERT INTO AwardCeremony VALUES (63683, '2022-12-11', 'Microsoft Theater');

INSERT INTO Staff VALUES ('6132350112', 'Jeffrey Lovegood', 10000, 'Producer');
INSERT INTO Staff VALUES ('6135550192', 'Maria Salt', 11103, 'Presentor');
INSERT INTO Staff VALUES('6223450192', 'Kamila Butter', 12103, 'Presentor');
INSERT INTO Staff VALUES('6225650177', 'John Pink', 13103, 'Presentor');
INSERT INTO Staff VALUES ('6115001232', 'John Pool', 11190, 'Producer Assistant');
INSERT INTO Staff VALUES ('6131110152', 'Tommy Nook', 11111, 'Marketing');
INSERT INTO Staff VALUES ('2388631123', 'Lizzy Fox', 10100, 'Communications Director');
INSERT INTO Staff VALUES ('6115220352', 'Marshal Pepper', 40113, 'Communications Assistant');
INSERT INTO Staff VALUES ('2374442346', 'Kurl Kem', 10235,  'Communications Director');
INSERT INTO Staff VALUES ('4013333452', 'Shamsem Chumlo', 12355, 'Financing Management');
INSERT INTO Staff VALUES ('1232224511', 'Sam Cho', 23122, 'Security and Safety');
INSERT INTO Staff VALUES ('2234422455', 'Arjun Dhaliwal', 12245, 'Internal Jury');
INSERT INTO Staff VALUES ('4122320485', 'Olivia Moon', 12431, 'Internal Jury');
INSERT INTO Staff VALUES ('1344422123', 'Sam Person', 12456, 'Internal Jury');
INSERT INTO Staff VALUES ('1412120435', 'Chris Amongus', 12436, 'Internal Jury');

INSERT INTO Company VALUES ('Red Bull Inc', 'sponsor@redbull.com');
INSERT INTO Company VALUES ('Nintendo of America', 'nintendoSponsor@ntdsponsor.com');
INSERT INTO Company VALUES ('Coca Cola Company', 'cocacolaUSA@communications.sponsor.com');
INSERT INTO Company VALUES ('Sony', 'sony.sponsorships@sony.com');
INSERT INTO Company VALUES ('Snapple Beverages', 'communications@snapple.com');

INSERT INTO Sponsors VALUES ('Red Bull Inc', '2013-12-09', 10000);
INSERT INTO Sponsors VALUES ('Nintendo of America',  '2014-12-10', 5000);
INSERT INTO Sponsors VALUES ('Coca Cola Company','2019-12-02', 5000);
INSERT INTO Sponsors VALUES ('Sony','2020-12-11', 10000);
INSERT INTO Sponsors VALUES ('Snapple Beverages','2021-12-09', 5000);
INSERT INTO Sponsors VALUES ('Sony','2022-12-11', 5000);

INSERT INTO LivestreamUrl VALUES ('https://www.youtube.com/watch?v=jfKfPfyJRk/es', 'English', 'Youtube', '2013-12-09');
INSERT INTO LivestreamUrl VALUES ('https://www.youtube.com/watch?v=sfKfqcvJRdk/fr', 'French', 'Youtube', '2013-12-09');
INSERT INTO LivestreamUrl VALUES ('https://www.youtube.com/watch?v=KfPfyJRdk/en', 'English', 'Youtube', '2014-12-10');
INSERT INTO LivestreamUrl VALUES ('https://www.twitch.tv/GP23/pl', 'Polish', 'Twitch', '2019-12-02');
INSERT INTO LivestreamUrl VALUES ('https://www.twitch.tv/GA19/en', 'English', 'Twitch', '2019-12-02');
INSERT INTO LivestreamUrl VALUES ('https://www.twitch.tv/GS20/sp', 'Spanish', 'Twitch', '2020-12-11');
INSERT INTO LivestreamUrl VALUES ('https://www.twitch.tv/GP20/po', 'Portuguese', 'Twitch', '2020-12-11');
INSERT INTO LivestreamUrl VALUES ('https://www.twitch.tv/GA20/en', 'English', 'Twitch', '2020-12-11');
INSERT INTO LivestreamUrl VALUES ('https://www.twitch.tv/GA21/en', 'English', 'Twitch', '2021-12-09');
INSERT INTO LivestreamUrl VALUES ('https://www.twitch.tv/GA22/en', 'English', 'Twitch', '2022-12-11');
INSERT INTO LivestreamUrl VALUES ('https://www.youtube.com/watch?v=aPfyJRdk/en', 'English', 'Youtube', '2022-12-11');

INSERT INTO LivestreamViewerCount VALUES ('English', 2521, 'Youtube', '2013-12-09');
INSERT INTO LivestreamViewerCount VALUES ('French', 1262, 'Youtube', '2013-12-09');
INSERT INTO LivestreamViewerCount VALUES ('English', 3087, 'Youtube', '2014-12-10');
INSERT INTO LivestreamViewerCount VALUES ('Polish', 1021, 'Twitch', '2019-12-02');
INSERT INTO LivestreamViewerCount VALUES ('English', 2948, 'Twitch', '2019-12-02');
INSERT INTO LivestreamViewerCount VALUES ('Spanish', 1042, 'Twitch', '2020-12-11');
INSERT INTO LivestreamViewerCount VALUES ('Portuguese', 1345, 'Twitch', '2020-12-11');
INSERT INTO LivestreamViewerCount VALUES ('English', 3423, 'Twitch', '2020-12-11');
INSERT INTO LivestreamViewerCount VALUES ('English', 6423, 'Twitch', '2021-12-09');
INSERT INTO LivestreamViewerCount VALUES ('English', 3423, 'Twitch', '2022-12-11');
INSERT INTO LivestreamViewerCount VALUES ('English', 3683, 'Youtube', '2022-12-11');

INSERT INTO DeveloperName VALUES ('Todd Howard', 'https://bethesdagamestudios.com/', 'Bethesda Game Studios');
INSERT INTO DeveloperName VALUES ('Todd Howard', 'https://www.blizzard.com', 'Blizzard Entertainment');
INSERT INTO DeveloperName VALUES ('Hidetaka Miyazaki', 'https://www.fromsoftware.jp/ww/', 'FromSoftware');
INSERT INTO DeveloperName VALUES ('Hideo Kojima','https://kojimaproductions.jp/', 'Kojima Productions');
INSERT INTO DeveloperName VALUES ('Shinya Takahashi', 'https://www.nintendo.com/en-ca/', 'Nintendo EPD');
INSERT INTO DeveloperName VALUES ('Ted Price', 'https://insomniac.games/', 'Insomniac Games');
INSERT INTO DeveloperName VALUES ('Yumi Yang', 'https://sms.playstation.com/', 'Santa Monica Studios');
INSERT INTO DeveloperName VALUES ('Andree Cossette', 'https://quebec.ubisoft.com/en/', 'Ubisoft Quebec');
INSERT INTO DeveloperName VALUES ('Jason Vandenberghe', 'https://montreal.ubisoft.com/en/', 'Ubisoft Montreal');

INSERT INTO VideoGame VALUES ('Skyrim', 2013, 'Action role-playing', 'Bethesda Game Studios');
INSERT INTO VideoGame VALUES ('Elder Scrolls', 2017, 'Action-adventure', 'Bethesda Game Studios');
INSERT INTO VideoGame VALUES ('DOOM Eternal', 2020, 'Action', 'Bethesda Game Studios');
INSERT INTO VideoGame VALUES ('Quake', 2000, 'First-person shooter', 'Bethesda Game Studios');
INSERT INTO VideoGame VALUES ('Sekiro: Shadows Die Twice', 2019, 'Action-adventure', 'FromSoftware');
INSERT INTO VideoGame VALUES ('Death Stranding', 2021, 'Action', 'Kojima Productions');
INSERT INTO VideoGame VALUES ('The Legend of Zelda: Breath of the Wild', 2018, 'Action-adventure', 'Nintendo EPD');
INSERT INTO VideoGame VALUES ('Spider-Man', 2018, 'Action-adventure', 'Insomniac Games');
INSERT INTO VideoGame VALUES ('God of War', 2018, 'Action-adventure', 'Santa Monica Studios');
INSERT INTO VideoGame VALUES ('Assassins Creed Odyssey', 2019, 'Action role-playing', 'Ubisoft Quebec');
INSERT INTO VideoGame VALUES ('Far Cry 5', 2018, 'First-person shooter', 'Ubisoft Montreal');
INSERT INTO VideoGame VALUES ('Far Cry 4', 2014, 'First-person shooter', 'Ubisoft Montreal');
INSERT INTO VideoGame VALUES ('It Takes Two', 2021, 'Action-adventure', 'Insomniac Games');

INSERT INTO VideoGame_DLC VALUES ('Dawnguard', 2014, 'Skyrim', 2013);
INSERT INTO VideoGame_DLC VALUES ('Hearthfire', 2013, 'Skyrim', 2013);
INSERT INTO VideoGame_DLC VALUES ('Dragonborn', 2014, 'Skyrim', 2013);
INSERT INTO VideoGame_DLC VALUES ('The Master Trials', 2019, 'The Legend of Zelda: Breath of the Wild', 2018);
INSERT INTO VideoGame_DLC VALUES ('The Champions Ballad', 2019, 'The Legend of Zelda: Breath of the Wild', 2018);

INSERT INTO Award VALUES ('Best Music', 5000, '2013-12-09', 'Skyrim', 2013);
INSERT INTO Award VALUES ('Best Storyline', 3000,  '2014-12-10', 'Far Cry 4', 2014);
INSERT INTO Award VALUES ('Best Action', 3000, '2020-12-11', 'Sekiro: Shadows Die Twice', 2019);
INSERT INTO Award VALUES ('Best Art Direction', 1000, '2021-12-09', 'Death Stranding', 2021);
INSERT INTO Award VALUES ('Game of the Year', 5000, '2019-12-02', 'The Legend of Zelda: Breath of the Wild', 2018);
INSERT INTO Award VALUES ('Best Design', 1000, '2019-12-02', 'Spider-Man', 2018);
INSERT INTO Award VALUES ('Best Debut', 1000, '2020-12-11', 'God of War', 2018);
INSERT INTO Award VALUES ('Social Impact Award', 1000, '2019-12-02', 'Spider-Man', 2018);
INSERT INTO Award VALUES ('Best Technology', 5000, '2022-12-11', 'It Takes Two', 2021);
INSERT INTO Award VALUES ('Best Food', 1000, '2020-12-11', 'Assassins Creed Odyssey', 2019);
INSERT INTO Award VALUES ('Best Animation', 5000, '2019-12-02', 'Far Cry 5', 2018);
INSERT INTO Award VALUES ('Best Innovation', 5000, '2019-12-02', 'The Legend of Zelda: Breath of the Wild', 2018);

INSERT INTO CommunityAward VALUES (2393, '2020-03-23', 'Best Action','2020-12-11');
INSERT INTO CommunityAward VALUES (3002, '2020-03-23', 'Best Debut','2020-12-11');
INSERT INTO CommunityAward VALUES (3609, '2019-03-29', 'Social Impact Award','2019-12-02');
INSERT INTO CommunityAward VALUES (2093, '2014-03-22', 'Best Storyline', '2014-12-10');
INSERT INTO CommunityAward VALUES (5403, '2019-03-27', 'Best Design','2019-12-02');

INSERT INTO SponsoredAward VALUES ('Bose', 'Best Music', '2013-12-09');
INSERT INTO SponsoredAward VALUES ('Microsoft', 'Best Technology', '2022-12-11');
INSERT INTO SponsoredAward VALUES ('McDonalds', 'Best Food', '2020-12-11');
INSERT INTO SponsoredAward VALUES ('Adobe', 'Best Animation', '2019-12-02');
INSERT INTO SponsoredAward VALUES ('Apple', 'Best Innovation', '2019-12-02');

INSERT INTO DeveloperCountry VALUES ('Bethesda Game Studios', 'USA');
INSERT INTO DeveloperCountry VALUES ('FromSoftware', 'Japan');
INSERT INTO DeveloperCountry VALUES ('Kojima Productions', 'Japan');
INSERT INTO DeveloperCountry VALUES ('Nintendo EPD', 'Japan');
INSERT INTO DeveloperCountry VALUES ('Insomniac Games', 'Canada');
INSERT INTO DeveloperCountry VALUES ('Santa Monica Studios', 'China');
INSERT INTO DeveloperCountry VALUES ('Ubisoft Quebec', 'France');
INSERT INTO DeveloperCountry VALUES ('Ubisoft Montreal', 'Germany');

INSERT INTO Staff_AwardCeremony VALUES (10000,'2013-12-09');
INSERT INTO Staff_AwardCeremony VALUES (10000, '2014-12-10');
INSERT INTO Staff_AwardCeremony VALUES (10000,'2019-12-02');
INSERT INTO Staff_AwardCeremony VALUES (10000,'2020-12-11');
INSERT INTO Staff_AwardCeremony VALUES (10000,'2021-12-09');
INSERT INTO Staff_AwardCeremony VALUES (10000,'2022-12-11');
INSERT INTO Staff_AwardCeremony VALUES (11103,'2013-12-09');
INSERT INTO Staff_AwardCeremony VALUES (11103, '2014-12-10');
INSERT INTO Staff_AwardCeremony VALUES (11103,'2019-12-02');
INSERT INTO Staff_AwardCeremony VALUES (11103,'2020-12-11');
INSERT INTO Staff_AwardCeremony VALUES (11103,'2021-12-09');
INSERT INTO Staff_AwardCeremony VALUES (11103,'2022-12-11');
INSERT INTO Staff_AwardCeremony VALUES (12103,'2013-12-09');
INSERT INTO Staff_AwardCeremony VALUES (12103, '2014-12-10');
INSERT INTO Staff_AwardCeremony VALUES (12103,'2019-12-02');
INSERT INTO Staff_AwardCeremony VALUES (12103,'2020-12-11');
INSERT INTO Staff_AwardCeremony VALUES (12103,'2021-12-09');
INSERT INTO Staff_AwardCeremony VALUES (12103,'2022-12-11');
INSERT INTO Staff_AwardCeremony VALUES (13103,'2020-12-11');
INSERT INTO Staff_AwardCeremony VALUES (13103,'2021-12-09');
INSERT INTO Staff_AwardCeremony VALUES (13103,'2022-12-11');
INSERT INTO Staff_AwardCeremony VALUES (11190,'2021-12-09');
INSERT INTO Staff_AwardCeremony VALUES (11111,'2020-12-11');
INSERT INTO Staff_AwardCeremony VALUES (11111,'2021-12-09');
INSERT INTO Staff_AwardCeremony VALUES (10100,'2013-12-09');
INSERT INTO Staff_AwardCeremony VALUES (10100, '2014-12-10');
INSERT INTO Staff_AwardCeremony VALUES (10100,'2019-12-02');
INSERT INTO Staff_AwardCeremony VALUES (40113,'2021-12-09');
INSERT INTO Staff_AwardCeremony VALUES (40113,'2022-12-11');
INSERT INTO Staff_AwardCeremony VALUES (10235, '2014-12-10');
INSERT INTO Staff_AwardCeremony VALUES (12355, '2022-12-11');
INSERT INTO Staff_AwardCeremony VALUES (23122, '2022-12-11');
INSERT INTO Staff_AwardCeremony VALUES (12245, '2022-12-11');
INSERT INTO Staff_AwardCeremony VALUES (12431, '2022-12-11');
INSERT INTO Staff_AwardCeremony VALUES (12456, '2022-12-11');
INSERT INTO Staff_AwardCeremony VALUES (12436, '2022-12-11');

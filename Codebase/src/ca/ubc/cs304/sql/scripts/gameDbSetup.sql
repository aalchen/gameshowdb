CREATE TABLE Venue(
                      name VARCHAR(50) PRIMARY KEY,
                      address VARCHAR(50),
                      capacity INTEGER
);

CREATE TABLE AwardCeremony(
                              viewer_count INTEGER,
                              date Date PRIMARY KEY,
                              venue_name VARCHAR(50),
                              FOREIGN KEY (venue_name) REFERENCES Venue(name)
                                  ON DELETE SET NULL
                                  ON UPDATE CASCADE
);

CREATE TABLE Staff(
                      phone_number INTEGER UNIQUE,
                      name VARCHAR(50),
                      id INTEGER PRIMARY KEY,
                      role VARCHAR(50)
);

CREATE TABLE Company(
                        name VARCHAR(50) PRIMARY KEY,
                        contact_info VARCHAR(100)
);

CREATE TABLE VideoGame_DLC(
                              title CHAR(50),
                              year INTEGER,
                              videogame_title CHAR(50),
                              videogame_year INTEGER,
                              PRIMARY KEY (title, year, videogame_title, videogame_year),
                              FOREIGN KEY (videogame_title, videogame_year) REFERENCES VideoGame(title, year)
                                  ON DELETE NO ACTION
                                  ON UPDATE CASCADE
);

CREATE TABLE VideoGame(
                          title VARCHAR(128),
                          year INTEGER,
                          genre VARCHAR(50),
                          developer_name VARCHAR(20),
                          PRIMARY KEY (title, year),
                          FOREIGN KEY (developer_name) REFERENCES Developer(Name)
                              ON DELETE SET NULL
                              ON UPDATE CASCADE
);

CREATE TABLE LivestreamViewerCount(
                                      language VARCHAR(50),
                                      viewer_count INTEGER,
                                      name VARCHAR(50),
                                      awardceremony_date Date,
                                      PRIMARY KEY (language, awardceremony_date, name),
                                      FOREIGN KEY (awardceremony_date) REFERENCES AwardCeremony(date)
                                          ON DELETE SET NULL
                                          ON UPDATE CASCADE,
                                      FOREIGN KEY (name) REFERENCES LivestreamUrl(name)
                                          ON DELETE CASCADE
                                          ON UPDATE CASCADE,
                                      FOREIGN KEY (language) REFERENCES LivestreamUrl(language)
                                          ON DELETE CASCADE
                                          ON UPDATE CASCADE
);

CREATE TABLE LivestreamUrl(
                              url VARCHAR(100) PRIMARY KEY,
                              language VARCHAR(50),
                              name VARCHAR(50),
                              awardceremony_date Date,
                              FOREIGN KEY (awardceremony_date) REFERENCES AwardCeremony(Date)
                                  ON DELETE SET NULL
                                  ON UPDATE CASCADE
);

CREATE TABLE Award(
                      name VARCHAR(50),
                      prize INTEGER,
                      date Date,
                      videogame_title VARCHAR(50),
                      videogame_year VARCHAR(50),
                      PRIMARY KEY (date, name),
                      FOREIGN KEY(date) REFERENCES AwardCeremony(date)
                          ON DELETE CASCADE
                          ON UPDATE CASCADE,
                      FOREIGN KEY(videogame_title, videogame_year) REFERENCES VideoGame(Title, Year)
                          ON DELETE NO ACTION
                          ON UPDATE CASCADE
);

CREATE TABLE CommunityAward(
                               votes INTEGER,
                               deadline Date,
                               award_name VARCHAR(50),
                               award_date Date,
                               PRIMARY KEY (award_name, award_date),
                               FOREIGN KEY (award_name, award_date) REFERENCES Award(name, date)
                                   ON DELETE CASCADE
                                   ON UPDATE CASCADE
);

CREATE TABLE SponsoredAward(
                               sponsor VARCHAR(50),
                               award_name VARCHAR(50),
                               award_date Date,
                               PRIMARY KEY (award_name, award_date),
                               FOREIGN KEY (award_name, award_date) REFERENCES Award(name, date)
                                   ON DELETE CASCADE
                                   ON UPDATE CASCADE
);

CREATE TABLE DeveloperCountry(
                                 lead_developer VARCHAR(50) PRIMARY KEY,
                                 country VARCHAR(50),
                                 FOREIGN KEY (lead_developer) REFERENCES DeveloperName(lead_developer)
                                     ON DELETE CASCADE
                                     ON UPDATE CASCADE
);

CREATE TABLE DeveloperName(
                              lead_developer VARCHAR(50),
                              website VARCHAR(50),
                              name VARCHAR(50) PRIMARY KEY
);

CREATE TABLE Staff_AwardCeremony(
                                    staff_id INTEGER,
                                    awardceremony_date Date,
                                    PRIMARY KEY(staff_id, awardceremony_date),
                                    FOREIGN KEY(staff_id) REFERENCES Staff(id),
                                    FOREIGN KEY(awardceremony_date) REFERENCES AwardCeremony(date)
);

CREATE TABLE Sponsors(
                         company_name VARCHAR(50),
                         awardceremony_date Date,
                         money INTEGER,
                         PRIMARY KEY(company_name, awardceremony_date),
                         FOREIGN KEY(company_name) REFERENCES Company(company_name),
                         FOREIGN KEY(awardceremony_date) REFERENCES AwardCeremony(date)
);

INSERT INTO Venue(name, address, capacity)
VALUES
    ('Microsoft Theater', '777 Chick Hearn Ct Los Angeles California USA', 7100),
    ('Zappos Theater', '3667 S Las Vegas Blvd Las Vegas USA', 7000),
    ('Nokia Theater Live', '230 Bird Barn Ct Los Angeles California', 3500),
    ('Sven Theater', '3101 Sizzlin Road  Los Angeles California', 7000),
    ('The AXIS Theater', '3330 Palm Avenue Los Angeles California', 7100);

INSERT INTO AwardCeremony(viewer_count, date, venue_name)
VALUES
    (2601, '2013-12-09', 'Zappos Theater'),
    (3087, '2014-12-10', 'Sven Theater'),
    (8900, '2019-12-02', 'Nokia Theater. Live'),
    (13423, '2020-12-11', 'The Axis Theater'),
    (33683, '2021-12-09', 'Microsoft Theater'),
    (63683, '2022-12-11', 'Microsoft Theater');

INSERT INTO Staff(phone_number, name, id, role)
VALUES
    (6132350112, 'Jeffrey Lovegood', 10000, 'Producer'),
    (6135550192, 'Maria Salt', 11103, 'Presentor'),
    (6223450192, 'Kamila Butter', 12103, 'Presentor'),
    (6225650177, 'John Pink', 13103, 'Presentor'),
    (6115001232, 'John Pool', 11190, 'Producer Assistant'),
    (6131110152, 'Tommy Nook', 11111, 'Marketing'),
    (2388631123, 'Lizzy Fox', 10100, 'Communications Director'),
    (6115220352, 'Marshal Pepper', 40113, 'Communications Assistant'),
    (2374442346, 'Kurl Kem', 10235,  'Communications Director'),
    (4013333452, 'Shamsem Chumlo', 12355, 'Financing Management'),
    (1232224511, 'Sam Cho', 23122, 'Security and Safety'),
    (2234422455, 'Arjun Dhaliwal', 12245, 'Internal Jury'),
    (4122320485, 'Olivia Moon', 12431, 'Internal Jury'),
    (1344422123, 'Sam Person', 12456, 'Internal Jury'),
    (141-212-0435, 'Chris Amongus', 12436, 'Internal Jury');

INSERT INTO Company(name, contact_info)
VALUES
    ('Red Bull Inc', 'sponsor@redbull.com'),
    ('Nintendo of America', 'nintendoSponsor@ntdsponsor.com'),
    ('Coca Cola Company', 'cocacolaUSA@communications.sponsor.com'),
    ('Sony', 'sony.sponsorships@sony.com'),
    ('Snapple Beverages', 'communications@snapple.com');

INSERT INTO Sponsors(company_name, awardceremony_date, money)
VALUES
    ('Red Bull Inc', '2013-12-09', 10000),
    ('Nintendo of America',  '2014-12-10', 5000),
    ('Coca Cola Company','2019-12-02', 5000),
    ('Sony','2020-12-11', 10000),
    ('Snapple Beverages','2021-12-09', 5000),
    ('Sony','2022-12-11', 5000);

INSERT INTO LivestreamViewerCount(language, viewer_count, name, awardceremony_date)
VALUES
    ('English', 2521, 'Youtube', 2013),
    ('French', 1262, 'Youtube', 2013),
    ('English', 3087, 'Youtube', 2014),
    ('Polish', 1021, 'Twitch', 2019),
    ('English', 2948, 'Twitch', 2019),
    ('Spanish', 1042, 'Twitch', 2020),
    ('Portuguese', 1345, 'Twitch', 2020),
    ('English', 3423, 'Twitch', 2020),
    ('English', 6423, 'Twitch', 2021),
    ('English', 3423, 'Twitch', 2022),
    ('English', 3683, 'Youtube', 2022);

INSERT INTO LivestreamUrl(url, language, name, awardceremony_date)
VALUES
    ('https://www.youtube.com/watch?v=jfKfPfyJRk/es', 'English', 'Youtube', 2013),
    ('https://www.youtube.com/watch?v=sfKfqcvJRdk/fr', 'French', 'Youtube', 2013),
    ('https://www.youtube.com/watch?v=KfPfyJRdk/en', 'English', 'Youtube', 2014),
    ('https://www.twitch.tv/GP23/pl', 'Polish', 'Twitch', 2019),
    ('https://www.twitch.tv/GA19/en', 'English', 'Twitch', 2019),
    ('https://www.twitch.tv/GS20/sp', 'Spanish', 'Twitch', 2020),
    ('https://www.twitch.tv/GP20/po', 'Portuguese', 'Twitch', 2020),
    ('https://www.twitch.tv/GA20/en', 'English', 'Twitch', 2020),
    ('https://www.twitch.tv/GA21/en', 'English', 'Twitch', 2021),
    ('https://www.twitch.tv/GA22/en', 'English', 'Twitch', 2022),
    ('https://www.youtube.com/watch?v=aPfyJRdk/en', 'English', 'Youtube', 2022);

INSERT INTO Award(name, prize, date, videogame_title, videogame_year)
VALUES
    ('Best Music', 5000, '2013-12-09', 'Skyrim', 2013),
    ('Best Storyline', 3000,  '2014-12-10', 'Far Cry 4', 2014),
    ('Best Action', 3000, '2020-12-11', 'Sekiro', 2019),
    ('Best Art Direction', 1000, '2021-12-09', 'Death Stranding', 2021),
    ('Game of the Year', 5000, '2019-12-02', 'The Legend of Zelda: Breath of the Wild', 2018),
    ('Best Design', 1000, '2019-12-02', 'Spider-Man', 2018),
    ('Best Debut', 1000, '2020-12-11', 'God of War', 2018),
    ('Social Impact Award', 1000, '2019-12-02', 'Spider-Man', 2018),
    ('Best Technology', 5000, '2022-12-11', 'It Takes Two', 2021),
    ('Best Food', 1000, '2020-12-11', 'Assassins Creed Odyssey', 2019),
    ('Best Animation', 5000, '2019-12-02', 'Far Cry 5', 2018),
    ('Best Innovation', 5000, '2019-12-02', 'The Legend of Zelda: Breath of the Wild', 2018);

INSERT INTO CommunityAward(votes, deadline, award_name, award_date)
VALUES
    (2393, 2020-03-23, 'Best Action','2020-12-11'),
    (3002, 2020-03-23, 'Best Debut','2020-12-11'),
    (3609, 2019-03-29, 'Social Impact Award','2019-12-02'),
    (2093, 2014-03-22, 'Best Storyline', '2014-12-10'),
    (5403, 2019-03-27, 'Best Design','2019-12-02');

INSERT INTO SponsoredAward(sponsor, award_name, award_date)
VALUES
    ('Bose', 'Best Music', '2013-12-09'),
    ('Microsoft', 'Best Technology', '2022-12-11'),
    ('McDonalds', 'Best Food', '2020-12-11'),
    ('Adobe', 'Best Animation', '2019-12-02'),
    ('Apple', 'Best Innovation', '2019-12-02');

INSERT INTO VideoGame_DLC(title, year, videogame_title, videogame_year)
VALUES
    ('Dawnguard', 2014, 'Skyrim', 2013),
    ('Hearthfire', 2013, 'Skyrim', 2013),
    ('Dragonborn', 2014, 'Skyrim', 2013),
    ('The Master Trials', 2019, 'The Legend of Zelda: Breath of the Wild', 2018),
    ('The Champions Ballad', 2019, 'The Legend of Zelda: Breath of the Wild', 2018);

INSERT INTO VideoGame(title, year, genre, developer_name)
VALUES
    ('Skyrim', 2013, 'Action role-playing', 'Bethesda Game Studios'),
    ('Sekiro: Shadows Die Twice', 2019, 'Action-adventure', 'FromSoftware'),
    ('Death Stranding', 2021, 'Action', 'Kojima Productions'),
    ('The Legend of Zelda: Breath of the Wild', 2018, 'Action-adventure', 'Nintendo EPD'),
    ('Spider-Man', 2018, 'Action-adventure', 'Insomniac Games'),
    ('God of War', 2018, 'Action-adventure', 'Santa Monica Studio'),
    ('Assassins Creed Odyssey', 2019, 'Action role-playing', 'Ubisoft Quebec'),
    ('Far Cry 5', 2018, 'First-person shooter', 'Ubisoft Montreal'),
    ('Far Cry 4', 2014, 'First-person shooter', 'Ubisoft Montreal'),
    ('It Takes Two', 2021, 'Action-adventure', 'Insomniac Games');

INSERT INTO Staff_AwardCeremony(staff_id, awardceremony_date)
VALUES
    (10000,'2013-12-09'),
    (10000, '2014-12-10'),
    (10000,'2019-12-02'),
    (10000,'2020-12-11'),
    (10000,'2021-12-09'),
    (10000,'2022-12-11'),
    (11103,'2013-12-09'),
    (11103, '2014-12-10'),
    (11103,'2019-12-02'),
    (11103,'2020-12-11'),
    (11103,'2021-12-09'),
    (11103,'2022-12-11'),
    (12103,'2013-12-09'),
    (12103, '2014-12-10'),
    (12103,'2019-12-02'),
    (12103,'2020-12-11'),
    (12103,'2021-12-09'),
    (12103,'2022-12-11'),
    (13103,'2020-12-11'),
    (13103,'2021-12-09'),
    (13103,'2022-12-11'),
    (11190,'2021-12-09'),
    (11111,'2020-12-11'),
    (11111,'2021-12-09'),
    (10100,'2013-12-09'),
    (10100, '2014-12-10'),
    (10100,'2019-12-02'),
    (40113,'2021-12-09'),
    (40113,'2022-12-11'),
    (10235, '2014-12-10'),
    (12355, '2022-12-11'),
    (23122, '2022-12-11'),
    (12245, '2022-12-11'),
    (12431, '2022-12-11'),
    (12456, '2022-12-11'),
    (12436, '2022-12-11');


INSERT INTO DeveloperCountry(lead_developer, country)
VALUES
    ('Todd Howard', 'USA'),
    ('Hidetaka Miyazaki', 'Japan'),
    ('Hideo Kojima', 'Japan'),
    ('Shinya Takahashi', 'Japan'),
    ('Ted Price', 'Canada'),
    ('Yumi Yang', 'China'),
    ('Andree Cossette', 'France'),
    ('Jason Vandenberghe', 'Germany');

INSERT INTO DeveloperName(lead_developer, website, name)
VALUES
    ('Todd Howard', 'https://bethesdagamestudios.com/', 'Bethesda Game Studio'),
    ('Hidetaka Miyazaki', 'https://www.fromsoftware.jp/ww/', 'FromSoftware'),
    ('Hideo Kojima','https://kojimaproductions.jp/', 'Kojima Productions'),
    ('Shinya Takahashi', 'https://www.nintendo.com/en-ca/', 'Nintendo EPD'),
    ('Ted Price', 'https://insomniac.games/', 'Insomniac Games'),
    ('Yumi Yang', 'https://sms.playstation.com/', 'Santa Monica Studios'),
    ('Andree Cossette', 'https://quebec.ubisoft.com/en/', 'Ubisoft Quebec'),
    ('Jason Vandenberghe', 'https://montreal.ubisoft.com/en/', 'Ubisoft Montreal');


CREATE TABLE Country (
    countryId SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);


CREATE TABLE District (
    districtId SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    countryId INT REFERENCES Country(countryId)
);


CREATE TABLE Location (
    locationId SERIAL PRIMARY KEY,
    address VARCHAR(100) NOT NULL,
    districtId INT REFERENCES District(districtId),
    county VARCHAR(100) NOT NULL,
    postalCode VARCHAR(20) NOT NULL,
    latitude DECIMAL(9,6),
    longitude DECIMAL(9,6)
);


CREATE TABLE Owner (
    ownerId SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(20),
    type VARCHAR(50), -- Individual, Empresa, etc.
    numberOfLocations INT
);

CREATE TABLE Club(
    clubId SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    sports VARCHAR(100) CHECK (sports IN ('Tennis', 'Padel', 'Both')),
    nrOfCourts INT,
    locationId INT REFERENCES Location(locationId),
    ownerId INT REFERENCES Owner(ownerId)
);


CREATE TABLE Court (
    courtId SERIAL PRIMARY KEY,
    name VARCHAR(100),
    clubId INT REFERENCES Club(clubId),
    type VARCHAR(20) CHECK (type IN ('Tennis', 'Padel')),
    surfaceType VARCHAR(50),
    capacity INT,
    pricePerHour DECIMAL(6,2)
);

--CREATE TABLE Court_Price (
--    courtId INT REFERENCES Court(courtId),
--    startTime TIME,
--    endTime TIME,
--    pricePerHour DECIMAL(6,2),
--    PRIMARY KEY (courtId, startTime, endTime)
--);


CREATE TABLE Player (
    playerId SERIAL PRIMARY KEY,
    name VARCHAR(100),
    birthDate TIMESTAMP,
    gender VARCHAR(10),
    email VARCHAR(100) NOT NULL,
    countryId VARCHAR(10), -- exemplo +351
    weight DECIMAL(5,2),
    height DECIMAL(5,2),
    photo VARCHAR(255),
    lastMatchResult VARCHAR(10),
    plays VARCHAR(50),
    phone VARCHAR(20),
    last10Record VARCHAR(50)
);


CREATE TABLE Reservation (
    reservationId SERIAL PRIMARY KEY,
    courtId INT REFERENCES Court(courtId),
    createdByPlayerId INT REFERENCES Player(playerId), -- jogador que fez a reserva
    startTime TIMESTAMP NOT NULL,
    endTime TIMESTAMP NOT NULL,
    estimatedPrice DECIMAL(8,2),
    status VARCHAR(20) CHECK (status IN ('Pending', 'Confirmed', 'Cancelled'))
);

CREATE TABLE Player_Reservation (
    reservationId INT REFERENCES Reservation(reservationId),
    playerId INT REFERENCES Player(playerId),
    status VARCHAR(20), -- Ex: Confirmado, Convidado, etc.
    PRIMARY KEY (reservationId, playerId)
);

CREATE TABLE WeeklySchedule (
    scheduleId SERIAL PRIMARY KEY,
    courtId INT REFERENCES Court(courtId),
    dayOfWeek VARCHAR(50) NOT NULL,
    startTime TIMESTAMP,
    endTime TIMESTAMP
);

CREATE TABLE SpecialSchedule (
    specialId SERIAL PRIMARY KEY,
    courtId INT REFERENCES Court(courtId),
    date DATE NOT NULL, -- por o dia em especifico
    startTime TIMESTAMP,
    endTime TIMESTAMP,
    working BOOLEAN NOT NULL -- true = aberto nesse dia, false = fechado (feriado)
);


CREATE TABLE Tournament (
    tournamentId SERIAL PRIMARY KEY,
    name VARCHAR(100),
    courtId INT REFERENCES Court(courtId),
    startDate DATE,
    endDate DATE,
    category VARCHAR(50),
    surfaceType VARCHAR(50),
    prizeMoney DECIMAL(10,2),
    playerTotal INT,
    over BOOLEAN,
    winnerId INT REFERENCES Player(playerId)
);

CREATE TABLE Player_Tournament (
    playerTournamentId SERIAL PRIMARY KEY,
    tournamentId INT REFERENCES Tournament(tournamentId),
    playerId INT REFERENCES Player(playerId),
    status VARCHAR(50),
    seed INT
);


CREATE TABLE Match (
    matchId SERIAL PRIMARY KEY,
    player1Id INT REFERENCES Player(playerId),
    player2Id INT REFERENCES Player(playerId),
    tournamentId INT REFERENCES Tournament(tournamentId),
    locationId INT REFERENCES Location(locationId),
    startDatetime TIMESTAMP,
    round VARCHAR(50)
);


CREATE TABLE Match_Sets (
    matchSetId SERIAL PRIMARY KEY,
    matchId INT REFERENCES Match(matchId),
    gamesWonPlayer1 INT,
    gamesWonPlayer2 INT,
    setNumber INT,
    over BOOLEAN
);


-- País
INSERT INTO Country (countryId, name) VALUES (1000, 'Portugal');

-- Distrito
INSERT INTO District (districtId, name, countryId) VALUES (1000, 'Lisboa', 1000);

-- Localização
INSERT INTO Location (locationId, address, districtId, county, postalCode, latitude, longitude)
VALUES (1000, 'Rua Central 123', 1000, 'Lisboa', '1000-001', 38.7169, -9.1399);

-- Dono
INSERT INTO Owner (ownerId, name, email, phone, type, numberOfLocations)
VALUES (1000, 'João Silva', 'joao@example.com', '912345678', 'Individual', 1);

-- Clube
INSERT INTO Club (clubId, name, sports, nrOfCourts, locationId, ownerId)
VALUES (1000, 'Padel Clube Lisboa', 'Padel', 3, 1000, 1000);

-- Campo
INSERT INTO Court (courtId, name, clubId, type, surfaceType, capacity, pricePerHour)
VALUES (1000, 'Court A', 1000, 'Padel', 'Sintético', 4, 20.00);

-- Jogador
INSERT INTO Player (playerId, name, birthDate, gender, email, countryId, weight, height, photo, lastMatchResult, plays, phone, last10Record)
VALUES (1000, 'Ana Costa', '1995-06-15', 'Feminino', 'ana@example.com', '+351', 60.5, 1.70, 'ana.jpg', 'Win', 'Right-handed', '911223344', 'W-W-L-W-W-L-W-W-W-L');

-- Reserva
INSERT INTO Reservation (reservationId, courtId, createdByPlayerId, startTime, endTime, estimatedPrice, status)
VALUES (1000, 1000, 1000, '2025-07-11 10:00:00', '2025-07-11 11:00:00', 20.00, 'Confirmed');

-- Participação na reserva
INSERT INTO Player_Reservation (reservationId, playerId, status)
VALUES (1000, 1000, 'Confirmado');

-- Horário semanal
INSERT INTO WeeklySchedule (scheduleId, courtId, dayOfWeek, startTime, endTime)
VALUES (1000, 1000, 'Monday', '2025-07-14 09:00:00', '2025-07-14 21:00:00');

-- Horário especial
INSERT INTO SpecialSchedule (specialId, courtId, date, startTime, endTime, working)
VALUES (1000, 1000, '2025-12-25', '2025-12-25 00:00:00', '2025-12-25 23:59:59', false);

-- Torneio
INSERT INTO Tournament (tournamentId, name, courtId, startDate, endDate, category, surfaceType, prizeMoney, playerTotal, over, winnerId)
VALUES (1000, 'Open Lisboa', 1000, '2025-07-20', '2025-07-25', 'Pro', 'Sintético', 1000.00, 8, false, 1000);

-- Participação no torneio
INSERT INTO Player_Tournament (playerTournamentId, tournamentId, playerId, status, seed)
VALUES (1000, 1000, 1000, 'Ativo', 1);



export const fakeClubs = [
  {
    clubId: 1,
    name: "Clube Padel Lisboa",
    sport: "PADEL",
    numCourts: 3,
    locationId: 1,
  },
  {
    clubId: 2,
    name: "Ténis Porto Central",
    sport: "TENNIS",
    numCourts: 5,
    locationId: 2,
  },
];

export const fakeCourts = [
  {
    courtId: 1,
    clubId: 1,
    name: "Court 1",
    type: "Tennis",
    surfaceType: "terra batida",
    capacity: 4,
    pricePerHour: 20,
  },
  {
    courtId: 2,
    clubId: 1,
    name: "Court 2",
    type: "Padel",
    capacity: 4,
    pricePerHour: 20,
  },
  {
    courtId: 3,
    clubId: 2,
    name: "Court A",
    type: "Tennis",
    surfaceType: "relva",
    capacity: 2,
    pricePerHour: 15,
  },
];

export const fakeLocations = [
  {
    locationId: 1,
    address: "Rua das Flores, 123",
    county: "Lisboa",
    district: "Lisboa",
    postalCode: "1000-001",
  },
  {
    locationId: 2,
    address: "Avenida do Futebol, 45",
    county: "Porto",
    district: "Porto",
    postalCode: "4000-123",
  },
];

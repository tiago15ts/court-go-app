
// mockDatabase.ts (simples mock in-memory)



export const fakeClubs = [
  {
    clubId: 1,
    name: "Beloura Tennis Academy",
    sport: "Tennis",
    numCourts: 4,
    locationId: 1,
  },
  {
    clubId: 2,
    name: "Lisboa Rackets",
    sport: "Padel",
    numCourts: 4,
    locationId: 2,
  },
  {
    clubId: 3,
    name: "Porto Club Padel",
    sport: "Padel",
    numCourts: 6,
    locationId: 3,
  },
  {
    clubId: 4,
    name: "Estoril Country Club",
    sport: "Tennis",
    numCourts: 4,
    locationId: 4,
  },
  {
    clubId: 5,
    name: "Braga Tennis Club",
    sport: "Tennis",
    numCourts: 4,
    locationId: 5,
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
    pricePerHour: 15,
  },
  {
    courtId: 2,
    clubId: 4,
    name: "Court 2",
    type: "Relva",
    capacity: 6,
    pricePerHour: 20,
  },
  {
    courtId: 3,
    clubId: 2,
    name: "Court 3",
    type: "Padel",
    surfaceType: "",
    capacity: 4,
    pricePerHour: 10,
  },
  {
    courtId: 4,
    clubId: 1,
    name: "Court 2",
    type: "Tennis",
    surfaceType: "Terra Batida",
    capacity: 4,
    pricePerHour: 15,
  },
  {
    courtId: 5,
    clubId: 5,
    name: "Court 5",
    type: "Tennis",
    surfaceType: "relva",
    capacity: 2,
    pricePerHour: 18,
  }
];

export const fakeLocations = [
  {
    locationId: 1,
    address: "Rua do Campo 123",
    county: "Sintra",
    district: "Lisboa",
    postalCode: "2710-123",
  },
  {
    locationId: 2,
    address: "Avenida da Liberdade 456",
    county: "Lisboa",
    district: "Lisboa",
    postalCode: "1250-456",
  },
  {
    locationId: 3,
    address: "Rua de Santa Catarina 789",
    county: "Porto",
    district: "Porto",
    postalCode: "4000-789",
  },
  {
    locationId: 4,
    address: "Avenida Marginal 101",
    county: "Cascais",
    district: "Lisboa",
    postalCode: "2765-101",
  },
  {
    locationId: 5,
    address: "Rua do Comércio 321",
    county: "Braga",
    district: "Braga",
    postalCode: "4700-321",
  },
];

export const fakeSchedules = [
  {
    scheduleId: 1,
    clubId: 1,
    dayOfWeek: "MONDAY",
    startTime: "09:00",
    endTime: "18:00",
  },
  {
    scheduleId: 2,
    clubId: 1,
    dayOfWeek: "TUESDAY",
    startTime: "09:00",
    endTime: "18:00",
  },
  {
    scheduleId: 3,
    clubId: 1,
    dayOfWeek: "WEDNESDAY",
    startTime: "09:00",
    endTime: "18:00",
  },
  
];


export const api = new sst.aws.ApiGatewayV2("CourtAndGoAPI");

// === UserService ===
api.route("POST /user/register", { handler: "functions/user/register.handler" });
api.route("POST /user/login", { handler: "functions/user/login.handler" });
api.route("POST /user/logout", { handler: "functions/user/logout.handler" });
api.route("GET /user/{id}", { handler: "functions/user/getById.handler" });
api.route("GET /user/email/{email}", { handler: "functions/user/getByEmail.handler" });
api.route("PUT /user", { handler: "functions/user/update.handler" });
api.route("POST /user/oauth-register", { handler: "functions/user/oauth-register.handler" });

// === ScheduleCourtsService ===
api.route("GET /schedule/weekly/{courtId}", { handler: "functions/schedule/weekly.handler" });
api.route("GET /schedule/special/{courtId}", { handler: "functions/schedule/special.handler" });
api.route("POST /schedule/weekly", {
  handler: "functions/schedule/createWeekly.handler",
});
api.route("POST /schedule/special", {
  handler: "functions/schedule/createSpecial.handler",
});

// === ReservationService ===
api.route("GET /reservations", { handler: "functions/reservation/getAll.handler" });
api.route("GET /reservations/{id}", { handler: "functions/reservation/getById.handler" });
api.route("GET /reservations/player/{playerId}", { handler: "functions/reservation/getByPlayer.handler" });
api.route("POST /reservations", { handler: "functions/reservation/create.handler" }); // Create a new reservation
api.route("PUT /reservations/{id}", { handler: "functions/reservation/update.handler" }); // Update reservation by ID
api.route("DELETE /reservations/cancel/{id}", { handler: "functions/reservation/delete.handler" }); // Cancel reservation by ID
api.route("POST /reservations/confirm/{id}", { handler: "functions/reservation/confirm.handler" }); // Confirm reservation by ID
api.route("GET /reservations/filter", { //confirmar se esta de acordo
  handler: "functions/reservation/getByCourtIdsAndDate.handler",
});
//acho que falta uma rota

api.route("GET /reservations/{id}/ics", {
  handler: "functions/reservation/getICS.handler"
});


// === CourtService ===
api.route("GET /courts", { handler: "functions/court/all.handler" });
api.route("GET /courts/district/{district}", { handler: "functions/court/byDistrict.handler" });
api.route("GET /courts/sport/{sport}", { handler: "functions/court/bySportType.handler" }); // Get courts by sport type
api.route("GET /courts/filter", { handler: "functions/court/getFiltered.handler" });
api.route("GET /courts/{id}", { handler: "functions/court/getById.handler" }); // Get court by ID
api.route("GET /courts/owner/{ownerId}", { handler: "functions/court/byOwner.handler" });
api.route("POST /courts", { handler: "functions/court/create.handler" }); // Create a new court
api.route("PUT /courts{id}", { handler: "functions/court/update.handler" }); // Update court details
api.route("DELETE /courts/{id}", { handler: "functions/court/delete.handler" });
api.route("GET /courts/club/{clubID}", { handler: "functions/court/byClubId.handler" }); //getCourtsByClubId


// === ClubService ===
api.route("GET /clubs", { handler: "functions/club/getAll.handler" });
api.route("GET /clubs/district/{district}", { handler: "functions/club/getByDistrict.handler" });
api.route("GET /clubs/county/{county}", { handler: "functions/club/getByCounty.handler" });
api.route("GET /clubs/country/{country}", { handler: "functions/club/getByCountry.handler" });
api.route("GET /clubs/postal/{postalCode}", { handler: "functions/club/getByPostal.handler" });
api.route("GET /clubs/name/{name}", { handler: "functions/club/getByName.handler" });
api.route("GET /clubs/sport/{sport}", { handler: "functions/club/getBySport.handler" });
api.route("GET /clubs/{id}", { handler: "functions/club/getById.handler" }); // Get club by ID
api.route("GET /clubs/owner/{ownerId}", { handler: "functions/club/getByOwner.handler" });
api.route("GET /clubs/court/{courtId}", { handler: "functions/club/getClubIdByCourtId.handler" });
api.route("GET /clubs/filter", { handler: "functions/club/getFiltered.handler" }); //confirmar se esta de acordo
api.route("POST /clubs", { handler: "functions/club/create.handler" }); // Create a new club
api.route("PUT /clubs", { handler: "functions/club/update.handler" }); // Update club details


api.route("POST /clubs/location", { handler: "functions/club/createLocation.handler" }); // Create a new location for a club
api.route("PUT /clubs/location", { handler: "functions/club/updateLocation.handler" }); // Update location details for a club

// === OwnerService ===
api.route("POST /owners/register", { handler: "functions/owners/register.handler" });
//api.route("POST /owners/login", { handler: "functions/owners/login.handler" });

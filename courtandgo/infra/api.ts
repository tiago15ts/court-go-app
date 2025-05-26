
export const api = new sst.aws.ApiGatewayV2("CourtAndGoAPI");

 // === UserService ===
  api.route("POST /user/register", { handler: "functions/user/register.handler" });
  api.route("POST /user/login", { handler: "functions/user/login.handler" });
  api.route("POST /user/logout", { handler: "functions/user/logout.handler" });
  api.route("GET /user/{id}", { handler: "functions/user/getById.handler" });
  api.route("GET /user/email/{email}", { handler: "functions/user/getByEmail.handler" });
  api.route("PUT /user", { handler: "functions/user/update.handler" });

  // === ScheduleCourtsService ===
  api.route("GET /schedule/weekly/{courtId}", { handler: "functions/schedule/weekly.handler" });
  api.route("GET /schedule/special/{courtId}", { handler: "functions/schedule/special.handler" });

  // === ReservationService ===
  api.route("GET /reservations", { handler: "functions/reservation/list.handler" });
  api.route("GET /reservations/{id}", { handler: "functions/reservation/get.handler" });
  api.route("GET /reservations/player/{playerId}", { handler: "functions/reservation/byPlayer.handler" });
  api.route("POST /reservations", { handler: "functions/reservation/create.handler" });
  api.route("PUT /reservations", { handler: "functions/reservation/update.handler" });
  api.route("DELETE /reservations/{id}", { handler: "functions/reservation/delete.handler" });
  api.route("POST /reservations/confirm/{id}", { handler: "functions/reservation/confirm.handler" });

  // === CourtService ===
  api.route("GET /courts", { handler: "functions/court/all.handler" });
  api.route("GET /courts/district/{district}", { handler: "functions/court/byDistrict.handler" });
  api.route("GET /courts/sport/{sport}", { handler: "functions/court/bySport.handler" });
  api.route("GET /courts/filter", { handler: "functions/court/filter.handler" });
  api.route("GET /courts/{id}", { handler: "functions/court/get.handler" });
  api.route("GET /courts/owner/{ownerId}", { handler: "functions/court/byOwner.handler" });

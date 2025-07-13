

export default $config({
  app(input) {
    return {
      name: "CourtAndGo",
      removal: input?.stage === "production" ? "retain" : "remove",
      protect: input?.stage === "production", //protect: ["production"].includes(input?.stage),
      home: "aws",
    };
  },
  async run() {
    const vpc = new sst.aws.Vpc("MyVpc", { nat: "ec2" });

    const database = new sst.aws.Postgres("CourtAndGoDB", {
      /*
      engine: "postgresql11.13",
      //defaultDatabaseName: "courtandgodb",
      migrations: "../../Database/CreateTables.sql",
      scaling: {
        autoPause: true,
        minCapacity: 1,
        maxCapacity: 2,
      },
      */
      vpc,
      dev: {
        username: "postgres",
        password: "1506",
        database: "local",
        host: "localhost",
        port: 5432,
      }
    });

    const api = new sst.aws.ApiGatewayV2("CourtAndGoAPI", {
      link: [database],
    });
    // === UserService ===
    api.route("POST /user/register", { handler: "packages/functions/user/register.handler" });
    api.route("POST /user/login", { handler: "packages/functions/user/login.handler" });
    api.route("POST /user/logout", { handler: "packages/functions/user/logout.handler" });
    api.route("GET /user/{id}", { handler: "packages/functions/user/getById.handler" });
    api.route("GET /user/email/{email}", { handler: "packages/functions/user/getByEmail.handler" });
    api.route("PUT /user/{id}", { handler: "packages/functions/user/update.handler" });
    api.route("POST /user/oauthregister", { handler: "packages/functions/user/oauthregister.handler" });
    api.route("GET /user", { handler: "packages/functions/user/getAll.handler" });

    // === ScheduleCourtsService ===
    api.route("GET /schedule/weekly/{courtId}", { handler: "packages/functions/schedule/weekly.handler" });
    api.route("GET /schedule/special/{courtId}", { handler: "packages/functions/schedule/special.handler" });
    api.route("POST /schedule/weekly", {
      handler: "packages/functions/schedule/createWeekly.handler",
    });
    api.route("POST /schedule/special", {
      handler: "packages/functions/schedule/createSpecial.handler",
    });

    // === ReservationService ===
    api.route("GET /reservations", { handler: "packages/functions/reservation/getAll.handler" });
    api.route("GET /reservations/{id}", { handler: "packages/functions/reservation/getById.handler" });
    api.route("GET /player/{playerId}/reservations", { handler: "packages/functions/reservation/getByPlayer.handler" });
    api.route("POST /reservations", { handler: "packages/functions/reservation/create.handler" }); // Create a new reservation
    api.route("PUT /reservations/{id}", { handler: "packages/functions/reservation/update.handler" }); // Update reservation by ID
    api.route("DELETE /reservations/{id}/cancel", { handler: "packages/functions/reservation/delete.handler" }); // Cancel reservation by ID
    api.route("POST /reservations/{id}/confirm", { handler: "packages/functions/reservation/confirm.handler" }); // Confirm reservation by ID
    api.route("GET /reservations/filter", { //confirmar se esta de acordo
      handler: "packages/functions/reservation/getByCourtIdsAndDate.handler",
    });
    //acho que falta uma rota

    api.route("GET /reservations/{id}/ics", {
      handler: "packages/functions/reservation/getICS.handler"
    });


    // === CourtService ===
    api.route("GET /courts", { handler: "packages/functions/court/all.handler" });
    api.route("GET /courts/district", { handler: "packages/functions/court/byDistrict.handler" });
    api.route("GET /courts/sport", { handler: "packages/functions/court/bySportType.handler" }); // Get courts by sport type
    api.route("GET /courts/filter", { handler: "packages/functions/court/getFiltered.handler" });
    api.route("GET /courts/{id}", { handler: "packages/functions/court/getById.handler" }); // Get court by ID
    api.route("GET /courts/owner/{ownerId}", { handler: "packages/functions/court/byOwner.handler" });
    api.route("POST /courts", { handler: "packages/functions/court/create.handler" }); // Create a new court
    api.route("PUT /courts/{id}", { handler: "packages/functions/court/update.handler" }); // Update court details
    api.route("DELETE /courts/{id}", { handler: "packages/functions/court/delete.handler" });
    api.route("GET /courts/club/{clubId}", { handler: "packages/functions/court/byClubId.handler" }); //getCourtsByClubId


    // === ClubService ===
    api.route("GET /clubs", { handler: "packages/functions/club/getAll.handler" });
    api.route("GET /clubs/district", { handler: "packages/functions/club/getByDistrict.handler" });
    api.route("GET /clubs/county", { handler: "packages/functions/club/getByCounty.handler" });
    api.route("GET /clubs/country", { handler: "packages/functions/club/getByCountry.handler" });
    api.route("GET /clubs/postal", { handler: "packages/functions/club/getByPostal.handler" });
    api.route("GET /clubs/name", { handler: "packages/functions/club/getByName.handler" });
    api.route("GET /clubs/sport", { handler: "packages/functions/club/getBySport.handler" });
    api.route("GET /clubs/{id}", { handler: "packages/functions/club/getById.handler" }); // Get club by ID
    api.route("GET /clubs/owner/{ownerId}", { handler: "packages/functions/club/getByOwner.handler" });
    api.route("GET /clubs/court/{courtId}", { handler: "packages/functions/club/getClubIdByCourtId.handler" });
    api.route("GET /clubs/filter", { handler: "packages/functions/club/getFiltered.handler" }); //confirmar se esta de acordo
    api.route("POST /clubs", { handler: "packages/functions/club/create.handler" }); // Create a new club
    api.route("PUT /clubs/{id}", { handler: "packages/functions/club/update.handler" }); // Update club details


    api.route("POST /clubs/location", { handler: "packages/functions/location/create.handler" }); // Create a new location for a club
    api.route("PUT /clubs/location/{id}", { handler: "packages/functions/location/update.handler" }); // Update location details for a club
    api.route("GET /clubs/{clubId}/location", { handler: "packages/functions/location/locationByClubId.handler" }); // Get location by club ID

    // === OwnerService ===
    api.route("POST /owners/register", { handler: "packages/functions/owners/register.handler" });
    api.route("POST /owners/login", { handler: "packages/functions/owners/login.handler" });



    const site = new sst.aws.React("CourtAndGoAdminSite", {
      path: "frontend-admin",
      buildCommand: "npm run build",
      buildOutput: "dist",
      //link:  [database],
      //vpc: vpc,

    });

    return {
      host: database.host,
      port: database.port,
      username: database.username,
      password: database.password,
      database: database.database,
    };
  },
});

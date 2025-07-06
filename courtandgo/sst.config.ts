import { $config } from "sst";
import * as sst from "sst";

export default $config({
  app(input) {
    return {
      name: "CourtAndGo",
      removal: input?.stage === "production" ? "retain" : "remove",
      protect: input?.stage === "production",
      home: "aws",
    };
  },
  async run(app) {
    // Import your existing infra
    await import("./infra/storage");
    await import("./infra/api");

    // Create the static site (React frontend)
    const site = new sst.StaticSite(app, "CourtAndGoSite", {
      path: "frontend",
      buildCommand: "npm run build",
      buildOutput: "dist",
      environment: {
        // e.g. inject API URL if you have it exported from api.ts
        VITE_API_URL: process.env.VITE_API_URL || "", 
      },
    });

    // Export the site URL
    app.stackExports = {
      siteUrl: site.url,
    };
  },
});

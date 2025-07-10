

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
    // Import your existing infra
    await import("./infra/storage");
    await import("./infra/api");
    

    const site = new sst.aws.React("CourtAndGoAdminSite", {
      path: "frontend-admin",
      buildCommand: "npm run build",
      buildOutput: "dist",
      //link:  [database],
      //vpc: vpc,

    });

    // Export the site URL
    // app.stackExports = {
    //   siteUrl: site.url,
    // };
  },


});

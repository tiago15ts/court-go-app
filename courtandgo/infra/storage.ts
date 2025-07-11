
export function MyStack({ stack }: StackContext) {
  
   const bucket = new sst.aws.Bucket("Uploads");

   const vpc = new sst.aws.Vpc("MyVpc");

   const database = new sst.aws.Postgres("CourtAndGo Database", {
    engine: "postgresql11.13", 
    defaultDatabaseName: "courtandgodb",
    migrations: "../../Database/CreateTables.sql", 
    scaling: {
      autoPause: true,
      minCapacity: 1,
      maxCapacity: 2,
    },
    vpc,
  });

  return {
    bucket,
    database,
  };
}



//export function MyStack({ stack }: StackContext) {

export const bucket = new sst.aws.Bucket("Uploads");

export const vpc = new sst.aws.Vpc("MyVpc");

export const database = new sst.aws.Postgres("CourtAndGoDB", {
  engine: "postgresql11.13",
  defaultDatabaseName: "courtandgodb",
  migrations: "../../Database/CreateTables.sql",
  scaling: {
    autoPause: true,
    minCapacity: 1,
    maxCapacity: 2,
  },
  vpc,
  dev: {
    username: "postgres",
    password: "1506",
    database: "CGlocalDB",
    port: 5432,
  }
});

//  return {
//    bucket,
//    database,
//  };
//}


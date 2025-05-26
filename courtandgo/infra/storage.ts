
export function MyStack({ stack }: StackContext) {
  
  const bucket = new Bucket(stack, "Uploads");

  const database = new RDS(stack, "Database", {
    engine: "postgresql11.13", 
    defaultDatabaseName: "mydb",
    migrations: "../../Database/CreateTables.sql", 
    scaling: {
      autoPause: true,
      minCapacity: 1,
      maxCapacity: 2,
    },
  });

  return {
    bucket,
    database,
  };
}

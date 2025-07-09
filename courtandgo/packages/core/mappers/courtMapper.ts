export function mapRowToCourtDTO(row: any) {
  return {
    id: row.courtid,
    name: row.name,
    sportTypeCourt: row.type,
    surfaceType: row.surfacetype,
    capacity: row.capacity,
    price: parseFloat(row.priceperhour),
    clubId: row.clubid
  };
}

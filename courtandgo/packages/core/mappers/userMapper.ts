export function mapRowToUserDTO(row: any) {
  return {
    id: row.playerid,
    email: row.email,
    name: row.name,
    countryCode: row.countryid,
    phone: row.phone,
    gender: row.gender ?? null,
    birthDate: row.birthdate ? row.birthdate.toISOString().split("T")[0] : null,
    weight: row.weight !== null ? parseFloat(row.weight) : null,
    height: row.height !== null ? parseFloat(row.height) : null,
  };
}

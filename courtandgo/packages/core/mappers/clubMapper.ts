
export function mapRowToClubDTO(row: any) {
  return {
    id: row.clubid,
    name: row.club_name,
    sportsClub: row.sports,
    nrOfCourts: row.nrofcourts,
    averagePrice: parseFloat(row.averageprice),
    location: {
      id: row.location_id,
      address: row.address,
      county: row.county,
      postalCode: row.postalcode,
      latitude: row.latitude,
      longitude: row.longitude,
      district: {
        id: row.district_id,
        name: row.district_name,
        countryId: row.district_country_id
      },
      country: {
        id: row.country_id,
        name: row.country_name
      }
    }
  };
}

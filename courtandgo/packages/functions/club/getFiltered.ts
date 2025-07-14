import { getClubsFiltered } from "../../core/queries/club";

export async function handler(event) {
  const qs = event.queryStringParameters || {};

  const clubs = await getClubsFiltered({
    query: qs.query || null,
    county: qs.county || null,
    district: qs.district || null,
    country: qs.country || null,
    postalCode: qs.postalCode || null,
    sport: qs.sport || "", // required
  });

  return {
    statusCode: 200,
        headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(clubs),
  };
}

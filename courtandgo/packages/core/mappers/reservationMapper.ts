
export function mapRowToReservationDTO(row: any) {
  return {
    reservationId: row.reservationid,
    courtId: row.courtid,
    createdByPlayerId: row.createdbyplayerid,
    startTime: row.starttime.toISOString(),  // assume que vem Date do pg driver
    endTime: row.endtime.toISOString(),
    estimatedPrice: parseFloat(row.estimatedprice),
    status: row.status,
  };
}

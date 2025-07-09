
export function mapRowToReservationDTO(row: any) {
  return {
    reservationId: row.reservationid,
    courtId: row.courtid,
    createdByPlayerId: row.createdbyplayerid,
    startTime: row.starttime.toISOString().slice(0, 19), // Format to 'YYYY-MM-DDTHH:mm:ss' 
    endTime: row.endtime.toISOString().slice(0, 19),
    estimatedPrice: parseFloat(row.estimatedprice),
    status: row.status,
  };
}

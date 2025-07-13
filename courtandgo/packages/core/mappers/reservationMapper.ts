
export function mapRowToReservationDTO(row: any) {
  
  function formatDateTimeLocal(dt: any): string {
    if (!dt) return  "";
    const dateObj = dt instanceof Date ? dt : new Date(dt);
    const year = dateObj.getFullYear();
    const month = String(dateObj.getMonth() + 1).padStart(2, '0');
    const day = String(dateObj.getDate()).padStart(2, '0');
    const hours = String(dateObj.getHours()).padStart(2, '0');
    const minutes = String(dateObj.getMinutes()).padStart(2, '0');
    const seconds = String(dateObj.getSeconds()).padStart(2, '0');
    return `${year}-${month}-${day}T${hours}:${minutes}:${seconds}`;
  }

  return {
    reservationId: row.reservationid,
    courtId: row.courtid,
    createdByPlayerId: row.createdbyplayerid,
    startTime: formatDateTimeLocal(row.starttime),
    endTime: formatDateTimeLocal(row.endtime),
    estimatedPrice: parseFloat(row.estimatedprice),
    status: row.status,
  };
}

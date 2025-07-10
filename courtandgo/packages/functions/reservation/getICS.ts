
import { createReservationICS } from '../../../utils/createReservationICSfile';
import { getReservationById } from '../../core/queries/reservation'; 
import { getClubNameByCourtId } from '../../core/queries/club';

export async function handler(event) {
  const reservationId = event.pathParameters?.id;
  if (!reservationId) {
    return { statusCode: 400, body: 'Reservation ID missing' };
  }

  const reservation = await getReservationById(reservationId);
  if (!reservation) {
    return { statusCode: 404, body: 'Reservation not found' };
  }
  const clubName = await getClubNameByCourtId(reservation.courtId);

  const icsContent = createReservationICS(reservation, clubName);

  return {
    statusCode: 200,
    headers: {
      'Content-Type': 'text/calendar; charset=utf-8',
      'Content-Disposition': `attachment; filename=reservation-${reservationId}.ics`,
    },
    body: icsContent,
  };
};

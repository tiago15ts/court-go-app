import ical, { ICalEventStatus }  from 'ical-generator';

export function createReservationICS(reservation: any, clubName: string) {
  const cal = ical({ name: 'Court&Go Reserva' });

  console.log("Start time:", reservation.starttime);
console.log("End time:", reservation.endtime);
console.log("Type:", typeof reservation.starttime);

  cal.createEvent({
    start: new Date(reservation.startTime),
    end: new Date(reservation.endTime),
    summary: `Reserva Court&Go - ${clubName}`,
    description: `Reserva no clube ${clubName}\nCourt ID: ${reservation.courtId}\nPreço: ${reservation.estimatedPrice}€`,
    location: clubName,
    status: ICalEventStatus.CONFIRMED,
    organizer: {
      name: 'Court&Go',
      email: process.env.EMAIL_USER,
    },
  });

  return cal.toString(); // devolve o conteúdo do ficheiro .ics
}

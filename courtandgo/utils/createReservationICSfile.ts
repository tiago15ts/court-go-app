import ical, { ICalEventStatus }  from 'ical-generator';

export function createReservationICS(reservation: any, clubName: string) {
  const cal = ical({ name: 'Court&Go Reserva' });

  cal.createEvent({
    start: new Date(reservation.starttime),
    end: new Date(reservation.endtime),
    summary: `Reserva Court&Go - ${clubName}`,
    description: `Reserva no clube ${clubName}\nCourt ID: ${reservation.courtid}\nPreço: ${reservation.estimatedprice}€`,
    location: clubName,
    status: ICalEventStatus.CONFIRMED,
    organizer: {
      name: 'Court&Go',
      email: process.env.EMAIL_USER,
    },
  });

  return cal.toString(); // devolve o conteúdo do ficheiro .ics
}

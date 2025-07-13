// utils/email.ts




export function createReservationEmail(playerName: string, clubName: string, reservation: any, diffText: string) {
  const startDate = new Date(reservation.startTime);
const endDate = new Date(reservation.endTime);

  // Formatadores de data
  const timeFormatter = new Intl.DateTimeFormat('pt-PT', {
    hour: '2-digit',
    minute: '2-digit',
    hour12: false,
  });

  const dateFormatter = new Intl.DateTimeFormat('pt-PT', {
    day: '2-digit',
    month: '2-digit',
    year: 'numeric',
  });

  const formattedStart = `às ${timeFormatter.format(startDate)} de ${dateFormatter.format(startDate)}`;
  const formattedEnd = `às ${timeFormatter.format(endDate)} de ${dateFormatter.format(endDate)}`;



  return `Olá ${playerName},

A tua reserva no clube "${clubName}" é ${diffText}.

Court ID: ${reservation.courtId}
Início: ${formattedStart}
Fim: ${formattedEnd}
Preço estimado: ${reservation.estimatedPrice}€

Por favor, confirma a tua reserva na aplicação.

Obrigado por usar a Court&Go!`;
}

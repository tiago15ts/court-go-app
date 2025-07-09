import cron from 'node-cron';
import { sendEmail, createReservationEmail } from './sendEmail';

export function scheduleReservationReminder(reservation: any, playerEmail: string, playerName: string, clubName: string) {
  const startTime = new Date(reservation.starttime);
  const now = new Date();
  const diffMs = startTime.getTime() - now.getTime();
  const diffHours = diffMs / (1000 * 60 * 60);

  if (diffHours <= 0) {
    // Reserva já passou, não enviar
    return;
  }

  const subject = 'Lembrete da tua reserva Court&Go';
  const emailText = createReservationEmail(playerName, clubName, reservation, diffHours);

  if (diffHours > 24) {
    // Agenda cron para enviar exatamente 24h antes do início
    const sendTime = new Date(startTime.getTime() - 24 * 60 * 60 * 1000);
    const cronExpression = `${sendTime.getMinutes()} ${sendTime.getHours()} ${sendTime.getDate()} ${sendTime.getMonth() + 1} *`;

    cron.schedule(cronExpression, () => {
      sendEmail(playerEmail, subject, emailText)
        .catch(console.error);
    }, { timezone: 'Europe/Lisbon' }); // ajusta timezone conforme necessário
  } else {
    // Envia já o email, porque falta menos de 24h
    sendEmail(playerEmail, subject, emailText).catch(console.error);
  }
}

import cron from 'node-cron';
import { createReservationEmail } from './sendEmail';
import nodemailer from 'nodemailer';
import { EMAIL_USER, EMAIL_PASS } from './credentials';


//create file credentials.ts with EMAIL_USER and EMAIL_PASS in this directory
// to store email credentials securely
async function sendEmail(to: string, subject: string, text: string, html?: string) {
  if (!EMAIL_USER || !EMAIL_PASS) {
    throw new Error('Email credentials are not set in environment variables');
  }
  const transporter = nodemailer.createTransport({
    service: 'gmail', // ou 'hotmail', ou usa host/port
    auth: {
      user: EMAIL_USER, // ex: courtandgo@gmail.com
      pass: EMAIL_PASS
    }
  });

  await transporter.sendMail({
    from: `"Court&Go" <${EMAIL_USER}>`,
    to,
    subject,
    text,
    html
  });
}

export function scheduleReservationReminder(reservation: any, playerEmail: string, playerName: string, clubName: string) {
  function parseLocalDateTime(dateStr: string): Date {
  // dateStr: '2025-07-13T21:00:00'
  const [datePart, timePart] = dateStr.split("T");
  const [year, month, day] = datePart.split("-").map(Number);
  const [hour, minute, second] = (timePart || "00:00:00").split(":").map(Number);

  return new Date(year, month - 1, day, hour, minute, second);
}

const startTime = parseLocalDateTime(reservation.startTime);
  const now = new Date();

  const diffMs = startTime.getTime() - now.getTime();

  if (diffMs <= 0) {
    // Reserva já passou, não enviar
    return;
  }

  // Calcula horas e minutos restantes
  const totalMinutes = Math.floor(diffMs / (1000 * 60));
  const diffHours = Math.floor(totalMinutes / 60);
  const diffMinutes = totalMinutes % 60;

  const diffText =
    diffHours > 0
      ? `daqui a ${diffHours}h${diffMinutes > 0 ? ` e ${diffMinutes}m` : ''}`
      : `daqui a ${diffMinutes} minutos`;

  const subject = 'Lembrete da tua reserva Court&Go';
  const emailText = createReservationEmail(playerName, clubName, reservation, diffText);

  if (diffHours >= 24) {
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

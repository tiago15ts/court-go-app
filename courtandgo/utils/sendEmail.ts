// utils/email.ts
import nodemailer from 'nodemailer';

export async function sendEmail(to: string, subject: string, text: string, html?: string) {
  const transporter = nodemailer.createTransport({
    service: 'gmail', // ou 'hotmail', ou usa host/port
    auth: {
      user: process.env.EMAIL_USER, // ex: courtandgo@gmail.com
      pass: process.env.EMAIL_PASS
    }
  });

  await transporter.sendMail({
    from: `"Court&Go" <${process.env.EMAIL_USER}>`,
    to,
    subject,
    text,
    html
  });
}

export function createReservationEmail(playerName: string, clubName: string, reservation: any, diffHours: number) {
  return `Olá ${playerName},

A tua reserva no clube "${clubName}" é dentro de ${diffHours.toFixed(1)} horas.

Court ID: ${reservation.courtid}
Início: ${reservation.starttime}
Fim: ${reservation.endtime}
Preço estimado: ${reservation.estimatedprice}€

Por favor, confirma a tua reserva na aplicação.

Obrigado por usar a Court&Go!`;
}

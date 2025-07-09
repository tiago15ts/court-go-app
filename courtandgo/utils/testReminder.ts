import { scheduleReservationReminder } from './reminders'; // caminho correto para a tua função
import { config } from 'dotenv';
config(); // carrega variáveis de ambiente

// Mock reserva com startTime a 1 minuto daqui para frente
const now = new Date();
const startTime = new Date(now.getTime() + 1 * 60 * 1000).toISOString();

const reservation = {
  starttime: startTime,
  courtid: 123,
  endtime: new Date(now.getTime() + 2 * 60 * 1000).toISOString(),
  estimatedprice: 20,
};

const playerEmail = 'tiago.silva15@hotmail.com';
const playerName = 'Tiago';
const clubName = 'Clube Teste';

console.log('Agendando email de teste para daqui a 1 minuto...');

scheduleReservationReminder(reservation, playerEmail, playerName, clubName);

// Para o Node não fechar, porque o cron espera
setTimeout(() => {
  console.log('Teste terminado.');
  process.exit(0);
}, 2 * 60 * 1000); // fecha após 2 minutos

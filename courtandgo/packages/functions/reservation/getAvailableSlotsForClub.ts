export async function handler(event) {
  const body = JSON.parse(event.body || "{}");

  const { timeSlotsByCourt, occupiedTimesByCourt } = body;

  const result: Record<number, string[]> = {};

  for (const courtId in timeSlotsByCourt) {
    const all = timeSlotsByCourt[courtId] || [];
    const busy = occupiedTimesByCourt[courtId] || [];

    result[courtId] = all.filter(t => !busy.includes(t));
  }

  return {
    statusCode: 200,
    body: JSON.stringify(result),
  };
}

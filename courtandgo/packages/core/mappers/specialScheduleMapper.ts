export function mapRowToSpecialScheduleDTO(row: any) {
  return {
    specialId: row.specialid,
    courtId: row.courtid,
    date: row.date ? row.date.toISOString().slice(0, 10) : null, // 'YYYY-MM-DD'
    startTime: row.starttime ? row.starttime.slice(0, 5) : null, // 'HH:MM'
    endTime: row.endtime ? row.endtime.slice(0, 5) : null,
    working: row.working
  };
}

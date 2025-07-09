export function mapRowToWeeklyScheduleDTO(row: any) {
  return {
    scheduleId: row.scheduleid,
    courtId: row.courtid,
    weekDay: row.weekday, 
    startTime: row.starttime ? row.starttime.slice(0, 5) : null,
    endTime: row.endtime ? row.endtime.slice(0, 5) : null,
  };
}
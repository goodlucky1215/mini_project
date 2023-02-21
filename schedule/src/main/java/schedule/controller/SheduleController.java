package schedule.controller;

import java.text.ParseException;

import schedule.service.ScheduleService;

public class SheduleController {

	public void makeShedule(String startScheduleDay, String[][] scheduleData) throws ParseException {
		System.out.println(startScheduleDay);
		for(int i=0;i<3;i++) {
			for(int j=0;j<7;j++) {
				System.out.print(scheduleData[i][j]+" ");
			}
			System.out.println();
		}
		new ScheduleService().makeSchedule(startScheduleDay, scheduleData);
		System.out.println("======= end =======");
	}
}

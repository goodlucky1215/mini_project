package schedule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ScheduleButton {

	public static void main(String[] args) throws ParseException {
		System.out.println("");
		String startScheduleDay = "20230213";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat formatterWeek = new SimpleDateFormat("yyyy/MM/dd/E����");
		Date date = formatter.parse(startScheduleDay);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		
		String[] person = {"a","b","c","d","e","f","g"};
		int numberOfPerson = 7;
		//IndividualSchduleDto[] individualSchduleDto = new IndividualSchduleDto[numberOfPerson];
		
		List<List<ScheduleDto>> monthSchedule = new ArrayList();
		for(int i=0;i<4;i++) {
			List<ScheduleDto> weekSchedule = new ArrayList();
			//������ �ޱ�
			for(int j=0;j<7;j++) {
				ScheduleDto scheduleDto = new ScheduleDto();
				String todayFormatterString = formatter.format(calendar.getTime());
				Date todayFormatterDate = formatter.parse(todayFormatterString);
				scheduleDto.today = formatterWeek.format(todayFormatterDate).split("/");
				//System.out.println(scheduleDto.today[0]+scheduleDto.today[1]+scheduleDto.today[2]+scheduleDto.today[3]);
				calendar.add(Calendar.DATE,1);
				weekSchedule.add(scheduleDto);
			}
			
			//������ �ð�ǥ ����
			boolean[] checkDayOffWorker = new boolean[numberOfPerson];
			List<IndividualSchduleDto> individualSchduleDto = new ArrayList();
			for(int j=0;j<numberOfPerson;j++)individualSchduleDto.add(new IndividualSchduleDto());
			
			
			for(int j=0;j<7;j++) {
				//���� �� ����
				int dayOffPerson = -1;
				while(dayOffPerson==-1) {
					int dayOffWorker = (int) (Math.random() * numberOfPerson);
					if(!checkDayOffWorker[dayOffWorker]) {
						checkDayOffWorker[dayOffWorker]= true;
						weekSchedule.get(j).dayOffWorker = person[dayOffWorker];
						dayOffPerson = dayOffWorker;
					}
				}
				
				//���� �ð� ����(����) - 3��
				boolean[] checkWorker = new boolean[numberOfPerson];
				while(weekSchedule.get(j).morningWorker.size()<3) {
					int morningWorker = (int) (Math.random() * numberOfPerson);
					if(dayOffPerson!=morningWorker && individualSchduleDto.get(morningWorker).morningWork<3 && !checkWorker[morningWorker]) {
						weekSchedule.get(j).morningWorker.add(person[morningWorker]);
						System.out.println(person[morningWorker]);
						individualSchduleDto.get(morningWorker).morningWork++;
						checkWorker[morningWorker]=true;
					}
					System.out.println(weekSchedule.get(j).morningWorker.size());
				}				
				
				//���� �ð� ����(����) - 3��
				while(weekSchedule.get(j).afternoonWorker.size()<3) {
					int afternoonWork = (int) (Math.random() * numberOfPerson);
					if(dayOffPerson!=afternoonWork && individualSchduleDto.get(afternoonWork).afternoonWork<3 && !checkWorker[afternoonWork]) {
						weekSchedule.get(j).afternoonWorker.add(person[afternoonWork]);
						individualSchduleDto.get(afternoonWork).afternoonWork++;
						checkWorker[afternoonWork]=true;
					}
				}	
				
			}
			
			//�Ѵ� �ð�ǥ�� �ֱ�
			monthSchedule.add(weekSchedule);
		}
		
		
		//test
		for(int i=0;i<4;i++) {
			List<ScheduleDto> weekSchedule = monthSchedule.get(i);
			//������ �ޱ�
			for(int j=0;j<7;j++) {
				System.out.println(weekSchedule.get(j).today[1]+"/"+
								   weekSchedule.get(j).today[2]+"/"+
								   weekSchedule.get(j).today[3]+
									":"+weekSchedule.get(j).dayOffWorker);
				System.out.println("������ : "+
									weekSchedule.get(j).morningWorker.get(0)+","+
									weekSchedule.get(j).morningWorker.get(1)+","+
									weekSchedule.get(j).morningWorker.get(2));
				System.out.println("������ : "+
						weekSchedule.get(j).afternoonWorker.get(0)+","+
						weekSchedule.get(j).afternoonWorker.get(1)+","+
						weekSchedule.get(j).afternoonWorker.get(2));
			}
			System.out.println("//////////////////////////////");
		}
		
		
	}
}

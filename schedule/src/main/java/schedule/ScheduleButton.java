package schedule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ScheduleButton {
	String[] weekDay = {"월요일","화요일","수요일","목요일","금요일","토요일","일요일"};
	String startScheduleDay = "20230219"; //시작날짜 입력받음
	int[] morningPeople =   {3,2,3,2,3,3,3}; // 월-일 입력 받음
	int[] afternoonPeople = {2,2,2,2,2,3,3}; // 월-일 입력 받음
	int[] dayOffPeople =    {2,3,2,3,2,1,1}; // 월-일 쉬는 사람

	String[] person = {"a","b","c","d","e","f","g"}; //사람이름
	int numberOfPerson = 7; //전체사람수
	
	private void start() throws ParseException {
		SimpleDateFormat formatterWeek = new SimpleDateFormat("yyyy/MM/dd/E요일");
		Calendar calendar = getCalendar();
		changeStartWeek(formatterWeek.format(calendar.getTime()).split("/")[3]);//시작요일을 기점으로 순서 변경
		
		List<List<ScheduleDto>> monthSchedule = new ArrayList();
		
		for(int i=0;i<4;i++) {
			List<ScheduleDto> weekSchedule = new ArrayList();
			
			//일주일 받기
			for(int j=0;j<7;j++) {
				ScheduleDto scheduleDto = new ScheduleDto();
				scheduleDto.today = formatterWeek.format(calendar.getTime()).split("/");
				calendar.add(Calendar.DATE,1);
				weekSchedule.add(scheduleDto);
			}
			
			
			//일주일 시간표 설정
			int[] work   = {5,5,5,5,5,5,5};
			int[] dayOff = {2,2,2,2,2,2,2};
			int[] dayOffPeopleCopy = dayOffPeople;
			for(int j=0;j<7;j++) {
				//쉬는 날 설정
				while(weekSchedule.get(j).dayOffWorker.size()<dayOffPeople[j]) {
					int dayOffWorker = (int) (Math.random() * numberOfPerson);
					if(dayOff[dayOffWorker]>0) {
						dayOff[dayOffWorker]--;
						weekSchedule.get(j).dayOffWorker.add(person[dayOffWorker]);
					}
				}
			}
			//weekSchedulelogTest(weekSchedule);//일주일 쉬는 사람 로그 확인
			
		}
				/*
				System.out.println("쉬는 사람 "+person[dayOffPerson]);
				//일할 시간 설정(오전) - 3명
				boolean[] checkWorker = new boolean[numberOfPerson];
				while(weekSchedule.get(j).morningWorker.size()<3) {
					int morningWorker = (int) (Math.random() * numberOfPerson);
					if(dayOffPerson!=morningWorker && individualSchduleDto.get(morningWorker).morningWork<3 && !checkWorker[morningWorker]) {
						weekSchedule.get(j).morningWorker.add(person[morningWorker]);
						System.out.println("일"+person[morningWorker]);
						individualSchduleDto.get(morningWorker).morningWork++;
						checkWorker[morningWorker]=true;
					}
				}		
				
				System.out.println("/////////////////");
				//일할 시간 설정(오후) - 3명
				while(weekSchedule.get(j).afternoonWorker.size()<3) {
					int afternoonWork = (int) (Math.random() * numberOfPerson);
					if(dayOffPerson!=afternoonWork && individualSchduleDto.get(afternoonWork).afternoonWork<3 && !checkWorker[afternoonWork]) {
						weekSchedule.get(j).afternoonWorker.add(person[afternoonWork]);
						System.out.println("일"+person[afternoonWork]);
						individualSchduleDto.get(afternoonWork).afternoonWork++;
						checkWorker[afternoonWork]=true;
					}
				}	
				System.out.println("/////////////////");
			}
			
			//한달 시간표에 넣기
			monthSchedule.add(weekSchedule);
			*/
		
	}
	
	public Calendar getCalendar() throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		Date date = formatter.parse(startScheduleDay);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}
	
	public void changeStartWeek(String startWeekDay) {
		int weekNum = 0;
		for(int i=0;i<7;i++) {
			if(weekDay[i].equals(startWeekDay)) {
				weekNum = i;
				break;
			}
		}
		for(int i=0;i<weekNum;i++) {
			int morningFirst =   morningPeople[0];
			int afternoonFirst = afternoonPeople[0];
			int dayOffFirst = dayOffPeople[0];
			for(int j=0;j<6;j++) {
				morningPeople[j]=morningPeople[j+1];
				afternoonPeople[j]=afternoonPeople[j+1];
				dayOffPeople[j]=dayOffPeople[j+1];
			}
			morningPeople[6]=morningFirst;
			afternoonPeople[6]=afternoonFirst;
			dayOffPeople[6]=dayOffFirst;
		}
	}
	
	public void weekSchedulelogTest(List<ScheduleDto> weekSchedule) {
		for(int i=0;i<7;i++) {
			List dayOffWorker = weekSchedule.get(i).dayOffWorker;
			for(Object object:dayOffWorker) {
				System.out.print(object+", ");
			}
			System.out.println();
		}
		System.out.println("/////////////////////////////");
	}
	
	public static void main(String[] args) throws ParseException {
		
		for(int i=0;i<1000;i++)
			new ScheduleButton().start();

		System.out.println("////////end/////////");
	
	/*	
		//test
		for(int i=0;i<4;i++) {
			List<ScheduleDto> weekSchedule = monthSchedule.get(i);
			//일주일 받기
			for(int j=0;j<7;j++) {
				System.out.println(weekSchedule.get(j).today[1]+"/"+
								   weekSchedule.get(j).today[2]+"/"+
								   weekSchedule.get(j).today[3]+
									":"+weekSchedule.get(j).dayOffWorker);
				System.out.println("오전조 : "+
									weekSchedule.get(j).morningWorker.get(0)+","+
									weekSchedule.get(j).morningWorker.get(1)+","+
									weekSchedule.get(j).morningWorker.get(2));
				System.out.println("오후조 : "+
						weekSchedule.get(j).afternoonWorker.get(0)+","+
						weekSchedule.get(j).afternoonWorker.get(1)+","+
						weekSchedule.get(j).afternoonWorker.get(2));
			}
			System.out.println("//////////////////////////////");
		}
		
		*/
	}

}

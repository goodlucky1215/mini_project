package schedule.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import schedule.dto.ScheduleDto;

public class ScheduleService {
	String[] weekDay = {"������","ȭ����","������","�����","�ݿ���","�����","�Ͽ���"};
	//String startScheduleDay = "20230219"; //���۳�¥ �Է¹���
	String startScheduleDay; //���۳�¥ �Է¹���
	//int[] morningPeople =   {3,2,3,2,3,3,3}; // ��-�� �Է� ����
	int[] morningPeople; // ��-�� �Է� ����
	//int[] afternoonPeople = {2,2,2,2,2,3,3}; // ��-�� �Է� ����
	int[] afternoonPeople; // ��-�� �Է� ����
	//int[] dayOffPeople =    {2,3,2,3,2,1,1}; // ��-�� ���� ���
	int[] dayOffPeople; // ��-�� ���� ���

	String[] person = {"a","b","c","d","e","f","g"}; //����̸�
	int numberOfPerson = 7; //��ü�����
	
	public void makeSchedule(String startScheduleDay, String[][] scheduleData) throws ParseException {
		this.startScheduleDay = startScheduleDay;
		morningPeople = new int[7];
		afternoonPeople = new int[7];
		dayOffPeople = new int[7];
		for(int i=0;i<7;i++) morningPeople[i] = Integer.valueOf(scheduleData[0][i]);
		for(int i=0;i<7;i++) afternoonPeople[i] = Integer.valueOf(scheduleData[1][i]);
		for(int i=0;i<7;i++) dayOffPeople[i] = Integer.valueOf(scheduleData[2][i]);
		start();
	}
	
	
	public void start() throws ParseException {
		SimpleDateFormat formatterWeek = new SimpleDateFormat("yyyy/MM/dd/E����");
		Calendar calendar = getCalendar();
		changeStartWeek(formatterWeek.format(calendar.getTime()).split("/")[3]);//���ۿ����� �������� ���� ����
		
		List<List<ScheduleDto>> monthSchedule = new ArrayList();
		
		for(int i=0;i<4;i++) {
			List<ScheduleDto> weekSchedule = new ArrayList();
			
			//������ �ޱ�
			for(int j=0;j<7;j++) {
				ScheduleDto scheduleDto = new ScheduleDto();
				scheduleDto.today = formatterWeek.format(calendar.getTime()).split("/");
				calendar.add(Calendar.DATE,1);
				weekSchedule.add(scheduleDto);
			}
		
			//////////////////������ �ð�ǥ ����//////////////////////
			//���³� ����
			List<List> weekScheduleDayOffWorkerList = getDayOffShedule();
			for(int j=0;j<7;j++) weekSchedule.get(j).dayOffWorker = weekScheduleDayOffWorkerList.get(j);
			
			//���ϴ³� ����
			int[] work   = {5,5,5,5,5,5,5}; //�� ����� ���ؾ��ϴ� ��
			Map<String, List> weekScheduleWorkWorkerList = getWorkShedule(weekScheduleDayOffWorkerList);
			List<List> weekScheduleMorningWorkList = weekScheduleWorkWorkerList.get("weekScheduleMorningWork");
			List<List> weekScheduleAfternoonWorkList = weekScheduleWorkWorkerList.get("weekScheduleAfternoonWork");
			for(int j=0;j<7;j++) {
				weekSchedule.get(j).morningWorker = weekScheduleMorningWorkList.get(j);
				weekSchedule.get(j).afternoonWorker = weekScheduleAfternoonWorkList.get(j);
			}
			
			weekSchedulelogTest(weekSchedule);//������ ���� ��� �α� Ȯ��
						
			//�Ѵ� �ð�ǥ�� ������ �ð�ǥ �ֱ�
			monthSchedule.add(weekSchedule);
		}
		
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
	
	public List<List> getDayOffShedule() {
		int[] dayOff; //�� ����� ������ϴ� ��
		boolean dayOffOverlapPerson = false;
		List<List> weekScheduleDayOffWorkerList = null;
		while(!dayOffOverlapPerson) {
			dayOffOverlapPerson=true;
			dayOff = new int[]{2,2,2,2,2,2,2};
			weekScheduleDayOffWorkerList = new ArrayList<>();
			List weekScheduleDayOffWorker;
			for(int j=0;j<7;j++) {
				weekScheduleDayOffWorker = new ArrayList<>();
				//���� �� ����
				while(weekScheduleDayOffWorker.size()<dayOffPeople[j]) {
					int dayOffWorker = (int) (Math.random() * numberOfPerson);
					if(dayOff[dayOffWorker]>0) {
						dayOff[dayOffWorker]--;
						weekScheduleDayOffWorker.add(person[dayOffWorker]);
					}
				}
				int[] personCheck = {0,0,0,0,0,0,0};
				for(int k=0;k<weekScheduleDayOffWorker.size();k++) {
					int index = Arrays.asList(person).indexOf(weekScheduleDayOffWorker.get(k));
					if(personCheck[index]==0) {
						personCheck[index]++;
					}else {
						dayOffOverlapPerson=false;
						break;
					}
				}
				if(!dayOffOverlapPerson) break;
				weekScheduleDayOffWorkerList.add(weekScheduleDayOffWorker);
			}
		}
		return weekScheduleDayOffWorkerList;
	}
	
	public Map getWorkShedule(List<List> weekScheduleDayOffWorkerList) {
		int[] work; //�� ����� ���ؾ��ϴ� ��
		boolean overlapPerson = false;
		List<List> weekScheduleMorningWorkList = null;
		List<List> weekScheduleAfternoonWorkList = null;
		while(!overlapPerson) {
			overlapPerson=true;
			work = new int[]{5,5,5,5,5,5,5};
			weekScheduleMorningWorkList = new ArrayList<>();
			weekScheduleAfternoonWorkList = new ArrayList<>();
			List weekScheduleMorningWork;
			List weekScheduleAfternoonWork;
			for(int j=0;j<7;j++) {
				boolean[] usePersonCheck = {false, false, false, false, false, false, false};
				for(int k=0;k<weekScheduleDayOffWorkerList.get(j).size();k++) {
					int index = Arrays.asList(person).indexOf(weekScheduleDayOffWorkerList.get(j).get(k));
					usePersonCheck[index] = true;
				}
				weekScheduleMorningWork = new ArrayList<>();
				weekScheduleAfternoonWork = new ArrayList<>();
				//���� ����
				while(weekScheduleMorningWork.size()<morningPeople[j]) {
					int workWorker = (int) (Math.random() * numberOfPerson);
					if(!usePersonCheck[workWorker] && work[workWorker]>0) {
						work[workWorker]--;
						weekScheduleMorningWork.add(person[workWorker]);
						usePersonCheck[workWorker] = true;
					}
				}
				
				//���� ����
				for(int k=0;k<7;k++) {
					if(!usePersonCheck[k] && work[k]>0) {
						weekScheduleAfternoonWork.add(person[k]);
						work[k]--;
						usePersonCheck[k] = true;
					}
				}
				
				if(weekScheduleAfternoonWork.size()!=afternoonPeople[j]) {
					overlapPerson=false;
					break;
				}
				weekScheduleMorningWorkList.add(weekScheduleMorningWork);
				weekScheduleAfternoonWorkList.add(weekScheduleAfternoonWork);
			}
		}
		Map<String, List> weekScheduleWorkWorkerList = new HashMap<>(); //����, ���� �ֱ�
		weekScheduleWorkWorkerList.put("weekScheduleMorningWork",weekScheduleMorningWorkList);
		weekScheduleWorkWorkerList.put("weekScheduleAfternoonWork",weekScheduleAfternoonWorkList);
		return weekScheduleWorkWorkerList;
	}
	
	public void weekSchedulelogTest(List<ScheduleDto> weekSchedule) {
		for(int i=0;i<7;i++) {
			System.out.println("���� ��¥  : "+weekSchedule.get(i).today[1]+"/"+
					   weekSchedule.get(i).today[2]+"/"+ weekSchedule.get(i).today[3]);
			List dayOffWorker = weekSchedule.get(i).dayOffWorker;
			List morningWorker = weekSchedule.get(i).morningWorker;
			List afternoonWorker = weekSchedule.get(i).afternoonWorker;
			System.out.print("���� �޽��� ��� : ");
			for(Object object:dayOffWorker) {
				System.out.print(object+", ");
			}
			System.out.print("//// ���� ���ϴ� ���� ��� : ");
			for(Object object:morningWorker) {
				System.out.print(object+", ");
			}
			System.out.print("//// ���� ���ϴ� ���� ��� : ");
			for(Object object:afternoonWorker) {
				System.out.print(object+", ");
			}
			System.out.println();
		}
		System.out.println("/////////////////////////////");
	}
		

	/*	
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
		
		*/

}

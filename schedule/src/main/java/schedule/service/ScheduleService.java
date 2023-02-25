package schedule.service;

import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import schedule.dto.ScheduleDto;

public class ScheduleService {
	String[] weekDay = {"월요일","화요일","수요일","목요일","금요일","토요일","일요일"};
	//String startScheduleDay = "20230219"; //시작날짜 입력받음
	String startScheduleDay; //시작날짜 입력받음
	//int[] morningPeople =   {3,2,3,2,3,3,3}; // 월-일 입력 받음
	int[] morningPeople; // 월-일 입력 받음
	//int[] afternoonPeople = {2,2,2,2,2,3,3}; // 월-일 입력 받음
	int[] afternoonPeople; // 월-일 입력 받음
	//int[] dayOffPeople =    {2,3,2,3,2,1,1}; // 월-일 쉬는 사람
	int[] dayOffPeople; // 월-일 쉬는 사람

	List<List<ScheduleDto>> monthSchedule;
	
	String[] person = {"아리엘","렐라","엘사","벨","백설이","안나","자스민"}; //사람이름
	int numberOfPerson = 7; //전체사람수
	
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
		SimpleDateFormat formatterWeek = new SimpleDateFormat("yyyy/MM/dd/E요일");
		Calendar calendar = getCalendar();
		changeStartWeek(formatterWeek.format(calendar.getTime()).split("/")[3]);//시작요일을 기점으로 순서 변경
		
		monthSchedule = new ArrayList();
		
		for(int i=0;i<4;i++) {
			List<ScheduleDto> weekSchedule = new ArrayList();
			
			//일주일 받기
			for(int j=0;j<7;j++) {
				ScheduleDto scheduleDto = new ScheduleDto();
				scheduleDto.today = formatterWeek.format(calendar.getTime()).split("/");
				calendar.add(Calendar.DATE,1);
				weekSchedule.add(scheduleDto);
			}
		
			//////////////////일주일 시간표 설정//////////////////////
			//쉬는날 설정
			List<List> weekScheduleDayOffWorkerList = getDayOffShedule();
			for(int j=0;j<7;j++) weekSchedule.get(j).dayOffWorker = weekScheduleDayOffWorkerList.get(j);
			
			//일하는날 설정
			int[] work   = {5,5,5,5,5,5,5}; //한 사람당 일해야하는 양
			Map<String, List> weekScheduleWorkWorkerList = getWorkShedule(weekScheduleDayOffWorkerList);
			List<List> weekScheduleMorningWorkList = weekScheduleWorkWorkerList.get("weekScheduleMorningWork");
			List<List> weekScheduleAfternoonWorkList = weekScheduleWorkWorkerList.get("weekScheduleAfternoonWork");
			for(int j=0;j<7;j++) {
				weekSchedule.get(j).morningWorker = weekScheduleMorningWorkList.get(j);
				weekSchedule.get(j).afternoonWorker = weekScheduleAfternoonWorkList.get(j);
			}
			
			weekSchedulelogTest(weekSchedule);//일주일 쉬는 사람 로그 확인
						
			//한달 시간표에 일주일 시간표 넣기
			monthSchedule.add(weekSchedule);			
		}
		excelMake();
		
	}
	
	private void excelMake() {
		
		for(int i=0;i<1;i++) {
			//.xls 확장자 지원
			HSSFWorkbook wb = null;
			HSSFSheet sheet = null;
			Row row = null;
			Cell cell = null;
			
			//.xlsx 확장자 지원
			XSSFWorkbook xssfWb = null; // .xlsx
			XSSFSheet xssfSheet = null; // .xlsx
			XSSFRow xssfRow = null; // .xlsx
			XSSFCell xssfCell = null;// .xlsx
				
				try {
				int rowNo = 0; // 행 갯수 
				// 워크북 생성
				xssfWb = new XSSFWorkbook();
				xssfSheet = xssfWb.createSheet("엑셀 테스트"); // 워크시트 이름
				
				//헤더용 폰트 스타일
				XSSFFont font = xssfWb.createFont();
				font.setFontName(HSSFFont.FONT_ARIAL); //폰트스타일
				font.setFontHeightInPoints((short)14); //폰트크기
				font.setBold(true); //Bold 유무
				
				//테이블 타이틀 스타일
				CellStyle cellStyle_Title = xssfWb.createCellStyle();
				cellStyle_Title.setFont(font); // cellStle에 font를 적용
				cellStyle_Title.setAlignment(HorizontalAlignment.CENTER); // 정렬
				
				//셀병합
				xssfSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8)); //첫행, 마지막행, 첫열, 마지막열( 0번째 행의 0~8번째 컬럼을 병합한다)
				//타이틀 생성
				xssfRow = xssfSheet.createRow(rowNo++); //행 객체 추가
				xssfCell = xssfRow.createCell((short) 0); // 추가한 행에 셀 객체 추가
				xssfCell.setCellStyle(cellStyle_Title); // 셀에 스타일 지정
				xssfCell.setCellValue("Schedule"); // 데이터 입력
				
				CellStyle cellStyle_Body = xssfWb.createCellStyle(); 
				cellStyle_Body.setAlignment(HorizontalAlignment.LEFT); 
				
				xssfRow = xssfSheet.createRow(rowNo++);  // 요일 추가
				for(int col=1;col<8;col++) {
					xssfSheet.setColumnWidth(col, (xssfSheet.getColumnWidth(col))+(short)2048);
					XSSFCell dayOftheWeekRow = xssfRow.createCell((short) col);
					dayOftheWeekRow.setCellStyle(cellStyle_Body);
					dayOftheWeekRow.setCellValue(weekDay[col-1]);
				}
				
				for(int oneWeek=0;oneWeek<4;oneWeek++) {
					XSSFRow xssfRowDay = xssfSheet.createRow(rowNo++);  // 요일 추가
					XSSFRow xssfRowMorning = xssfSheet.createRow(rowNo++); //오전
					XSSFRow xssfRowAfternoon = xssfSheet.createRow(rowNo++); //오후
					XSSFRow xssfRowDayoff = xssfSheet.createRow(rowNo++); //쉬는날
					
					XSSFCell xssfCellDayRow = xssfRowDay.createCell((short) 0);
					xssfCellDayRow.setCellStyle(cellStyle_Body);
					xssfCellDayRow.setCellValue("");
					XSSFCell xssfCellMorning = xssfRowMorning.createCell((short) 0);
					xssfCellMorning.setCellStyle(cellStyle_Body);
					xssfCellMorning.setCellValue("오전");
					XSSFCell xssfCellAfternoon = xssfRowAfternoon.createCell((short) 0);
					xssfCellAfternoon.setCellStyle(cellStyle_Body);
					xssfCellAfternoon.setCellValue("오후");
					XSSFCell xssfCellDayoff = xssfRowDayoff.createCell((short) 0);
					xssfCellDayoff.setCellStyle(cellStyle_Body);
					xssfCellDayoff.setCellValue("쉬는 날");
					
					for(int oneDayCol=0,col=1;oneDayCol<7;oneDayCol++,col++) {
						xssfCellMorning = xssfRowMorning.createCell((short) col);
						xssfCellMorning.setCellValue(monthSchedule.get(oneWeek).get(oneDayCol).morningWorker.toString());
						xssfCellAfternoon = xssfRowAfternoon.createCell((short) col);
						xssfCellAfternoon.setCellValue(monthSchedule.get(oneWeek).get(oneDayCol).afternoonWorker.toString());
						xssfCellDayRow = xssfRowDay.createCell((short) col);
						xssfCellDayRow.setCellValue(getTodayCol(monthSchedule.get(oneWeek).get(oneDayCol).today));
						xssfCellDayoff = xssfRowDayoff.createCell((short) col);
						xssfCellDayoff.setCellValue(monthSchedule.get(oneWeek).get(oneDayCol).dayOffWorker.toString());						
					}
					
				}

				//테이블 스타일 설정
				CellStyle cellStyle_Table_Center = xssfWb.createCellStyle();
				cellStyle_Table_Center.setBorderTop(BorderStyle.THIN); //테두리 위쪽
				cellStyle_Table_Center.setBorderBottom(BorderStyle.THIN); //테두리 아래쪽
				cellStyle_Table_Center.setBorderLeft(BorderStyle.THIN); //테두리 왼쪽
				cellStyle_Table_Center.setBorderRight(BorderStyle.THIN); //테두리 오른쪽
				cellStyle_Table_Center.setAlignment(HorizontalAlignment.CENTER);
				
				String localFile = "C:\\Users\\ariel\\Downloads\\" + "테스트_엑셀" + ".xlsx";
				
				File file = new File(localFile);
				FileOutputStream fos = null;
				fos = new FileOutputStream(file);
				xssfWb.write(fos);

				if (xssfWb != null)	xssfWb.close();
				if (fos != null) fos.close();
				System.out.println("이건 완료야");

				}
				catch(Exception e){
					System.out.println(e);
				}finally{
					
			    }
		}
	}


	private String getTodayCol(String[] today) {
		return today[0]+"-"+today[1]+"-"+today[2];
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
			String weekDayFirst =   weekDay[0];
			int morningFirst =   morningPeople[0];
			int afternoonFirst = afternoonPeople[0];
			int dayOffFirst = dayOffPeople[0];
			for(int j=0;j<6;j++) {
				weekDay[j]=weekDay[j+1];
				morningPeople[j]=morningPeople[j+1];
				afternoonPeople[j]=afternoonPeople[j+1];
				dayOffPeople[j]=dayOffPeople[j+1];
			}
			weekDay[6]=weekDayFirst;
			morningPeople[6]=morningFirst;
			afternoonPeople[6]=afternoonFirst;
			dayOffPeople[6]=dayOffFirst;
		}
	}
	
	public List<List> getDayOffShedule() {
		int[] dayOff; //한 사람당 쉬어야하는 양
		boolean dayOffOverlapPerson = false;
		List<List> weekScheduleDayOffWorkerList = null;
		while(!dayOffOverlapPerson) {
			dayOffOverlapPerson=true;
			dayOff = new int[]{2,2,2,2,2,2,2};
			weekScheduleDayOffWorkerList = new ArrayList<>();
			List weekScheduleDayOffWorker;
			for(int j=0;j<7;j++) {
				weekScheduleDayOffWorker = new ArrayList<>();
				//쉬는 날 설정
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
		int[] work; //한 사람당 일해야하는 양
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
				//오전 설정
				while(weekScheduleMorningWork.size()<morningPeople[j]) {
					int workWorker = (int) (Math.random() * numberOfPerson);
					if(!usePersonCheck[workWorker] && work[workWorker]>0) {
						work[workWorker]--;
						weekScheduleMorningWork.add(person[workWorker]);
						usePersonCheck[workWorker] = true;
					}
				}
				
				//오후 설정
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
		Map<String, List> weekScheduleWorkWorkerList = new HashMap<>(); //오전, 오후 넣기
		weekScheduleWorkWorkerList.put("weekScheduleMorningWork",weekScheduleMorningWorkList);
		weekScheduleWorkWorkerList.put("weekScheduleAfternoonWork",weekScheduleAfternoonWorkList);
		return weekScheduleWorkWorkerList;
	}
	
	public void weekSchedulelogTest(List<ScheduleDto> weekSchedule) {
		for(int i=0;i<7;i++) {
			System.out.println("오늘 날짜  : "+weekSchedule.get(i).today[1]+"/"+
					   weekSchedule.get(i).today[2]+"/"+ weekSchedule.get(i).today[3]);
			List dayOffWorker = weekSchedule.get(i).dayOffWorker;
			List morningWorker = weekSchedule.get(i).morningWorker;
			List afternoonWorker = weekSchedule.get(i).afternoonWorker;
			System.out.print("오늘 휴식인 사람 : ");
			for(Object object:dayOffWorker) {
				System.out.print(object+", ");
			}
			System.out.print("//// 오늘 일하는 오전 사람 : ");
			for(Object object:morningWorker) {
				System.out.print(object+", ");
			}
			System.out.print("//// 오늘 일하는 오후 사람 : ");
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

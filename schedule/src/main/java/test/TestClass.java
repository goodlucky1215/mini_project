package test;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

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

public class TestClass {
	
	String[] weekDay = {"월요일","화요일","수요일","목요일","금요일","토요일","일요일"};
	String[] today =  {"2023","02","13","수요일"};
	List<List<ScheduleDto>> monthSchedule;

	private void excelMake() {
		List<ScheduleDto> scheduleDtos = new ArrayList();
		monthSchedule = new ArrayList();
		ScheduleDto scheduleDto = new ScheduleDto();
		scheduleDto.today = this.today;
		scheduleDto.dayOffWorker.add("a");
		scheduleDto.morningWorker.add("b");
		scheduleDto.morningWorker.add("c");
		scheduleDto.afternoonWorker.add("f");
		scheduleDto.afternoonWorker.add("g");
		scheduleDtos.add(scheduleDto);
		monthSchedule.add(scheduleDtos);
		
		for(int i=0;i<1;i++) {
			List<ScheduleDto> weekSchedule = monthSchedule.get(i);
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
				
				xssfSheet.setColumnWidth(3, (xssfSheet.getColumnWidth(3))+(short)2048); // 3번째 컬럼 넓이 조절
				xssfSheet.setColumnWidth(4, (xssfSheet.getColumnWidth(4))+(short)2048); // 4번째 컬럼 넓이 조절
				xssfSheet.setColumnWidth(5, (xssfSheet.getColumnWidth(5))+(short)2048); // 5번째 컬럼 넓이 조절
				
				xssfSheet.setColumnWidth(8, (xssfSheet.getColumnWidth(8))+(short)4096); // 8번째 컬럼 넓이 조절
				
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
					XSSFCell dayOftheWeekRow = xssfRow.createCell((short) col);
					dayOftheWeekRow.setCellStyle(cellStyle_Body);
					dayOftheWeekRow.setCellValue(weekDay[col-1]);
				}
				
				//헤더 생성
				xssfSheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, 0, 1)); //첫행,마지막행,첫열,마지막열
				for(int oneWeek=0;oneWeek<4;oneWeek++) {
					xssfRow = xssfSheet.createRow(rowNo++);  // 빈행 추가
					XSSFRow xssfRowMorning = xssfSheet.createRow(rowNo++); //오전
					XSSFRow xssfRowAfternoon = xssfSheet.createRow(rowNo++); //오후
					XSSFRow xssfRowDayoff = xssfSheet.createRow(rowNo++); //쉬는날
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
						xssfCellMorning.setCellStyle(cellStyle_Body);
						xssfCellMorning.setCellValue(scheduleDto.morningWorker.toString());
						xssfCellAfternoon = xssfRowAfternoon.createCell((short) col);
						xssfCellAfternoon.setCellStyle(cellStyle_Body);
						xssfCellAfternoon.setCellValue(scheduleDto.afternoonWorker.toString());
						xssfCellDayoff = xssfRowDayoff.createCell((short) col);
						xssfCellDayoff.setCellStyle(cellStyle_Body);
						xssfCellDayoff.setCellValue(scheduleDto.dayOffWorker.toString());						
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
	
	public static void main(String[] args) {
		new TestClass().excelMake();
	}
}

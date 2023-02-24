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
	
	String[] weekDay = {"������","ȭ����","������","�����","�ݿ���","�����","�Ͽ���"};
	String[] today =  {"2023","02","13","������"};
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
			//.xls Ȯ���� ����
			HSSFWorkbook wb = null;
			HSSFSheet sheet = null;
			Row row = null;
			Cell cell = null;
			
			//.xlsx Ȯ���� ����
			XSSFWorkbook xssfWb = null; // .xlsx
			XSSFSheet xssfSheet = null; // .xlsx
			XSSFRow xssfRow = null; // .xlsx
			XSSFCell xssfCell = null;// .xlsx
				
				try {
				int rowNo = 0; // �� ���� 
				// ��ũ�� ����
				xssfWb = new XSSFWorkbook();
				xssfSheet = xssfWb.createSheet("���� �׽�Ʈ"); // ��ũ��Ʈ �̸�
				
				//����� ��Ʈ ��Ÿ��
				XSSFFont font = xssfWb.createFont();
				font.setFontName(HSSFFont.FONT_ARIAL); //��Ʈ��Ÿ��
				font.setFontHeightInPoints((short)14); //��Ʈũ��
				font.setBold(true); //Bold ����
				
				//���̺� Ÿ��Ʋ ��Ÿ��
				CellStyle cellStyle_Title = xssfWb.createCellStyle();
				
				xssfSheet.setColumnWidth(3, (xssfSheet.getColumnWidth(3))+(short)2048); // 3��° �÷� ���� ����
				xssfSheet.setColumnWidth(4, (xssfSheet.getColumnWidth(4))+(short)2048); // 4��° �÷� ���� ����
				xssfSheet.setColumnWidth(5, (xssfSheet.getColumnWidth(5))+(short)2048); // 5��° �÷� ���� ����
				
				xssfSheet.setColumnWidth(8, (xssfSheet.getColumnWidth(8))+(short)4096); // 8��° �÷� ���� ����
				
				cellStyle_Title.setFont(font); // cellStle�� font�� ����
				cellStyle_Title.setAlignment(HorizontalAlignment.CENTER); // ����
				
				//������
				xssfSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8)); //ù��, ��������, ù��, ��������( 0��° ���� 0~8��° �÷��� �����Ѵ�)
				//Ÿ��Ʋ ����
				xssfRow = xssfSheet.createRow(rowNo++); //�� ��ü �߰�
				xssfCell = xssfRow.createCell((short) 0); // �߰��� �࿡ �� ��ü �߰�
				xssfCell.setCellStyle(cellStyle_Title); // ���� ��Ÿ�� ����
				xssfCell.setCellValue("Schedule"); // ������ �Է�
				
				CellStyle cellStyle_Body = xssfWb.createCellStyle(); 
				cellStyle_Body.setAlignment(HorizontalAlignment.LEFT); 
				
				xssfRow = xssfSheet.createRow(rowNo++);  // ���� �߰�
				for(int col=1;col<8;col++) {
					XSSFCell dayOftheWeekRow = xssfRow.createCell((short) col);
					dayOftheWeekRow.setCellStyle(cellStyle_Body);
					dayOftheWeekRow.setCellValue(weekDay[col-1]);
				}
				
				//��� ����
				xssfSheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, 0, 1)); //ù��,��������,ù��,��������
				for(int oneWeek=0;oneWeek<4;oneWeek++) {
					xssfRow = xssfSheet.createRow(rowNo++);  // ���� �߰�
					XSSFRow xssfRowMorning = xssfSheet.createRow(rowNo++); //����
					XSSFRow xssfRowAfternoon = xssfSheet.createRow(rowNo++); //����
					XSSFRow xssfRowDayoff = xssfSheet.createRow(rowNo++); //���³�
					XSSFCell xssfCellMorning = xssfRowMorning.createCell((short) 0);
					xssfCellMorning.setCellStyle(cellStyle_Body);
					xssfCellMorning.setCellValue("����");
					XSSFCell xssfCellAfternoon = xssfRowAfternoon.createCell((short) 0);
					xssfCellAfternoon.setCellStyle(cellStyle_Body);
					xssfCellAfternoon.setCellValue("����");
					XSSFCell xssfCellDayoff = xssfRowDayoff.createCell((short) 0);
					xssfCellDayoff.setCellStyle(cellStyle_Body);
					xssfCellDayoff.setCellValue("���� ��");
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

				//���̺� ��Ÿ�� ����
				CellStyle cellStyle_Table_Center = xssfWb.createCellStyle();
				cellStyle_Table_Center.setBorderTop(BorderStyle.THIN); //�׵θ� ����
				cellStyle_Table_Center.setBorderBottom(BorderStyle.THIN); //�׵θ� �Ʒ���
				cellStyle_Table_Center.setBorderLeft(BorderStyle.THIN); //�׵θ� ����
				cellStyle_Table_Center.setBorderRight(BorderStyle.THIN); //�׵θ� ������
				cellStyle_Table_Center.setAlignment(HorizontalAlignment.CENTER);
				
				String localFile = "C:\\Users\\ariel\\Downloads\\" + "�׽�Ʈ_����" + ".xlsx";
				
				File file = new File(localFile);
				FileOutputStream fos = null;
				fos = new FileOutputStream(file);
				xssfWb.write(fos);

				if (xssfWb != null)	xssfWb.close();
				if (fos != null) fos.close();
				System.out.println("�̰� �Ϸ��");

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

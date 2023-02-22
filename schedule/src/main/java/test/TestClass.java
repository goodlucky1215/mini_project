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
				xssfCell.setCellValue("Ÿ��Ʋ �Դϴ�."); // ������ �Է�
				
				xssfRow = xssfSheet.createRow(rowNo++);  // ���� �߰�
				
				CellStyle cellStyle_Body = xssfWb.createCellStyle(); 
				cellStyle_Body.setAlignment(HorizontalAlignment.LEFT); 
				
				//��� ����
				xssfSheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, 0, 1)); //ù��,��������,ù��,��������
				xssfRow = xssfSheet.createRow(rowNo++); //��� 01
				xssfCell = xssfRow.createCell((short) 0);
				xssfCell.setCellStyle(cellStyle_Body);
				xssfCell.setCellValue("���01 ��01");
				xssfCell = xssfRow.createCell((short) 8);
				xssfCell.setCellStyle(cellStyle_Body);
				xssfCell.setCellValue("���01 ��08");
				xssfRow = xssfSheet.createRow(rowNo++); //��� 02
				xssfCell = xssfRow.createCell((short) 0);
				xssfCell.setCellStyle(cellStyle_Body);
				xssfCell.setCellValue("���02 ��01");
				xssfCell = xssfRow.createCell((short) 8);
				xssfCell.setCellStyle(cellStyle_Body);
				xssfCell.setCellValue("���02 ��08");
				xssfRow = xssfSheet.createRow(rowNo++); //��� 03
				xssfCell = xssfRow.createCell((short) 0);
				xssfCell.setCellStyle(cellStyle_Body);
				xssfCell.setCellValue("���03 ��01");
				xssfCell = xssfRow.createCell((short) 8);
				xssfCell.setCellStyle(cellStyle_Body);
				xssfCell.setCellValue("���03 ��08");
				xssfRow = xssfSheet.createRow(rowNo++); //��� 04
				xssfCell = xssfRow.createCell((short) 0);
				xssfCell.setCellStyle(cellStyle_Body);
				xssfCell.setCellValue("���04 ��01");
				xssfCell = xssfRow.createCell((short) 8);
				xssfCell.setCellStyle(cellStyle_Body);
				xssfCell.setCellValue("���04 ��08");
				
				//���̺� ��Ÿ�� ����
				CellStyle cellStyle_Table_Center = xssfWb.createCellStyle();
				cellStyle_Table_Center.setBorderTop(BorderStyle.THIN); //�׵θ� ����
				cellStyle_Table_Center.setBorderBottom(BorderStyle.THIN); //�׵θ� �Ʒ���
				cellStyle_Table_Center.setBorderLeft(BorderStyle.THIN); //�׵θ� ����
				cellStyle_Table_Center.setBorderRight(BorderStyle.THIN); //�׵θ� ������
				cellStyle_Table_Center.setAlignment(HorizontalAlignment.CENTER);
				
				xssfRow = xssfSheet.createRow(rowNo++);
				xssfCell = xssfRow.createCell((short) 0);
				xssfCell.setCellStyle(cellStyle_Table_Center);
				xssfCell.setCellValue("���̺� ��1");
				xssfCell = xssfRow.createCell((short) 1);
				xssfCell.setCellStyle(cellStyle_Table_Center);
				xssfCell.setCellValue("���̺� ��2");
				xssfCell = xssfRow.createCell((short) 2);
				xssfCell.setCellStyle(cellStyle_Table_Center);
				xssfCell.setCellValue("���̺� ��3");
				xssfCell = xssfRow.createCell((short) 3);
				xssfCell.setCellStyle(cellStyle_Table_Center);
				xssfCell.setCellValue("���̺� ��4");
				xssfCell = xssfRow.createCell((short) 4);
				xssfCell.setCellStyle(cellStyle_Table_Center);
				xssfCell.setCellValue("���̺� ��5");
				xssfCell = xssfRow.createCell((short) 5);
				xssfCell.setCellStyle(cellStyle_Table_Center);
				xssfCell.setCellValue("���̺� ��6");
				xssfCell = xssfRow.createCell((short) 6);
				xssfCell.setCellStyle(cellStyle_Table_Center);
				xssfCell.setCellValue("���̺� ��7");
				xssfCell = xssfRow.createCell((short) 7);
				xssfCell.setCellStyle(cellStyle_Table_Center);
				xssfCell.setCellValue("���̺� ��8");
				xssfCell = xssfRow.createCell((short) 8);
				xssfCell.setCellStyle(cellStyle_Table_Center);
				xssfCell.setCellValue("���̺� ��9");
				
				String localFile = "�ٿ�ε�\\" + "�׽�Ʈ_����" + ".xlsx";
				
				File file = new File(localFile);
				FileOutputStream fos = null;
				fos = new FileOutputStream(file);
				xssfWb.write(fos);

				if (xssfWb != null)	xssfWb.close();
				if (fos != null) fos.close();
				
				//ctx.put("FILENAME", "�԰�����_"+ mapList.get(0).get("PRINT_DATE"));
				//if(file != null) file.deleteOnExit();
				}
				catch(Exception e){
		        	
				}finally{
					
			    }
		}
	}
	
	public static void main(String[] args) {
		new TestClass().excelMake();
	}
}

package schedule.view;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Panel;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class View extends JFrame{
	
	//	String startScheduleDay = "20230219"; //시작날짜 입력받음
	//int[] morningPeople =   {3,2,3,2,3,3,3}; // 월-일 입력 받음
	//int[] afternoonPeople = {2,2,2,2,2,3,3}; // 월-일 입력 받음
	//int[] dayOffPeople =    {2,3,2,3,2,1,1}; // 월-일 쉬는 사람
	
	String cols[] = {"","월","화","수","목","금","토","일"}     ; // 컬럼에 헤더 값
	JTextField day;
	JButton button;
	// 화면
	public View(String str){
		super(str);
		//날짜 입력
		JPanel dayPanel =new JPanel(); 
		this.add(dayPanel,"Center");
				
		day = new JTextField(10);
		day.setBounds(12, 340, 560, 25); // 채팅 입력창 위치지정
		dayPanel.add(new JLabel("시작 날짜를 입력하세요.", JLabel.LEFT));
		dayPanel.add(day);
		dayPanel.add(new JLabel("ex) 20230101"));
		dayPanel.add(new JLabel("7명 기준 : 근무 총 합 35, 쉬는 수 총 합 14"));
		//시간표 테이블
	    String data[][] = new String[3][8];
	    data[0][0] = "오전 사람 수";
	    data[1][0] = "오후 사람 수";
	    data[2][0] = "쉬는 사람 수";
		JTable jtm = new JTable();
	    jtm.setModel(new DefaultTableModel(data,cols));
	    TableColumn col = jtm.getColumnModel().getColumn(0);
	    col.setPreferredWidth(120);
	    JScrollPane spTable = new JScrollPane(jtm);
	    dayPanel.add(spTable);
	    
	    //엑셀 만드는 버튼
	    dayPanel.add(button = new JButton("전송")); // 전송버튼 생성 및 부착


		// 윈도우창 띄우기
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(500, 600);
		this.setVisible(true);
	}
	
	public static void main(String args[]){
		// 버튼 생성자
		new View("스케줄 화면");
	}
	
}
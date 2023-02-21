package schedule.view;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import schedule.controller.SheduleController;

public class View extends JFrame{
	
	//	String startScheduleDay = "20230219"; //시작날짜 입력받음
	//int[] morningPeople =   {3,2,3,2,3,3,3}; // 월-일 입력 받음
	//int[] afternoonPeople = {2,2,2,2,2,3,3}; // 월-일 입력 받음
	//int[] dayOffPeople =    {2,3,2,3,2,1,1}; // 월-일 쉬는 사람
	
	String cols[] = {"","월","화","수","목","금","토","일"}     ; // 컬럼에 헤더 값
	JTable jtm;
	JTextField day; //시작 날 입력
	String scheduleData[][]; //시간 입력 
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
		dayPanel.add(new JLabel("[마지막 값 입력 후 마우스로 다른 값을 한 번 눌러주세요]"));
		//시간표 테이블
		scheduleData = new String[3][8];
	    scheduleData[0][0] = "오전 사람 수";
	    scheduleData[1][0] = "오후 사람 수";
	    scheduleData[2][0] = "쉬는 사람 수";
		jtm = new JTable();
	    jtm.setModel(new DefaultTableModel(scheduleData,cols));
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
		addListener();
	}
	
    private void addListener() {
        
        // ActionListener 객체를 생성하고
        // 인터페이스 안에 있는 메소드를 사용하기 위해
        // @Override을 사용합니다.
        // actionPerformed 라고 적힌 이 메소드안에
        // 해당 Component를 선택했을때, 실행되는
        // 코드를 작성하시면 됩니다.
        
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                
                // button를 선택했을때 
                if(button.equals(ae.getSource())){
                	try {
						new SheduleController().makeShedule(day.getText(),getscheduleData());
					} catch (ParseException e) {
						/*
						 //parent Frame을 f로 하고, modal을 true로 해서 필수 응답 dialog로 함.

			             Dialog info = new Dialog(f, "Information", true);
			
			             info.setSize(140,90);
			
			             //parent Frame이 아닌, 화면이 위치의 기준이 된다.
			
			             info.setLocation(50,50);
			
			             info.setLayout(new FlowLayout());
			
			            
			
			             Label msg = new Label("This is modal Dialog", Label.CENTER);
			
			             Button ok = new Button("OK");
			
			             info.add(msg);
			
			             info.add(ok);
			
			            
			
			             f.setVisible(true);
			
			             //Dialog를 화면에 보이게 한다.
			
			             info.setVisible(true);
						 * */
						e.printStackTrace();
					}
                }
            }
        };
        
        // 위에서 해당 Component를 실행했을때,
        // 코드를 작성했다면 마무리로
        // addActionListener(listener); 추가 필요
        // 이를 추가하지 않을시에는 해당 액션이 발생하지 않습니다.       
        button.addActionListener(listener);
    }
	
	private String[][] getscheduleData() {
		String resultScheduleData[][] = new String[3][7];
		for(int i=0;i<3;i++) {
			for(int j=0;j<7;j++) {
				resultScheduleData[i][j]=(String) jtm.getModel().getValueAt(i, j+1);
			}
		}
		return resultScheduleData;
	}
	
	public static void main(String args[]){
		// 버튼 생성자
		new View("스케줄 화면");
	}
	
}
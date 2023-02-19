package schedule.view;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Panel;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class View extends JFrame{
	
	//	String startScheduleDay = "20230219"; //시작날짜 입력받음
	//int[] morningPeople =   {3,2,3,2,3,3,3}; // 월-일 입력 받음
	//int[] afternoonPeople = {2,2,2,2,2,3,3}; // 월-일 입력 받음
	//int[] dayOffPeople =    {2,3,2,3,2,1,1}; // 월-일 쉬는 사람
	
	// 버튼 변수 선언
	Button btn1, btn2, btn3;
	// 버튼 생성자
	public View(String str){
		super(str);
		//날짜 입력
		JPanel dayPanel =new JPanel();
		dayPanel.setLayout(new GridLayout(1,3)); 
		JTextField day = new JTextField(10); 
		dayPanel.add(new JLabel("시작 날짜를 입력하세요.", JLabel.LEFT));
		dayPanel.add(day);
		dayPanel.add(new JLabel("ex) 20230101"));
		dayPanel.setToolTipText("ID를 입력하세요");
		dayPanel.setSize(new Dimension(200,100));
		
		JPanel weekPanel =new JPanel();
		weekPanel.setLayout(new GridLayout(3,7)); 
		weekPanel.add(new JLabel("월", JLabel.LEFT));
		weekPanel.add(new JLabel("월", JLabel.LEFT));
		weekPanel.add(new JLabel("월", JLabel.LEFT));
		weekPanel.add(new JLabel("월", JLabel.LEFT));
		weekPanel.add(new JLabel("월", JLabel.LEFT));
		weekPanel.add(new JLabel("월", JLabel.LEFT));
		weekPanel.add(new JLabel("월", JLabel.LEFT));
		weekPanel.add(new JLabel("월", JLabel.LEFT));

		
		
		

/*
		
		Panel p = new Panel();
		Panel p2 = new Panel();
		// 가위 버튼 생성
		btn1 = new Button(" 가위 ");
		// 바위 버튼 생성
		btn2 = new Button(" 바위 ");
		// 보 버튼 생성
		btn3 = new Button("  보  ");
		// 패널에 3가지 버튼 생성 -> 이거 해야 화면에 보임
		p.add(btn1); p.add(btn2); p2.add(btn3);
		btn3.setEnabled(false); //버튼 비활성화 여부 지정
		*/
		
		getContentPane().add(dayPanel,"Center");
		getContentPane().add(weekPanel,"South");
		// 윈도우창 띄우기
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(800, 800);
		this.setVisible(true);
	}
	
	public static void main(String args[]){
		// 버튼 생성자
		new View("스케줄 화면");
	}
}
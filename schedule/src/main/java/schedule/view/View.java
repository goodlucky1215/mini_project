package schedule.view;

import java.awt.Button;
import java.awt.Frame;
import java.awt.Panel;

public class View extends Frame{
	
	//	String startScheduleDay = "20230219"; //시작날짜 입력받음
	//int[] morningPeople =   {3,2,3,2,3,3,3}; // 월-일 입력 받음
	//int[] afternoonPeople = {2,2,2,2,2,3,3}; // 월-일 입력 받음
	//int[] dayOffPeople =    {2,3,2,3,2,1,1}; // 월-일 쉬는 사람
	
	// 버튼 변수 선언
	Button btn1, btn2, btn3;
	// 버튼 생성자
	public View(String str){
		super(str);
		// 버튼을 생성하기위해 패널 생성
		Panel p = new Panel();
		// 가위 버튼 생성
		btn1 = new Button(" 가위 ");
		// 바위 버튼 생성
		btn2 = new Button(" 바위 ");
		// 보 버튼 생성
		btn3 = new Button("  보  ");
		// 패널에 3가지 버튼 생성 -> 이거 해야 화면에 보임
		p.add(btn1); p.add(btn2); p.add(btn3);
		add(p);
		btn3.setEnabled(false); //버튼 비활성화 여부 지정
		setSize(800, 800);
		// 윈도우창 띄우기
		setVisible(true);
	}
	
	public static void main(String args[]){
		// 버튼 생성자
		new View("스케줄 화면");
	}
}
package schedule.view;

import java.awt.Button;
import java.awt.Frame;
import java.awt.Panel;

public class View extends Frame{
	
	//	String startScheduleDay = "20230219"; //���۳�¥ �Է¹���
	//int[] morningPeople =   {3,2,3,2,3,3,3}; // ��-�� �Է� ����
	//int[] afternoonPeople = {2,2,2,2,2,3,3}; // ��-�� �Է� ����
	//int[] dayOffPeople =    {2,3,2,3,2,1,1}; // ��-�� ���� ���
	
	// ��ư ���� ����
	Button btn1, btn2, btn3;
	// ��ư ������
	public View(String str){
		super(str);
		// ��ư�� �����ϱ����� �г� ����
		Panel p = new Panel();
		// ���� ��ư ����
		btn1 = new Button(" ���� ");
		// ���� ��ư ����
		btn2 = new Button(" ���� ");
		// �� ��ư ����
		btn3 = new Button("  ��  ");
		// �гο� 3���� ��ư ���� -> �̰� �ؾ� ȭ�鿡 ����
		p.add(btn1); p.add(btn2); p.add(btn3);
		add(p);
		btn3.setEnabled(false); //��ư ��Ȱ��ȭ ���� ����
		setSize(800, 800);
		// ������â ����
		setVisible(true);
	}
	
	public static void main(String args[]){
		// ��ư ������
		new View("������ ȭ��");
	}
}
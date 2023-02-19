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
	
	//	String startScheduleDay = "20230219"; //���۳�¥ �Է¹���
	//int[] morningPeople =   {3,2,3,2,3,3,3}; // ��-�� �Է� ����
	//int[] afternoonPeople = {2,2,2,2,2,3,3}; // ��-�� �Է� ����
	//int[] dayOffPeople =    {2,3,2,3,2,1,1}; // ��-�� ���� ���
	
	// ��ư ���� ����
	Button btn1, btn2, btn3;
	// ��ư ������
	public View(String str){
		super(str);
		//��¥ �Է�
		JPanel dayPanel =new JPanel();
		dayPanel.setLayout(new GridLayout(1,3)); 
		JTextField day = new JTextField(10); 
		dayPanel.add(new JLabel("���� ��¥�� �Է��ϼ���.", JLabel.LEFT));
		dayPanel.add(day);
		dayPanel.add(new JLabel("ex) 20230101"));
		dayPanel.setToolTipText("ID�� �Է��ϼ���");
		dayPanel.setSize(new Dimension(200,100));
		
		JPanel weekPanel =new JPanel();
		weekPanel.setLayout(new GridLayout(3,7)); 
		weekPanel.add(new JLabel("��", JLabel.LEFT));
		weekPanel.add(new JLabel("��", JLabel.LEFT));
		weekPanel.add(new JLabel("��", JLabel.LEFT));
		weekPanel.add(new JLabel("��", JLabel.LEFT));
		weekPanel.add(new JLabel("��", JLabel.LEFT));
		weekPanel.add(new JLabel("��", JLabel.LEFT));
		weekPanel.add(new JLabel("��", JLabel.LEFT));
		weekPanel.add(new JLabel("��", JLabel.LEFT));

		
		
		

/*
		
		Panel p = new Panel();
		Panel p2 = new Panel();
		// ���� ��ư ����
		btn1 = new Button(" ���� ");
		// ���� ��ư ����
		btn2 = new Button(" ���� ");
		// �� ��ư ����
		btn3 = new Button("  ��  ");
		// �гο� 3���� ��ư ���� -> �̰� �ؾ� ȭ�鿡 ����
		p.add(btn1); p.add(btn2); p2.add(btn3);
		btn3.setEnabled(false); //��ư ��Ȱ��ȭ ���� ����
		*/
		
		getContentPane().add(dayPanel,"Center");
		getContentPane().add(weekPanel,"South");
		// ������â ����
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(800, 800);
		this.setVisible(true);
	}
	
	public static void main(String args[]){
		// ��ư ������
		new View("������ ȭ��");
	}
}
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
	
	//	String startScheduleDay = "20230219"; //���۳�¥ �Է¹���
	//int[] morningPeople =   {3,2,3,2,3,3,3}; // ��-�� �Է� ����
	//int[] afternoonPeople = {2,2,2,2,2,3,3}; // ��-�� �Է� ����
	//int[] dayOffPeople =    {2,3,2,3,2,1,1}; // ��-�� ���� ���
	
	String cols[] = {"","��","ȭ","��","��","��","��","��"}     ; // �÷��� ��� ��
	JTextField day;
	JButton button;
	// ȭ��
	public View(String str){
		super(str);
		//��¥ �Է�
		JPanel dayPanel =new JPanel(); 
		this.add(dayPanel,"Center");
				
		day = new JTextField(10);
		day.setBounds(12, 340, 560, 25); // ä�� �Է�â ��ġ����
		dayPanel.add(new JLabel("���� ��¥�� �Է��ϼ���.", JLabel.LEFT));
		dayPanel.add(day);
		dayPanel.add(new JLabel("ex) 20230101"));
		dayPanel.add(new JLabel("7�� ���� : �ٹ� �� �� 35, ���� �� �� �� 14"));
		//�ð�ǥ ���̺�
	    String data[][] = new String[3][8];
	    data[0][0] = "���� ��� ��";
	    data[1][0] = "���� ��� ��";
	    data[2][0] = "���� ��� ��";
		JTable jtm = new JTable();
	    jtm.setModel(new DefaultTableModel(data,cols));
	    TableColumn col = jtm.getColumnModel().getColumn(0);
	    col.setPreferredWidth(120);
	    JScrollPane spTable = new JScrollPane(jtm);
	    dayPanel.add(spTable);
	    
	    //���� ����� ��ư
	    dayPanel.add(button = new JButton("����")); // ���۹�ư ���� �� ����


		// ������â ����
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(500, 600);
		this.setVisible(true);
	}
	
	public static void main(String args[]){
		// ��ư ������
		new View("������ ȭ��");
	}
	
}
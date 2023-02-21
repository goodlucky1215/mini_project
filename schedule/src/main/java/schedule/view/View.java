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
	
	//	String startScheduleDay = "20230219"; //���۳�¥ �Է¹���
	//int[] morningPeople =   {3,2,3,2,3,3,3}; // ��-�� �Է� ����
	//int[] afternoonPeople = {2,2,2,2,2,3,3}; // ��-�� �Է� ����
	//int[] dayOffPeople =    {2,3,2,3,2,1,1}; // ��-�� ���� ���
	
	String cols[] = {"","��","ȭ","��","��","��","��","��"}     ; // �÷��� ��� ��
	JTable jtm;
	JTextField day; //���� �� �Է�
	String scheduleData[][]; //�ð� �Է� 
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
		dayPanel.add(new JLabel("[������ �� �Է� �� ���콺�� �ٸ� ���� �� �� �����ּ���]"));
		//�ð�ǥ ���̺�
		scheduleData = new String[3][8];
	    scheduleData[0][0] = "���� ��� ��";
	    scheduleData[1][0] = "���� ��� ��";
	    scheduleData[2][0] = "���� ��� ��";
		jtm = new JTable();
	    jtm.setModel(new DefaultTableModel(scheduleData,cols));
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
		addListener();
	}
	
    private void addListener() {
        
        // ActionListener ��ü�� �����ϰ�
        // �������̽� �ȿ� �ִ� �޼ҵ带 ����ϱ� ����
        // @Override�� ����մϴ�.
        // actionPerformed ��� ���� �� �޼ҵ�ȿ�
        // �ش� Component�� ����������, ����Ǵ�
        // �ڵ带 �ۼ��Ͻø� �˴ϴ�.
        
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                
                // button�� ���������� 
                if(button.equals(ae.getSource())){
                	try {
						new SheduleController().makeShedule(day.getText(),getscheduleData());
					} catch (ParseException e) {
						/*
						 //parent Frame�� f�� �ϰ�, modal�� true�� �ؼ� �ʼ� ���� dialog�� ��.

			             Dialog info = new Dialog(f, "Information", true);
			
			             info.setSize(140,90);
			
			             //parent Frame�� �ƴ�, ȭ���� ��ġ�� ������ �ȴ�.
			
			             info.setLocation(50,50);
			
			             info.setLayout(new FlowLayout());
			
			            
			
			             Label msg = new Label("This is modal Dialog", Label.CENTER);
			
			             Button ok = new Button("OK");
			
			             info.add(msg);
			
			             info.add(ok);
			
			            
			
			             f.setVisible(true);
			
			             //Dialog�� ȭ�鿡 ���̰� �Ѵ�.
			
			             info.setVisible(true);
						 * */
						e.printStackTrace();
					}
                }
            }
        };
        
        // ������ �ش� Component�� ����������,
        // �ڵ带 �ۼ��ߴٸ� ��������
        // addActionListener(listener); �߰� �ʿ�
        // �̸� �߰����� �����ÿ��� �ش� �׼��� �߻����� �ʽ��ϴ�.       
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
		// ��ư ������
		new View("������ ȭ��");
	}
	
}
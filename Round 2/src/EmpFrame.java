import java.awt.BorderLayout; 
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.awt.List;

public class EmpFrame extends JFrame implements ActionListener, ItemListener{

	private JPanel contentPane;
	private JTextField SearchtextField;
	private JTextField EmpNametextField;
	private JTextField AgetextField;
	private JTextField Department_textField;
	JButton btnAddEmployee;
	JButton btnUpdate;
	JButton btnSave;
	JButton btnDelete;
	JList list;
	String Url="jdbc:postgresql://localhost:5432/Emp_dept_DB";
	String username="postgres";
	String pass="root";
	DefaultListModel demoList = new DefaultListModel();
	List Detail_list;
	private JButton btnS;
	String flag="";
	int empid;
	private JLabel idLabel;
	private JLabel lblEmpId_1;
	private JLabel lblSearchById;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmpFrame frame = new EmpFrame();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public EmpFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		SearchtextField = new JTextField();
		SearchtextField.setBounds(103, 13, 116, 22);
		contentPane.add(SearchtextField);
		SearchtextField.setColumns(10);
		
		btnAddEmployee = new JButton("Add Employee");
		btnAddEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EmpNametextField.enable(true);
				AgetextField.enable(true);
				flag="Add";
			}
		});
		btnAddEmployee.setBounds(52, 303, 116, 25);
		contentPane.add(btnAddEmployee);
		
		btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EmpNametextField.enable(true);
				AgetextField.enable(true);
				btnSave.enable(true);
				flag="Update";
			}
		});
		btnUpdate.setBounds(199, 303, 97, 25);
		contentPane.add(btnUpdate);
		
		list = new JList();
		list.setBounds(103, 48, 117, 46);
		contentPane.add(list);
		
		JLabel lblNewLabel = new JLabel("Emp Name");
		lblNewLabel.setBounds(36, 148, 72, 16);
		contentPane.add(lblNewLabel);
		
		JLabel lblAge = new JLabel("Age");
		lblAge.setBounds(36, 177, 56, 16);
		contentPane.add(lblAge);
		
		JLabel lblDepartment = new JLabel("Department ID");
		lblDepartment.setBounds(36, 206, 85, 16);
		contentPane.add(lblDepartment);
		
		EmpNametextField = new JTextField();
		EmpNametextField.setBounds(133, 145, 116, 22);
		contentPane.add(EmpNametextField);
		EmpNametextField.setColumns(10);
		
		AgetextField = new JTextField();
		AgetextField.setBounds(133, 174, 116, 22);
		contentPane.add(AgetextField);
		AgetextField.setColumns(10);
		
		Department_textField = new JTextField();
		Department_textField.setBounds(133, 203, 116, 22);
		contentPane.add(Department_textField);
		Department_textField.setColumns(10);
		
		btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Save();
			}
		});
		btnSave.setBounds(46, 245, 72, 25);
		contentPane.add(btnSave);
		btnSave.enable(false);
		
		btnDelete = new JButton("Delete");
		btnDelete.setBounds(333, 303, 97, 25);
		contentPane.add(btnDelete);
		
		JLabel lblEmpName = new JLabel("Emp Name");
		lblEmpName.setBounds(391, 33, 77, 16);
		contentPane.add(lblEmpName);
		
		JLabel lblAge_1 = new JLabel("Age");
		lblAge_1.setBounds(480, 33, 56, 16);
		contentPane.add(lblAge_1);
		
		JLabel lblEmpId = new JLabel("Emp ID");
		lblEmpId.setBounds(323, 33, 56, 16);
		contentPane.add(lblEmpId);
		
		Detail_list = new List();
		Detail_list.setBounds(323, 79, 191, 191);
		contentPane.add(Detail_list);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(162, 245, 72, 25);
		contentPane.add(btnCancel);
		
		btnS = new JButton("Search");
		btnS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				empid=Integer.parseInt(SearchtextField.getText());
				idLabel.setText(SearchtextField.getText());
				searchDb(empid);
			}
		});
		btnS.setBounds(12, 12, 80, 25);
		contentPane.add(btnS);
		
		idLabel = new JLabel("");
		idLabel.setBounds(133, 116, 116, 16);
		contentPane.add(idLabel);
		
		lblEmpId_1 = new JLabel("Emp Id");
		lblEmpId_1.setBounds(36, 119, 72, 16);
		contentPane.add(lblEmpId_1);
		
		lblSearchById = new JLabel("Search By ID");
		lblSearchById.setBounds(246, 16, 106, 16);
		contentPane.add(lblSearchById);
		
		EmpNametextField.enable(false);
		AgetextField.enable(false);
		Department_textField.enable(false);
		
		fillList();
	}
	
	void fillList() 
	{
		
		Detail_list.clear();
		Connection con = null;
		Statement stmt=null;
		
		try {
			Class.forName("org.postgresql.Driver");
	         con = DriverManager
	            .getConnection(Url,
	            username, pass);
	         stmt=con.createStatement();
	         ResultSet rs=stmt.executeQuery("select * from emptable");
	         while(rs.next()) {
	        	 Detail_list.add(rs.getString("empid")+"             "+rs.getString("EmpName")+"             "+rs.getString("age"));
	        	 //System.out.println(rs.getString("Id")+"	"+rs.getString("Emp_Name"));
	         }
	     rs.close();

			stmt.close();
			con.close();
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
	}
	
	void addToDb(String Name,int age) {
		Connection con = null;
		Statement stmt=null;
		String query="INSERT INTO emptable(\"empname\", age) VALUES ('"+Name+"',"+age+");";
		try {
			Class.forName("org.postgresql.Driver");
	        con = DriverManager.getConnection(Url,username, pass);
	        stmt=con.createStatement();
	        ResultSet rs=stmt.executeQuery(query);
	        rs.close();
			stmt.close();
			con.close();
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
	}
	
	void updateToDb(int id) {
		String Empname = EmpNametextField.getText();
		int Age = Integer.parseInt(AgetextField.getText()); 
		
		Connection con = null;
		Statement stmt=null;
		String query="UPDATE emptable SET empname='"+Empname+"', age="+Age+" WHERE empid="+id+";";
		try {
			Class.forName("org.postgresql.Driver");
	        con = DriverManager.getConnection(Url,username, pass);
	        stmt=con.createStatement();
	        ResultSet rs=stmt.executeQuery(query);
	        rs.close();
			stmt.close();
			con.close();
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
	}
	
	void Save() {
		if(EmpNametextField.equals("")||AgetextField.equals("")) {
			
		}
		else {
			if(flag=="Add") {
				addToDb(EmpNametextField.getText(),Integer.parseInt(AgetextField.getText()));	
			}
			if(flag=="Update") {
				updateToDb(empid);
			}
			
			fillList();
			EmpNametextField.setText("");
			EmpNametextField.enable(false);
			AgetextField.setText("");
			AgetextField.enable(false);
			btnSave.enable(false);
		}
	}
	
	void searchDb(int empid) {
		Connection con = null;
		Statement stmt=null;
		String query="select * from emptable where empid="+empid+";";
		
		try {
			Class.forName("org.postgresql.Driver");
	        con = DriverManager.getConnection(Url,username, pass);
	        stmt=con.createStatement();
	        ResultSet rs=stmt.executeQuery(query);
	        rs.next();
	        EmpNametextField.setText(rs.getString("empname"));
	        AgetextField.setText(rs.getString("age"));
	        rs.close();
			stmt.close();
			con.close();
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
		
	}
	
	

	public void actionPerformed(ActionEvent ae) {
		String AEstr = ae.getActionCommand();
		if(AEstr.equals("Add Employee")) {
			System.out.println("Inside action");
		}
		
	}
	
	
	public void itemStateChanged(ItemEvent ie) {
		
		String item=Detail_list.getSelectedItem();
		String arr[ ]=item.split(" ");
		System.out.println(arr[0]+arr[1]);		
	}
}

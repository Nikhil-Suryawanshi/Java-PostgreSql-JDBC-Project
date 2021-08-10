import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Frame1 {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame1 window = new Frame1();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Frame1() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 650, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnEmployeeDetails = new JButton("Employee Details");
		btnEmployeeDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				EmpFrame Ef=new EmpFrame();
				Ef.setVisible(true);
			}
		});
		btnEmployeeDetails.setBounds(87, 135, 163, 40);
		frame.getContentPane().add(btnEmployeeDetails);
		
		JButton btnDepartmentDetails = new JButton("Department Details");
		btnDepartmentDetails.setBounds(397, 135, 158, 40);
		frame.getContentPane().add(btnDepartmentDetails);
	}
}

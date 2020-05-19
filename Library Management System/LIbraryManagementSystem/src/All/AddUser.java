package All;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class AddUser extends JFrame {

	private JPanel contentPane;
	private JTextField textFullname;
	private JTextField textUsername;
	private JTextField textPassword;
	private JTextField textNrc;

	public AddUser() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblFullName = new JLabel("full name");
		lblFullName.setBounds(24, 46, 61, 16);
		panel.add(lblFullName);
		
		JLabel lblUsername = new JLabel("username");
		lblUsername.setBounds(24, 91, 61, 16);
		panel.add(lblUsername);
		
		JLabel lblPassword = new JLabel("password");
		lblPassword.setBounds(24, 139, 61, 16);
		panel.add(lblPassword);
		
		textFullname = new JTextField();
		textFullname.setBounds(97, 41, 288, 26);
		panel.add(textFullname);
		textFullname.setColumns(10);
		
		textUsername = new JTextField();
		textUsername.setBounds(97, 86, 288, 26);
		panel.add(textUsername);
		textUsername.setColumns(10);
		
		textPassword = new JTextField();
		textPassword.setBounds(97, 134, 288, 26);
		panel.add(textPassword);
		textPassword.setColumns(10);
		
		JButton btnAdd = new JButton("add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sql = "INSERT INTO `user` (`userID`,`fullname`, `username`, `password`, `nrc`) VALUES (NULL,?, ?, ?, ?)";
				try {
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/myLibraryDB","root","");
					PreparedStatement pst = con.prepareStatement(sql);
					pst.setString(1, textFullname.getText());
					pst.setString(2, textUsername.getText());
					pst.setString(3, textPassword.getText());
					pst.setString(4, textNrc.getText());
					int x = pst.executeUpdate();
					if( x > 0 ) { //because executeUpdate returns integer
						JOptionPane.showMessageDialog(null, "Successfully inserted");
					}
					else {
						JOptionPane.showMessageDialog(null, "Insert failed");
					}
					pst.close();
					con.close();
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				
				
			}
		});
		btnAdd.setBounds(268, 221, 117, 29);
		panel.add(btnAdd);
		
		textNrc = new JTextField();
		textNrc.setBounds(97, 183, 288, 26);
		panel.add(textNrc);
		textNrc.setColumns(10);
		
		JLabel lblNrc = new JLabel("nrc");
		lblNrc.setBounds(24, 188, 61, 16);
		panel.add(lblNrc);
		
		JButton button = new JButton("<<back");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Admin_MainMenu adminmain = new Admin_MainMenu();
				adminmain.setVisible(true);
				dispose();
				
			}
		});
		button.setBounds(0, 0, 117, 29);
		panel.add(button);
		
		JButton btnCancel = new JButton("cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				textFullname.setText(null);
				textNrc.setText(null);
				textPassword.setText(null);
				textUsername.setText(null);
				
			}
		});
		btnCancel.setBounds(97, 221, 117, 29);
		panel.add(btnCancel);
	}

}

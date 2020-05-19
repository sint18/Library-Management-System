package All;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.*;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;

public class loginForm extends JFrame {

	private JPanel contentPane;
	private JTextField textUser;
	private JPasswordField passwordField;
	private String loggedInUser = null;

	/**
	 * Launch the application.
	 */
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					loginForm frame = new loginForm();
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
	public loginForm() {
		setTitle("Yangon College IT and Engineering Library");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblUsername = new JLabel("username");
		lblUsername.setBounds(26, 43, 61, 16);
		panel.add(lblUsername);
		
		JLabel lblPassword = new JLabel("password");
		lblPassword.setBounds(26, 103, 61, 16);
		panel.add(lblPassword);
		
		textUser = new JTextField();
		textUser.setBounds(106, 38, 270, 26);
		panel.add(textUser);
		textUser.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(106, 98, 270, 26);
		panel.add(passwordField);
		
		JRadioButton rdbtnAdmin = new JRadioButton("admin");
		rdbtnAdmin.setBounds(106, 146, 141, 23);
		panel.add(rdbtnAdmin);
		
		JRadioButton rdbtnUser = new JRadioButton("user");
		rdbtnUser.setBounds(259, 146, 105, 23);
		panel.add(rdbtnUser);
		
		ButtonGroup g = new ButtonGroup();
		g.add(rdbtnAdmin);
		g.add(rdbtnUser);
		
		JButton btnLogin = new JButton("login");
		btnLogin.addActionListener(new ActionListener() {

			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				
				if(rdbtnAdmin.isSelected()) {

					try {
						Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/myLibraryDB","root","");
						PreparedStatement pst = con.prepareStatement("select * from admin where username=? and password=?");
						pst.setString(1, textUser.getText());
						pst.setString(2, passwordField.getText());
						ResultSet rs = pst.executeQuery();
						
						if(rs.next()) {
							loggedInUser = rs.getString("adminName");
							
							JOptionPane.showMessageDialog( null, "Login successful\nWelcome, Admin : "+loggedInUser);
							Admin_MainMenu mainmenu = new Admin_MainMenu();
							mainmenu.setVisible(true);
							dispose();
							
						}
						else {
							JOptionPane.showMessageDialog(null, "Something's wrong");
						}
						rs.close();
						pst.close();
						con.close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						System.out.println(e1.getMessage());;
					}
				}
				else if(rdbtnUser.isSelected()) {
					
					try {
						Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/myLibraryDB","root","");
						PreparedStatement pst = con.prepareStatement("select * from user where username=? and password=?");
						pst.setString(1, textUser.getText());
						pst.setString(2, passwordField.getText());
						ResultSet rs = pst.executeQuery();
						
						if(rs.next()) {
							JOptionPane.showMessageDialog( null, "Login successful\nWelcome, "+rs.getString("fullname"));
							User_MainMenu mainmenu = new User_MainMenu(rs.getInt(1));
							mainmenu.setVisible(true);
							dispose();
						}
						else {
							JOptionPane.showMessageDialog(null, "Something's wrong");
						}
						rs.close();
						pst.close();
						con.close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						System.out.println(e1.getMessage());;
					}
				}
				
				
			}
		});
		btnLogin.setBounds(106, 192, 117, 29);
		panel.add(btnLogin);
		
		JButton btnNewButton = new JButton("exit");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(DISPOSE_ON_CLOSE);
			}
		});
		btnNewButton.setBounds(259, 192, 117, 29);
		panel.add(btnNewButton);
		

		
	}

}

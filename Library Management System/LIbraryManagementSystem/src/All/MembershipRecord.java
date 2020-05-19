package All;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

import sun.misc.FormattedFloatingDecimal;

public class MembershipRecord extends JFrame {

	private JPanel contentPane;
	private Connection con;
	HashMap<String, Integer> map = new HashMap<>();
	private JTextField textField;
	private JTextField textField_1;
	
	public MembershipRecord() {
		setTitle("Membership ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(165, 50, 194, 27);
		panel.add(comboBox);
	
		
		String selsql = "Select userID, fullname from user";
		
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/myLibraryDB","root","");
			PreparedStatement selpst = con.prepareStatement(selsql);
			ResultSet rs = selpst.executeQuery();
			while(rs.next()) {
				int userID = rs.getInt(1);
				String fullname = rs.getString(2);
				map.put(fullname, userID);
				comboBox.addItem(fullname);
			}
			rs.close();
			selpst.close();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		
		
		JButton btnNewButton = new JButton("add");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				String sql = "INSERT INTO `memberRecord` (`memberID`, `userID`, `dateOfMembership`, `expireDate`) VALUES (NULL, ?, ?, ?)";
				
				try {
					PreparedStatement pst = con.prepareStatement(sql);
					
					pst.setInt(1, map.get(comboBox.getSelectedItem().toString()));

					java.util.Date date1 = null;
					java.util.Date date2 = null;
					try {
						date1 = format.parse(textField.getText());
						date2 = format.parse(textField_1.getText());
						
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					java.sql.Date sqlDate1 = new java.sql.Date(date1.getTime());
					java.sql.Date sqlDate2 = new java.sql.Date(date2.getTime());
					
					pst.setDate(2, sqlDate1);
					pst.setDate(3, sqlDate2);
					
					int x = pst.executeUpdate();
					if ( x > 0) {
						JOptionPane.showMessageDialog(null, "Success");
					}
					else {
						JOptionPane.showMessageDialog(null, "Something's wrong");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1.getMessage());
					JOptionPane.showMessageDialog(null, "Only one membership record for a user");
				}
				
				
			}
		});
		btnNewButton.setBounds(75, 216, 117, 29);
		panel.add(btnNewButton);
		
		JLabel lblChooseUser = new JLabel("choose user");
		lblChooseUser.setBounds(61, 54, 82, 16);
		panel.add(lblChooseUser);
		
		JLabel lblStartDate = new JLabel("start date");
		lblStartDate.setBounds(61, 100, 82, 16);
		panel.add(lblStartDate);
		
		JLabel lblExpireDate = new JLabel("expire date");
		lblExpireDate.setBounds(61, 149, 82, 16);
		panel.add(lblExpireDate);
		
		textField = new JTextField();
		textField.setBounds(177, 95, 158, 26);
		panel.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(177, 144, 160, 26);
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblExample = new JLabel("Example. 2001-11-9");
		lblExample.setBounds(207, 182, 152, 16);
		panel.add(lblExample);
		
		JButton btnNewButton_1 = new JButton("member list");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				MemberList memberlist = new MemberList();
				memberlist.setVisible(true);
				dispose();
				
			}
		});
		btnNewButton_1.setBounds(260, 218, 117, 29);
		panel.add(btnNewButton_1);
		
		JButton button = new JButton("<<back");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Admin_MainMenu adminmain = new Admin_MainMenu();
				adminmain.setVisible(true);
				dispose();
			}
		});
		button.setBounds(6, 6, 117, 29);
		panel.add(button);
	
		
		
		
		
		
	}
}

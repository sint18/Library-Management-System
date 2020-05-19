package All;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class information extends JFrame {

	private JPanel contentPane;
	private JTextField textDate;
	private JTextField textTotalDays;
	Connection con;
	HashMap<String, Integer> member = new HashMap<>();
	HashMap<String, Integer> book = new HashMap<>();

	public information() {
		
		
		
		setTitle("lend book");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 278);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("select member");
		lblNewLabel.setBounds(30, 41, 117, 16);
		panel.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("<<Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Admin_MainMenu adminmain = new Admin_MainMenu();
				adminmain.setVisible(true);
				dispose();
			}
		});
		btnNewButton.setBounds(0, 0, 117, 29);
		panel.add(btnNewButton);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(139, 37, 249, 27);
		panel.add(comboBox);
		
		JLabel lblSelectBook = new JLabel("select book");
		lblSelectBook.setBounds(30, 86, 117, 16);
		panel.add(lblSelectBook);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(139, 82, 249, 27);
		panel.add(comboBox_1);
		
		textDate = new JTextField();
		textDate.setBounds(139, 126, 249, 26);
		panel.add(textDate);
		textDate.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("date");
		lblNewLabel_1.setBounds(30, 131, 61, 16);
		panel.add(lblNewLabel_1);
		
		JLabel lblTotalDays = new JLabel("total days");
		lblTotalDays.setBounds(30, 184, 87, 16);
		panel.add(lblTotalDays);
		
		textTotalDays = new JTextField();
		textTotalDays.setBounds(139, 179, 249, 26);
		panel.add(textTotalDays);
		textTotalDays.setColumns(10);
		
		
		String sql = "select fullname, memberID FROM user, memberRecord WHERE user.userID=memberRecord.userID";
		String sql1 = "select title, bookID from book";
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/myLibraryDB","root","");
			PreparedStatement pst = con.prepareStatement(sql);
			PreparedStatement pst1 = con.prepareStatement(sql1);
			ResultSet rs = pst.executeQuery();
			ResultSet rs1 = pst1.executeQuery();
			while (rs.next()) {
				String name = rs.getString("fullname");
				int id = rs.getInt("memberID");
				member.put(name, id);
				comboBox.addItem(name);
			}
			while (rs1.next()) {
				String name = rs1.getString(1);
				int id = rs1.getInt(2);
				book.put(name, id);
				comboBox_1.addItem(name);
			}
			rs.close();
			rs1.close();
			pst.close();
			pst1.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		JButton btnAdd = new JButton("add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				float days = 0;
				java.util.Date date1 = null;
				java.util.Date date2 = null;
				try {
					date1 = format.parse(textDate.getText());
//					date2 = format.parse(textDueDate.getText());
//					
//					long diff = date2.getTime() - date1.getTime();
//					days = (diff / (1000*60*60*24));
//					String d = Float.toString(days);
//					textTotalDays.setText(d);
					
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				java.sql.Date sqlDate1 = new java.sql.Date(date1.getTime());
//				java.sql.Date sqlDate2 = new java.sql.Date(date2.getTime());
				
				String sql2 ="INSERT INTO `status` (`statusID`, `memberID`, `bookID`, `borrowedDate`, `dueDate`, `numberOfDays`) VALUES (NULL, ?, ?, ?, NULL, ?)";
				try {
					PreparedStatement pst2 = con.prepareStatement(sql2);
					pst2.setInt(1, member.get(comboBox.getSelectedItem()));
					pst2.setInt(2, book.get(comboBox_1.getSelectedItem()));
					pst2.setDate(3, sqlDate1);
//					pst2.setDate(4, sqlDate2);
					pst2.setInt(4, Integer.parseInt(textTotalDays.getText()));
					int x = pst2.executeUpdate();
					if( x > 0) {
						JOptionPane.showMessageDialog(null, "Success");
					}
					else {
						JOptionPane.showMessageDialog(null, "something's wrong");
					}
					pst2.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				String sql3 = "UPDATE book SET copies=copies-1 WHERE bookID=? and copies>0";
				try {
					PreparedStatement pst3 = con.prepareStatement(sql3);
					pst3.setInt(1, book.get(comboBox_1.getSelectedItem()));
					pst3.executeUpdate();
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
			}
		});
		btnAdd.setBounds(271, 217, 117, 29);
		panel.add(btnAdd);
		
		JButton btnCancel = new JButton("cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textDate.setText(null);
				textTotalDays.setText(null);
			}
		});
		btnCancel.setBounds(30, 217, 117, 29);
		panel.add(btnCancel);
		
		JLabel lblExample = new JLabel("Example, 2020-12-3");
		lblExample.setBounds(193, 154, 155, 16);
		panel.add(lblExample);
		
		JButton btnList = new JButton("list");
		btnList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Info_List infolist = new Info_List();
				infolist.setVisible(true);
				dispose();
				
			}
		});
		btnList.setBounds(323, 0, 117, 29);
		panel.add(btnList);

		
		
	}

}

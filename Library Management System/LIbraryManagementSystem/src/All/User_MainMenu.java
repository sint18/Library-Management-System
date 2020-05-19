package All;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;

public class User_MainMenu extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel dtm;
	private JComboBox comboBox;
	private JButton btnNewButton;
	Connection con;

	public User_MainMenu(int s) {
		setTitle("User Menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 30, 440, 238);
		
		table = new JTable();
		table.setBounds(0, 0, 440, 0);
		scrollPane.setViewportView(table);
		panel.add(scrollPane);
		
		dtm = new DefaultTableModel(new Object[] {"Title", "Author", "Type", "Edition"}, 0) {
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		table.setModel(dtm);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"view membership status", "view books", "logout", "customer support"}));
		comboBox.setBounds(0, 0, 209, 27);
		panel.add(comboBox);
		
		String sql = "SELECT title, authorName, bookType, edition from book, author WHERE author.authorID=book.authorID";
		
		try {
			dtm.setRowCount(0);
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/myLibraryDB","root","");
			PreparedStatement pst = con.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				Object data[] = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)};
				dtm.addRow(data);
			}
			rs.close();
			pst.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Connection Error");;
		}
		
	
		
		btnNewButton = new JButton("go");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loginForm lg = new loginForm();
				String sql1 = "select memberID, fullname, nrc, dateOfMembership, expireDate from user, memberRecord where user.userID=? and user.userID=memberRecord.userID";
				if(comboBox.getSelectedItem().equals("view membership status")) {
					try {		
						PreparedStatement pst1 = con.prepareStatement(sql1);
						pst1.setInt(1, s);
						ResultSet rs1 = pst1.executeQuery();
						if(rs1.next()) {
							JOptionPane.showMessageDialog(null, "Member ID : "+rs1.getInt(1)+
									"\nName : "+rs1.getString(2)+
									"\nNRC : "+rs1.getString(3)+
									"\nMembership date : "+rs1.getDate(4)+
									"\nValid until : "+rs1.getDate(5));
							rs1.close();
							pst1.close();
						}	
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else if(comboBox.getSelectedItem().equals("view books")) {
					
//					String sql = "SELECT title, authorName, bookType, edition from book, author WHERE author.authorID=book.authorID";
					
					try {
						dtm.setRowCount(0);
//						con = DriverManager.getConnection("jdbc:mysql://localhost:3306/myLibraryDB","root","");
						PreparedStatement pst = con.prepareStatement(sql);
						ResultSet rs = pst.executeQuery();
						while(rs.next()) {
							Object data[] = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)};
							dtm.addRow(data);
						}
						rs.close();
						pst.close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, "Connection Error");;
					}
					
				}
				else if(comboBox.getSelectedItem().equals("customer support")) {
					String contact = "contact : phone number\n"
									+ "email : support@test.com";
					JOptionPane.showMessageDialog(null, contact);
				}
				else if(comboBox.getSelectedItem().equals("logout")) {
					loginForm lgf = new loginForm();
					lgf.setVisible(true);
					dispose();
				}
				
				
			}
		});
		btnNewButton.setBounds(221, -1, 117, 29);
		panel.add(btnNewButton);
		

		
	}
}

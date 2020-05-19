package All;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import javafx.scene.control.ComboBox;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class Info_List extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField textSearch;
	DefaultTableModel dtm;
	Connection con;
	private JTextField textField;
	
	private Date date1;
	private JTextField textField_1;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Info_List frame = new Info_List();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Info_List() {
		
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 683, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 122, 661, 240);
		panel.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"search by name", "search by type", "search by title", "search by author"}));
		comboBox.setBounds(132, 1, 148, 27);
		panel.add(comboBox);
		
		dtm = new DefaultTableModel(new Object[] {"Status ID","Fullname", "Book Title", "Book Type", "Date", "Valid until", "Total Days"}, 0) {
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		table.setModel(dtm);
		
		try {
			String sql = "select statusID, fullname, title, bookType, borrowedDate, dueDate, numberOfDays from user, book, memberRecord, status where user.userID=memberRecord.userID and memberRecord.memberID=status.memberID and book.bookID=status.bookID";
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/myLibraryDB","root","");
			PreparedStatement pst = con.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				Object data[] = {rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDate(5), rs.getDate(6), rs.getInt(7)};
				dtm.addRow(data);
			}
			pst.close();
			rs.close();
		} catch(SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		
		JButton btnNewButton = new JButton("<<back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				information info = new information();
				info.setVisible(true);
				dispose();
			}
		});
		btnNewButton.setBounds(0, 0, 117, 29);
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("search");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(comboBox.getSelectedItem().equals("search by name")) {
					String sql1 = "select statusID, fullname, title, bookType, borrowedDate, dueDate, numberOfDays from user, book, memberRecord, status where fullname=? and user.userID=memberRecord.userID and memberRecord.memberID=status.memberID and book.bookID=status.bookID";
					try {
						PreparedStatement pst1 = con.prepareStatement(sql1);
						pst1.setString(1, textSearch.getText());
						ResultSet rs1 = pst1.executeQuery();
						
						dtm.setRowCount(0); //reset table;
						while(rs1.next()) {
							Object data[] = {rs1.getInt(1), rs1.getString(2), rs1.getString(3), rs1.getString(4), rs1.getDate(5), rs1.getDate(6), rs1.getInt(7)};
							dtm.addRow(data);
						}
						pst1.close();
						rs1.close();
						
					} catch (Exception e2) {
						// TODO: handle exception
					}
				}
				else if (comboBox.getSelectedItem().equals("search by type")) {
					String sql1 = "select statusID, fullname, title, bookType, borrowedDate, dueDate, numberOfDays from user, book, memberRecord, status where bookType=? and user.userID=memberRecord.userID and memberRecord.memberID=status.memberID and book.bookID=status.bookID";
					try {
						PreparedStatement pst1 = con.prepareStatement(sql1);
						pst1.setString(1, textSearch.getText());
						ResultSet rs1 = pst1.executeQuery();
						
						dtm.setRowCount(0); //reset table;
						while(rs1.next()) {
							Object data[] = {rs1.getInt(1), rs1.getString(2), rs1.getString(3), rs1.getString(4), rs1.getDate(5), rs1.getDate(6), rs1.getInt(7)};
							dtm.addRow(data);
						}
						pst1.close();
						rs1.close();
						
					} catch (Exception e2) {
						// TODO: handle exception
					}
				}
				else if(comboBox.getSelectedItem().equals("search by title")) {
					String sql1 = "select statusID, fullname, title, bookType, borrowedDate, dueDate, numberOfDays from user, book, memberRecord, status where title=? and user.userID=memberRecord.userID and memberRecord.memberID=status.memberID and book.bookID=status.bookID";
					try {
						PreparedStatement pst1 = con.prepareStatement(sql1);
						pst1.setString(1, textSearch.getText());
						ResultSet rs1 = pst1.executeQuery();
						
						dtm.setRowCount(0); //reset table;
						while(rs1.next()) {
							Object data[] = {rs1.getInt(1), rs1.getString(2), rs1.getString(3), rs1.getString(4), rs1.getDate(5), rs1.getDate(6), rs1.getInt(7)};
							dtm.addRow(data);
						}
						pst1.close();
						rs1.close();
						
					} catch (Exception e2) {
						// TODO: handle exception
					}
				}
			
			}
		});
		btnNewButton_1.setBounds(556, 0, 117, 29);
		panel.add(btnNewButton_1);
		
		textSearch = new JTextField();
		textSearch.setBounds(414, 0, 130, 26);
		panel.add(textSearch);
		textSearch.setColumns(10);
		
		JButton btnRefresh = new JButton("refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dtm.setRowCount(0);
				try {
					String sql = "select statusID, fullname, title, bookType, borrowedDate, dueDate, numberOfDays from user, book, memberRecord, status where user.userID=memberRecord.userID and memberRecord.memberID=status.memberID and book.bookID=status.bookID";
					con = DriverManager.getConnection("jdbc:mysql://localhost:3306/myLibraryDB","root","");
					PreparedStatement pst = con.prepareStatement(sql);
					ResultSet rs = pst.executeQuery();
					
					while(rs.next()) {
						Object data[] = {rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDate(5), rs.getDate(6), rs.getInt(7)};
						dtm.addRow(data);
					}
					rs.close();
					pst.close();
				} catch(SQLException e2) {
					JOptionPane.showMessageDialog(null, e2.getMessage());
				}
			}
		});
		btnRefresh.setBounds(292, 0, 117, 29);
		panel.add(btnRefresh);
		
		textField = new JTextField();
		textField.setBounds(414, 38, 130, 26);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("status id");
		lblNewLabel.setBounds(302, 41, 61, 16);
		panel.add(lblNewLabel);
		

		
		JButton btnNewButton_2 = new JButton("return book");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				try {
					date1 = format.parse(textField_1.getText());
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				java.sql.Date sqlDate1 = new java.sql.Date(date1.getTime());
				String sql1 = "UPDATE `status` SET `dueDate` =? WHERE `status`.`statusID`=?";
				try {
					PreparedStatement pst2 = con.prepareStatement(sql1);
					pst2.setDate(1, sqlDate1);
					pst2.setInt(2, Integer.parseInt(textField.getText()));
					
					int x = pst2.executeUpdate();
					if(x > 0) {
						dtm.setRowCount(0);
						String sql2 = "select statusID, fullname, title, bookType, borrowedDate, dueDate, numberOfDays from user, book, memberRecord, status where user.userID=memberRecord.userID and memberRecord.memberID=status.memberID and book.bookID=status.bookID";
						
						PreparedStatement pst = con.prepareStatement(sql2);
						ResultSet rs = pst.executeQuery();
						JOptionPane.showMessageDialog(null, "return date successfully updated");
						while(rs.next()) {
							Object data[] = {rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDate(5), rs.getDate(6), rs.getInt(7)};
							dtm.addRow(data);

							textField.setText(null);
							textField_1.setText(null);
						}
						rs.close();
						pst.close();
					}
					
					
					pst2.close();
				
				} catch (Exception e3) {
					e3.printStackTrace();// TODO: handle exception
				}
				
			}
		});
		btnNewButton_2.setBounds(556, 36, 117, 29);
		panel.add(btnNewButton_2);
		
		textField_1 = new JTextField();
		textField_1.setBounds(414, 76, 130, 26);
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("return date");
		lblNewLabel_1.setBounds(302, 81, 107, 16);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("example, 2020-9-22");
		lblNewLabel_2.setBounds(410, 103, 158, 16);
		panel.add(lblNewLabel_2);
		
		
		
		
	}
}

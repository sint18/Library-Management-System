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

import com.mysql.cj.protocol.Resultset;

import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class MemberList extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTable table;
	Connection con;
	private DefaultTableModel dtm;

	public MemberList() {
		setTitle("member list");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 698, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 37, 676, 225);
		panel.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		textField = new JTextField();
		textField.setBounds(386, 6, 167, 26);
		panel.add(textField);
		textField.setColumns(10);
		
		dtm = new DefaultTableModel(new Object[] {"Member ID", "Fullname", "NRC", "username", "password", "Date", "Valid until"}, 0) {
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		table.setModel(dtm);
		
		try {
			String sql = "select memberID, fullname, nrc, username, password, dateOfMembership, expireDate from user, memberRecord where user.userID=memberRecord.userID";
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/myLibraryDB","root","");
			PreparedStatement pst = con.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				Object data[] = {rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDate(6), rs.getDate(7)};
				dtm.addRow(data);
			}
			
			pst.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JButton btnNewButton = new JButton("delete");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PreparedStatement pst;
				String sql = "delete from memberRecord where memberID=?";
				
				int dialogButton = JOptionPane.YES_NO_OPTION;
				int dialogResult = JOptionPane.showConfirmDialog(null, "you sure?", "Delete a user", dialogButton);
				
				if(dialogResult == JOptionPane.YES_OPTION) {
					try {
						pst = con.prepareStatement(sql);
						pst.setInt(1, Integer.parseInt(textField.getText()));
						int x = pst.executeUpdate();
						if( x > 0 ) {
							JOptionPane.showMessageDialog(null, "Successfully deleted");
							dtm.setRowCount(0);
							table.setModel(dtm);
							
							try {
								String sql1 = "select memberID, fullname, nrc, username, password, dateOfMembership, expireDate from user, memberRecord where user.userID=memberRecord.userID";
								PreparedStatement pst1 = con.prepareStatement(sql1);
								ResultSet rs1 = pst1.executeQuery();
								
								while(rs1.next()) {
									Object data[] = {rs1.getInt(1), rs1.getString(2), rs1.getString(3), rs1.getString(4), rs1.getString(5), rs1.getDate(6), rs1.getDate(7)};
									dtm.addRow(data);
								}
								
								
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
							
						}
						else {
							JOptionPane.showMessageDialog(null, "Something's wrong");
						}
					} catch(SQLException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
						JOptionPane.showMessageDialog(null, "ther user can't be deleted because that user hasn't return the book yet");
					}
				}
				else {
					textField.setText(null);
				}
				
			}
		});
		btnNewButton.setBounds(565, 6, 117, 29);
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("<<back");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				MembershipRecord memberRecord = new MembershipRecord();
				memberRecord.setVisible(true);
				dispose();
				
			}
		});
		btnNewButton_1.setBounds(6, 6, 117, 29);
		panel.add(btnNewButton_1);
		
		JLabel lblMemberId = new JLabel("member id");
		lblMemberId.setBounds(293, 9, 81, 16);
		panel.add(lblMemberId);
	}

}

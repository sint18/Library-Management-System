package All;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.mysql.cj.protocol.Resultset;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class AddAuthor extends JFrame {

	private JPanel contentPane;
	private JTextField textAuthorName;
	private JTable table;
	private DefaultTableModel dtm;
	private Connection con;

	public AddAuthor() {
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
		scrollPane.setBounds(0, 92, 440, 176);
		panel.add(scrollPane);
		
		table = new JTable();
		table.setBounds(107, 166, 1, 1);
		scrollPane.add(table);
		
		JLabel lblAuthorName = new JLabel("author name");
		lblAuthorName.setBounds(10, 37, 94, 16);
		panel.add(lblAuthorName);
		
		textAuthorName = new JTextField();
		textAuthorName.setBounds(116, 32, 297, 26);
		panel.add(textAuthorName);
		textAuthorName.setColumns(10);
		
		dtm = new DefaultTableModel(new Object[] {"AuthorID", "Author Name"}, 0) {
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		
		table.setModel(dtm);
		scrollPane.setViewportView(table);
		try {
			String sql = "select * from author";
			
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/myLibraryDB","root","");
			PreparedStatement pst = con.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				Object data[] = {rs.getInt(1), rs.getString(2)};
				dtm.addRow(data);
			}
			
			rs.close();
			pst.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		
		JButton btnAdd = new JButton("add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sql = "insert into author (authorID, authorName) values (NULL, ?)";
				
				try {
					
					PreparedStatement pst = con.prepareStatement(sql);
					pst.setString(1, textAuthorName.getText());
					int x = pst.executeUpdate();
					
					if( x > 0) {
						JOptionPane.showMessageDialog(null, "Successfully inserted");
						
						//table update
						String selsql = "select * from author";
						PreparedStatement pst1  = con.prepareStatement(selsql);
						ResultSet rs = pst1.executeQuery();
						dtm.setRowCount(0);
						while(rs.next()) {
							Object data[] = {rs.getInt(1), rs.getString(2)};
							dtm.addRow(data);
						}
						
						rs.close();
						pst1.close();
					}
					else {
						JOptionPane.showMessageDialog(null, "Something's wrong");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
				
			}
		});
		btnAdd.setBounds(296, 63, 117, 29);
		panel.add(btnAdd);
		
		JButton btnCancel = new JButton("cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				textAuthorName.setText(null);
				
			}
		});
		btnCancel.setBounds(116, 63, 117, 29);
		panel.add(btnCancel);
		
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
	}

}

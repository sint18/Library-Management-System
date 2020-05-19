package All;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import net.ucanaccess.jdbc.ExecuteUpdate;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class IssueBook extends JFrame {

	private JPanel contentPane;
	private JTextField textTitle;
	private JTextField textBookType;
	private JTextField textEdition;
	HashMap< String, Integer> map = new HashMap<String, Integer>();
	private DefaultTableModel dtm;
	private JTable table_1;
	private JTextField textISBN;
	private JTextField textLocation;
	private JTextField textCopies;
	private JTextField textField;
//	Connection con;

	public IssueBook() {
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 996, 361);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblBookTitle = new JLabel("book title");
		lblBookTitle.setBounds(10, 37, 61, 16);
		panel.add(lblBookTitle);
		
		JLabel lblBookType = new JLabel("book type");
		lblBookType.setBounds(10, 78, 77, 16);
		panel.add(lblBookType);
		
		textTitle = new JTextField();
		textTitle.setBounds(99, 32, 201, 26);
		panel.add(textTitle);
		textTitle.setColumns(10);
		
		textBookType = new JTextField();
		textBookType.setBounds(99, 73, 201, 26);
		panel.add(textBookType);
		textBookType.setColumns(10);
		
		JLabel lblEdition = new JLabel("edition");
		lblEdition.setBounds(10, 119, 61, 16);
		panel.add(lblEdition);
		
		JLabel lblAuthor = new JLabel("author");
		lblAuthor.setBounds(10, 161, 61, 16);
		panel.add(lblAuthor);
		
		textEdition = new JTextField();
		textEdition.setBounds(99, 114, 201, 26);
		panel.add(textEdition);
		textEdition.setColumns(10);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(99, 157, 201, 27);
		panel.add(comboBox);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(312, 2, 668, 256);
		panel.add(scrollPane);
		
		table_1 = new JTable();
		scrollPane.setViewportView(table_1);
		
		JButton btnCancel = new JButton("<<back");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Admin_MainMenu adminmain = new Admin_MainMenu();
				adminmain.setVisible(true);
				dispose();
				
			}
		});
		btnCancel.setBounds(0, 2, 117, 29);
		panel.add(btnCancel);
		
		dtm = new DefaultTableModel(new Object[] {"Title", "Author", "Type", "Edition", "Location", "Copies", "ISBN"}, 0) {
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		
		table_1.setModel(dtm);
		
		String selsql = "SELECT title, authorName, bookType, edition, location, copies, isbn from book, author WHERE author.authorID=book.authorID";
		
		try {
			dtm.setRowCount(0);
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/myLibraryDB","root","");
			PreparedStatement pst = con.prepareStatement(selsql);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				Object data[] = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getString(7)};
				dtm.addRow(data);
			}
			rs.close();
			pst.close();
//			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Connection Error");;
		}
		
		try {
			String sql = "select * from author";
			
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/myLibraryDB","root","");
			PreparedStatement pst = con.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getString(2);
				map.put(name, id);
				comboBox.addItem(name);
			}
			rs.close();
			pst.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		JButton btnAdd = new JButton("add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sql = "INSERT INTO `book` (`bookID`, `title`, `authorID`, `bookType`, `edition`, `location`, `isbn`, `copies`) VALUES (NULL, ?, ?, ?, ?, ?, ?, ?)";
				String selsql1 = "SELECT title, authorName, bookType, edition, location, copies, isbn from book, author WHERE author.authorID=book.authorID";
				try {
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/myLibraryDB","root","");
					PreparedStatement pst = con.prepareStatement(sql);
					pst.setString(1, textTitle.getText());
					pst.setInt(2, map.get(comboBox.getSelectedItem().toString()));
					pst.setString(3, textBookType.getText());
					pst.setString(4, textEdition.getText());
					pst.setString(5, textLocation.getText());
					pst.setString(6, textISBN.getText());
					pst.setInt(7, Integer.parseInt(textCopies.getText()));
					
					int x = pst.executeUpdate();
					if( x > 0 ) {
						JOptionPane.showMessageDialog(null, "Success");
						dtm.setRowCount(0);
						PreparedStatement pst1 = con.prepareStatement(selsql1);
						ResultSet rs1 = pst1.executeQuery();
						while(rs1.next()) {
							Object data[] = {rs1.getString(1), rs1.getString(2), rs1.getString(3), rs1.getString(4), rs1.getString(5), rs1.getInt(6), rs1.getString(7)};
							dtm.addRow(data);
						}
						rs1.close();
						pst1.close();
					}
					else {
						JOptionPane.showMessageDialog(null, "Unsuccessful T_T");
					}
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
				
			}
		});
		btnAdd.setBounds(164, 294, 117, 29);
		panel.add(btnAdd);
		
		JButton btnCancel_1 = new JButton("cancel");
		btnCancel_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textTitle.setText(null);
				textEdition.setText(null);
				textBookType.setText(null);
				
				
			}
		});
		btnCancel_1.setBounds(10, 294, 117, 29);
		panel.add(btnCancel_1);
		
		JLabel lblNewLabel = new JLabel("isbn");
		lblNewLabel.setBounds(10, 201, 61, 16);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("location");
		lblNewLabel_1.setBounds(10, 237, 61, 16);
		panel.add(lblNewLabel_1);
		
		textISBN = new JTextField();
		textISBN.setBounds(99, 196, 201, 26);
		panel.add(textISBN);
		textISBN.setColumns(10);
		
		textLocation = new JTextField();
		textLocation.setBounds(99, 232, 201, 26);
		panel.add(textLocation);
		textLocation.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("copies");
		lblNewLabel_2.setBounds(10, 266, 61, 16);
		panel.add(lblNewLabel_2);
		
		textCopies = new JTextField();
		textCopies.setBounds(99, 261, 201, 26);
		panel.add(textCopies);
		textCopies.setColumns(10);
		
		panel.add(textLocation);
		textLocation.setColumns(10);
		
		textField = new JTextField();
		textField.setBounds(721, 261, 130, 26);
		panel.add(textField);
		textField.setColumns(10);
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"search by title", "search by author", "search by type"}));
		comboBox_1.setBounds(555, 262, 145, 27);
		panel.add(comboBox_1);
		
		JButton btnNewButton = new JButton("search");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(comboBox_1.getSelectedItem().equals("search by title")) {
					
					String selsql2 = "SELECT title, authorName, bookType, edition, location, copies, isbn from book, author WHERE title=? and author.authorID=book.authorID";
					
					try {
						Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/myLibraryDB","root","");
						dtm.setRowCount(0);
						PreparedStatement pst2 = con.prepareStatement(selsql2);
						pst2.setString(1, textField.getText());
						ResultSet rs2 = pst2.executeQuery();
						while(rs2.next()) {
							Object data[] = {rs2.getString(1), rs2.getString(2), rs2.getString(3), rs2.getString(4), rs2.getString(5), rs2.getInt(6), rs2.getString(7)};
							dtm.addRow(data);
						}
						rs2.close();
						pst2.close();
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
//						JOptionPane.showMessageDialog(null, "Connection Error");;
						e2.printStackTrace();
					}
					
				}
				else if(comboBox_1.getSelectedItem().equals("search by author")) {
					
					String selsql2 = "SELECT title, authorName, bookType, edition, location, copies, isbn from book, author WHERE authorName=? and author.authorID=book.authorID";
					
					try {
						dtm.setRowCount(0);
						Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/myLibraryDB","root","");
						PreparedStatement pst2 = con.prepareStatement(selsql2);
						pst2.setString(1, textField.getText());
						ResultSet rs2 = pst2.executeQuery();
						while(rs2.next()) {
							Object data[] = {rs2.getString(1), rs2.getString(2), rs2.getString(3), rs2.getString(4), rs2.getString(5), rs2.getInt(6), rs2.getString(7)};
							dtm.addRow(data);
						}
						rs2.close();
						pst2.close();
//						con.close();
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, "Connection Error");;
					}


				}
				else if(comboBox_1.getSelectedItem().equals("search by type")) {
					
					String selsql2 = "SELECT title, authorName, bookType, edition, location, copies, isbn from book, author WHERE bookType=? and author.authorID=book.authorID";
					
					try {
						dtm.setRowCount(0);
						Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/myLibraryDB","root","");
						PreparedStatement pst2 = con.prepareStatement(selsql2);
						pst2.setString(1, textField.getText());
						ResultSet rs2 = pst2.executeQuery();
						while(rs2.next()) {
							Object data[] = {rs2.getString(1), rs2.getString(2), rs2.getString(3), rs2.getString(4), rs2.getString(5), rs2.getInt(6), rs2.getString(7)};
							dtm.addRow(data);
						}
						rs2.close();
						pst2.close();
//						con.close();
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, "Connection Error");;
					}
					
				}
				
				
			}
		});
		btnNewButton.setBounds(863, 261, 117, 29);
		panel.add(btnNewButton);
		
		
		
		
		
	}
}

package All;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class Admin_MainMenu extends JFrame {

	private JPanel contentPane;

	public Admin_MainMenu() {
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
		
		JButton btnAddNewUsers = new JButton("add user");
		btnAddNewUsers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddUser adduser = new AddUser();
				adduser.setVisible(true);
				dispose();
				
			}
		});
		btnAddNewUsers.setBounds(24, 31, 167, 29);
		panel.add(btnAddNewUsers);
		
		JLabel lblAddNewUsers = new JLabel("add new users to the system");
		lblAddNewUsers.setBounds(203, 36, 215, 16);
		panel.add(lblAddNewUsers);
		
		JButton btnIssueBook = new JButton("issue book");
		btnIssueBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				IssueBook issuebook = new IssueBook();
				issuebook.setVisible(true);
				dispose();
				
			}
		});
		btnIssueBook.setBounds(24, 74, 167, 29);
		panel.add(btnIssueBook);
		
		JLabel lblNewLabel = new JLabel("add new books to the system");
		lblNewLabel.setBounds(203, 79, 194, 16);
		panel.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("manage membership");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MembershipRecord membershipRecord = new MembershipRecord();
				membershipRecord.setVisible(true);
				dispose();
			}
		});
		btnNewButton.setBounds(24, 161, 167, 29);
		panel.add(btnNewButton);
		
		JButton btnAddAuthor = new JButton("add author");
		btnAddAuthor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				AddAuthor addauthor = new AddAuthor();
				addauthor.setVisible(true);
				dispose();
				
			}
		});
		btnAddAuthor.setBounds(24, 120, 167, 29);
		panel.add(btnAddAuthor);
		
		JLabel lblAddNewAuthors = new JLabel("add new authors");
		lblAddNewAuthors.setBounds(203, 125, 117, 16);
		panel.add(lblAddNewAuthors);
		
		JLabel lblAddremoveMembers = new JLabel("add/remove members");
		lblAddremoveMembers.setBounds(203, 166, 167, 16);
		panel.add(lblAddremoveMembers);
		
		JButton btnNewButton_1 = new JButton("information");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				information info = new information();
				info.setVisible(true);
				dispose();
			}
		});
		btnNewButton_1.setBounds(24, 202, 167, 29);
		panel.add(btnNewButton_1);
		
		JLabel lblLentBook = new JLabel("lend book");
		lblLentBook.setBounds(203, 207, 88, 16);
		panel.add(lblLentBook);
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loginForm lg = new loginForm();
				lg.setVisible(true);
				dispose();
			}
		});
		btnLogout.setBounds(317, 233, 117, 29);
		panel.add(btnLogout);
	}
}

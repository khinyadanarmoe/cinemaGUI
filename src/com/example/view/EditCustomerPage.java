package com.example.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.cinema.dao.AbstractDao;
import com.cinema.dao.CustomerDao;
import com.cinema.model.Customer;

public class EditCustomerPage {

	private JFrame frame;
	private JPanel panel;
	private JLabel nameLabel;
	private JTextField nameTextField;
	private JLabel emailLabel;
	private JTextField emailTextField;
	private JLabel addressLabel;
	private JTextField addressTextField;
	private JButton updateBtn;
	private JButton cancelBtn;
	private AbstractDao<Customer> customerDao;
	private Customer customer;
	private CustomerOverviewPage parentFrame;


	public EditCustomerPage(CustomerOverviewPage parentFrame,int customerId) {
		
		this.parentFrame = parentFrame;
		this.customerDao = new CustomerDao();
		this.customer = this.customerDao.findbyId(customerId);
		initializeComponent();
		addToPanel();
	}

	private void addToPanel() {

		this.panel.add(this.nameLabel);
		this.panel.add(this.nameTextField);

		this.panel.add(this.emailLabel);
		this.panel.add(this.emailTextField);

		this.panel.add(this.addressLabel);
		this.panel.add(this.addressTextField);

		this.panel.add(cancelBtn);
		this.panel.add(updateBtn);
		updateBtnAction();

		this.frame.add(this.panel, BorderLayout.NORTH);

	}

	private void updateBtnAction() {
		this.updateBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String name = nameTextField.getText();
				String email = emailTextField.getText();
				String address = addressTextField.getText();
				
				customer.setName(name);
				customer.setEmail(email);
				customer.setAddress(address);
				customer.setId(customer.getId());
				
				
				
				customerDao.update(customer);

				JOptionPane.showMessageDialog(frame, "Successfully updated!!");

				parentFrame.refreshCustomerTable();
				frame.dispose();
			}


		});
	}
	
	

	private void initializeComponent() {
		this.frame = new JFrame("Update Customer");
		this.frame.setSize(400, 300);
		this.frame.setLayout(new BorderLayout());
		this.frame.setLocation(300, 300);
		this.frame.setVisible(true);

		this.panel = new JPanel();
		this.panel.setLayout(new GridLayout(5, 2));

		this.nameLabel = new JLabel("Name: " );
		this.nameTextField = new JTextField(this.customer.getName());

		this.emailLabel = new JLabel("Email: ");
		this.emailTextField = new JTextField(this.customer.getEmail());

		this.addressLabel = new JLabel("Address: ");
		this.addressTextField = new JTextField(this.customer.getAddress());

		this.updateBtn = new JButton("Update");
		this.cancelBtn = new JButton("Cancel");

	}
}

package com.example.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.cinema.dao.AbstractDao;
import com.cinema.dao.CustomerDao;
import com.cinema.model.Customer;

public class CustomerOverviewPage {

	private JFrame frame;
	private JTable customersTable;
	private JScrollPane scrollPane;
	private JButton addBtn;
	private JButton editBtn;
	private JButton deleteBtn;
	private String[] columns = { "id", "Name", "Email", "Address" };
	private String[][] customerTableData;
	private AbstractDao<Customer> customerDao;
	private JPanel buttonPanel;
	private JPanel panel;
	private DefaultTableModel tableModel;

	public CustomerOverviewPage() {
		System.out.println("jjjj");
		this.customerDao = new CustomerDao();
		initializeComponents();
		loadCustomersData();
	}

	
	private void initializeComponents() {
		this.frame = new JFrame("Customer Overview");
		this.frame.setSize(800, 500);
		this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.frame.setLayout(new BorderLayout());

		this.tableModel = new DefaultTableModel(null, this.columns);
		this.customersTable = new JTable(this.tableModel);

		this.scrollPane = new JScrollPane(this.customersTable);
		this.frame.add(this.scrollPane, BorderLayout.CENTER);

		this.addBtn = new JButton("Add");
		this.editBtn = new JButton("Edit");
		this.deleteBtn = new JButton("Delete");

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 3));

		buttonPanel.add(addBtn);
		addCustomer();
		buttonPanel.add(editBtn);
		editCustomerInfo();
		buttonPanel.add(deleteBtn);
		deleteCustomer();

		this.frame.add(buttonPanel, BorderLayout.SOUTH);
		this.frame.setLocationRelativeTo(null);
		this.frame.setVisible(true);

	}

	private void deleteCustomer() {
		this.deleteBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				deleteCustomerAction();
		

			}

		});

	}

	protected void deleteCustomerAction() {
		int selectedRow = customersTable.getSelectedRow();

		if (selectedRow != -1) {
			int customerId = Integer.parseInt(this.tableModel.getValueAt(selectedRow,0).toString());
			customerDao.delete(customerId);
			JOptionPane.showMessageDialog(frame, "Successfully Delected CustomerId: " + customerId);

		} else {
			JOptionPane.showMessageDialog(frame, "Please select a customer to delete");
		}
		
	}

	private void editCustomerInfo() {
		this.editBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				editBtnAction();

			}

		});

	}

	protected void editBtnAction() {
		int selectedRow = customersTable.getSelectedRow();

		if (selectedRow != -1) {

			int customerId = Integer.parseInt(getCustomerData()[selectedRow][0]);
			EditCustomerPage editCustomerPage = new EditCustomerPage(this, customerId);

		} else {
			JOptionPane.showMessageDialog(frame, "Please select a customer to delete");
		}
		
		

	}

	private void addCustomer() {

		this.addBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				addCustomerAction();

			}

		});

	}

	private void addCustomerAction() {
		AddCustomerPage addCustomerPage = new AddCustomerPage(this);
	}

	private String[][] getCustomerData() {

		List<Customer> customers = this.customerDao.getAll();
		String[][] customersData = new String[customers.size()][columns.length];
		int rowCount = 0;
		for (Customer customer : customers) {
			for (int i = 0; i < columns.length; i++) {
				customersData[rowCount][i] = customer.toArray()[i];
			}
			rowCount++;
		}
		return customersData;
	}

	void refreshCustomerTable() {

		this.tableModel.setRowCount(0);
		loadCustomersData();
	};

	private void loadCustomersData() {
		List<Customer> customers = this.customerDao.getAll();
		for (Customer customer : customers) {
			this.tableModel.addRow(customer.toArray());
		}

	}

}

package Assignment;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import java.awt.Toolkit;

public class QuanLyNhanVien implements Runnable {
	private JFrame frame;
	private JTextField txtID;
	private JTextField txtName;
	private JTextField txtAge;
	private JTextField txtEmail;
	private JTextField txtSalary;

	private JScrollPane scrollPane;
	private JLabel lblTitle;
	private JLabel lblID;
	private JLabel lblName;
	private JLabel lblAge;
	private JLabel lblEmail;
	private JLabel lblSalary;
	private JLabel lblClock;
	private JButton btnNew;
	private JButton btnSave;
	private JButton btnDelete;
	private JButton btnFind;
	private JButton btnOpen;
	private JButton btnExit;
	private JButton btnBegin;
	private JButton btnBack;
	private JButton btnNext;
	private JButton btnEnd;
	private JLabel lblStatus;
	private DefaultTableModel model;
	private JTable table;

	List<Employee> list = new ArrayList<Employee>();
	int index = -1;
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);
	StringBuilder s = new StringBuilder();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
					QuanLyNhanVien window = new QuanLyNhanVien();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public QuanLyNhanVien() {
		initialize();
		frame.setLocationRelativeTo(null);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\holid\\Desktop\\employees.png"));
		frame.setTitle("QUẢN LÝ NHÂN VIÊN");
		frame.setBounds(100, 100, 579, 438);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		lblTitle = new JLabel("QUẢN LÝ NHÂN VIÊN");
		lblTitle.setFont(new Font("SansSerif", Font.BOLD, 17));
		lblTitle.setToolTipText("");
		lblTitle.setBounds(86, 6, 172, 23);
		frame.getContentPane().add(lblTitle);

		lblID = new JLabel("MÃ NHÂN VIÊN");
		lblID.setBounds(6, 50, 87, 16);
		frame.getContentPane().add(lblID);

		lblName = new JLabel("HỌ VÀ TÊN");
		lblName.setBounds(6, 90, 64, 16);
		frame.getContentPane().add(lblName);

		lblAge = new JLabel("TUỔI");
		lblAge.setBounds(6, 126, 28, 16);
		frame.getContentPane().add(lblAge);

		lblEmail = new JLabel("EMAIL");
		lblEmail.setBounds(6, 161, 36, 16);
		frame.getContentPane().add(lblEmail);

		lblSalary = new JLabel("LƯƠNG");
		lblSalary.setBounds(6, 195, 45, 16);
		frame.getContentPane().add(lblSalary);

		txtID = new JTextField();
		txtID.setToolTipText("Nhập Mã ID Nhân viên");
		txtID.setBounds(105, 44, 112, 28);
		frame.getContentPane().add(txtID);
		txtID.setColumns(10);

		txtName = new JTextField();
		txtName.setToolTipText("Nhập Họ tên");
		txtName.setColumns(10);
		txtName.setBounds(105, 84, 350, 28);
		frame.getContentPane().add(txtName);

		txtAge = new JTextField();
		txtAge.setToolTipText("Nhập số tuổi");
		txtAge.setColumns(10);
		txtAge.setBounds(105, 120, 112, 28);
		frame.getContentPane().add(txtAge);

		txtEmail = new JTextField();
		txtEmail.setToolTipText("abc@mail.com");
		txtEmail.setColumns(10);
		txtEmail.setBounds(105, 155, 350, 28);
		frame.getContentPane().add(txtEmail);

		txtSalary = new JTextField();
		txtSalary.setToolTipText("> 5.000.000");
		txtSalary.setColumns(10);
		txtSalary.setBounds(105, 189, 112, 28);
		frame.getContentPane().add(txtSalary);

		lblClock = new JLabel("HH:mm aa");
		lblClock.setForeground(Color.RED);
		lblClock.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblClock.setBounds(485, 20, 68, 19);
		frame.getContentPane().add(lblClock);
		Thread t = new Thread() {
			public void run() {
				while (true) {
					try {
						SimpleDateFormat formater = new SimpleDateFormat();
						formater.applyPattern("hh:mm aa");
						String time = formater.format(new Date());
						lblClock.setText(time);
					} catch (Exception e) {
						break;
					}
				}
			}
		};
		t.start();

		btnNew = new JButton("New");
		btnNew.setBackground(new Color(255, 153, 153));
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearForm();
				index = -1;
				setStatusButton();
			}
		});
		btnNew.setBounds(467, 48, 90, 28);
		frame.getContentPane().add(btnNew);

		btnSave = new JButton("Save");
		btnSave.setBackground(new Color(255, 153, 153));
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (index == -1) {
					addEmployee();
				} else {
					updateEmployee();
				}
				fillToTable();
				setStatusButton();
			}
		});
		btnSave.setBounds(467, 84, 90, 28);
		frame.getContentPane().add(btnSave);

		btnDelete = new JButton("Delete");
		btnDelete.setBackground(new Color(255, 153, 153));
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeEmployee();
				clearForm();
				index = -1;
				fillToTable();
				setStatusButton();
			}
		});
		btnDelete.setBounds(467, 119, 90, 28);
		frame.getContentPane().add(btnDelete);

		btnFind = new JButton("Find");
		btnFind.setBackground(new Color(255, 153, 153));
		btnFind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				findEmployee();
			}
		});
		btnFind.setBounds(467, 153, 90, 28);
		frame.getContentPane().add(btnFind);

		btnOpen = new JButton("Open");
		btnOpen.setBackground(new Color(255, 153, 153));
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openFile();
				if (list.size() > 0) {
					index = 0;
					fillToTable();
					showDetail();
				} else {
					index = -1;
					clearForm();
					fillToTable();
				}
				setStatusButton();
			}
		});
		btnOpen.setBounds(467, 191, 90, 28);
		frame.getContentPane().add(btnOpen);

		btnExit = new JButton("Exit");
		btnExit.setBackground(new Color(255, 153, 153));
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveFile();
				System.exit(0);
			}
		});
		btnExit.setBounds(467, 229, 90, 28);
		frame.getContentPane().add(btnExit);

		btnBegin = new JButton("|<");
		btnBegin.setBackground(new Color(255, 204, 102));
		btnBegin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (list.size() != 0) {
					index = 0;
					showDetail();
				}
				setStatusButton();
			}
		});
		btnBegin.setBounds(68, 229, 50, 28);
		frame.getContentPane().add(btnBegin);

		btnBack = new JButton("<<");
		btnBack.setBackground(new Color(255, 204, 153));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (index > 0) {
					index--;
					showDetail();
				}
				setStatusButton();
			}
		});
		btnBack.setBounds(118, 229, 50, 28);
		frame.getContentPane().add(btnBack);

		btnNext = new JButton(">>");
		btnNext.setBackground(new Color(255, 204, 153));
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (index < list.size() - 1) {
					index++;
					showDetail();
				}
				setStatusButton();
			}
		});
		btnNext.setBounds(180, 229, 50, 28);
		frame.getContentPane().add(btnNext);

		btnEnd = new JButton(">|");
		btnEnd.setBackground(new Color(255, 204, 102));
		btnEnd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (list.size() != 0) {
					index = list.size() - 1;
					showDetail();
				}
				setStatusButton();
			}
		});
		btnEnd.setBounds(230, 229, 50, 28);
		frame.getContentPane().add(btnEnd);

		lblStatus = new JLabel("");
		lblStatus.setForeground(Color.RED);
		lblStatus.setBounds(292, 235, 95, 16);
		frame.getContentPane().add(lblStatus);

		JPanel panel = new JPanel();
		panel.setBounds(6, 261, 547, 132);
		frame.getContentPane().add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		panel.add(scrollPane, gbc_scrollPane);

		model = new DefaultTableModel();
		model.addColumn("MÃ");
		model.addColumn("HỌ VÀ TÊN");
		model.addColumn("TUỔI");
		model.addColumn("EMAIL");
		model.addColumn("LƯƠNG");

		table = new JTable(model);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				index = table.getSelectedRow();
				showDetail();
				setStatusButton();
			}
		});
		scrollPane.setViewportView(table);

		JLabel lblBackground = new JLabel("");
		lblBackground.setIcon(new ImageIcon("C:\\Users\\holid\\Pictures\\0004 - HBKMXib.jpg"));
		lblBackground.setBounds(0, 0, 573, 409);
		frame.getContentPane().add(lblBackground);
		frame.setResizable(false);

	}

	private void setStatusButton() {
		if (index == list.size() - 1) {
			btnNext.setEnabled(false);
			btnEnd.setEnabled(false);
		}
		if (index == 0) {
			btnBack.setEnabled(false);
			btnBegin.setEnabled(false);
		}
		if (index != 0) {
			btnBegin.setEnabled(true);
			btnBack.setEnabled(true);

		}
		if(index != list.size() - 1) {
			btnNext.setEnabled(true);
			btnEnd.setEnabled(true);
		}
	}

	private void fillToTable() {
		model.setRowCount(0);
		for (Employee employee : list) {
			model.addRow(new Object[] { employee.getId(), employee.getName(), employee.getAge(), employee.getEmail(),
					employee.getSalary() });
		}
	}

	private void showDetail() {
		lblStatus.setText("Record: " + (index + 1) + " of " + list.size());
		txtID.setText(list.get(index).getId());
		txtName.setText(list.get(index).getName());
		txtAge.setText(String.valueOf(list.get(index).getAge()));
		txtEmail.setText(list.get(index).getEmail());
		txtSalary.setText(String.valueOf(list.get(index).getSalary()));
		table.setRowSelectionInterval(index, index);
	}

	@SuppressWarnings("unchecked")
	private void openFile() {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("C:/Users/holid/Desktop/staff.dat"));
			Object object = ois.readObject();
			ois.close();
			list = (List<Employee>) object;
			table.setEditingRow(0);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void saveFile() {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("C:/Users/holid/Desktop/staff.dat"));
			oos.writeObject(list);
			oos.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// Hàm check tên tiếng Việt Unicode
	public static boolean validateLetters(String txt) {
		String regx = "^[\\p{L}\\p{M}]+([\\p{L}\\p{Pd}\\p{Zs}]*[\\p{L}\\p{M}])+$|^[\\p{L}\\p{M}]+$";
		Pattern pattern = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(txt);
		return matcher.find();
	}

	// Function validate Email
	public static boolean validateEmail(String emailStr) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
		return matcher.find();
	}

	private boolean readForm() {
		// ID
		if (txtID.getText().isBlank()) {
			txtID.setBackground(Color.YELLOW);
			JOptionPane.showMessageDialog(btnSave, "Trống", "Mã nhân viên", JOptionPane.WARNING_MESSAGE);
			return false;
		} else
		// Name
		if (txtName.getText().isBlank()) {
			txtID.setBackground(null);
			txtName.setBackground(Color.YELLOW);
			JOptionPane.showMessageDialog(btnSave, "Trống", "Họ và tên", JOptionPane.WARNING_MESSAGE);
			return false;
		} else if (!validateLetters(txtName.getText())) {
			txtID.setBackground(null);
			txtName.setBackground(Color.YELLOW);
			JOptionPane.showMessageDialog(btnSave, "Kí tự nhập không hợp lệ!\nVui lòng chỉ nhập kí tự chữ!!",
					"Họ và tên", JOptionPane.ERROR_MESSAGE);
			return false;
		} else {
			txtID.setBackground(null);
			txtName.setBackground(null);
			// Age
			try {
				if (txtAge.getText().isBlank()) {
					txtAge.setBackground(Color.YELLOW);
					JOptionPane.showMessageDialog(btnSave, "Trống", "Tuổi", JOptionPane.WARNING_MESSAGE);
					return false;
				} else if (Integer.parseInt(txtAge.getText()) <= 16 || Integer.parseInt(txtAge.getText()) >= 55) {
					JOptionPane.showMessageDialog(btnSave, "Tuổi không hợp lệ!", "Tuổi", JOptionPane.ERROR_MESSAGE);
					return false;
				} else {
					Integer.parseInt(txtAge.getText());
					txtAge.setBackground(null);
				}
			} catch (Exception e) {
				txtAge.setBackground(Color.YELLOW);
				JOptionPane.showMessageDialog(btnSave, "Số Tuổi nhập không hợp lệ!\nNhập lại!", "Tuổi",
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
			// Email
			if (txtEmail.getText().isBlank()) {
				txtEmail.setBackground(Color.YELLOW);
				JOptionPane.showMessageDialog(btnSave, "Trống", "Email", JOptionPane.WARNING_MESSAGE);
				return false;
			} else if (!validateEmail(txtEmail.getText())) {
				txtEmail.setBackground(Color.YELLOW);
				JOptionPane.showMessageDialog(btnSave, "Email không chính xác!", "Email", JOptionPane.ERROR_MESSAGE);
				return false;
			} else {
				txtEmail.setBackground(null);
			}
			// Salary
			try {
				if (txtSalary.getText().isBlank()) {
					txtSalary.setBackground(Color.YELLOW);
					JOptionPane.showMessageDialog(btnSave, "Trống", "Lương", JOptionPane.WARNING_MESSAGE);
					return false;
				} else if (Double.parseDouble(txtSalary.getText()) <= 5000000) {
					JOptionPane.showMessageDialog(btnSave, "Lương không hợp lệ", "Lương", JOptionPane.ERROR_MESSAGE);
					return false;
				} else {
					Double.parseDouble(txtSalary.getText());
					txtSalary.setBackground(null);
				}
			} catch (Exception e) {
				txtSalary.setBackground(Color.YELLOW);
				JOptionPane.showMessageDialog(btnSave, "Số nhập không hợp lệ!\nNhập lại!", "Lương",
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
			return true;
		}
	}

	private void addEmployee() {
		if (readForm()) {
			Employee emp = new Employee(txtID.getText(), txtName.getText(), Integer.parseInt(txtAge.getText()),
					txtEmail.getText(), Double.parseDouble(txtSalary.getText()));
			list.add(emp);
			index = list.indexOf(emp);
			clearForm();
		}
	}

	private void updateEmployee() {
//		index = table.getSelectedRow();
		if (readForm()) {
			list.remove(index);
			Employee emp = new Employee(txtID.getText(), txtName.getText(), Integer.parseInt(txtAge.getText()),
					txtEmail.getText(), Double.parseDouble(txtSalary.getText()));
			list.add(index, emp);
			JOptionPane.showMessageDialog(btnSave, "Cập nhật Mã Nhân viên " + emp.getId() + " thành công", "Thông báo",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private void removeEmployee() {
		if (txtID.getText().isBlank()) {
			JOptionPane.showMessageDialog(btnDelete, "Xoá Thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
		} else {
			for (Employee employee : list) {
				if (employee.getId().equals(txtID.getText())) {
					list.remove(list.indexOf(employee));
					JOptionPane.showMessageDialog(btnDelete, "Xoá Thành công!", "Thông báo",
							JOptionPane.INFORMATION_MESSAGE);
					lblStatus.setText("");
					break;
				}
			}
		}
	}

	private void findEmployee() {
		if (list.size() == 0) {
			JOptionPane.showMessageDialog(btnFind, "Dữ liệu trống!", "Thông báo", JOptionPane.ERROR_MESSAGE);
		} else if (txtID.getText().isBlank()) {
			JOptionPane.showMessageDialog(btnFind, "Chưa nhập Mã nhân viên", "Thông báo", JOptionPane.ERROR_MESSAGE);
		} else {
			boolean flag = false;
			for (Employee employee : list) {
				if (employee.getId().equals(txtID.getText())) {
					index = list.indexOf(employee);
					flag = true;
					showDetail();
					JOptionPane.showMessageDialog(btnFind, "Đã tìm thấy!", "Thông báo",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
			if (!flag) {
				JOptionPane.showMessageDialog(btnFind, "Không tìm thấy!", "Thông báo", JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	private void clearForm() {
		txtID.setText("");
		txtName.setText("");
		txtAge.setText("");
		txtEmail.setText("");
		txtSalary.setText("");
		lblStatus.setText("");
		table.clearSelection();
	}

	@Override
	public void run() {

	}
}

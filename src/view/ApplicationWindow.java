package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.SpringLayout;
import javax.swing.border.LineBorder;

import model.Document;

public class ApplicationWindow {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ApplicationWindow window = new ApplicationWindow();
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
	public ApplicationWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 640, 541);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);

		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.45);
		springLayout.putConstraint(SpringLayout.NORTH, splitPane, 10, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, splitPane, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, splitPane, -10, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, splitPane, -10, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(splitPane);

		Font plainFont = new Font("Halvetica", Font.PLAIN,14);
		JPanel folderPanel = new JPanel();
		folderPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		splitPane.setLeftComponent(folderPanel);
		SpringLayout folderLayout = new SpringLayout();
		folderPanel.setLayout(folderLayout);

		JComboBox<Document> comboBox = new JComboBox<Document>();
		folderLayout.putConstraint(SpringLayout.NORTH, comboBox, 5, SpringLayout.NORTH, folderPanel);
		folderLayout.putConstraint(SpringLayout.WEST, comboBox, 5, SpringLayout.WEST, folderPanel);
		folderLayout.putConstraint(SpringLayout.EAST, comboBox, -5, SpringLayout.EAST, folderPanel);
		folderPanel.add(comboBox);
		JSeparator topSeparator = new JSeparator();
		folderLayout.putConstraint(SpringLayout.NORTH, topSeparator, 0, SpringLayout.SOUTH, comboBox);
		folderLayout.putConstraint(SpringLayout.WEST, topSeparator, 0, SpringLayout.WEST, folderPanel);
		folderLayout.putConstraint(SpringLayout.EAST, topSeparator, 0, SpringLayout.EAST, folderPanel);
		folderPanel.add(topSeparator);

		JLabel lblSort = new JLabel("Sort By:");
		folderLayout.putConstraint(SpringLayout.NORTH, lblSort, 4, SpringLayout.SOUTH, topSeparator);
		folderLayout.putConstraint(SpringLayout.WEST, lblSort, 0, SpringLayout.WEST, comboBox);
		lblSort.setFont(plainFont);
		folderPanel.add(lblSort);

		JRadioButton rdbtnName = new JRadioButton("Name");
		rdbtnName.setFont(plainFont);
		folderLayout.putConstraint(SpringLayout.WEST, rdbtnName, 0, SpringLayout.EAST, lblSort);
		folderLayout.putConstraint(SpringLayout.NORTH, rdbtnName, 0, SpringLayout.SOUTH, topSeparator);
		folderPanel.add(rdbtnName);

		JRadioButton rdbtnCreationDate = new JRadioButton("Creation date");
		rdbtnCreationDate.setFont(plainFont);
		folderLayout.putConstraint(SpringLayout.NORTH, rdbtnCreationDate, -4, SpringLayout.NORTH, lblSort);
		folderLayout.putConstraint(SpringLayout.WEST, rdbtnCreationDate, 0, SpringLayout.EAST, rdbtnName);
		folderLayout.putConstraint(SpringLayout.EAST, rdbtnCreationDate, 0, SpringLayout.EAST, folderPanel);
		folderPanel.add(rdbtnCreationDate);

		JRadioButton rdbtnSize = new JRadioButton("Size");
		rdbtnSize.setFont(plainFont);
		folderLayout.putConstraint(SpringLayout.NORTH, rdbtnSize, 0, SpringLayout.SOUTH, rdbtnCreationDate);
		folderLayout.putConstraint(SpringLayout.WEST, rdbtnSize, 0, SpringLayout.WEST, rdbtnCreationDate);
		folderLayout.putConstraint(SpringLayout.EAST, rdbtnSize, 0, SpringLayout.EAST, rdbtnCreationDate);
		folderPanel.add(rdbtnSize);

		JRadioButton rdbtnType = new JRadioButton("Type");
		rdbtnType.setFont(plainFont);
		folderLayout.putConstraint(SpringLayout.NORTH, rdbtnType, 0, SpringLayout.SOUTH, rdbtnName);
		folderLayout.putConstraint(SpringLayout.WEST, rdbtnType, 0, SpringLayout.WEST, rdbtnName);
		folderLayout.putConstraint(SpringLayout.EAST, rdbtnType, 0, SpringLayout.EAST, rdbtnName);
		folderPanel.add(rdbtnType);

		JSeparator centerSeparator = new JSeparator();
		folderLayout.putConstraint(SpringLayout.NORTH, centerSeparator, 0, SpringLayout.SOUTH, rdbtnType);
		folderLayout.putConstraint(SpringLayout.WEST, centerSeparator, 0, SpringLayout.WEST, folderPanel);
		folderLayout.putConstraint(SpringLayout.EAST, centerSeparator, 0, SpringLayout.EAST, folderPanel);
		folderPanel.add(centerSeparator);

		JButton btnDocumentDelete = new JButton("Delete");
		folderLayout.putConstraint(SpringLayout.WEST, btnDocumentDelete, 0, SpringLayout.WEST, folderPanel);
		folderLayout.putConstraint(SpringLayout.SOUTH, btnDocumentDelete, 0, SpringLayout.SOUTH, folderPanel);
		folderLayout.putConstraint(SpringLayout.EAST, btnDocumentDelete, 0, SpringLayout.EAST, folderPanel);
		folderPanel.add(btnDocumentDelete);

		JButton btnDocumentAdd = new JButton("Add");
		folderLayout.putConstraint(SpringLayout.WEST, btnDocumentAdd, 0, SpringLayout.WEST, folderPanel);
		folderLayout.putConstraint(SpringLayout.SOUTH, btnDocumentAdd, 0, SpringLayout.NORTH, btnDocumentDelete);
		folderLayout.putConstraint(SpringLayout.EAST, btnDocumentAdd, 0, SpringLayout.EAST, folderPanel);
		folderPanel.add(btnDocumentAdd);

		JButton btnDocumentShow = new JButton("Show");
		folderLayout.putConstraint(SpringLayout.WEST, btnDocumentShow, 0, SpringLayout.WEST, folderPanel);
		folderLayout.putConstraint(SpringLayout.SOUTH, btnDocumentShow, 0, SpringLayout.NORTH, btnDocumentAdd);
		folderLayout.putConstraint(SpringLayout.EAST, btnDocumentShow, 0, SpringLayout.EAST, folderPanel);
		folderPanel.add(btnDocumentShow);

		JButton btnFolderRefresh = new JButton("Refresh");
		folderLayout.putConstraint(SpringLayout.WEST, btnFolderRefresh, 0, SpringLayout.WEST, folderPanel);
		folderLayout.putConstraint(SpringLayout.SOUTH, btnFolderRefresh, 0, SpringLayout.NORTH, btnDocumentShow);
		folderLayout.putConstraint(SpringLayout.EAST, btnFolderRefresh, 0, SpringLayout.EAST, folderPanel);
		folderPanel.add(btnFolderRefresh);

		JSeparator battonSeparator = new JSeparator();
		folderLayout.putConstraint(SpringLayout.WEST, battonSeparator, 0, SpringLayout.WEST, folderPanel);
		folderLayout.putConstraint(SpringLayout.SOUTH, battonSeparator, 0, SpringLayout.NORTH, btnFolderRefresh);
		folderLayout.putConstraint(SpringLayout.EAST, battonSeparator, 0, SpringLayout.EAST, folderPanel);
		folderPanel.add(battonSeparator);

		JList<String> list = new JList<String>();
		list.setModel(new AbstractListModel<String>() {

			private static final long serialVersionUID = 1L;
			String[] values = new String[] {"item01", "item02", "item03 ", "item01", "item02", "item03 ", "item01", "item02", "item03 ", "item01", "item02", "item03 ", "item01", "item02", "item03 ", "item01", "item02", "item03 item01", "item02", "item03 ", "item01", "item02", "item03 item01", "item02", "item03 ", "item01", "item02", "item03 ", "item01", "item02", "item03 ", "item01", "item02", "item03 ", "item01", "item02", "item03 item01", "item02", "item03 "};
			public int getSize() {
				return values.length;
			}
			public String getElementAt(int index) {
				return values[index];
			}
		});
		list.setBorder(new LineBorder(new Color(0, 0, 0)));
		folderLayout.putConstraint(SpringLayout.NORTH, list, 0, SpringLayout.SOUTH, centerSeparator);
		folderLayout.putConstraint(SpringLayout.WEST, list, 5, SpringLayout.WEST, folderPanel);
		folderLayout.putConstraint(SpringLayout.SOUTH, list, 0, SpringLayout.NORTH, battonSeparator);
		folderLayout.putConstraint(SpringLayout.EAST, list, -5, SpringLayout.EAST, folderPanel);
		folderPanel.add(list);

		JPanel documentPanel = new JPanel();
		splitPane.setRightComponent(documentPanel);
		SpringLayout documentLayout = new SpringLayout();
		documentPanel.setLayout(documentLayout);

	}
}

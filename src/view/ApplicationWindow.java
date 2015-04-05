package view;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.AbstractListModel;
import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.LineBorder;

import model.Document;
import model.DocumentType;
import model.Folder;
import controller.ViewController;

public class ApplicationWindow {

	private final class AbstractListModelExtension extends AbstractListModel<String> {

		private static final long serialVersionUID = -3522481107430003683L;

		int selectedIndex = getComboBoxFolders().getSelectedIndex();

		Folder selectedFolder = getController().getFolders().get(selectedIndex);

		private ArrayList<Document> values = sortValues();

		private ArrayList<Document> sortValues() {
			ArrayList<Document> files = selectedFolder.folderContent();

			String key = getSelectedButtonString();

			// We test to see if the key is null in order to know if we should sort the array 
			if (key != null) {
				if (key.equals("Creation Date")) {
					files.sort(Document.DocumentComparatorCreationDate);
				} else if (key.equals("Name")) {
					files.sort(Document.DocumentComparatorName);
				} else if (key.equals("Size")) {
					files.sort(Document.DocumentComparatorSize);
				} else if (key.equals("Type")) {
					files.sort(Document.DocumentComparatorType);
				}
			}
			return files;
		}

		@Override
		public int getSize() {
			return getValues().size();
		}

		@Override
		public String getElementAt(int index) {
			return getValues().get(index).getDocumentName();
		}
		public ArrayList<Document> getValues() {
			return values;
		}
	}
	
	// Attributes
	private ViewController controller;

	private Font plainFont;
	private Font  boldFont;

	private JFrame frame;

	// Folder Panel
	private JComboBox<Folder> comboBoxFolders;
	private ButtonGroup sortSelection;
	private JList<String> documentsList;
	private JButton btnFolderRefresh;
	private JButton btnDocumentShow;
	private JButton btnDocumentAdd;
	private JButton btnDocumentDelete;

	// Document Panel
	private JTextField txtFileNameValue;
	private JTextField txtFilePathValue;
	private JTextArea txtDocumentText;
	private JButton btnDocumentEdit;
	private JComboBox<Object> comboBoxDocumentType;
	private JButton btnDocumentSaveAs;
	private JButton btnDocumentNew;
	private JButton btnDocumentClear;
	private JButton btnDocumentSave;
	private JRadioButton rdbtnName;
	private JRadioButton rdbtnCreationDate;
	private JRadioButton rdbtnSize;
	private JRadioButton rdbtnType;



	// Constructors
	public ApplicationWindow(ViewController controller) {
		this.setController(controller);
		this.plainFont = new Font("Halvetica", Font.PLAIN,14);
		this.boldFont = new Font("Halvetica", Font.BOLD,14);
		initialize();
		registerControllerAsListener();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 640, 541);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		getFrame().getContentPane().setLayout(springLayout);

		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.45);

		springLayout.putConstraint(SpringLayout.NORTH, splitPane, 10, SpringLayout.NORTH, getFrame().getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, splitPane, 10, SpringLayout.WEST, getFrame().getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, splitPane, -10, SpringLayout.SOUTH, getFrame().getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, splitPane, -10, SpringLayout.EAST, getFrame().getContentPane());
		getFrame().getContentPane().add(splitPane);

		setUpFolderPanel(splitPane);
		setUpDocument(splitPane);
		loadDataToViews();

		getFrame().setVisible(true);
	}

	private void setUpFolderPanel(JSplitPane splitPane) {
		JPanel folderPanel = new JPanel();
		folderPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		splitPane.setLeftComponent(folderPanel);
		SpringLayout folderLayout = new SpringLayout();
		folderPanel.setLayout(folderLayout);

		comboBoxFolders = new JComboBox<Folder>();
		folderLayout.putConstraint(SpringLayout.NORTH, comboBoxFolders, 5, SpringLayout.NORTH, folderPanel);
		folderLayout.putConstraint(SpringLayout.WEST, comboBoxFolders, 5, SpringLayout.WEST, folderPanel);
		folderLayout.putConstraint(SpringLayout.EAST, comboBoxFolders, -5, SpringLayout.EAST, folderPanel);
		folderPanel.add(comboBoxFolders);

		JSeparator topFolderSeparator = new JSeparator();
		folderLayout.putConstraint(SpringLayout.NORTH, topFolderSeparator, 0, SpringLayout.SOUTH, comboBoxFolders);
		folderLayout.putConstraint(SpringLayout.WEST, topFolderSeparator, 0, SpringLayout.WEST, folderPanel);
		folderLayout.putConstraint(SpringLayout.EAST, topFolderSeparator, 0, SpringLayout.EAST, folderPanel);
		folderPanel.add(topFolderSeparator);

		JLabel lblSort = new JLabel("Sort By:");
		folderLayout.putConstraint(SpringLayout.NORTH, lblSort, 4, SpringLayout.SOUTH, topFolderSeparator);
		folderLayout.putConstraint(SpringLayout.WEST, lblSort, 0, SpringLayout.WEST, comboBoxFolders);
		lblSort.setFont(getPlainFont());
		folderPanel.add(lblSort);

		rdbtnName = new JRadioButton("Name");
		rdbtnName.setFont(getPlainFont());
		folderLayout.putConstraint(SpringLayout.WEST, rdbtnName, 0, SpringLayout.EAST, lblSort);
		folderLayout.putConstraint(SpringLayout.NORTH, rdbtnName, 0, SpringLayout.SOUTH, topFolderSeparator);
		folderPanel.add(rdbtnName);

		rdbtnCreationDate = new JRadioButton("Creation date");
		rdbtnCreationDate.setFont(getPlainFont());
		folderLayout.putConstraint(SpringLayout.NORTH, rdbtnCreationDate, -4, SpringLayout.NORTH, lblSort);
		folderLayout.putConstraint(SpringLayout.WEST, rdbtnCreationDate, 0, SpringLayout.EAST, rdbtnName);
		folderLayout.putConstraint(SpringLayout.EAST, rdbtnCreationDate, 0, SpringLayout.EAST, folderPanel);
		folderPanel.add(rdbtnCreationDate);

		rdbtnSize = new JRadioButton("Size");
		rdbtnSize.setFont(getPlainFont());
		folderLayout.putConstraint(SpringLayout.NORTH, rdbtnSize, 0, SpringLayout.SOUTH, rdbtnCreationDate);
		folderLayout.putConstraint(SpringLayout.WEST, rdbtnSize, 0, SpringLayout.WEST, rdbtnCreationDate);
		folderLayout.putConstraint(SpringLayout.EAST, rdbtnSize, 0, SpringLayout.EAST, rdbtnCreationDate);
		folderPanel.add(rdbtnSize);

		rdbtnType = new JRadioButton("Type");
		rdbtnType.setFont(getPlainFont());
		folderLayout.putConstraint(SpringLayout.NORTH, rdbtnType, 0, SpringLayout.SOUTH, rdbtnName);
		folderLayout.putConstraint(SpringLayout.WEST, rdbtnType, 0, SpringLayout.WEST, rdbtnName);
		folderLayout.putConstraint(SpringLayout.EAST, rdbtnType, 0, SpringLayout.EAST, rdbtnName);
		folderPanel.add(rdbtnType);

		sortSelection = new ButtonGroup();
		sortSelection.add(rdbtnCreationDate);
		sortSelection.add(rdbtnName);
		sortSelection.add(rdbtnSize);
		sortSelection.add(rdbtnType);
		sortSelection.clearSelection();
		rdbtnName.setSelected(true);

		JSeparator centerFolderSeparator = new JSeparator();
		folderLayout.putConstraint(SpringLayout.NORTH, centerFolderSeparator, 0, SpringLayout.SOUTH, rdbtnType);
		folderLayout.putConstraint(SpringLayout.WEST, centerFolderSeparator, 0, SpringLayout.WEST, folderPanel);
		folderLayout.putConstraint(SpringLayout.EAST, centerFolderSeparator, 0, SpringLayout.EAST, folderPanel);
		folderPanel.add(centerFolderSeparator);

		btnDocumentDelete = new JButton("Delete");
		folderLayout.putConstraint(SpringLayout.WEST, btnDocumentDelete, 0, SpringLayout.WEST, folderPanel);
		folderLayout.putConstraint(SpringLayout.SOUTH, btnDocumentDelete, 0, SpringLayout.SOUTH, folderPanel);
		folderLayout.putConstraint(SpringLayout.EAST, btnDocumentDelete, 0, SpringLayout.EAST, folderPanel);
		folderPanel.add(btnDocumentDelete);

		btnDocumentAdd = new JButton("Add");
		folderLayout.putConstraint(SpringLayout.WEST, btnDocumentAdd, 0, SpringLayout.WEST, folderPanel);
		folderLayout.putConstraint(SpringLayout.SOUTH, btnDocumentAdd, 0, SpringLayout.NORTH, btnDocumentDelete);
		folderLayout.putConstraint(SpringLayout.EAST, btnDocumentAdd, 0, SpringLayout.EAST, folderPanel);
		folderPanel.add(btnDocumentAdd);

		btnDocumentShow = new JButton("Show");
		folderLayout.putConstraint(SpringLayout.WEST, btnDocumentShow, 0, SpringLayout.WEST, folderPanel);
		folderLayout.putConstraint(SpringLayout.SOUTH, btnDocumentShow, 0, SpringLayout.NORTH, btnDocumentAdd);
		folderLayout.putConstraint(SpringLayout.EAST, btnDocumentShow, 0, SpringLayout.EAST, folderPanel);
		folderPanel.add(btnDocumentShow);

		btnFolderRefresh = new JButton("Refresh");
		folderLayout.putConstraint(SpringLayout.WEST, btnFolderRefresh, 0, SpringLayout.WEST, folderPanel);
		folderLayout.putConstraint(SpringLayout.SOUTH, btnFolderRefresh, 0, SpringLayout.NORTH, btnDocumentShow);
		folderLayout.putConstraint(SpringLayout.EAST, btnFolderRefresh, 0, SpringLayout.EAST, folderPanel);
		folderPanel.add(btnFolderRefresh);

		JSeparator battomFolderSeparator = new JSeparator();
		folderLayout.putConstraint(SpringLayout.WEST, battomFolderSeparator, 0, SpringLayout.WEST, folderPanel);
		folderLayout.putConstraint(SpringLayout.SOUTH, battomFolderSeparator, 0, SpringLayout.NORTH, btnFolderRefresh);
		folderLayout.putConstraint(SpringLayout.EAST, battomFolderSeparator, 0, SpringLayout.EAST, folderPanel);
		folderPanel.add(battomFolderSeparator);

		JScrollPane scrollPaneFolder = new JScrollPane();
		scrollPaneFolder.setBorder(new LineBorder(new Color(0, 0, 0)));
		folderLayout.putConstraint(SpringLayout.NORTH, scrollPaneFolder, 0, SpringLayout.SOUTH, centerFolderSeparator);
		folderLayout.putConstraint(SpringLayout.WEST, scrollPaneFolder, 5, SpringLayout.WEST, folderPanel);
		folderLayout.putConstraint(SpringLayout.SOUTH, scrollPaneFolder, 0, SpringLayout.NORTH, battomFolderSeparator);
		folderLayout.putConstraint(SpringLayout.EAST, scrollPaneFolder, -5, SpringLayout.EAST, folderPanel);
		folderPanel.add(scrollPaneFolder);

		documentsList = new JList<String>();
		scrollPaneFolder.setViewportView(documentsList);
	}

	private void setUpDocument(JSplitPane splitPane) {
		JPanel documentPanel = new JPanel();
		documentPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		splitPane.setRightComponent(documentPanel);
		SpringLayout documentLayout = new SpringLayout();
		documentPanel.setLayout(documentLayout);

		JLabel lblFileName = new JLabel("File Name :");
		lblFileName.setFont(getBoldFont());
		documentPanel.add(lblFileName);

		txtFileNameValue = new JTextField();
		txtFileNameValue.setText("fileName");
		txtFileNameValue.setEnabled(false);
		documentLayout.putConstraint(SpringLayout.WEST, txtFileNameValue, 90, SpringLayout.WEST, documentPanel);
		documentLayout.putConstraint(SpringLayout.EAST, txtFileNameValue, 0, SpringLayout.EAST, documentPanel);
		documentLayout.putConstraint(SpringLayout.NORTH, lblFileName, 5, SpringLayout.NORTH, txtFileNameValue);
		documentLayout.putConstraint(SpringLayout.EAST, lblFileName, 0, SpringLayout.WEST, txtFileNameValue);
		documentLayout.putConstraint(SpringLayout.NORTH, txtFileNameValue, 10, SpringLayout.NORTH, documentPanel);
		documentPanel.add(txtFileNameValue);
		txtFileNameValue.setColumns(10);

		JLabel lblFilePath = new JLabel("File Path :");
		documentLayout.putConstraint(SpringLayout.WEST, lblFilePath, 0, SpringLayout.WEST, lblFileName);
		lblFilePath.setFont(getBoldFont());
		documentPanel.add(lblFilePath);

		txtFilePathValue = new JTextField();
		txtFilePathValue.setEnabled(false);
		txtFilePathValue.setText("filePath");
		documentLayout.putConstraint(SpringLayout.NORTH, lblFilePath, 5, SpringLayout.NORTH, txtFilePathValue);
		documentLayout.putConstraint(SpringLayout.EAST, lblFilePath, 0, SpringLayout.WEST, txtFilePathValue);
		documentLayout.putConstraint(SpringLayout.NORTH, txtFilePathValue, 0, SpringLayout.SOUTH, txtFileNameValue);
		documentLayout.putConstraint(SpringLayout.WEST, txtFilePathValue, 0, SpringLayout.WEST, txtFileNameValue);
		documentLayout.putConstraint(SpringLayout.EAST, txtFilePathValue, 0, SpringLayout.EAST, documentPanel);
		documentPanel.add(txtFilePathValue);
		txtFilePathValue.setColumns(10);

		JSeparator topDocumentSeparator = new JSeparator();
		documentLayout.putConstraint(SpringLayout.NORTH, topDocumentSeparator, 0, SpringLayout.SOUTH, txtFilePathValue);
		documentLayout.putConstraint(SpringLayout.WEST, topDocumentSeparator, 0, SpringLayout.WEST, documentPanel);
		documentLayout.putConstraint(SpringLayout.EAST, topDocumentSeparator, 0, SpringLayout.EAST, documentPanel);
		documentPanel.add(topDocumentSeparator);

		btnDocumentSave = new JButton("Save");
		btnDocumentSave.setEnabled(false);
		documentLayout.putConstraint(SpringLayout.SOUTH, btnDocumentSave, 0, SpringLayout.SOUTH, documentPanel);
		documentLayout.putConstraint(SpringLayout.EAST, btnDocumentSave, 0, SpringLayout.EAST, documentPanel);
		documentPanel.add(btnDocumentSave);

		btnDocumentClear = new JButton("Clear");
		btnDocumentClear.setEnabled(false);
		documentLayout.putConstraint(SpringLayout.SOUTH, btnDocumentClear, 0, SpringLayout.SOUTH, btnDocumentSave);
		documentLayout.putConstraint(SpringLayout.EAST, btnDocumentClear, 0, SpringLayout.WEST, btnDocumentSave);
		documentPanel.add(btnDocumentClear);

		btnDocumentNew = new JButton("New");
		btnDocumentNew.setEnabled(false);
		documentLayout.putConstraint(SpringLayout.NORTH, btnDocumentNew, 0, SpringLayout.NORTH, btnDocumentSave);
		documentLayout.putConstraint(SpringLayout.SOUTH, btnDocumentNew, 0, SpringLayout.SOUTH, documentPanel);
		documentLayout.putConstraint(SpringLayout.EAST, btnDocumentNew, 0, SpringLayout.WEST, btnDocumentClear);
		documentPanel.add(btnDocumentNew);

		btnDocumentSaveAs = new JButton("Save As");
		btnDocumentSaveAs.setEnabled(false);
		documentLayout.putConstraint(SpringLayout.WEST, btnDocumentSave, 0, SpringLayout.WEST, btnDocumentSaveAs);
		documentLayout.putConstraint(SpringLayout.SOUTH, btnDocumentSaveAs, 0, SpringLayout.NORTH, btnDocumentSave);
		documentLayout.putConstraint(SpringLayout.EAST, btnDocumentSaveAs, 0, SpringLayout.EAST, documentPanel);
		documentPanel.add(btnDocumentSaveAs);

		comboBoxDocumentType = new JComboBox<Object>();
		comboBoxDocumentType.setEnabled(false);
		comboBoxDocumentType.setModel(new DefaultComboBoxModel<Object>(DocumentType.values()));
		documentLayout.putConstraint(SpringLayout.NORTH, comboBoxDocumentType, 0, SpringLayout.NORTH, btnDocumentSaveAs);
		documentLayout.putConstraint(SpringLayout.WEST, comboBoxDocumentType, 0, SpringLayout.WEST, btnDocumentNew);
		documentLayout.putConstraint(SpringLayout.EAST, comboBoxDocumentType, 0, SpringLayout.WEST, btnDocumentSaveAs);
		documentPanel.add(comboBoxDocumentType);

		JSeparator battomDocumentSeparator = new JSeparator();
		documentLayout.putConstraint(SpringLayout.WEST, battomDocumentSeparator, 0, SpringLayout.WEST, documentPanel);
		documentLayout.putConstraint(SpringLayout.SOUTH, battomDocumentSeparator, 0, SpringLayout.NORTH, comboBoxDocumentType);
		documentLayout.putConstraint(SpringLayout.EAST, battomDocumentSeparator, 0, SpringLayout.EAST, documentPanel);
		documentPanel.add(battomDocumentSeparator);

		JScrollPane scrollPaneDocument = new JScrollPane();
		documentLayout.putConstraint(SpringLayout.NORTH, scrollPaneDocument, 0, SpringLayout.SOUTH, topDocumentSeparator);
		documentLayout.putConstraint(SpringLayout.WEST, scrollPaneDocument, 5, SpringLayout.WEST, documentPanel);
		documentLayout.putConstraint(SpringLayout.SOUTH, scrollPaneDocument, 0, SpringLayout.NORTH, battomDocumentSeparator);
		documentLayout.putConstraint(SpringLayout.EAST, scrollPaneDocument, -5, SpringLayout.EAST, documentPanel);
		documentPanel.add(scrollPaneDocument);

		txtDocumentText = new JTextArea();
		txtDocumentText.setEnabled(false);
		txtDocumentText.setText("item01\nitem02\nitem03 \nitem01\nitem02\nitem03 \nitem01\nitem02\nitem03 \nitem01\nitem02\nitem03 \nitem01\nitem02\nitem03 \nitem01\nitem02\nitem03 item01\nitem02\nitem03 \nitem01\nitem02\nitem03 item01\nitem02\nitem03 \nitem01\nitem02\nitem03 \nitem01\nitem02\nitem03 \nitem01\nitem02\nitem03 \nitem01\nitem02\nitem03 item01\nitem02\nitem03 \n");
		scrollPaneDocument.setViewportView(txtDocumentText);

		btnDocumentEdit = new JButton("Edit");
		documentLayout.putConstraint(SpringLayout.NORTH, btnDocumentEdit, 0, SpringLayout.SOUTH, battomDocumentSeparator);
		documentLayout.putConstraint(SpringLayout.WEST, btnDocumentEdit, 0, SpringLayout.WEST, documentPanel);
		documentLayout.putConstraint(SpringLayout.SOUTH, btnDocumentEdit, 0, SpringLayout.SOUTH, documentPanel);
		documentLayout.putConstraint(SpringLayout.EAST, btnDocumentEdit, 0, SpringLayout.WEST, btnDocumentNew);
		documentPanel.add(btnDocumentEdit);
	}

	private void loadDataToViews() {
		// Folders combo box
		ArrayList<Folder> folders = getController().getFolders();
		ComboBoxModel<Folder> model = new DefaultComboBoxModel<Folder>(folders.toArray(new Folder[folders.size()]));
		getComboBoxFolders().setModel(model);

		// Documents list
		reloadDocumentList();
	}

	public void reloadDocumentList() {
		getDocumentsList().setModel(new AbstractListModelExtension());
	}

	public void registerControllerAsListener() {
		getBtnDocumentAdd().addActionListener(getController());
		getBtnDocumentClear().addActionListener(getController());
		getBtnDocumentDelete().addActionListener(getController());
		getBtnDocumentEdit().addActionListener(getController());
		getBtnDocumentNew().addActionListener(getController());
		getBtnDocumentSave().addActionListener(getController());
		getBtnDocumentSaveAs().addActionListener(getController());
		getBtnDocumentShow().addActionListener(getController());
		getBtnFolderRefresh().addActionListener(getController());
		
		getTxtFileNameValue().getDocument().addDocumentListener(getController());
		getTxtFileNameValue().addActionListener(controller);
	}

	// Access methods
	public ViewController getController() {
		return controller;
	}

	public void setController(ViewController controller) {
		this.controller = controller;
	}

	public Font getPlainFont() {
		return plainFont;
	}

	public Font getBoldFont() {
		return boldFont;
	}

	public JFrame getFrame() {
		return frame;
	}

	public JComboBox<Folder> getComboBoxFolders() {
		return comboBoxFolders;
	}

	public void setComboBoxFolders(JComboBox<Folder> comboBoxFolders) {
		this.comboBoxFolders = comboBoxFolders;
	}

	public ButtonGroup getSortSelection() {
		return sortSelection;
	}

	public void setSortSelection(ButtonGroup sortSelection) {
		this.sortSelection = sortSelection;
	}

	public JList<String> getDocumentsList() {
		return documentsList;
	}

	public void setDocumentsList(JList<String> documentsList) {
		this.documentsList = documentsList;
	}

	public JButton getBtnFolderRefresh() {
		return btnFolderRefresh;
	}

	public void setBtnFolderRefresh(JButton btnFolderRefresh) {
		this.btnFolderRefresh = btnFolderRefresh;
	}

	public JButton getBtnDocumentShow() {
		return btnDocumentShow;
	}

	public void setBtnDocumentShow(JButton btnDocumentShow) {
		this.btnDocumentShow = btnDocumentShow;
	}

	public JButton getBtnDocumentAdd() {
		return btnDocumentAdd;
	}

	public void setBtnDocumentAdd(JButton btnDocumentAdd) {
		this.btnDocumentAdd = btnDocumentAdd;
	}

	public JButton getBtnDocumentDelete() {
		return btnDocumentDelete;
	}

	public void setBtnDocumentDelete(JButton btnDocumentDelete) {
		this.btnDocumentDelete = btnDocumentDelete;
	}

	public JTextField getTxtFileNameValue() {
		return txtFileNameValue;
	}

	public void setTxtFileNameValue(JTextField txtFileNameValue) {
		this.txtFileNameValue = txtFileNameValue;
	}

	public JTextField getTxtFilePathValue() {
		return txtFilePathValue;
	}

	public void setTxtFilePathValue(JTextField txtFilePathValue) {
		this.txtFilePathValue = txtFilePathValue;
	}

	public JTextArea getTxtDocumentText() {
		return txtDocumentText;
	}

	public void setTxtDocumentText(JTextArea txtDocumentText) {
		this.txtDocumentText = txtDocumentText;
	}

	public JButton getBtnDocumentEdit() {
		return btnDocumentEdit;
	}

	public void setBtnDocumentEdit(JButton btnDocumentEdit) {
		this.btnDocumentEdit = btnDocumentEdit;
	}

	public JComboBox<Object> getComboBoxDocumentType() {
		return comboBoxDocumentType;
	}

	public void setComboBoxDocumentType(JComboBox<Object> comboBoxDocumentType) {
		this.comboBoxDocumentType = comboBoxDocumentType;
	}

	public JButton getBtnDocumentSaveAs() {
		return btnDocumentSaveAs;
	}

	public void setBtnDocumentSaveAs(JButton btnDocumentSaveAs) {
		this.btnDocumentSaveAs = btnDocumentSaveAs;
	}

	public JButton getBtnDocumentNew() {
		return btnDocumentNew;
	}

	public void setBtnDocumentNew(JButton btnDocumentNew) {
		this.btnDocumentNew = btnDocumentNew;
	}

	public JButton getBtnDocumentClear() {
		return btnDocumentClear;
	}

	public void setBtnDocumentClear(JButton btnDocumentClear) {
		this.btnDocumentClear = btnDocumentClear;
	}

	public JButton getBtnDocumentSave() {
		return btnDocumentSave;
	}

	public void setBtnDocumentSave(JButton btnDocumentSave) {
		this.btnDocumentSave = btnDocumentSave;
	}
	public JRadioButton getRdbtnName() {
		return rdbtnName;
	}

	public JRadioButton getRdbtnCreationDate() {
		return rdbtnCreationDate;
	}

	public JRadioButton getRdbtnSize() {
		return rdbtnSize;
	}

	public JRadioButton getRdbtnType() {
		return rdbtnType;
	}
	
	// Methods
	private String getSelectedButtonString()
	{  
		for (Enumeration<AbstractButton> buttons = getSortSelection().getElements(); buttons.hasMoreElements();) {
			AbstractButton button = buttons.nextElement();
			if (button.isSelected()) {
				return button.getText();
			}
		}
		return null;
	}
}

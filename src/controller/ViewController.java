package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import model.Document;
import model.DocumentType;
import model.Folder;
import model.PlainTextDocument;
import model.WordDocument;
import view.ApplicationWindow;

public class ViewController implements ActionListener, DocumentListener {
	// Attributes
	private ApplicationWindow view;
	private ArrayList<Folder> folders;
	private Document currentDocument;
	private PlainTextDocument defaultDocument;

	// Constructors
	public ViewController() {
		// Initialize documents
		setCurrentDocument(getDefaultDocument());

		// Initialize Data
		this.folders = new ArrayList<Folder>();
		this.folders.add(new Folder("My documents"));
		Folder f = new Folder("My Desktop");
		f.addDocument(new WordDocument("Some stuff"));
		this.folders.add(f);

		// Initialize View
		this.view = new ApplicationWindow(this);
	}

	// Access Methods
	public ApplicationWindow getView() {
		return view;
	}

	public void setView(ApplicationWindow view) {
		this.view = view;
	}

	public ArrayList<Folder> getFolders() {
		return folders;
	}

	public void setFolders(ArrayList<Folder> folders) {
		this.folders = folders;
	}

	public Document getCurrentDocument() {
		return currentDocument;
	}

	public void setCurrentDocument(Document currentDocument) {
		this.currentDocument = currentDocument;
	}

	public PlainTextDocument getDefaultDocument() {
		if (this.defaultDocument == null) {
			setDefaultDocument(new PlainTextDocument("Default Document","Please select a document to show"));
		}
		return defaultDocument;
	}

	public void setDefaultDocument(PlainTextDocument defaultDocument) {
		this.defaultDocument = defaultDocument;
	}

	// Listeners - Handle events
	@Override
	public void actionPerformed(ActionEvent e) {
		// 01. Folder Refresh ==========================================================================================================================
		if (e.getSource().equals(getView().getBtnFolderRefresh())) {

			// 1. Refresh the documents list
			getView().reloadDocumentList();

			// 2. Reset the current document shown to a default one
			setCurrentDocument(getDefaultDocument());

			// 2.1. Set new document name
			getView().getTxtFileNameValue().setText(getCurrentDocument().getDocumentName());
			getView().getTxtFileNameValue().setEditable(false);
			getView().getTxtFileNameValue().setEnabled(false);

			// 2.2. Set new document path
			getView().getTxtFilePathValue().setText(getFolderPath().toString() + "\\" + getView().getTxtFileNameValue().getText());
			getView().getTxtFilePathValue().setEditable(false);

			// 2.3. Set new document text
			getView().getTxtDocumentText().setText(getCurrentDocument().printFile());
			getView().getTxtDocumentText().setEditable(false);
			getView().getTxtDocumentText().setEnabled(false);

			// 4. Set the current document to be read only
			getView().getBtnDocumentNew().setEnabled(false);
			getView().getBtnDocumentClear().setEnabled(false);
			getView().getComboBoxDocumentType().setEnabled(false);
			getView().getBtnDocumentSave().setEnabled(false);
			getView().getBtnDocumentSaveAs().setEnabled(false);

			// 5. Set the edit button to e disabled
			getView().getBtnDocumentEdit().setEnabled(false);
			System.out.println(e.getActionCommand() + " was pressed");
		} 
		// 02. Document Show ==========================================================================================================================
		if (e.getSource().equals(getView().getBtnDocumentShow())) {
			// 1. Get the selected file
			Folder selectedFolder = getFolderPath();
			int selectedIndex = getView().getDocumentsList().getSelectedIndex();

			// 2. Set up the currently selected document
			if (selectedIndex >= 0) {
				Document doc = selectedFolder.folderContent().get(selectedIndex);
				setCurrentDocument(doc);
			}

			// 2.1. Set new document name
			getView().getTxtFileNameValue().setText(getCurrentDocument().getDocumentName());
			getView().getTxtFileNameValue().setEditable(false);
			getView().getTxtFileNameValue().setEnabled(false);

			// 2.2. Set new document path
			getView().getTxtFilePathValue().setText(getFolderPath().toString() + "\\" + getView().getTxtFileNameValue().getText());
			getView().getTxtFilePathValue().setEditable(false);
			getView().getTxtFilePathValue().setEnabled(false);

			// 2.3. Set new document text
			getView().getTxtDocumentText().setText(getCurrentDocument().printFile());
			getView().getTxtDocumentText().setEditable(false);
			getView().getTxtDocumentText().setEnabled(false);

			// 3. Disable document controls
			getView().getBtnDocumentNew().setEnabled(false);
			getView().getBtnDocumentClear().setEnabled(false);
			getView().getBtnDocumentSave().setEnabled(false);
			getView().getBtnDocumentSaveAs().setEnabled(false);

			// 4. Set the edit button to e disabled
			getView().getBtnDocumentEdit().setEnabled(true);

			// 5. Set the currant document type
			getView().getComboBoxDocumentType().setEnabled(false);
			getView().getComboBoxDocumentType().setSelectedItem(getCurrentDocument().getDocumentType());
			System.out.println(e.getActionCommand() + " was pressed");		
		} 
		// 03. Document Add ==========================================================================================================================
		if (e.getSource().equals(getView().getBtnDocumentAdd())) {

			// 1. Set the current document to a new document
			setCurrentDocument(new PlainTextDocument("New Document","Please press edit to enter editing mode"));

			// 2.1 Set the UI to the current document data
			getView().getTxtFileNameValue().setText(getCurrentDocument().getDocumentName());
			getView().getTxtFileNameValue().setEnabled(true);
			getView().getTxtFileNameValue().setEditable(true);

			// 2.2. Set new document path
			getView().getTxtFilePathValue().setText(getFolderPath().toString() + "\\" + getView().getTxtFileNameValue().getText());
			getView().getTxtFilePathValue().setEditable(false);

			// 2.3. Set new document text
			getView().getTxtDocumentText().setText(getCurrentDocument().printFile());
			getView().getTxtDocumentText().setEditable(false);

			// 3. Disable document controls
			getView().getBtnDocumentNew().setEnabled(false);
			getView().getBtnDocumentClear().setEnabled(false);
			getView().getBtnDocumentSave().setEnabled(false);
			getView().getBtnDocumentSaveAs().setEnabled(false);

			// 4. Set the currant document type
			getView().getComboBoxDocumentType().setEnabled(false);
			getView().getComboBoxDocumentType().setSelectedItem(getCurrentDocument().getDocumentType());

			// 5. Enter editing mode
			getView().getBtnDocumentEdit().setEnabled(true);

			System.out.println(e.getActionCommand() + " was pressed");
		}  
		// 04. Document Delete ==========================================================================================================================
		if (e.getSource().equals(getView().getBtnDocumentDelete())) {
			// 1. Get the selected file
			Folder selectedFolder = getFolderPath();
			int selectedIndex = getView().getDocumentsList().getSelectedIndex();

			// 2. Set up the currently selected document
			if (selectedIndex >= 0) {
				Document doc = selectedFolder.folderContent().get(selectedIndex);
				setCurrentDocument(doc);

				// if it exists delete it from the list
				selectedFolder.removeDocument(getCurrentDocument());

				// 1. Refresh the documents list
				getView().reloadDocumentList();

				// 2. Reset the current document shown to a default one
				setCurrentDocument(getDefaultDocument());

				// 2.1. Set new document name
				getView().getTxtFileNameValue().setText(getCurrentDocument().getDocumentName());
				getView().getTxtFileNameValue().setEditable(false);

				// 2.2. Set new document path
				getView().getTxtFilePathValue().setText(getFolderPath().toString() + "\\" + getView().getTxtFileNameValue().getText());
				getView().getTxtFilePathValue().setEditable(false);

				// 2.3. Set new document text
				getView().getTxtDocumentText().setText(getCurrentDocument().printFile());
				getView().getTxtDocumentText().setEditable(false);

				// 4. Set the current document to be read only
				getView().getBtnDocumentNew().setEnabled(false);
				getView().getBtnDocumentClear().setEnabled(false);
				getView().getComboBoxDocumentType().setEnabled(false);
				getView().getBtnDocumentSave().setEnabled(false);
				getView().getBtnDocumentSaveAs().setEnabled(false);
			}


			System.out.println(e.getActionCommand() + " was pressed");
		} 
		// 05. Document Edit ==========================================================================================================================
		if (e.getSource().equals(getView().getBtnDocumentEdit())) {
			if (!getCurrentDocument().equals(getDefaultDocument())) {
				// 1. Set new document name
				getView().getTxtFileNameValue().setText(getCurrentDocument().getDocumentName());
				getView().getTxtFileNameValue().setEditable(true);
				getView().getTxtFileNameValue().setEnabled(true);

				// 2. Set new document path
				getView().getTxtFilePathValue().setText(getFolderPath().toString() + "\\" + getView().getTxtFileNameValue().getText());
				getView().getTxtFilePathValue().setEditable(false);

				// 3. Set new document text
				getView().getTxtDocumentText().setText(getCurrentDocument().getDocumentText().toString());
				getView().getTxtDocumentText().setEditable(true);
				getView().getTxtDocumentText().setEnabled(true);

				// 4. Enable document controls
				getView().getBtnDocumentNew().setEnabled(true);
				getView().getBtnDocumentClear().setEnabled(true);
				getView().getBtnDocumentSave().setEnabled(true);
				getView().getBtnDocumentSaveAs().setEnabled(true);

				// 5. Set the currant document type
				getView().getComboBoxDocumentType().setEnabled(true);
				getView().getComboBoxDocumentType().setSelectedItem(getCurrentDocument().getDocumentType());

				getView().getBtnDocumentEdit().setEnabled(false);
			} else {
				// do nothing
			}
			System.out.println(e.getActionCommand() + " was pressed");
		} 
		// 06. Document New ==========================================================================================================================
		if (e.getSource().equals(getView().getBtnDocumentNew())) {
			// Get the current file name
			String fileName = getView().getTxtFileNameValue().getText();

			// Get file text
			String fileText = "";//getView().getTxtDocumentText().getText();

			// Get the file type from the Combo Box Document Type 
			DocumentType type = (DocumentType) getView().getComboBoxDocumentType().getSelectedItem();

			// Create a new document according to the current selection and set it as the currentDocument
			Document newDocument;
			if (type == DocumentType.PlainTextDocument) {
				newDocument = new PlainTextDocument(fileName, fileText);
			} else {
				newDocument = new WordDocument(fileName, fileText);
			}
			setCurrentDocument(newDocument);

			// Update the UI
			// 2.1. Set new document name
			getView().getTxtFileNameValue().setText(getCurrentDocument().getDocumentName());
			getView().getTxtFileNameValue().setEditable(true);
			getView().getTxtFileNameValue().setEnabled(true);

			// 2.2. Set new document path
			getView().getTxtFilePathValue().setText(getFolderPath().toString() + "\\" + getView().getTxtFileNameValue().getText());
			getView().getTxtFilePathValue().setEditable(false);
			getView().getTxtFilePathValue().setEnabled(false);

			// 2.3. Set new document text
			getView().getTxtDocumentText().setText(getCurrentDocument().getDocumentText().toString());
			getView().getTxtDocumentText().setEditable(true);
			getView().getTxtDocumentText().setEnabled(true);

			// 3. Disable document controls
			getView().getBtnDocumentNew().setEnabled(true);
			getView().getBtnDocumentClear().setEnabled(true);
			getView().getBtnDocumentSave().setEnabled(true);
			getView().getBtnDocumentSaveAs().setEnabled(true);

			// 4. Set the edit button to e disabled
			getView().getBtnDocumentEdit().setEnabled(true);

			// 5. Set the currant document type
			getView().getComboBoxDocumentType().setEnabled(true);
			getView().getComboBoxDocumentType().setSelectedItem(getCurrentDocument().getDocumentType());

			System.out.println(e.getActionCommand() + " was pressed");
		} 
		// 07. Document Clear ==========================================================================================================================
		if (e.getSource().equals(getView().getBtnDocumentClear())) {

			// 1. Clear the text of the current document 
			getCurrentDocument().deleteAllText();

			// 2.1. Set new document name
			getView().getTxtFileNameValue().setText(getCurrentDocument().getDocumentName());
			getView().getTxtFileNameValue().setEditable(true);
			getView().getTxtFileNameValue().setEnabled(true);

			// 2.2. Set new document path
			getView().getTxtFilePathValue().setText(getFolderPath().toString() + "\\" + getView().getTxtFileNameValue().getText());
			getView().getTxtFilePathValue().setEditable(false);
			getView().getTxtFilePathValue().setEnabled(false);

			// 2.3. Set new document text
			getView().getTxtDocumentText().setText(getCurrentDocument().getDocumentText().toString());
			getView().getTxtDocumentText().setEditable(true);
			getView().getTxtDocumentText().setEnabled(true);

			// 3. Disable document controls
			getView().getBtnDocumentNew().setEnabled(true);
			getView().getBtnDocumentClear().setEnabled(true);
			getView().getBtnDocumentSave().setEnabled(true);
			getView().getBtnDocumentSaveAs().setEnabled(true);

			// 4. Set the edit button to e disabled
			getView().getBtnDocumentEdit().setEnabled(true);

			// 5. Set the currant document type
			getView().getComboBoxDocumentType().setEnabled(true);
			getView().getComboBoxDocumentType().setSelectedItem(getCurrentDocument().getDocumentType());

			System.out.println(e.getActionCommand() + " was pressed");
		} 
		// 08. Document Save ==========================================================================================================================
		if (e.getSource().equals(getView().getBtnDocumentSave())) {
			// 1. Get the file name from the File Name Value
			String fileName = getView().getTxtFileNameValue().getText();

			// 2. Get file text
			String fileText = getView().getTxtDocumentText().getText();

			// 3. Check if the document exist in the DB
			Document documentToAdd = getCurrentDocument();

			// 4. if it exists then set the values
			if (documentToAdd != null) {

				documentToAdd.setDocumentName(fileName);
				documentToAdd.deleteAllText();
				documentToAdd.appendText(fileText);
			}
			// REFRESH LIST
			// 1. Refresh the documents list
			getView().reloadDocumentList();

			// 2. Reset the current document shown to a default one
			setCurrentDocument(getDefaultDocument());

			// 2.1. Set new document name
			getView().getTxtFileNameValue().setText(getCurrentDocument().getDocumentName());
			getView().getTxtFileNameValue().setEditable(false);

			// 2.2. Set new document path
			getView().getTxtFilePathValue().setText(getFolderPath().toString() + "\\" + getView().getTxtFileNameValue().getText());
			getView().getTxtFilePathValue().setEditable(false);

			// 2.3. Set new document text
			getView().getTxtDocumentText().setText(getCurrentDocument().printFile());
			getView().getTxtDocumentText().setEditable(false);

			// 4. Set the current document to be read only
			getView().getBtnDocumentNew().setEnabled(false);
			getView().getBtnDocumentClear().setEnabled(false);
			getView().getComboBoxDocumentType().setEnabled(false);
			getView().getBtnDocumentSave().setEnabled(false);
			getView().getBtnDocumentSaveAs().setEnabled(false);

			System.out.println(e.getActionCommand() + " was pressed");
		} 
		// 09. Document Save As ==========================================================================================================================
		if (e.getSource().equals(getView().getBtnDocumentSaveAs())) {
			// 1. Get the current selected folder
			Folder selectedFolder = getFolderPath();

			// 2. Get the file name from the File Name Value
			String fileName = getView().getTxtFileNameValue().getText();

			// 3. Get file text
			String fileText = getView().getTxtDocumentText().getText();

			// 4. Get the file type from the Combo Box Document Type 
			DocumentType type = (DocumentType) getView().getComboBoxDocumentType().getSelectedItem();

			// 5. Check if the document exist in the DB
			Document documentToAdd = selectedFolder.fileForName(fileName, type, fileText);

			// 6. if it exists then set the values
			if (documentToAdd != null) {

				documentToAdd.setDocumentName(fileName);
				documentToAdd.deleteAllText();
				documentToAdd.appendText(fileText);

			} else {
				// 7. if it doesn't exists then create a document according to the needed type
				documentToAdd = getCurrentDocument();

				if (type == DocumentType.PlainTextDocument) {
					documentToAdd = new PlainTextDocument(fileName, fileText);
				} else if (type == DocumentType.WordDocument) {
					documentToAdd = new WordDocument(fileName, fileText);
				}

				documentToAdd.setDocumentName(fileName);
				documentToAdd.deleteAllText();
				documentToAdd.appendText(fileText);
				selectedFolder.addDocument(documentToAdd);
			}

			// REFRESH LIST
			// 1. Refresh the documents list
			getView().reloadDocumentList();

			// 2. Reset the current document shown to a default one
			setCurrentDocument(getDefaultDocument());

			// 2.1. Set new document name
			getView().getTxtFileNameValue().setText(getCurrentDocument().getDocumentName());
			getView().getTxtFileNameValue().setEditable(false);

			// 2.2. Set new document path
			getView().getTxtFilePathValue().setText(getFolderPath().toString() + "\\" + getView().getTxtFileNameValue().getText());
			getView().getTxtFilePathValue().setEditable(false);

			// 2.3. Set new document text
			getView().getTxtDocumentText().setText(getCurrentDocument().printFile());
			getView().getTxtDocumentText().setEditable(false);

			// 4. Set the current document to be read only
			getView().getBtnDocumentNew().setEnabled(false);
			getView().getBtnDocumentClear().setEnabled(false);
			getView().getComboBoxDocumentType().setEnabled(false);
			getView().getBtnDocumentSave().setEnabled(false);
			getView().getBtnDocumentSaveAs().setEnabled(false);

			System.out.println(e.getActionCommand() + " was pressed");
		}
		// 10. FileNameValue changed ==========================================================================================================================
		if (e.getSource().equals(getView().getTxtFileNameValue())) { 
			// 1. Get the current selected folder
			Folder selectedFolder = getFolderPath();

			// 2. Get the file name from the File Name Value
			String fileName = getView().getTxtFileNameValue().getText();

			// 3. Get file text
			String fileText = getView().getTxtDocumentText().getText();

			// 4. Get the file type from the Combo Box Document Type 
			DocumentType type = (DocumentType) getView().getComboBoxDocumentType().getSelectedItem();

			// 5. Check if the document exist in the DB
			Document documentToAdd = selectedFolder.fileForName(fileName, type, fileText);

			// 6. if it exists then set the values
			if (documentToAdd != null) {

				documentToAdd.setDocumentName(fileName);
				documentToAdd.deleteAllText();
				documentToAdd.appendText(fileText);

			} else {
				// 7. if it doesn't exists then create a document according to the needed type
				documentToAdd = getCurrentDocument();

				if (type == DocumentType.PlainTextDocument) {
					documentToAdd = new PlainTextDocument(fileName, fileText);
				} else if (type == DocumentType.WordDocument) {
					documentToAdd = new WordDocument(fileName, fileText);
				}

				documentToAdd.setDocumentName(fileName);
				documentToAdd.deleteAllText();
				documentToAdd.appendText(fileText);
				selectedFolder.addDocument(documentToAdd);
			}

			// REFRESH LIST
			// 1. Refresh the documents list
			getView().reloadDocumentList();

			// 2. Reset the current document shown to a default one
			setCurrentDocument(getDefaultDocument());

			// 2.1. Set new document name
			getView().getTxtFileNameValue().setText(getCurrentDocument().getDocumentName());
			getView().getTxtFileNameValue().setEditable(false);

			// 2.2. Set new document path
			getView().getTxtFilePathValue().setText(getFolderPath().toString() + "\\" + getView().getTxtFileNameValue().getText());
			getView().getTxtFilePathValue().setEditable(false);

			// 2.3. Set new document text
			getView().getTxtDocumentText().setText(getCurrentDocument().printFile());
			getView().getTxtDocumentText().setEditable(false);

			// 4. Set the current document to be read only
			getView().getBtnDocumentNew().setEnabled(false);
			getView().getBtnDocumentClear().setEnabled(false);
			getView().getComboBoxDocumentType().setEnabled(false);
			getView().getBtnDocumentSave().setEnabled(false);
			getView().getBtnDocumentSaveAs().setEnabled(false);
			System.out.println(e.getActionCommand() + " was pressed");
		}
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		changedUpdate(e);
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		changedUpdate(e);
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		Folder selectedFolder = getFolderPath();
		getView().getTxtFilePathValue().setText(selectedFolder.toString() + "\\" + getView().getTxtFileNameValue().getText());
	}
	// ===========================================================================================================================================================
	// IN USE METHODS
	// ===========================================================================================================================================================	
	private Folder getFolderPath() {
		int selectedIndex = getView().getComboBoxFolders().getSelectedIndex();
		Folder selectedFolder = getFolders().get(selectedIndex);
		return selectedFolder;
	}
}

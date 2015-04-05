package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import model.Document;
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
		// 1. Folder Refresh ==========================================================================================================================
		if (e.getSource().equals(getView().getBtnFolderRefresh())) {

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
		// 2. Document Show ==========================================================================================================================
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

			// 2.2. Set new document path
			getView().getTxtFilePathValue().setText(getFolderPath().toString() + "\\" + getView().getTxtFileNameValue().getText());
			getView().getTxtFilePathValue().setEditable(false);

			// 2.3. Set new document text
			getView().getTxtDocumentText().setText(getCurrentDocument().printFile());
			getView().getTxtDocumentText().setEditable(false);

			// Disable document controls
			getView().getBtnDocumentNew().setEnabled(true);
			getView().getBtnDocumentClear().setEnabled(true);
			getView().getComboBoxDocumentType().setEnabled(true);
			getView().getBtnDocumentSave().setEnabled(true);
			getView().getBtnDocumentSaveAs().setEnabled(true);

			System.out.println(e.getActionCommand() + " was pressed");		
		} 
		// 3. ==========================================================================================================================
		// TODO DocumentAdd
		//		if (e.getSource().equals(getView().getBtnDocumentAdd())) {
		//			addNewDocument();
		//			System.out.println(e.getActionCommand() + " was pressed");
		//		}  
		// 4. Document Delete ==========================================================================================================================
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
		// 5. ==========================================================================================================================
		// TODO DocumentEdit
		//		if (e.getSource().equals(getView().getBtnDocumentEdit())) {
		//			showSelectedDocument();
		//			updateDocumentUIModeEdit(getCurrentDocument());
		//			System.out.println(e.getActionCommand() + " was pressed");
		//		} 
		//		if (e.getSource().equals(getView().getBtnDocumentNew())) {
		//			System.out.println(e.getActionCommand() + " was pressed");
		//		} 
		//
		//		if (e.getSource().equals(getView().getBtnDocumentClear())) {
		//			// Get the selected file
		//			Folder selectedFolder = getFolderPath();
		//			int selectedIndex = getView().getDocumentsList().getSelectedIndex();
		//
		//			if (selectedIndex >= 0) {
		//				Document doc = selectedFolder.folderContent().get(selectedIndex);
		//				setCurrentDocument(doc);
		//				getCurrentDocument().deleteAllText();
		//				updateDocumentUIModeShow(getCurrentDocument());
		//				updateDocumentUIModeEdit(getCurrentDocument());
		//			}
		//			getView().getTxtDocumentText().setText(null);
		//			System.out.println(e.getActionCommand() + " was pressed");
		//		} 

		// ==========================================================================================================================
		// TODO DocumentSave
		//		if (e.getSource().equals(getView().getBtnDocumentSave())) {
		//			saveFile();
		//			System.out.println(e.getActionCommand() + " was pressed");
		//		} 
		// ==========================================================================================================================
		// TODO DocumentSaveAs
		//		if (e.getSource().equals(getView().getBtnDocumentSaveAs())) {
		//			saveFile();
		//			System.out.println(e.getActionCommand() + " was pressed");
		//		}
		// ==========================================================================================================================
		// TODO FileNameValue changed
		//		if (e.getSource().equals(getView().getTxtFileNameValue())) { 
		//			saveFile();
		//			System.out.println(e.getActionCommand() + " was pressed");
		//		}
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


	// ===========================================================================================================================================================
	// NOT IN USE
	// ===========================================================================================================================================================
	// Methods
	//	private void setupNewDocumentOnView() {
	//		// 1. Set new document name
	//		getView().getTxtFileNameValue().setText("New file");
	//		getView().getTxtFileNameValue().setEditable(true);
	//
	//		// 2. Set new document path
	//		Folder selectedFolder = getFolderPath();
	//		getView().getTxtFilePathValue().setText(selectedFolder.toString() + "\\" + getView().getTxtFileNameValue().getText());
	//		getView().getTxtFilePathValue().setEditable(false);
	//
	//		// 3. Set new document text
	//		getView().getTxtDocumentText().setText(null);
	//		getView().getTxtDocumentText().setEditable(true);
	//	}
	//
	//
	//	private void updateDocumentUIModeEdit(Document doc) {
	//		getView().getTxtFileNameValue().setEnabled(true);
	//		getView().getTxtDocumentText().setEnabled(true);
	//		getView().getTxtDocumentText().setEditable(true);
	//		getView().getBtnDocumentNew().setEnabled(true);
	//		getView().getBtnDocumentClear().setEnabled(true);
	//		getView().getComboBoxDocumentType().setEnabled(true);
	//		getView().getBtnDocumentSave().setEnabled(true);
	//		getView().getBtnDocumentSaveAs().setEnabled(true);
	//
	//		if (doc != null) {
	//			if (doc.getDocumentName() != null) {
	//				if (doc.getDocumentText().length() > 0 ) {
	//					getView().getTxtFileNameValue().setText(doc.getDocumentName());
	//					getView().getTxtDocumentText().setText(doc.printFile());	
	//				}
	//			}	
	//		}
	//	}
	//
	//	private void saveFile() {
	//		// Get the full current selected folder
	//		Folder selectedFolder = getFolderPath();
	//
	//		// Get the file name from the File Name Value
	//		String fileName = getView().getTxtFileNameValue().getText();
	//
	//		// Get file text
	//		String fileText = getView().getTxtDocumentText().getText();
	//
	//		// Get the file type from the Combo Box Document Type 
	//		DocumentType type = (DocumentType) getView().getComboBoxDocumentType().getSelectedItem();
	//
	//		// Search for the file at the full file path location (from the components not the string)
	//		Document doc;
	//
	//		if (getCurrentDocument() != null) {
	//			doc = selectedFolder.fileForName(fileName, type, fileText);
	//		} else {
	//			doc = getCurrentDocument();	
	//		}
	//
	//		// If the file exists update the text content according to the currently displayed text
	//		if (doc != null) {
	//			doc.setText(fileText);
	//		} else {
	//			// Else create the file according to the current file path (folder + file name) and the currently selected document type
	//			if (type == DocumentType.PlainTextDocument) {
	//				selectedFolder.addDocument(new PlainTextDocument(fileName, fileText));
	//			} else if (type == DocumentType.WordDocument) {
	//				selectedFolder.addDocument(new WordDocument(fileName, fileText));
	//			}
	//		}
	//		//		refreshDocumentsList();
	//	}
}

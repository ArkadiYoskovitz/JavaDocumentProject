package model;

import java.util.ArrayList;
import java.util.Comparator;

public class Folder {

	// Attributes
	private String folderName;
	private ArrayList<Document> files;
	
	// Constructors
	public Folder(String name) 
	{
		this.folderName = name;
		this.files = new ArrayList<Document>();
		this.files.add(new WordDocument());
		this.files.add(new PlainTextDocument());
		this.files.add(new PlainTextDocument());
		this.files.add(new WordDocument());
		this.files.add(new PlainTextDocument());
		this.files.add(new PlainTextDocument());
		this.files.add(new WordDocument());
	}
	
	// Access methods
	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	private ArrayList<Document> getFiles() {
		return files;
	}

	// Override Methods
	@Override
	public String toString() {
		return getFolderName();
	}
	
	// Methods
	public void deleteFolderContent()
	{
		getFiles().clear();
	}
	
	public void    addDocument(Document doc) {
		getFiles().add(doc);
	}
	public void removeDocument(Document doc) {
		getFiles().remove(doc);
	}
	
	public String ShowDocumentContent(Document doc) { 
		return getFiles().get(getFiles().indexOf(doc)).printFile();
	}
	
	public ArrayList<Document> folderContent() {
		return getFiles();
	}
	
	public String printFolderContent() { 
		
		String separator = System.getProperty( "line.separator" );
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("Folder name: ").append(getFolderName()).append(separator);
		
		for (Document document : getFiles()) {
			sb.append(document.getDocumentName()).append(separator);
		}
		return sb.toString();
	}
	
	public String printFolderContentSortedBy(Comparator<Document> comp) { 
		
		@SuppressWarnings("unchecked")
		ArrayList<Document> sorted = (ArrayList<Document>) getFiles().clone();
		sorted.sort(comp);
		
		StringBuilder sb = new StringBuilder();
		String separator = System.getProperty( "line.separator" );
		sb.append("Folder name: ").append(getFolderName()).append(separator);
		
		for (Document document : sorted) {
			sb.append(document.getDocumentName()).append(separator);
		}
		return sb.toString();
	}	
	
	public Document fileForName(String Name, DocumentType type, String documentText) {
		for (Document document : files) {
			if (document.getDocumentName().equals(Name) && 
					document.getDocumentType().equals(type) && 
					document.getDocumentText().toString().equals(documentText)) {
				return document;
			}
		}
		return null;
	}
}

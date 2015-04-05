package model;

import java.util.Date;

public class PlainTextDocument extends Document {

	// Attributes
	private final static int MAX_WORDS_FOR_LINE = 10;
	private String documentName;
	private StringBuffer documentText;
	private Date documentCreationDate;

	// Constructors
	public PlainTextDocument()
	{
		this(new Date().getTime() + "-New plain text document");
	}
	public PlainTextDocument(String name)
	{
		this.documentName = name;
		this.documentCreationDate = new Date();
		this.documentText = new StringBuffer();
	}
	public PlainTextDocument(String name, String text) {
		this(name);
		this.documentText.append(text);
	} 

	// Access methods 
	@Override
	public String getDocumentName() {
		return this.documentName;
	}

	@Override
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	@Override
	public StringBuffer getDocumentText() {
		return this.documentText;
	}

	@Override
	public void setDocumentText(StringBuffer documentText) {
		this.documentText = documentText;
	}

	@Override
	public Date getDocumentCreationDate() {
		return this.documentCreationDate;
	}

	@Override
	public int getWordsForLine() {
		return MAX_WORDS_FOR_LINE;
	}

	// Methods
	@Override
	public String printFile() {
		String separator = System.getProperty( "line.separator" );
		String[] lines = documentTextInLines();
		StringBuffer documentOutprint = new StringBuffer();
		for (String string : lines) {
			documentOutprint.append(string);
			documentOutprint.append(separator);
		}
		return documentOutprint.toString();
		
	}
	@Override
	public DocumentType getDocumentType() {
		return DocumentType.PlainTextDocument;
	}

}

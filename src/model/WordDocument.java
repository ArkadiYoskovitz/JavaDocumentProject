package model;

import java.util.Date;

public class WordDocument extends Document {

	// Attributes
	private final static int MAX_WORDS_FOR_LINE = 8;
	private String documentName;
	private StringBuffer documentText;
	private Date documentCreationDate;

	// Constructors
	public WordDocument()
	{
		this(new Date() + "-New word document");
	}
	public WordDocument(String name)
	{
		this.documentName = name;
		this.documentCreationDate = new Date();
		this.documentText = new StringBuffer();
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
	@Override
	public String printFile() {
		String separator = System.getProperty( "line.separator" );
		String frameSeparator = " * ";
		String[] lines = documentTextInLines();
		StringBuffer documentOutprint = new StringBuffer();
		for (String string : lines) {
			documentOutprint.append(frameSeparator);
			documentOutprint.append(string);
			documentOutprint.append(frameSeparator);
			documentOutprint.append(separator);
		}
		return documentOutprint.toString();
	}
}

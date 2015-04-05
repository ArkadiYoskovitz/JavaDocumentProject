package model;
import java.util.Comparator;
import java.util.Date;

public abstract class Document {
	// Attributes

	// Constructors

	// Access methods
	public abstract String 		 getDocumentName();
	public abstract void		 setDocumentName(String documentName);
	public abstract StringBuffer getDocumentText();
	public abstract void 		 setDocumentText(StringBuffer documentText);
	public abstract Date		 getDocumentCreationDate();
	public abstract int			 getWordsForLine();
	public abstract DocumentType getDocumentType();
	
	public int getDocumentSize() {
		return this.getDocumentText().length();
	}

	// Comparators
	public static Comparator<Document> DocumentComparatorSize = new Comparator<Document>() {

		public int compare(Document docOne, Document docTwo) {
			return docOne.getDocumentSize() - docTwo.getDocumentSize();
		}
	};
	public static Comparator<Document> DocumentComparatorCreationDate = new Comparator<Document>() {

		public int compare(Document docOne, Document docTwo) {
			Date docOneCreationDate = docOne.getDocumentCreationDate();
			Date docTwoCreationDate = docTwo.getDocumentCreationDate();
			//ascending order
			return docOneCreationDate.compareTo(docTwoCreationDate);
		}
	};
	public static Comparator<Document> DocumentComparatorName = new Comparator<Document>() {

		public int compare(Document docOne, Document docTwo) {
			String docOneName = docOne.getDocumentName().toUpperCase();
			String docTwoName = docTwo.getDocumentName().toUpperCase();

			//ascending order
			return docOneName.compareTo(docTwoName);
		}
	};
	public static Comparator<Document> DocumentComparatorType = new Comparator<Document>() {

		public int compare(Document docOne, Document docTwo) {
			String docOneType = docOne.getClass().toString().toUpperCase();
			String docTwoType = docTwo.getClass().toString().toUpperCase();

			//ascending order
			return docOneType.compareTo(docTwoType);
		}
	};
	
	// Action methods
	public void appendText(String text) {
		getDocumentText().append(text);
	}

	public void deleteAllText() {
		getDocumentText().delete(0, getDocumentText().length());
	}
	public void setText(String text) {
		this.deleteAllText();
		getDocumentText().append(text);
	}
	
	public abstract String printFile();

	protected String[] documentTextInLines()
	{
		StringBuffer line = new StringBuffer();
		String[] words = getDocumentText().toString().split("\\s+");

		int numberOfLines = 0;
		numberOfLines = words.length / getWordsForLine();
		if (words.length % getWordsForLine() != 0) {
			numberOfLines++;
		}


		String[] lines = new String[numberOfLines];

		for (int i = 0, j = 0; i < words.length; i++) {
			if (i % getWordsForLine() == 0 && i != 0) {
				lines[j] = line.toString();
				line.delete(0, line.length());
				j++;
			} 
			line.append(words[i] + " ");	
		}
		lines[numberOfLines - 1] = line.toString();
		return lines;
	}

	// Method overrides
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		Document other = (Document) obj;
		if (getDocumentSize() != other.getDocumentSize())
			return false;
		if (!getDocumentName().equals(other.getDocumentName())) {
			return false;
		}
		return true;
	}

}

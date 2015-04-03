package app;

import model.Document;
import model.Folder;
import model.PlainTextDocument;
import model.WordDocument;

public class TestAppInConsole {

	public static void main(String[] args) {
		
		PlainTextDocument doc1 = new PlainTextDocument();
		doc1.appendText("01 02 03 04 05 06 07 08 09 10 ");
		doc1.appendText("11 12 13 14 15 16 17 18 19 20 ");
		doc1.appendText("21 22 23 24 25 26 27 28 29 30 ");
		doc1.appendText("31 32 33 34 35 36 37 38 39 40 ");
		doc1.appendText("41 42 43 44 45 46 47 48 49 50 ");
		System.out.println(doc1.printFile());

		WordDocument doc2 = new WordDocument();
		doc2.appendText("01 02 03 04 05 06 07 08 09 10 ");
		doc2.appendText("11 12 13 14 15 16 17 18 19 20 ");
		doc2.appendText("21 22 23 24 25 26 27 28 29 30 ");
		doc2.appendText("31 32 33 34 35 36 37 38 39 40 ");
		doc2.appendText("41 42 43 44 45 46 47 48 49 50 ");
		System.out.println(doc2.printFile());
		
		Folder f = new Folder("My documents");
		f.addDocument(doc1);
		f.addDocument(doc2);
		System.out.println(f.printFolderContent());
		f.removeDocument(doc1);
		System.out.println(f.printFolderContent());
		
		Folder g = new Folder("My documents");
		g.addDocument(doc1);
		g.addDocument(doc2);
		g.addDocument(doc1);
		g.addDocument(doc2);
		g.addDocument(new WordDocument());
		g.addDocument(new PlainTextDocument());
		g.addDocument(new WordDocument());
		g.addDocument(new PlainTextDocument());
		g.addDocument(new WordDocument());
		g.addDocument(new PlainTextDocument());
		g.addDocument(new WordDocument());
		g.addDocument(new PlainTextDocument());
		g.addDocument(new WordDocument());
		g.addDocument(new PlainTextDocument());
		g.addDocument(new WordDocument());
		g.addDocument(new PlainTextDocument());
		
		g.addDocument(new PlainTextDocument());
		System.out.println(g.printFolderContent());
		System.out.println("-----Size");
		System.out.println(g.printFolderContentSortedBy(Document.DocumentComparatorSize));
		System.out.println("-----Date");
		System.out.println(g.printFolderContentSortedBy(Document.DocumentComparatorCreationDate));
		System.out.println("-----Name");
		System.out.println(g.printFolderContentSortedBy(Document.DocumentComparatorName));
		System.out.println("-----Type");
		System.out.println(g.printFolderContentSortedBy(Document.DocumentComparatorType));
	}

}

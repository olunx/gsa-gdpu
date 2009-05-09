/*
 *@author olunx , Time:2009-4-29
 *
 *Website : http://www.olunx.com
 *
 *This : 
 *
 */

package cn.imgdpu.test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.store.FSDirectory;
import org.apache.poi.hwpf.extractor.WordExtractor;

//public class LuneceCreateIndex {
//
//	public static void main(String[] args) throws CorruptIndexException, LockObtainFailedException, IOException {
//		
//		//读取文档
//		BufferedInputStream in = new BufferedInputStream(new FileInputStream( new File("E:\\收集资料要点.doc")));
//		WordExtractor we = new WordExtractor(in);
//		
//		//开始建立索引
//		StandardAnalyzer analyzer = new StandardAnalyzer();
//
//		String filePath = "data\\index.txt"; 
//		
//		IndexWriter iw = new IndexWriter(filePath, analyzer, true, IndexWriter.MaxFieldLength.LIMITED);
//		
//		Field field = new Field("contents", we.getText().getBytes(), Field.Store.NO);
//		Document doc = new Document();
//		doc.add(field);
//		
//		iw.addDocument(doc);
//		iw.optimize();
//		iw.close();
//	}
//}

public class LuneceProcess {

	public static void main(String[] args) throws Exception {
		// indexFiles();
		searchData();
	}

	static void indexFiles() throws Exception {

		// 读取文档
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(new File("E:\\作品介绍.doc")));
		WordExtractor we = new WordExtractor(in);
		System.out.println(we.getText());

		// 保存索引的文件夹
		File indexDir = new File("D:\\index");
		// 需建立索引的文件所在文件夹
		// File dataDir = new File("D:\\data");

		Analyzer luceneAnalyzer = new StandardAnalyzer();
		// File[] dataFiles = dataDir.listFiles();
		IndexWriter indexWriter = new IndexWriter(indexDir, luceneAnalyzer, true, IndexWriter.MaxFieldLength.LIMITED);

		// 开始时间
		long startTime = new Date().getTime();

		// for (int i = 0; i < dataFiles.length; i++) {
		// if (dataFiles[i].isFile() && dataFiles[i].getName().endsWith(".txt"))
		// {
		// System.out.println("Indexing file " +
		// dataFiles[i].getCanonicalPath());
		// Document doc = new Document();
		// Reader txtReader = new FileReader(dataFiles[i]);
		//
		// Field field = new Field("contents", txtReader);
		//
		// doc.add(field);
		// doc.add(new Field("path", dataFiles[i].getCanonicalPath(),
		// Field.Store.YES, Field.Index.NOT_ANALYZED));
		//
		// indexWriter.addDocument(doc);
		// }
		// }

		Document doc = new Document();
		doc.add(new Field("contents", we.getText(), Field.Store.YES, Field.Index.ANALYZED));
		doc.add(new Field("path", "不知道在哪里", Field.Store.YES, Field.Index.ANALYZED));
		indexWriter.addDocument(doc);

		indexWriter.optimize();
		indexWriter.close();

		// 结束时间
		long endTime = new Date().getTime();

		System.out.println("It takes " + (endTime - startTime) + " milliseconds to create index for the files in directory ");
	}

	static void searchData() throws IOException {

		// String dir = "D:\\index";
		Analyzer analyzer = new StandardAnalyzer();
		FSDirectory dir = FSDirectory.getDirectory(new File("D:\\index"));

		try {

			Searcher searcher = new IndexSearcher(dir);
			Query query = new QueryParser("contents", analyzer).parse("英语");
			ScoreDoc[] docs = searcher.search(query, searcher.maxDoc()).scoreDocs;
			System.out.println(docs.length);

			Document doc;
			for (int i = 0; i < docs.length; i++) {
				doc = searcher.doc(docs[i].doc);
				System.out.println(doc.get("path"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

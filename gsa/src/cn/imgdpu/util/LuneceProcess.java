/*
 *@author olunx , Time:2009-4-29
 *
 *Website : http://www.olunx.com
 *
 *This : 
 *
 */

package cn.imgdpu.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;

public class LuneceProcess {

	// 参数：要建立索引的文件路径集合、索引保存位置
	public void indexFiles(ArrayList<String> filesPath, String indexDir) {

		// 建立分析器
		Analyzer luceneAnalyzer = new StandardAnalyzer();

		try {
			// 索引保存的目录
			FSDirectory indexPath = FSDirectory.getDirectory(new File(indexDir));

			// 索引处理
			IndexWriter indexWriter = new IndexWriter(indexPath, luceneAnalyzer, true, IndexWriter.MaxFieldLength.LIMITED);

			// 开始时间
			long startTime = new Date().getTime();

			// 为每一个文件建立索引
			for (int i = 0; i < filesPath.size(); i++) {

				// 读取文件内容
				StringBuilder result = new ReadOfficeFiles().readfiles(filesPath.get(i));

				// 开始建立索引
				Document doc = new Document();

				// 添加内容
				doc.add(new Field("contents", result.toString(), Field.Store.YES, Field.Index.ANALYZED));
				// 添加文件路径
				doc.add(new Field("path", filesPath.get(i), Field.Store.YES, Field.Index.ANALYZED));

				indexWriter.addDocument(doc);
			}

			// 优化索引
			indexWriter.optimize();
			indexWriter.close();

			// 结束时间
			long endTime = new Date().getTime();

			System.out.println("共用了 " + (endTime - startTime) + " 毫秒建立索引");
		} catch (CorruptIndexException e) {
			cn.imgdpu.util.CatException.getMethod().catException(e, "CorruptIndex异常");
		} catch (LockObtainFailedException e) {
			cn.imgdpu.util.CatException.getMethod().catException(e, "LockObtainFailed异常");
		} catch (IOException e) {
			cn.imgdpu.util.CatException.getMethod().catException(e, "IO异常");
		}

	}

	// 参数：搜索索引目录、搜索内容
	public ArrayList<String> searchData(String indexDir, String queryStr) {

		// 保存返回的结果
		ArrayList<String> result = new ArrayList<String>();

		// 建立分析器
		Analyzer analyzer = new StandardAnalyzer();

		try {
			// 索引所在的目录
			FSDirectory indexPath = FSDirectory.getDirectory(new File(indexDir));

			// 搜索索引
			Searcher searcher = new IndexSearcher(indexPath);

			// 要搜索的内容
			Query query = new QueryParser("contents", analyzer).parse(queryStr);

			// 搜索结果
			ScoreDoc[] docs = searcher.search(query, searcher.maxDoc()).scoreDocs;

			// 搜索结果条数
			System.out.println(docs.length);

			Document doc;

			// 返回搜索的内容
			for (int i = 0; i < docs.length; i++) {
				doc = searcher.doc(docs[i].doc);
				System.out.println(doc.get("path"));
				result.add(doc.get("path"));
			}

		} catch (ParseException e) {
			cn.imgdpu.util.CatException.getMethod().catException(e, "Parse异常");
		} catch (IOException e) {
			cn.imgdpu.util.CatException.getMethod().catException(e, "IO异常");
		}

		return result;

	}

}

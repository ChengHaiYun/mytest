package com.itheima.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.jupiter.api.Test;

import com.itheima.dao.BookDao;
import com.itheima.dao.impl.BookDaoImpl;
import com.itheima.domain.Book;

class TestBook {

	@SuppressWarnings("resource")
	@Test
	public void test() throws Exception {
		//1.采集数据
		BookDao daoImpl = new BookDaoImpl();
		List<Book> bookList = daoImpl.findAll();
		//2。创建文档集合
		List<Document> documentList = new ArrayList<>();
		//3.创建一个文档对象
		//便利集合 
		for (Book book : bookList) {
			//创建一个文档对象
			Document document = new Document();
			//4.创建field对象存储
			TextField idField = new TextField("id", book.getId()+"",Field.Store.YES);
			TextField nameField = new TextField("name", book.getName(),Field.Store.YES);
			TextField priceField = new TextField("price", book.getPrice()+"",Field.Store.YES);
			TextField picField = new TextField("pic", book.getPic(),Field.Store.YES);
			TextField descriptionField = new TextField("description", book.getDescription()+"",Field.Store.YES);
			//5.把field添加到文档中
			document.add(idField);
			document.add(nameField);
			document.add(priceField);
			document.add(picField);
			document.add(descriptionField);
			
			//6.把文档添加到文档集合中
			documentList.add(document);
			
		}
		//7.创建分词对象
		Analyzer analyzer = new StandardAnalyzer();
		//8.创建制定索引库地址流对象
		FSDirectory fsDirectory = FSDirectory.open(new File("C:\\java_46\\lucene\\"));
		//9.创建写入索引库IndexWriteConfig
		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_4_10_3, analyzer);
		//10.创建写入索引库的对象IndexWriter需要两个参数1制定索引库的流2写入索引库的配置文件
		IndexWriter indexWriter = new IndexWriter(fsDirectory, indexWriterConfig);
		//11。把文档集合便利一个一个写入索引库
		//遍历文档集合，一个一个写入索引库
		for (Document document : documentList) {
			
			indexWriter.addDocument(document);
			
		}
		//12.关流
		indexWriter.close();
		
		
		
		
		
		
		
		
		
	}

}

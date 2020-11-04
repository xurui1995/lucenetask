package com.task.lucene.index;

import com.task.lucene.model.CranDoc;
import com.task.lucene.parser.CranDocParser;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * class for building com.task.lucene.index
 */
public class BuildIndex {
    public static final String INDEX_PATH = "index";
    public static final String DOC_PATH = "corpus/cran.all.1400";


    public static void startBuildIndex(Analyzer analyzer) {
        if (analyzer == null) {
            return;
        }
        System.out.println("------StartBuildIndex------");
        try {
            // init Directory
            String indexPath = analyzer.getClass().getSimpleName() + "_" + INDEX_PATH;

            if (new File(indexPath).exists()) {
                System.out.println("------You already built the index------");
                return;
            }

            Directory directory = FSDirectory.open(Paths.get(indexPath));
            // init IndexWriterConfig
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);

            // init IndexWriter
            IndexWriter indexWriter = new IndexWriter(directory, config);

            // get documents
            List<Document> documentList = getDocuments();

            if (documentList == null || documentList.size() == 0) {
                System.out.println("Fail to get documents");
                return;
            }
            // write documents
            System.out.println("adding documents");
            for (Document document : documentList) {
                indexWriter.addDocument(document);
            }
            // close closeable objects
            indexWriter.close();
            directory.close();
        } catch (Exception e) {
            System.out.println("Fail to build com.task.lucene.index");
            e.printStackTrace();
        }

        System.out.println("------EndBuildIndex------");
    }

    // get Documents from file
    private static List<Document> getDocuments() {

        CranDocParser documentsParser = new CranDocParser(DOC_PATH);
        List<CranDoc> cranDocs = documentsParser.getParseResult();
        if (cranDocs == null || cranDocs.size() == 0) {
            System.out.println("fail to get cran docs");
            return null;
        }
        ArrayList<Document> documentList = new ArrayList<Document>();
        for (CranDoc doc : cranDocs) {
            Document document = new Document();
            document.add(new StringField("id", String.valueOf(doc.getId()), Field.Store.YES));
            document.add(new TextField("title", doc.getTitle(), Field.Store.YES));
            document.add(new TextField("author", doc.getAuthor(), Field.Store.YES));
            document.add(new TextField("bibliography", doc.getBibliography(), Field.Store.YES));
            document.add(new TextField("words", doc.getWords(), Field.Store.YES));
            documentList.add(document);
        }
        return documentList;
    }
}

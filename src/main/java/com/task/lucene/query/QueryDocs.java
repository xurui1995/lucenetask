package com.task.lucene.query;

import com.task.lucene.model.CranQuery;
import com.task.lucene.parser.CranQueryParser;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * class for handling com.task.lucene.query
 */
public class QueryDocs {
    public static final String INDEX_PATH = "index";
    public static final String QUERY_PATH = "corpus/cran.qry";
    public static final String RESULT_PATH = "Results.txt";

    // start to handle queries from cran.qry
    public static void startQuery(Analyzer analyzer, Similarity similarity) {
        System.out.println("------startQuery------");

        if (analyzer == null) {
            System.out.println("analyzer is empty");
            return;
        }
        if (similarity == null) {
            System.out.println("similarity is empty");
            return;
        }
        try {

            String indexPath = analyzer.getClass().getSimpleName() + "_" + INDEX_PATH;

            if (!new File(indexPath).exists()) {
                System.out.println("index directory does not exist, please build index firstly");
                return;
            }
            // Open the index fold
            Directory directory = FSDirectory.open(Paths.get(indexPath));

            // init reader
            IndexReader reader = DirectoryReader.open(directory);

            // init IndexSearcher
            IndexSearcher searcher = new IndexSearcher(reader);

            // set Similarity
            searcher.setSimilarity(similarity);

            // get queries from file
            List<CranQuery> cranQueries = new CranQueryParser(QUERY_PATH).getParseResult();
            if (cranQueries == null || cranQueries.size() == 0) {
                System.out.println("can not get queries from file");
                return;
            }

            System.out.println("query.......");
            List<String> queryResults = new ArrayList<String>();
            for (CranQuery cranQuery : cranQueries) {
                // handle the text, remove stop words
                String text = handleQueryText(cranQuery.getQueryText());
                // get the query
                Query query = getQuery(analyzer, text);
                // get the search results
                TopDocs results = searcher.search(query, 1400);
                ScoreDoc[] scoreDocs = results.scoreDocs;
                for (int i = 0; i < scoreDocs.length; i++) {
                    ScoreDoc scoreDoc = scoreDocs[i];
                    int docID = scoreDoc.doc;
                    Document doc = searcher.doc(docID);

//                    System.out.println("=============================");
//                    System.out.println("doc id:" + doc.get("id"));
//                    System.out.println("score:" + scoreDoc.score);

                    // add the result to queryResults
                    queryResults.add(cranQuery.getId() + " Q0 " + doc.get("id") + " " + (i + 1) + " " + scoreDoc.score + " STANDARD");
                }
            }
            reader.close();
            System.out.println("------EndQuery------");
            // output the results
            String path = analyzer.getClass().getSimpleName() + similarity.getClass().getSimpleName() + RESULT_PATH;
            outputResult(queryResults, path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // generate the results for trec_eval
    private static void outputResult(List<String> queryResults, String resultPath) {
        if (queryResults == null) {
            System.out.println("------queryResults list is empty------");
            return;
        }
        System.out.println("------generating the result------");
        try {
            Path path = Paths.get(resultPath);
            Files.write(path, queryResults);
            System.out.println("------Generate the result over------");
            System.out.println("your result path : " + path.toAbsolutePath());
        } catch (Exception e) {
            System.out.println("------Fail to generate the result------");
            e.printStackTrace();
        }
    }

    // use MultiFieldQueryParser to get the Query
    private static Query getQuery(Analyzer analyzer, String text) throws ParseException {
        if (analyzer == null) {
            return null;
        }
        MultiFieldQueryParser multiFieldQP = new MultiFieldQueryParser(new String[]{"title", "author", "bibliography", "words"}, analyzer);
        return multiFieldQP.parse(text);
    }

    // remove special characters
    private static String handleQueryText(String queryText) {
        if (queryText != null) {
            queryText = queryText.replace("?", "");
        }
        return queryText;
    }
}

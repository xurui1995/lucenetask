package com.task.lucene.engine;

import com.task.lucene.index.BuildIndex;
import com.task.lucene.query.QueryDocs;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.search.similarities.ClassicSimilarity;

/**
 * the main class of project
 */
public class Engine {

    public static void main(String[] args) {
        String tips = "please check you command\n" +
                "build index:\n" +
                "java -jar target/lucenetask-1.0.jar index\n"+
                "start query(VSM or BM25):\n" +
                "java -jar target/lucenetask-1.0.jar query -s BM25\n" +
                "java -jar target/lucenetask-1.0.jar query -s VSM";
        if (args == null || args.length == 0) {
            System.out.println(tips);
            return;
        }
        if ("index".equals(args[0])) {
            // build index
            BuildIndex.startBuildIndex();
        } else if("query".equals(args[0])) {
            // use different similarity
            if (args.length == 2 && "BM25".equals(args[1])) {
                QueryDocs.startQuery(new BM25Similarity());
            } else if (args.length == 2 && "VSM".equals(args[1])){
                QueryDocs.startQuery(new ClassicSimilarity());
            } else {
                System.out.println(tips);
            }
        } else {
            System.out.println(tips);
        }
    }
}

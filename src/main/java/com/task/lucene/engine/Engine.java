package com.task.lucene.engine;

import com.task.lucene.index.BuildIndex;
import com.task.lucene.query.QueryDocs;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.search.similarities.Similarity;

import java.util.Arrays;


/**
 * the main class of project
 */
public class Engine {

    public static final String STANDARD_ANALYZER = "Standard";
    public static final String ENGLISH_ANALYZER = "English";

    public static final String BM25 = "BM25";
    public static final String CLASSIC = "Classic";


    // https://gist.github.com/sebleier/554280
    // NLTK's list of english stop words
    public static final String[] STOP_WORDS =
            {"i", "me", "my", "myself", "we", "our", "ours", "ourselves", "you", "your", "yours",
                    "yourself", "yourselves", "he", "him", "his", "himself", "she", "her", "hers",
                    "herself", "it", "its", "itself", "they", "them", "their", "theirs", "themselves",
                    "what", "which", "who", "whom", "this", "that", "these", "those", "am", "is", "are",
                    "was", "were", "be", "been", "being", "have", "has", "had", "having", "do", "does",
                    "did", "doing", "a", "an", "the", "and", "but", "if", "or", "because", "as", "until",
                    "while", "of", "at", "by", "for", "with", "about", "against", "between", "into", "through",
                    "during", "before", "after", "above", "below", "to", "from", "up", "down", "in", "out", "on",
                    "off", "over", "under", "again", "further", "then", "once", "here", "there", "when", "where",
                    "why", "how", "all", "any", "both", "each", "few", "more", "most", "other", "some", "such",
                    "no", "nor", "not", "only", "own", "same", "so", "than", "too", "very", "s", "t", "can",
                    "will", "just", "don", "should", "now"};

    public static String tips = "please check you command\n" +
            "build index(StandardAnalyzer or EnglishAnalyzer):\n" +
            "java -jar target/lucenetask-1.0.jar index -a {Analyzer}\n"+
            "Analyzer can be Standard or English\n" +
            "start query(Classic or BM25):\n" +
            "java -jar target/lucenetask-1.0.jar query -a {Analyzer} -s {Similarity}\n" +
            "Analyzer can be Standard or English\n" +
            "Similarity can be BM25 or Classic";

    public static void main(String[] args) {
        if (args == null || args.length == 0) {
            System.out.println(tips);
            return;
        }
        if ("index".equalsIgnoreCase(args[0])) {
            handleBuildIndex(args);
        } else if("query".equalsIgnoreCase(args[0])) {
            handleQuery(args);
        } else {
            System.out.println(tips);
        }
    }

    // handle query form command
    private static void handleQuery(String[] args) {
        if (args== null || args.length != 5 || !"-a".equalsIgnoreCase(args[1]) || !"-s".equalsIgnoreCase(args[3])) {
            System.out.println(tips);
            return;
        }
        QueryDocs.startQuery(getAnalyzer(args[2]), getSimilarity(args[4]));
    }

    // get Similarity form command
    private static Similarity getSimilarity(String s) {
        if (BM25.equalsIgnoreCase(s)) {
            return  new BM25Similarity();
        } else if (CLASSIC.equalsIgnoreCase(s)) {
            return new ClassicSimilarity();
        } else {
            System.out.println(tips);
            System.exit(0);
        }
        return null;
    }

    // handle index command
    private static void handleBuildIndex(String[] args) {
        if (args == null || args.length != 3 || !args[1].equals("-a")) {
            System.out.println(tips);
            return;
        }
        // build index
        BuildIndex.startBuildIndex(getAnalyzer(args[2]));
    }

    // get Analyzer form command
    public static Analyzer getAnalyzer(String a) {
        if (STANDARD_ANALYZER.equalsIgnoreCase(a)) {
            // EnglishAnalyzer.ENGLISH_STOP_WORDS_SET is not enough, so choose custom STOP_WORDS
            return  new StandardAnalyzer(new CharArraySet(Arrays.asList(STOP_WORDS), true));
        } else if (ENGLISH_ANALYZER.equalsIgnoreCase(a)) {
            return new EnglishAnalyzer(new CharArraySet(Arrays.asList(STOP_WORDS), true));
        } else {
            System.out.println(tips);
            System.exit(0);
        }
        return null;
    }
}

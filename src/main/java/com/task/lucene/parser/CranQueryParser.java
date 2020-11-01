package com.task.lucene.parser;

import com.task.lucene.model.CranQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * class for parse cran.qry
 */
public class CranQueryParser extends BaseParser {


    public CranQueryParser(String path) {
        super(path);
    }

    public List<CranQuery> getParseResult() {
        List<CranQuery> cranQueries = new ArrayList<CranQuery>();
        try {
            String content = readContent();
            if (content != null) {
                // split data with .I
                String[] queries = content.split(".I ");
                // docArray[0] is empty, so from com.task.lucene.index = 1
                for (int i = 1; i < queries.length; i++) {
                    String query = queries[i];
                    int index = query.indexOf(".W");
                    // length of tag is 2, so we need add 2 when calculate from com.task.lucene.index
                    String title = query.substring(index + 2).trim();
                    // generate CranQuery, use i as id
                    CranQuery cranQuery = new CranQuery(i, title);
                    cranQueries.add(cranQuery);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cranQueries;
    }

}

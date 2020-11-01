package com.task.lucene.parser;

import com.task.lucene.model.CranDoc;

import java.util.ArrayList;
import java.util.List;

/**
 * class for parse cran.all.1400
 */
public class CranDocParser extends BaseParser {

    public CranDocParser(String path) {
        super(path);
    }

    public List<CranDoc> getParseResult() {
        // .I : id
        // .T : title
        // .A : author
        // .B : bibliography
        // .W : words
        List<CranDoc> docList = new ArrayList<CranDoc>();
        String content = readContent();
        if (content != null && content.length() > 0) {
            // split data with .I
            String[] docArray = content.split("\\.I ");
            // docArray[0] is empty, so from com.task.lucene.index = 1
            for (int i = 1; i < docArray.length; i++) {
                String item = docArray[i];
                int indexIdEnd = item.indexOf("\n");
                String id = item.substring(0, indexIdEnd).trim();
                int IndexStartTitle = item.indexOf(".T");
                int IndexStartAuthor = item.indexOf(".A");
                // length of tag is 2, so we need add 2 when calculate from com.task.lucene.index
                String title = item.substring(IndexStartTitle + 2, IndexStartAuthor).trim();
                int IndexStartBib = item.indexOf(".B");
                String author = item.substring(IndexStartAuthor + 2, IndexStartBib).trim();
                int IndexStartWords = item.indexOf(".W");
                String bibliography = item.substring(IndexStartBib + 2, IndexStartWords).trim();
                String words = item.substring(IndexStartWords + 2).trim();

                // generate CranDoc
                CranDoc cranDoc = new CranDoc(Integer.parseInt(id), title, author, bibliography, words);
                docList.add(cranDoc);
            }
        }
        return docList;
    }

}

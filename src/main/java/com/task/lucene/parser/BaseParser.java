package com.task.lucene.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * BaseParser read content from specific path
 */
public abstract class BaseParser {

    // path of file
    private String mPath = "";

    public BaseParser(String path) {
        mPath = path;
    }

    // read content of file
    public String readContent() {
        try {
            File file = new File(mPath);
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
            String s = null;
            StringBuilder sb = new StringBuilder();
            while ((s = br.readLine()) != null) {
                sb.append(s).append("\n");
            }
            return sb.toString();
        } catch (Exception e) {
            System.out.println("readContent exception");
            e.printStackTrace();
        }
        return "";
    }
}

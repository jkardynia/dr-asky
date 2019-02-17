package com.jkgroup.drasky.commuting.bus.mpkcracow;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
public class JsoupClient {

    public Document get(String path, Map<String, String> params) throws IOException {
        if(path.startsWith("http://") || path.startsWith("https://")) {
            return Jsoup.connect(path)
                    .cookies(params)
                    .get();
        }else {
            return Jsoup.parse(new File(getClass().getClassLoader().getResource(path).getPath()), "UTF-8");
        }
    }
}

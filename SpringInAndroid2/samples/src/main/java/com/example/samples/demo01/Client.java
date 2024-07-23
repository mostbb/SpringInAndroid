package com.example.samples.demo01;

import java.io.Reader;
import java.util.List;

//客户端和不同的解析器紧密耦合
public class Client {
    private Parser csvParser, jsonParser;

    public Client(Parser csvParser, Parser jsonParser) {
        this.csvParser = csvParser;
        this.jsonParser = jsonParser;
    }

    Reader reader;

    public List getAll(String contentType) {

        switch (contentType) {
            case "CSV":
                return csvParser.parse(reader);
            case "JSON":
                return jsonParser.parse(reader);
        }

        return null;
    }

}

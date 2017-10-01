package project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import project.wordcount.WordCountService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;


@Controller
public class FileProccessController {

    @RequestMapping(method = RequestMethod.GET,value = "/fileProcess")
    @ResponseBody
    public String processFiles() {
        Path path = Paths.get("wordCounter");
        List<File> files = new ArrayList<>();
        String temp1 =                     "<div class=\"table-responsive\">\n" +
                "    <table class=\"table\">\n" +
                "        <thead>\n" +
                "        <tr>\n" +
                "            <th>Word</th>\n" +
                "            <th>Count</th>\n" +
                "        </tr>\n" +
                "        </thead>\n" +
                "        <tbody>\n";
        String temp2=                    "        </tbody>\n" +
                "    </table>\n" +
                "</div>";
        try {
            Stream<Path> pathsOfFiles = Files.list(path);
            pathsOfFiles.forEach(s ->
                    files.add(new File(String.valueOf(s)))
            );
            Map<String, Integer> countedWords = new WordCountService().countWords(files);
            String responseBody=temp1;
            Set<Map.Entry<String, Integer>> entries = countedWords.entrySet();
            Iterator<Map.Entry<String, Integer>> iterator = entries.iterator();
            while(iterator.hasNext()){
                Map.Entry<String, Integer> next = iterator.next();


                responseBody = responseBody.concat(
                        "        <tr>\n" +
                                "            <td>" + next.getKey().trim() + "</td>\n" +
                                "            <td>" + next.getValue() + "</td>\n" +
                                "        </tr>\n");
            }
            responseBody = responseBody.concat(temp2);
            System.out.println(responseBody);
          return responseBody;
        } catch (IOException e) {
            e.printStackTrace();
            return "something is wrong :(";
        }
    }
}

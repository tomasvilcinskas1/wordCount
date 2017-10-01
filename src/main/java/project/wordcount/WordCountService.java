package project.wordcount;

import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WordCountService implements IWordCountService {
    private static Map<String,Integer> uniqueWords ;
    public WordCountService(){
        uniqueWords = new ConcurrentHashMap<>();
    }
    @Override
    public Map<String, Integer> countWords(List<File> files) {
        for (File f:
             files) {
            new WordCountThread(uniqueWords,f).run();
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println("Wait after counting failed for some reason");
            e.printStackTrace();
        }
        return uniqueWords;
    }

    @Override
    public void writeResultToFileSorted(Map<String, Integer> words) {

    }
}

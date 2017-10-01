package project.wordcount;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface IWordCountService {

    Map<String,Integer> countWords(List<File> files);

    void writeResultToFileSorted(Map<String,Integer> words);
}

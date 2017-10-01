package project.wordcount;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;

public class WordCountThread extends Thread{
    private Map<String,Integer> words;
    private File file;
    public WordCountThread(Map<String,Integer> words, File file){
        this.words = words;
        this.file = file;
    }
    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        Scanner scan = null;
        try {
            scan = new Scanner(file);
            while (scan.hasNext()) {
                String temp = scan.next();
                if(words.containsKey(temp)){
                    words.replace(temp.trim(),(words.get(temp)+1));
                } else {
                    words.put(temp.trim(),1);
                }
            }
            scan.close();
            file.delete();
            System.out.println("Thread - "+Thread.currentThread().getName()+" has finished in "+ (System.currentTimeMillis()-startTime));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

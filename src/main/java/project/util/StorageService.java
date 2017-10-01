package project.util;

import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import project.util.IStorageService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
@Component
public class StorageService implements IStorageService {

    private Path path;
    private static List<File> files;
    private String fileName = "uploadFileNr" ;

    @Override
    public void init() {
        path = Paths.get("wordCounter");
        files = new ArrayList<>();
        boolean directoryError = new File(String.valueOf(path)).mkdir();
        if(!directoryError) System.out.println("Could not create a separate directory for storage. The directory either already exists or you need administrator rights");;
    }

    @Override
    public void store(MultipartFile file,int nr) {
        try {
            File tempfile = new File(String.valueOf(path)+"\\"+fileName+nr);
            tempfile.createNewFile();
            FileOutputStream fos = new FileOutputStream(tempfile);
            fos.write(file.getBytes());
            files.add(tempfile);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Could not create a temporary file to transfer the multipart file to");
        }
    }
    @Override
    public void store(MultipartFile[] file) {
        int nr = 0;
        for (MultipartFile i:
             file) {
            store(i,nr);
            nr++;
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            Stream<Path> tempStream = Files.list(path);
            return tempStream;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Could not get a Stream of paths of the created files in the StorageService");
        } finally {
            return null;
        }
    }

    @Override
    public Path load(String filename) {
        Path temppath = Paths.get(String.valueOf(path)+"\\"+filename);
        if (Files.exists(temppath)){
            return temppath;
        } else {
            System.out.println("The specified file does not exist");
            return null;
        }
    }

    @Override
    public Resource loadAsResource(String filename) {
        Path temppath = Paths.get(String.valueOf(path)+"\\"+filename);
        if (Files.exists(temppath)){
            return new PathResource(temppath);
        } else {
            System.out.println("The specified file does not exist");
            return null;
        }
    }


}

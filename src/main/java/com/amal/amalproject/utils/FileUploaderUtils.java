package com.amal.amalproject.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

public class FileUploaderUtils {

    private static Path rootPath = null;

    static {
        rootPath = Paths.get(System.getProperty("user.home") + "/static/images");

        if (!Files.exists(rootPath)) {
            System.out.println("SUCCESS-CREATE-IMAGE-DIRECTORY");
            try {
                Files.createDirectories(rootPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("ALREADY-EXIST-IMAGE-DIRECTORY");
        }
    }

    public static Path getRootPath() {
        return rootPath;
    }

    public static String loadImage(String filename) {
        return FileUtils.getFile(rootPath.toAbsolutePath() + "/" + filename).toURI().toString();
    }

    public static String savePhoto(File file) {


        Optional<String> extension = getFileExtension(file.getName());
        String filename = UUID.randomUUID().toString();

//        String fileOriginal = rootPath.toAbsolutePath() + "/" + file.getName();
        String fileMoved = rootPath.toAbsolutePath() + "/" + filename + "." + extension.get();

        try {
            FileUtils.copyFile(file,new File(fileMoved));
        } catch (IOException e) {
            e.printStackTrace();
        }


//        Files.copy(file.toPath(), toPath.toAbsolutePath());
//        Files.copy(file.toPath(),  toPath , StandardCopyOption.REPLACE_EXISTING);

//        System.out.println(fileOriginal);
//        System.out.println(fileMoved);
//
//        File fileToMove = new File(fileOriginal);
//        boolean isMoved = fileToMove.renameTo(new File(fileMoved));

//        FileUtils.copyFileToDirectory(FileUtils.getFile(file), toPath.toAbsolutePath());


//        Files.move(Path.of(file.getPath()), toPath, new StandardCopyOption[]{StandardCopyOption.REPLACE_EXISTING});



        return filename + "." + extension.get();
    }

    private static Optional<String> getFileExtension(String fileName) {
        final int indexOfLastDot = fileName.lastIndexOf('.');

        if (indexOfLastDot == -1) {
            return Optional.empty();
        } else {
            return Optional.of(fileName.substring(indexOfLastDot + 1));
        }
    }
}

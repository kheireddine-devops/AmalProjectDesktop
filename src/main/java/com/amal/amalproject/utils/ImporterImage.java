package com.amal.amalproject.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;



public class ImporterImage {

    public static Path rootPath=null;
    static {
        rootPath = Paths.get(Path.of("").toAbsolutePath().getParent() + "/AmalProjectServer/public/uploads/images/produits");

        if(!Files.exists(rootPath)){
            System.out.println("Image created successfully");
            try{
                Files.createDirectories(rootPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Already exist image directory");
        }
        }
    public static Path getRootPath(){
        return rootPath;    }

    public static String LoadImage (String filename){
        return FileUtils.getFile(rootPath.toAbsolutePath()+"/"+filename).toURI().toString();}

    public static String savePhoto(File file){
        Optional<String>extension = getFileExtension(file.getName());
        String filename = UUID.randomUUID().toString();
        String fileMoved = rootPath.toAbsolutePath() + "/" + filename + "." + extension.get();

        try {
            FileUtils.copyFile(file,new File(fileMoved));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filename + "." + extension.get();
    }

    static Optional<String> getFileExtension(String fileName) {
        final int indexOfLastDot = fileName.lastIndexOf('.');

        if (indexOfLastDot == -1) {
            return Optional.empty();
        } else {
            return Optional.of(fileName.substring(indexOfLastDot + 1));
        }
    }
    }














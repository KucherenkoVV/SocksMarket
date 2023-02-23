package com.coursework.socksmarket.services.impl;

import com.coursework.socksmarket.Exception.ReadFileException;
import com.coursework.socksmarket.Exception.CleanFileException;
import com.coursework.socksmarket.services.FilesService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FilesServiceImpl implements FilesService {

    @Value("${path.to.data.file}")
    private String dataFilePath;

    @Value("${name.of.data.file}")
    private String dataFileName;

    @Value("${name.of.operation.file}")
    private String operationFileName;

    @Override
    public boolean saveToFile(String json){
        Path path = Path.of(dataFilePath, dataFileName);
        try {
            cleanDataFile();
            Files.writeString(path, json);
            return true;
        } catch (IOException e){
            return false;

        }
    }

    @Override
    public String readFromFile(){
        Path path = Path.of(dataFilePath, dataFileName);
        try{
            return Files.readString(path);
        } catch (IOException e) {
            throw new ReadFileException("Не получается считать файл.");
        }
    }

    @Override
    public boolean cleanDataFile(){
        Path path = Path.of(dataFilePath, dataFileName);
        try{
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            throw new CleanFileException("Не удается очистить файл.");
        }
    }

    @Override
    public boolean saveToOperationFile(String json){
        Path path = Path.of(dataFilePath, operationFileName);
        try {
            cleanOperationFile();
            Files.writeString(path, json);
            return true;
        } catch (IOException e){
            return false;
        }
    }

    @Override
    public String readFromOperationFile(){
        Path path = Path.of(dataFilePath, operationFileName);
        try{
            return Files.readString(path);
        } catch (IOException e) {
            throw new ReadFileException("Не получается считать файл операций.");
        }
    }

    @Override
    public boolean cleanOperationFile(){
        Path path1 = Path.of(dataFilePath,operationFileName);
        try{
            Files.deleteIfExists(path1);
            Files.createFile(path1);
            return true;
        } catch (IOException e) {
            throw new CleanFileException("Не удается очистить файл операций.");
        }
    }

    @Override
    public File getDataFile(){
        return new File(dataFilePath + "/" + dataFileName);
    }

    @Override
    public File getOperationFile(){
        return new File(dataFilePath + "/" + operationFileName);
    }



}

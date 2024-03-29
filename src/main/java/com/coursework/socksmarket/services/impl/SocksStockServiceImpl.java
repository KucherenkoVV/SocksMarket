package com.coursework.socksmarket.services.impl;

import com.coursework.socksmarket.Exception.FileProcessingException;
import com.coursework.socksmarket.Exception.ProductExistException;
import com.coursework.socksmarket.model.*;
import com.coursework.socksmarket.services.SocksStockService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class SocksStockServiceImpl implements SocksStockService {

    public Set<Socks> socksStock = new LinkedHashSet<>();
    public Map<Operation, Socks> operationSocksMap = new LinkedHashMap<>();

    private final FilesServiceImpl filesService;

    public SocksStockServiceImpl(FilesServiceImpl filesService) {
        this.filesService = filesService;
    }

    @PostConstruct
    private void init() {
        try {
            readFromFile();
            readFromOperationFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addSocks(Socks socks) {
        if (socksStock.contains(socks)) {
            for (Socks socks1 : socksStock) {
                socks1.setQuantity(socks1.getQuantity() + socks.getQuantity());
            }
        } else {
            socksStock.add(socks);
        }
        operationSocksMap.put(new Operation(OperationType.ACCEPTANCE.getTranslate()), socks);
        saveToFile();
        saveToOperationFile();
    }

    @Override
    public int getSocks(Color color, Size size, int cottonMin, int cottonMax) {
        for (Socks sock : socksStock) {
            if (sock.getColor().equals(color) &&
                    sock.getSize().equals(size) &&
                    sock.getCottonPart() > cottonMin &&
                    sock.getCottonPart() < cottonMax) {
                return sock.getQuantity();
            } else if (!socksStock.iterator().hasNext()) {
                throw new ProductExistException("Товар с данными параметрами не найден");
            }
        }
        return 0;
    }


    @Override
    public Socks editSocksFromStock(Socks socks) {
        for (Socks socks1 : socksStock) {
            if (socks1.getColor().equals(socks.getColor()) &&
                    socks1.getSize().equals(socks.getSize()) &&
                    socks1.getCottonPart() == socks.getCottonPart() &&
                    socks1.getQuantity() > socks.getQuantity()) {
                socks1.setQuantity(socks1.getQuantity() - socks.getQuantity());
                operationSocksMap.put(new Operation(OperationType.DELIVERY.getTranslate()), socks1);
                saveToFile();
                saveToOperationFile();
            } else if (!socksStock.iterator().hasNext()) {
                throw new ProductExistException("Недостаточно товара на складе.");
            }
        }
        return socks;
    }

    @Override
    public boolean removeSocks(Socks socks) {
        for (Socks socks1 : socksStock) {
            if (socks1.getColor().equals(socks.getColor()) &&
                    socks1.getSize().equals(socks.getSize()) &&
                    socks1.getCottonPart() == socks.getCottonPart() &&
                    socks1.getQuantity() > socks.getQuantity()) {
                socks1.setQuantity(socks1.getQuantity() - socks.getQuantity());
                operationSocksMap.put(new Operation(OperationType.WRITE_OFF.getTranslate()), socks1);
                saveToFile();
                saveToOperationFile();
                return true;
            } else if (!socksStock.iterator().hasNext()) {
                throw new ProductExistException("Невозможно удалить товар. Товар с данными параметрами не найден.");
            }
        }
        return false;
    }

    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(socksStock);
            filesService.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new FileProcessingException("Ошибка в сохранении файла.");
        }
    }

    private void readFromFile() {
        String json = filesService.readFromFile();
        try {
            socksStock = new ObjectMapper().readValue(json, new TypeReference<LinkedHashSet<Socks>>() {
            });
        } catch (JsonProcessingException e) {
            throw new FileProcessingException("Ошибка в чтении файла.");
        }
    }

    private void saveToOperationFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(operationSocksMap);
            filesService.saveToOperationFile(json);
        } catch (JsonProcessingException e) {
            throw new FileProcessingException("Ошибка в сохранении файла Операций.");
        }
    }

    private void readFromOperationFile() {
        String json = filesService.readFromOperationFile();
        try {
            operationSocksMap = new ObjectMapper().readValue(json, new TypeReference<LinkedHashMap<Operation, Socks>>() {
            });
        } catch (JsonProcessingException e) {
            throw new FileProcessingException("Ошибка в чтении файла Операций.");
        }
    }
}

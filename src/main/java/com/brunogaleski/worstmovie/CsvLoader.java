package com.brunogaleski.worstmovie;

import com.brunogaleski.worstmovie.model.Nominee;
import com.brunogaleski.worstmovie.model.NomineeDeserializer;
import com.brunogaleski.worstmovie.model.Producer;
import com.brunogaleski.worstmovie.service.NomineeService;
import com.brunogaleski.worstmovie.service.ProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

@Component
@Order(1)
public class CsvLoader implements ApplicationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(CsvLoader.class);

    private final NomineeService nomineeService;

    private final ProducerService producerService;

    @Autowired
    public CsvLoader(NomineeService nomineeService, ProducerService producerService) {
        this.nomineeService = nomineeService;
        this.producerService = producerService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String fileName = "csv/movielist.csv";
        List<Nominee> nominees = loadCsvValues(fileName);
        persistNominees(nominees);
    }

    public List<Nominee> loadCsvValues(String fileName) {
        List<Nominee> nominees = new ArrayList<>();
        NomineeDeserializer deserializer = null;
        int count = 0;
        String line = "";

        Scanner reader = getFileReader(fileName);
        while (reader.hasNextLine()) {
            if (count++ == 0) {
                String headerString = reader.nextLine();
                deserializer = new NomineeDeserializer(headerString);
            }

            try {
                line = reader.nextLine();
                Nominee nominee = deserializer.deserializeNominee(line);
                nominees.add(nominee);
            } catch (Exception e) {
                LOGGER.warn("A problem was found while trying to deserialize line: " + line);
            }

        }
        reader.close();

        return nominees;
    }

    private void persistNominees(List<Nominee> nominees) {
        for (Nominee nominee : nominees) {
            List<Producer> producersAux = nominee.getProducers();
            nominee.setProducers(Collections.emptyList());
            nomineeService.persist(nominee);
            for (Producer producer : producersAux) {
                producerService.createIfItDoestExist(producer.getName());
                producer = producerService.findByName(producer.getName());
                nomineeService.addProducerToNominee(nominee, producer);
            }
        }
    }

    public Scanner getFileReader (String fileName) {
        Scanner scanner = null;

        try {
            File file = new ClassPathResource(fileName).getFile();
            scanner = new Scanner(file);
        } catch (FileNotFoundException fnf) {
            LOGGER.error("File: " + fileName + " was not found, file should be inside resources/csv folder");
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }

        return scanner;
    }

}

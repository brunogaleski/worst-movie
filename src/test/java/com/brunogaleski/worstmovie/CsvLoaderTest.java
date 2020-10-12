package com.brunogaleski.worstmovie;

import com.brunogaleski.worstmovie.model.Nominee;
import com.brunogaleski.worstmovie.model.Producer;
import com.brunogaleski.worstmovie.service.NomineeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class CsvLoaderTest {

    @Autowired
    private NomineeService nomineeService;

    @Autowired
    private CsvLoader csvLoaderTest;

    @Test
    public void testFetchNominees() {
        List<Nominee> nominees = nomineeService.findAll();
        assertFalse(nominees.isEmpty());

        for (Nominee nominee : nominees) {
            assertFalse(nominee.getTitle().isEmpty());
            assertFalse(nominee.getStudios().isEmpty());
            assertTrue(nominee.getYear() >= 1929 && nominee.getYear() <= Calendar.getInstance().get(Calendar.YEAR));
            List<Producer> producers = nominee.getProducers();
            assertFalse(producers.isEmpty());
            for (Producer producer : producers) {
                assertFalse(producer.getName().isEmpty());
            }
        }
    }

    @Test
    public void testCorrectNumberOfImports() {
        Scanner fileReader = csvLoaderTest.getFileReader("csv/movielist.csv");
        List<Nominee> nominees = nomineeService.findAll();
        assertNotNull(fileReader);
        assertEquals(getReaderNumberOfRecords(fileReader), nominees.size());
    }

    public int getReaderNumberOfRecords(Scanner scanner) {
        int count = 0;
        while (scanner.hasNextLine()) {
            count++;
            scanner.nextLine();
        }

        return count - 1;
    }

}

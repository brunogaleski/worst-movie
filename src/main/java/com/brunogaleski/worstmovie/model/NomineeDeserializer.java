package com.brunogaleski.worstmovie.model;

import com.brunogaleski.worstmovie.exception.InvalidYearException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class NomineeDeserializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(NomineeDeserializer.class);

    private final HeaderOrder headerOrder;

    private final String COLUMN_DELIMITER = ";";
    private final String ARRAY_DELIMITER = ",";

    public NomineeDeserializer(String headerOrder) {
        this.headerOrder = new HeaderOrder(headerOrder);
    }

    public Nominee deserializeNominee(String data) {
        Nominee nominee = new Nominee();
        String[] values = data.split(COLUMN_DELIMITER, -1);
        nominee.setYear(deserializeYear(values[headerOrder.getYearPos()]));
        nominee.setTitle(values[headerOrder.getTitlePos()]);
        nominee.setStudios(values[headerOrder.getStudiosPos()]);
        nominee.setProducers(deserializeProducers(values[headerOrder.getProducersPos()]));
        nominee.setWinner(deserializeWinner(values[headerOrder.getWinnerPos()]));

        return nominee;
    }

    private boolean deserializeWinner(String winnerString) {
        return "YES".equalsIgnoreCase(winnerString);
    }

    private List<Producer> deserializeProducers(String producersString) {
        List<Producer> producers;

        if (producersString == null) {
            producers = Collections.emptyList();
        } else {
            producersString = producersString.replace(" and ", ARRAY_DELIMITER);
            List<String> producersNames = Arrays.asList(producersString.split(ARRAY_DELIMITER));
            if (producersNames.isEmpty())
                LOGGER.warn("Could not get producers list from " + producersString);

            producers = producersNames.stream().filter(name -> !name.isEmpty()).map(String::trim).map(Producer::new).collect(Collectors.toList());
        }

        return producers;
    }

    public int deserializeYear(String yearString) {
        int year = 0;

        try {
            year = Integer.parseInt(yearString);
            if (year < 1929 || year > Calendar.getInstance().get(Calendar.YEAR)) {
                throw new InvalidYearException("Year should be between 1929 and current year");
            }

        } catch (NumberFormatException e) {
            LOGGER.warn("Not possible to parse " + yearString + " as a valid year, Value 0 will be used instead");
        } catch (InvalidYearException e) {
            LOGGER.warn(e.getMessage());
        }

        return year;
    }

    public class HeaderOrder {
        private final int yearPos;
        private final int titlePos;
        private final int studiosPos;
        private final int producersPos;
        private final int winnerPos;

        public HeaderOrder(String header) {
            List<String> headersList = Arrays.asList(header.split(COLUMN_DELIMITER));
            yearPos = headersList.indexOf("year") > 0 ? headersList.indexOf("year") : 0;
            titlePos = headersList.indexOf("title") > 0 ? headersList.indexOf("title") : 1;
            studiosPos = headersList.indexOf("studios") > 0 ? headersList.indexOf("studios") : 2;
            producersPos = headersList.indexOf("producers") > 0 ? headersList.indexOf("producers") : 3;
            winnerPos = headersList.indexOf("winner") > 0 ? headersList.indexOf("winner") : 4 ;
        }

        public int getYearPos() {
            return yearPos;
        }

        public int getTitlePos() {
            return titlePos;
        }

        public int getStudiosPos() {
            return studiosPos;
        }

        public int getProducersPos() {
            return producersPos;
        }

        public int getWinnerPos() {
            return winnerPos;
        }
    }

}

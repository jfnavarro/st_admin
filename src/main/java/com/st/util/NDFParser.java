package com.st.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.log4j.Logger;
import au.com.bytecode.opencsv.CSVReader;
import com.st.model.Chip;

/**
 * This class is an .ndf (chip) file parser. It is used in ChipController to
 * import chips from a file.
 */
public class NDFParser {

    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(NDFParser.class);

    private final InputStream fis;

    /**
     * Constructor.
     *
     * @param fis chip input content.
     */
    public NDFParser(InputStream fis) {
        this.fis = fis;
    }

    /**
     * Parses the class input stream and returns a Chip object.
     *
     * @return
     */
    public Chip readChip() {
        logger.info("About to read .ndf file with chip details.");

        int x1 = Integer.MAX_VALUE; // probes
        int y1 = Integer.MAX_VALUE;
        int x2 = Integer.MIN_VALUE;
        int y2 = Integer.MIN_VALUE;
        int x1_border = Integer.MAX_VALUE;
        int y1_border = Integer.MAX_VALUE;
        int x2_border = Integer.MIN_VALUE;
        int y2_border = Integer.MIN_VALUE;
        int x1_total = Integer.MAX_VALUE;
        int y1_total = Integer.MAX_VALUE;
        int x2_total = Integer.MIN_VALUE;
        int y2_total = Integer.MIN_VALUE;
        int barcodes = -1;
        try {
            try (CSVReader reader = new CSVReader(new InputStreamReader(fis), '\t',
                    '\'', 2)) {
                String[] nextLine;

                while ((nextLine = reader.readNext()) != null) {

                    String containerValue = nextLine[1];
                    int xValue = -1;
                    int yValue = -1;
                    if (nextLine[15] != null) {
                        xValue = Integer.parseInt(nextLine[15]);
                    }
                    if (nextLine[16] != null) {
                        yValue = Integer.parseInt(nextLine[16]);
                    }

                    if (containerValue.equals("BORDER")) {
                        if (xValue < x1_border) {
                            x1_border = xValue;
                        }
                        if (yValue < y1_border) {
                            y1_border = yValue;
                        }
                        if (xValue > x2_border) {
                            x2_border = xValue;
                        }
                        if (yValue > y2_border) {
                            y2_border = yValue;
                        }
                    } else if (containerValue.endsWith("PROBES")) {
                        if (xValue < x1) {
                            x1 = xValue;
                        }
                        if (yValue < y1) {
                            y1 = yValue;
                        }
                        if (xValue > x2) {
                            x2 = xValue;
                        }
                        if (yValue > y2) {
                            y2 = yValue;
                        }

                        // set barcodes if not yet set
                        if (barcodes < 0) {
                            // get number part before "K"
                            String[] parts = containerValue.split("K");
                            barcodes = Integer.parseInt(parts[0]) * 1000;
                        }
                    } else {
                        if (xValue < x1_total) {
                            x1_total = xValue;
                        }
                        if (yValue < y1_total) {
                            y1_total = yValue;
                        }
                        if (xValue > x2_total) {
                            x2_total = xValue;
                        }
                        if (yValue > y2_total) {
                            y2_total = yValue;
                        }
                    }

                }
            }

            // Create Chip and set values
            Chip chip = new Chip();
            chip.setX1(x1);
            chip.setY1(y1);
            chip.setX2(x2);
            chip.setY2(y2);
            chip.setX1_border(x1_border);
            chip.setY1_border(y1_border);
            chip.setX2_border(x2_border);
            chip.setY2_border(y2_border);
            chip.setX1_total(x1_total);
            chip.setY1_total(y1_total);
            chip.setX2_total(x2_total);
            chip.setY2_total(y2_total);
            chip.setBarcodes(barcodes);

            return chip;

        } catch (IOException e) {
            logger.info("Error reading .ndf file with chip details.");
            return null;
        }

    }
}

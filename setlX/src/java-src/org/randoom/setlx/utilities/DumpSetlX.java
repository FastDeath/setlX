package org.randoom.setlx.utilities;

import org.randoom.setlx.exceptions.FileNotWriteableException;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class DumpSetlX {

    public static void dumpToFile(String content, String fileName, boolean append) throws FileNotWriteableException {
        // then dump to file
        FileWriter     fWr = null;
        BufferedWriter out = null;
        try {
            // allow modification of fileName/path by environment provider
            fileName = Environment.filterFileName(fileName);
            // write file
            fWr = new FileWriter(fileName, append);
            out = new BufferedWriter(fWr);
            out.write(content);
        } catch (FileNotFoundException fnfe) {
            throw new FileNotWriteableException("File '" + fileName + "' could not be opened for writing.");
        } catch (IOException ioe) {
            throw new FileNotWriteableException(ioe.getMessage());
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (fWr != null) {
                    fWr.close();
                }
            } catch (IOException ioe) {
                // don't care at this point
            }
        }
    }
}


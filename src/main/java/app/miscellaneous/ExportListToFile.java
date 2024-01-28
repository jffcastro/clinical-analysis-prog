package app.miscellaneous;

import java.io.File;
import java.io.PrintWriter;

import java.util.List;

public class ExportListToFile {

    private final String fileName;
    private final List<?> list;
    public static final String FILE_TYPE_CSV = ".csv";
    public static final String FILE_TYPE_TXT = ".txt";

    public ExportListToFile(String fileName, List<?> list, String fileType ){
        this.fileName = fileName + fileType;
        this.list = list;
    }

    /**
     * Export the list to the file.
     *
     * @return true if the export was done successfully
     */
    public boolean exportList(String header) {
        File file = new File(fileName);
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(file);
            writer.format(header+"\n");
            for ( Object line: list) {
                writer.format("%s\n", line);
            }
        } catch (Exception FileNotFoundException) {
            return false;
        } finally {
            assert writer != null;
            writer.close();
        }
        return true;
    }
}

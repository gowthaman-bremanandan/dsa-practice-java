package practices;

import java.io.*;
import java.nio.file.*;
import java.util.stream.Stream;

public class LogEntryChecker {

    public static void main(String[] args) {

        // Provide folder path and search message here
        String folderPath = "D:\\Temp\\Customer logs\\High java memory\\2026_03_09_11_04_00_u_NqxgNsmnyKPnEgs\\u_pI4T3PJol9LfrN8\\13917192_2026295556555_ylpkm4";   // change this
        String searchMessage = "In the case of a Federated user(that is owned by a federated IdP";  // change this

        searchLogs(folderPath, searchMessage);
    }

    public static void searchLogs(String folderPath, String searchMessage) {

        Path folder = Paths.get(folderPath);

        // 🔹 Create new folder inside given folder
        Path outputFolder = folder.resolve("log_search_results");

        try {
            if (!Files.exists(outputFolder)) {
                Files.createDirectories(outputFolder);
            }
        } catch (IOException e) {
            System.err.println("Failed to create output folder");
            e.printStackTrace();
            return;
        }

        // 🔹 Output file inside new folder
        Path outputFile = outputFolder.resolve("log_search_output.txt");

        try (BufferedWriter writer = Files.newBufferedWriter(outputFile,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING)) {

            Files.list(folder)
                    .filter(Files::isRegularFile)
                    .forEach(file -> processFile(file, searchMessage, writer));

            System.out.println("Search completed.");
            System.out.println("Output file created at: " + outputFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void processFile(Path file, String searchMessage, BufferedWriter writer) {

        String searchLower = searchMessage.toLowerCase();

        try (Stream<String> lines = Files.lines(file)) {

            lines.filter(line -> line.toLowerCase().contains(searchLower))
                    .forEach(line -> {
                        try {
                            writer.write(file.getFileName() + " : " + line);
                            writer.newLine();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

        } catch (IOException e) {
            System.err.println("Error reading file: " + file.getFileName());
            e.printStackTrace();
        }
    }

}

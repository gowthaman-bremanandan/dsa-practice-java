package practices.ofcrelated;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;

public class DirectoryTreePrinter {

    public static void main(String[] args) {

        // 👉 CHANGE THIS PATH
        Path rootPath = Paths.get("D:\\Temp\\mickey\\mickeyLite\\AdventNetMickeyLite (1)\\AdventNet\\MickeyLite");

        // 👉 OUTPUT FILE
        String outputFile = "D:\\Temp\\mickey_structure.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

            writer.write("Directory Structure:\n\n");

            printTree(rootPath, writer, 0);

            System.out.println("✅ File generated at: " + outputFile);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= CORE LOGIC =================
    private static void printTree(Path path, BufferedWriter writer, int depth) throws IOException {

        if (!Files.exists(path)) return;

        // ✅ Java 8 compatible indent
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            sb.append("  ");
        }
        String indent = sb.toString();

        writer.write(indent + "|-- " + path.getFileName());
        writer.newLine();

        if (Files.isDirectory(path)) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
                for (Path entry : stream) {
                    printTree(entry, writer, depth + 1);
                }
            }
        }
    }
}
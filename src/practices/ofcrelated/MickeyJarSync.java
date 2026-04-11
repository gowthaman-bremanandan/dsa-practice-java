package practices.ofcrelated;

import java.io.*;
import java.nio.file.*;
import java.security.MessageDigest;
import java.util.*;

public class MickeyJarSync {

    static Path OLD_DIR = Paths.get("D:\\Temp\\mickey\\mickeyold\\AdventNetMickeyLite (2)\\AdventNet\\MickeyLite");
    static Path NEW_DIR = Paths.get("D:\\Temp\\mickey\\mickeyLite\\AdventNetMickeyLite (1)\\AdventNet\\MickeyLite");
    static Path TARGET_DIR = Paths.get("D:\\SPMP Lib\\mickey lib2");

    public static void main(String[] args) throws Exception {

        System.out.println("===== START =====");

        Set<String> oldJarNames = getAllJarNames(OLD_DIR);

        List<String> removed = new ArrayList<>();
        List<String> added = new ArrayList<>();
        List<String> updated = new ArrayList<>();

        // ================= REMOVE =================
        System.out.println("\n===== REMOVE PHASE =====");

        Files.walk(TARGET_DIR).forEach(path -> {

            if (path.toString().endsWith(".jar")) {

                String fileName = path.getFileName().toString();

                if (oldJarNames.contains(fileName)) {
                    try {
                        Files.delete(path);
                        removed.add(path.toString());
                        System.out.println("[REMOVED] " + path);
                    } catch (Exception e) {
                        System.out.println("[ERROR DELETE] " + path);
                    }
                }
            }
        });

        // ================= COPY (STRUCTURE PRESERVED) =================
        System.out.println("\n===== COPY PHASE (STRUCTURE PRESERVED) =====");

        Files.walk(NEW_DIR).forEach(path -> {

            if (path.toString().endsWith(".jar")) {

                try {
                    String fileName = path.getFileName().toString();

                    // 🔥 THIS IS THE KEY CHANGE
                    Path relativePath = NEW_DIR.relativize(path);
                    Path targetFile = TARGET_DIR.resolve(relativePath);

                    System.out.println("[COPY] " + relativePath);

                    // create folders if not exist
                    Files.createDirectories(targetFile.getParent());

                    if (Files.exists(targetFile)) {

                        if (!checksum(targetFile).equals(checksum(path))) {
                            updated.add(relativePath.toString());
                            System.out.println("[UPDATED] " + relativePath);
                        }

                    } else {
                        System.out.println("[NEW] " + relativePath);
                    }

                    Files.copy(path, targetFile, StandardCopyOption.REPLACE_EXISTING);
                    added.add(relativePath.toString());

                } catch (Exception e) {
                    System.out.println("[ERROR COPY] " + path);
                }
            }
        });

        // ================= REPORT =================
        writeReport(removed, added, updated);

        System.out.println("===== DONE =====");
    }

    // ================= GET ALL JAR NAMES =================
    static Set<String> getAllJarNames(Path base) throws IOException {

        Set<String> jars = new HashSet<>();

        Files.walk(base).forEach(path -> {
            if (path.toString().endsWith(".jar")) {
                jars.add(path.getFileName().toString());
            }
        });

        return jars;
    }

    // ================= CHECKSUM =================
    static String checksum(Path file) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] bytes = Files.readAllBytes(file);
        byte[] digest = md.digest(bytes);

        StringBuilder sb = new StringBuilder();
        for (byte b : digest) sb.append(String.format("%02x", b));
        return sb.toString();
    }

    // ================= REPORT =================
    static void writeReport(List<String> removed,
                            List<String> added,
                            List<String> updated) throws IOException {

        Path report = TARGET_DIR.resolve("jar-update-report.txt");

        try (BufferedWriter w = Files.newBufferedWriter(report)) {

            w.write("==== REMOVED ====\n");
            removed.forEach(r -> write(w, r));

            w.write("\n==== ADDED ====\n");
            added.forEach(a -> write(w, a));

            w.write("\n==== UPDATED ====\n");
            updated.forEach(u -> write(w, u));
        }
    }

    static void write(BufferedWriter w, String s) {
        try { w.write(s + "\n"); } catch (Exception ignored) {}
    }
}
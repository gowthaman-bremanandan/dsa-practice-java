package practices.ofcrelated;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;
import java.util.stream.*;

public class JarFileManager {

    // ── configuration ────────────────────────────────────────────────────────
    private static final String DELETABLE_PATH = "D:\\Temp\\Zoho security\\old\\ZohoSecurity\\ZohoSecurity\\lib\\agent";
    private static final String TARGET_PATH    = "D:\\Temp\\SPMP lib\\Lib";
    private static final String COPY_PATH      = "D:\\Temp\\Zoho security\\ZohoSecurity\\ZohoSecurity\\lib";
    // ─────────────────────────────────────────────────────────────────────────

    public static void main(String[] args) throws IOException {

        Path deletablePath = Paths.get(DELETABLE_PATH);
        Path targetPath    = Paths.get(TARGET_PATH);
        Path copyPath      = Paths.get(COPY_PATH);

        validateDirectory(deletablePath, "Deletable path");
        validateDirectory(targetPath,    "Target path");
        validateDirectory(copyPath,      "Copy path");

        // 1. Collect all jar names from deletable path (recursive)
        System.out.println("=== Scanning deletable path for JARs ===");
        List<String> deletableJars = collectJarNames(deletablePath);
        System.out.println("Found " + deletableJars.size() + " JARs in deletable path.\n");

        // 2. Collect all jar names from copy path (recursive) → map: baseName → Path
        System.out.println("=== Scanning copy path for JARs ===");
        Map<String, Path> copyJarMap = collectJarMap(copyPath);
        System.out.println("Found " + copyJarMap.size() + " JARs in copy path.\n");

        // 3. Delete matching jars from target path (flat — no nested scan needed)
        System.out.println("=== Deleting matched JARs from target path ===");
        deleteMatchingFromTarget(deletableJars, targetPath);

        // 4. Report missing jars (present in deletable but NOT in copy path,
        //    unless copy path has a higher version)
        System.out.println("\n=== Missing JARs in copy path ===");
        reportMissingJars(deletableJars, copyJarMap);

        // 5. Copy every jar from copy path to target path
        System.out.println("\n=== Copying JARs from copy path to target path ===");
        copyAllJarsToTarget(copyJarMap, targetPath);

        System.out.println("\n✔  Done.");
    }

    // ── Step 1 & 2 helpers ───────────────────────────────────────────────────

    /** Walk the directory tree and return just the file names of all .jar files. */
    private static List<String> collectJarNames(Path root) throws IOException {
        try (Stream<Path> walk = Files.walk(root)) {
            return walk
                    .filter(Files::isRegularFile)
                    .filter(p -> p.getFileName().toString().toLowerCase().endsWith(".jar"))
                    .map(p -> p.getFileName().toString())
                    .collect(Collectors.toList());
        }
    }

    /** Walk the directory tree and return a map of jarName → Path for all .jar files. */
    private static Map<String, Path> collectJarMap(Path root) throws IOException {
        Map<String, Path> map = new LinkedHashMap<>();
        try (Stream<Path> walk = Files.walk(root)) {
            walk.filter(Files::isRegularFile)
                    .filter(p -> p.getFileName().toString().toLowerCase().endsWith(".jar"))
                    .forEach(p -> map.put(p.getFileName().toString(), p));
        }
        return map;
    }

    // ── Step 3 ───────────────────────────────────────────────────────────────

    /**
     * For each jar name from the deletable list, find the exact file in the
     * target directory (flat search) and delete it.
     */
    private static void deleteMatchingFromTarget(List<String> deletableJars, Path targetPath)
            throws IOException {

        // Build a flat map of filename → path for the target directory
        Map<String, Path> targetFiles = new HashMap<>();
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(targetPath)) {
            for (Path p : ds) {
                if (Files.isRegularFile(p) &&
                        p.getFileName().toString().toLowerCase().endsWith(".jar")) {
                    targetFiles.put(p.getFileName().toString(), p);
                }
            }
        }

        for (String jarName : deletableJars) {
            if (targetFiles.containsKey(jarName)) {
                Files.delete(targetFiles.get(jarName));
                System.out.println("  Deleted: " + jarName);
            }
        }
    }

    // ── Step 4 ───────────────────────────────────────────────────────────────

    /**
     * For every jar in the deletable list, check whether the copy path contains
     * a matching artifact. "Matching" means:
     *   - exact file name present, OR
     *   - a jar with the same base artifact name but HIGHER version is present
     *     (in that case we silently ignore — no missing report).
     * Everything else is printed as MISSING.
     */
    private static void reportMissingJars(List<String> deletableJars,
                                          Map<String, Path> copyJarMap) {
        boolean anyMissing = false;

        for (String jarName : deletableJars) {

            // Exact match → present, nothing to report
            if (copyJarMap.containsKey(jarName)) {
                continue;
            }

            // Try to find same artifact with equal-or-higher version in copy path
            String[] delParsed = parseArtifactVersion(jarName);
            if (delParsed == null) {
                // Cannot parse version; treat as missing
                System.out.println("  MISSING: " + jarName);
                anyMissing = true;
                continue;
            }

            String delBase    = delParsed[0]; // e.g. "commons"
            String delVersion = delParsed[1]; // e.g. "1.1"

            boolean foundHigherOrEqual = copyJarMap.keySet().stream().anyMatch(copyJar -> {
                String[] copyParsed = parseArtifactVersion(copyJar);
                if (copyParsed == null) return false;
                return copyParsed[0].equalsIgnoreCase(delBase) &&
                        compareVersions(copyParsed[1], delVersion) >= 0;
            });

            if (!foundHigherOrEqual) {
                System.out.println("  MISSING: " + jarName);
                anyMissing = true;
            }
            // else: higher/equal version exists in copy → silently ignore
        }

        if (!anyMissing) {
            System.out.println("  No missing JARs found.");
        }
    }

    // ── Step 5 ───────────────────────────────────────────────────────────────

    /** Copy every jar from the copy path map into the target directory. */
    private static void copyAllJarsToTarget(Map<String, Path> copyJarMap, Path targetPath)
            throws IOException {
        for (Map.Entry<String, Path> entry : copyJarMap.entrySet()) {
            Path dest = targetPath.resolve(entry.getKey());
            Files.copy(entry.getValue(), dest, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("  Copied: " + entry.getKey());
        }
    }

    // ── Version utilities ────────────────────────────────────────────────────

    /**
     * Parses a jar file name into [baseName, version].
     * Handles patterns like:
     *   commons-1.1.jar         → ["commons", "1.1"]
     *   log4j-core-2.17.1.jar  → ["log4j-core", "2.17.1"]
     *   json-20231013.jar       → ["json", "20231013"]
     * Returns null if no version segment is found.
     */
    private static String[] parseArtifactVersion(String jarName) {
        // Strip .jar extension
        String name = jarName.replaceAll("(?i)\\.jar$", "");

        // Pattern: <base>-<version>  where version starts with a digit
        Pattern p = Pattern.compile("^(.+?)-(\\d[\\w.]*)$");
        Matcher m = p.matcher(name);
        if (m.matches()) {
            return new String[]{ m.group(1), m.group(2) };
        }
        return null;
    }

    /**
     * Compares two version strings numerically segment by segment.
     * Returns  > 0 if v1 > v2
     *          = 0 if v1 == v2
     *          < 0 if v1 < v2
     */
    private static int compareVersions(String v1, String v2) {
        String[] parts1 = v1.split("[.\\-]");
        String[] parts2 = v2.split("[.\\-]");
        int len = Math.max(parts1.length, parts2.length);

        for (int i = 0; i < len; i++) {
            int seg1 = i < parts1.length ? parseSegment(parts1[i]) : 0;
            int seg2 = i < parts2.length ? parseSegment(parts2[i]) : 0;
            if (seg1 != seg2) return Integer.compare(seg1, seg2);
        }
        return 0;
    }

    private static int parseSegment(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    // ── Validation ───────────────────────────────────────────────────────────

    private static void validateDirectory(Path path, String label) {
        if (!Files.exists(path) || !Files.isDirectory(path)) {
            throw new IllegalArgumentException(label + " does not exist or is not a directory: " + path);
        }
    }
}
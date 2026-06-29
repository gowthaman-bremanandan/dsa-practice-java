package practices.ofcrelated;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class JarCopyUtility {

    public static void main(String[] args) throws IOException {

        // 👉 INPUT DIRECTORIES
        String sourceDir = "D:\\SPMP Lib\\mickey lib2";       // Directory 1 (search here recursively)
        String destinationDir = "D:\\SPMP Lib\\mickey lib";    // Directory 2 (copy here)

        // 👉 Your Ant-style JAR list
        String jarListRaw = "${prod_home}/bin/run.jar:${prod_home}/lib/commons-io-2.20.0.jar:${prod_home}/lib/commons-csv-1.14.1.jar:${prod_home}/lib/tomcat/servlet-api.jar:${prod_home}/lib/AdvPersistence.jar:${prod_home}/lib/kryo-4.0.1.jar:${prod_home}/lib/minlog-1.3.0.jar:${prod_home}/lib/objenesis-2.1.jar:${prod_home}/lib/jgroups-3.6.16.Final.jar:${prod_home}/lib/reflectasm-1.10.1-shaded.jar:${prod_home}/lib/service-proxy-core-4.5.1.jar:${prod_home}/lib/spring-shaded.jar:${prod_home}/lib/ManageEngineADSLoadBalancer.jar:${prod_home}/lib/framework-tools.jar:${prod_home}/lib/AdventNetNPrevalent.jar:${prod_home}/lib/joda-time-2.1.jar:${prod_home}/lib/jasperreports-6.6.0.jar:${prod_home}/lib/AdvAuthentication.jar:${prod_home}/lib/json.jar:${prod_home}/lib/AdvMicroFramework.jar:${prod_home}/lib/AdvTaskEngine.jar:${prod_home}/lib/AdvTools.jar:${prod_home}/lib/ManageEngineADSFramework.jar:${prod_home}/lib/ManageEngineADSFStartup.jar:${prod_home}/lib/jna-4.0.0.jar:${prod_home}/lib/jna-platform-3.4.0.jar:${prod_home}/lib/metrack.jar:${prod_home}/lib/jsoup-1.15.3.jar:${prod_home}/lib/commons-codec-1.16.1.jar:${prod_home}/ES/lib/elasticsearch-5.6.4.jar:${prod_home}/ES/lib/lucene-core-6.6.1.jar:${prod_home}/lib/jackson-databind-2.17.1.jar:${prod_home}/lib/jackson-core-2.17.1.jar:${prod_home}/lib/za-dae.jar:${prod_home}/lib/transport-5.6.4.jar:${prod_home}/lib/guava-33.4.8-jre.jar:${prod_home}/lib/jcifs-1.3.17.jar:${prod_home}/lib/jcifs-krb5-1.2.13.jar:${prod_home}/lib/commons-lang3-3.19.0.jar:${prod_home}/lib/jta-1.1.jar:${prod_home}/lib/commons-httpclient-3.1.jar:${prod_home}/ES/lib/lucene-queryparser-6.6.1.jar:${prod_home}/ES/lib/lucene-suggest-6.6.1.jar:${prod_home}/lib/lucene-facet-6.6.1.jar:${prod_home}/ES/lib/lucene-analyzers-common-6.6.1.jar:${prod_home}/lib/zip4j-2.11.5.jar:${prod_home}/lib/postgresql-42.7.7.jar:${prod_home}/lib/httpclient-4.5.13.jar:${prod_home}/lib/httpcore-4.4.4.jar:${prod_home}/lib/bcprov-jdk18on-1.77.jar:${prod_home}/lib/bcpkix-jdk18on-1.77.jar:${prod_home}/lib/azure-client-authentication-1.6.4.jar:${prod_home}/lib/azure-client-runtime-1.6.4.jar:${prod_home}/lib/client-runtime-1.6.4.jar:${prod_home}/lib/adal4j-1.6.2.jar:${prod_home}/lib/json-smart-2.4.10.jar:${prod_home}/lib/nimbus-jose-jwt-10.6.jar:${prod_home}/lib/oauth2-oidc-sdk-5.64.4.jar:${prod_home}/lib/okhttp-3.3.1.jar:${prod_home}/lib/slf4j-api-1.7.31.jar:${prod_home}/lib/adapter-rxjava-2.4.0.jar:${prod_home}/lib/retrofit-2.4.0.jar:${prod_home}/lib/jackson-datatype-joda-2.9.4.jar:${prod_home}/lib/okio-1.14.0.jar:${prod_home}/lib/rxjava-1.3.4.jar:${prod_home}/lib/zoho-security.jar:${prod_home}/ES/lib/delete-by-query-2.2.0.jar:${prod_home}/lib/gson-2.13.2.jar:${prod_home}/lib/delete-by-query-2.2.0.jar:${prod_home}/lib/poi-5.2.3.jar:${prod_home}/lib/commons-compress-1.26.1.jar:${prod_home}/lib/poi-ooxml-5.2.3.jar:${prod_home}/lib/poi-ooxml-lite-5.2.3.jar:${prod_home}/lib/poi-scratchpad-5.2.3.jar:${prod_home}/lib/xmlbeans-5.1.1.jar:${prod_home}/lib/smbj-0.14.0.jar:${prod_home}/lib/dcerpc-0.10.0.jar:${prod_home}/lib/asn-one-0.4.0.jar:${prod_home}/lib/mbassador-1.3.0.jar:${prod_home}/lib/za-commons.jar:${mickeyliteupdate_home}/updatemanager:${prod_home}/lib/trayicon.jar:${build_dir}/pkg/ManageEngine/SPMP/lib/tools.jar:${prod_home}/lib/dbcp.jar:${prod_home}/lib/conf.jar:${prod_home}/lib/versioning.jar:${prod_home}/lib/ManageEngineADSFrameworkLogForwarder.jar:${prod_home}/lib/delta_agent.jar:${prod_home}/lib/commons-text-1.11.0.jar:${prod_home}/lib/waf-agent.jar:${prod_home}/lib/tomcat/catalina.jar:${prod_home}/lib/ManageEngineAPPFw.jar:${prod_home}/lib/AdventNetUpdateManagerInstaller.jar:${mickeylite_home}/lib/AdvPersistence.jar:${mickeylite_home}/lib/AdvAuthentication.jar::${mickeylite_home}/lib/meta.jar:${mickeylite_home}/lib/tomcatDependent.jar:${mickeylite_home}/lib/tomcat-i18n-cs.jar:${prod_home}/lib/meta.jar";
        // 👉 Extract only JAR file names
        Set<String> requiredJars = Arrays.stream(jarListRaw.split(":"))
                .map(path -> path.substring(path.lastIndexOf("/") + 1))
                .collect(Collectors.toSet());

        System.out.println("Total JARs to search: " + requiredJars.size());

        // 👉 Scan all files in source directory (recursive)
        Map<String, Path> foundJarMap = new HashMap<>();

        Files.walk(Paths.get(sourceDir))
                .filter(Files::isRegularFile)
                .forEach(path -> {
                    String fileName = path.getFileName().toString();
                    if (requiredJars.contains(fileName)) {
                        foundJarMap.put(fileName, path);
                    }
                });

        // 👉 Prepare destination
        Path destPath = Paths.get(destinationDir);
        if (!Files.exists(destPath)) {
            Files.createDirectories(destPath);
        }

        List<String> copied = new ArrayList<>();
        List<String> missing = new ArrayList<>();

        // 👉 Copy logic
        for (String jar : requiredJars) {
            if (foundJarMap.containsKey(jar)) {
                Path source = foundJarMap.get(jar);
                Path target = destPath.resolve(jar);

                try {
                    Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
                    copied.add(jar);
                } catch (IOException e) {
                    System.out.println("Failed to copy: " + jar + " -> " + e.getMessage());
                }
            } else {
                missing.add(jar);
            }
        }

        // 👉 Output results
        System.out.println("\n===== COPIED JARS =====");
        copied.forEach(System.out::println);

        System.out.println("\n===== MISSING JARS =====");
        missing.forEach(System.out::println);

        System.out.println("\nSummary:");
        System.out.println("Copied: " + copied.size());
        System.out.println("Missing: " + missing.size());
    }
}
package practices.java17;

import java.util.*;
import java.util.stream.Collectors;

// ✅ Java 17: Record (replaces boilerplate POJO)
record Employee(int id, String name, String department, double salary) {}

        public class Java17FeaturesDemo {

            public static void main(String[] args) {

                // Sample Data
                List<Employee> employees = List.of(
                        new Employee(1, "Gowtham", "IT", 70000),
                        new Employee(2, "Ravi", "HR", 50000),
                        new Employee(3, "Anu", "IT", 90000),
                        new Employee(4, "Kumar", "Finance", 60000)
                );

                // ============================================================
                // 1. SWITCH EXPRESSION (Major upgrade from Java 8)
                // ============================================================

                String department = "IT";

                // ❌ Java 8
                String role;
                switch (department) {
                    case "IT":
                        role = "Developer";
                        break;
                    case "HR":
                        role = "Recruiter";
                        break;
                    default:
                        role = "Unknown";
                }

                // ✅ Java 17
                String roleNew = switch (department) {
                    case "IT" -> "Developer";
                    case "HR" -> "Recruiter";
                    default -> "Unknown";
                };

                System.out.println("Role: " + roleNew);

                // 💡 Interview Tip:
                // "Switch expressions remove boilerplate and avoid fall-through bugs"


                // ============================================================
                // 2. STREAM + MAP + FILTER (same as Java 8 but improved readability)
                // ============================================================

                // Get IT employees with salary > 60000
                List<String> highPaidIT = employees.stream()
                        .filter(e -> e.department().equals("IT"))
                        .filter(e -> e.salary() > 60000)
                        .map(Employee::name)
                        .toList(); // ✅ Java 17 (instead of collect(Collectors.toList()))

                System.out.println(highPaidIT);


                // ============================================================
                // 3. VAR KEYWORD (Java 10+)
                // ============================================================

                // ❌ Java 8
                List<String> namesOld = employees.stream()
                        .map(Employee::name)
                        .collect(Collectors.toList());

                // ✅ Java 17
                var names = employees.stream()
                        .map(Employee::name)
                        .toList();

                System.out.println(names);

                // 💡 Interview Tip:
                // "var improves readability but should not reduce clarity"


                // ============================================================
                // 4. TEXT BLOCKS (Java 15+)
                // ============================================================

                // ❌ Java 8
                String jsonOld = "{\n" +
                        "  \"name\": \"Gowtham\",\n" +
                        "  \"role\": \"Developer\"\n" +
                        "}";

                // ✅ Java 17
                String json = """
                {
                  "name": "Gowtham",
                  "role": "Developer"
                }
                """;

                System.out.println(json);

                // 💡 Interview Tip:
                // "Text blocks are useful for JSON, SQL, HTML"


                // ============================================================
                // 5. OPTIONAL IMPROVEMENTS
                // ============================================================

                Optional<Employee> emp = employees.stream()
                        .filter(e -> e.id() == 10)
                        .findFirst();

                // ❌ Java 8
                if (emp.isPresent()) {
                    System.out.println(emp.get().name());
                } else {
                    System.out.println("Not found");
                }

                // ✅ Java 17
                emp.ifPresentOrElse(
                        e -> System.out.println(e.name()),
                        () -> System.out.println("Not found")
                );


                // ============================================================
                // 6. INSTANCEOF PATTERN MATCHING (Java 16+)
                // ============================================================

                Object obj = new Employee(5, "Test", "IT", 40000);

                // ❌ Java 8
                if (obj instanceof Employee) {
                    Employee e = (Employee) obj;
                    System.out.println(e.name());
                }

                // ✅ Java 17
                if (obj instanceof Employee e) {
                    System.out.println(e.name());
                }


                // ============================================================
                // 7. SEALED CLASSES (Java 17)
                // ============================================================

                Shape shape = new Circle();

                String result;

                if (shape instanceof Circle) {
                    result = "Circle Shape";
                } else if (shape instanceof Rectangle) {
                    result = "Rectangle Shape";
                } else {
                    result = "Unknown";
                }

                System.out.println(result);

                // 💡 Interview Tip:
                // "Sealed classes restrict inheritance for better design control"


                // ============================================================
                // 8. IMMUTABLE LIST (Java 9+)
                // ============================================================

                List<String> fixedList = List.of("A", "B", "C");

                // fixedList.add("D"); // ❌ Throws exception (immutable)

                System.out.println(fixedList);
            }
        }


// ============================================================
// SEALED CLASS EXAMPLE
// ============================================================

        sealed interface Shape permits Circle, Rectangle {}

        final class Circle implements Shape {}

        final class Rectangle implements Shape {}
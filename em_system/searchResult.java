package em_system;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;



//Wrapper class to store search results and execution time.
class SearchResult {
    private final List<Employee> employees;
    private final double timeMs;

//Constructor
    SearchResult(List<Employee> employees, double timeMs) {
        this.employees = employees;
        this.timeMs = timeMs;
    }

//Gets the list of matching employees.
    List<Employee> getEmployees() {
        return employees;
    }

    // Gets the search execution time.
    double getTimeMs() {
        return timeMs;
    }

    // Sorts employees by the specified field and order
    private void sortEmployees(String field, boolean ascending) {
        Comparator<Employee> comparator = switch (field.toLowerCase()) {
            case "id" -> Comparator.comparing(Employee::getId, String.CASE_INSENSITIVE_ORDER);
            case "name" -> Comparator.comparing(Employee::getName, String.CASE_INSENSITIVE_ORDER);
            default -> throw new IllegalArgumentException("Invalid sort field: " + field);
        };
        
        if (!ascending) {
            comparator = comparator.reversed();
        }
        
        Collections.sort(employees, comparator);
    }

    // Static method to search employees
    public static SearchResult searchEmployees(List<Employee> employees, String field, String value) {
        List<Employee> results = new ArrayList<>();
        long startTime = System.nanoTime();

        if (employees == null || employees.isEmpty()) {
            System.out.println("No employees to search.");
            return new SearchResult(results, (System.nanoTime() - startTime) / 1_000_000.0);
        }

        field = field.toLowerCase();
        final String searchValue = value != null ? value : "";

        switch (field) {
            case "id":
                Collections.sort(employees, Comparator.comparing(Employee::getId, String.CASE_INSENSITIVE_ORDER));
                Employee dummy = new RegularEmployee(searchValue, "", "", 0.0, 0.0, 0.0) {
                    @Override public String getId() { return searchValue; }
                };
                int index = Collections.binarySearch(employees, dummy, 
                    Comparator.comparing(Employee::getId, String.CASE_INSENSITIVE_ORDER));
                if (index >= 0) results.add(employees.get(index));
                break;

            case "name":
                String lowerValue = searchValue.toLowerCase();
                for (Employee emp : employees) {
                    if (emp.getName() != null && emp.getName().toLowerCase().contains(lowerValue)) {
                        results.add(emp);
                    }
                }
                break;

            default:
                System.out.println("Invalid search field: " + field + ". Use 'id' or 'name'.");
        }

        double timeMs = (System.nanoTime() - startTime) / 1_000_000.0;
        if (results.isEmpty()) {
            System.out.println("No employees found for " + field + ": " + searchValue);
        } else {
            System.out.println("Found " + results.size() + " employee(s) for " + field + ": " + searchValue);
        }
        return new SearchResult(results, timeMs);
    }
}
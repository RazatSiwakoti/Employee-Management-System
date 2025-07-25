package em_system;
import java.io.*;
import java.util.*;
import java.util.Scanner;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import java.util.Collections;
import java.util.Comparator;


/*
ICT711 - Java Group Project
KOI Newcastle
Razat Siwakoti (20032655)
*/


public class EMS {
	private ArrayList<Employee> employees;  //List to store all employees for iteration-based operations
    private HashMap<String, Employee> employeesByID; //Map to store employees by their ID (O(1) retrieval)
    private Scanner scanner; //Scanner to read user input
    private String actionFile; //User defined file name

    //Replace this with your own file path
    private static final String originalFilePath = "C:\\Users\\razat\\Desktop\\JavaProject\\JavaProject\\em_system\\data.csv";  //original Data file path
    private static final String projectDirectory = "C:\\Users\\razat\\Desktop\\JavaProject\\JavaProject\\em_system\\"; //Project folder name

    //Constructor to initialize the collection and scanner
    public EMS() {
        employees = new ArrayList<>();
        employeesByID = new HashMap<>();
        scanner = new Scanner(System.in); 
       }

         // Getter for action file
    public String getActionFile() {
        return actionFile;
    }

    // Getter for employees list
    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    // Getter for employeesByID map
    public HashMap<String, Employee> getEmployeesByID() {
        return employeesByID;
    }


    
    //Initialize data by prompting for file names and loading data
    private void initializeData(){
        System.out.println("Create a new file name to save data: ");
        String actionFileName = scanner.nextLine();
        actionFile = projectDirectory + actionFileName + ".csv"; //This constructs the full path to the action file

    //Check if the orginal file exists before reading or not
            File orginalFile = new File(originalFilePath);
            if (!orginalFile.exists()){
                System.out.println("Error: " + originalFilePath + " does not exist. Please check file path and restart the program.");
                System.exit(0);;
            }
            loadRecords(originalFilePath); //Load data from default file
            saveToFile(actionFile); //Create action file with the data from the default file            
            System.out.println("Data loaded from " + originalFilePath + " and copied to " + actionFile);
            }   
    
       // Show mode selection dialog
       public void showModeSelection() {
        String[] options = {"GUI", "CLI"};
        int choice = JOptionPane.showOptionDialog(
            null,
            "Select Interaction Mode",
            "Employee Management System",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            options,
            options[0]
        );

        if (choice == 0) {
            // Start GUI
            String fileName = JOptionPane.showInputDialog(null, "Enter action file name:");
            if (fileName == null || fileName.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Action file name required. Exiting.", "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
            actionFile = projectDirectory + fileName + ".csv";
            File originalFile = new File(originalFilePath);
            if (!originalFile.exists()) {
                JOptionPane.showMessageDialog(null, "Error: " + originalFilePath + " does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
            loadRecords(originalFilePath);
            saveToFile(actionFile);
            new EMS_GUI(this);
        } else {
            // Start CLI
            initializeData();
            runCLI();
        }  
       }  
        
 
    
  //Main loop for CLI Mode
    public void runCLI(){
        while(true){
            displayMenu(); //Show above display menu
            int choice = getUserChoice(); //Get user choice
            switch (choice) {
                case 1:
                //load record from action file
                    File actionFileObj = new File(actionFile);
                    if(!actionFileObj.exists()){
                        System.out.println("Error, " + actionFile + " does not exists. Create a new file first!");
                        break;
                    }
                    //Warn about discarding unsaved changes and offer to save again
                    System.out.println("Reloading will discard unsaved changes. Save everything to " + actionFile + "? (y/n)");
                    if(scanner.nextLine().equalsIgnoreCase("y")){
                        saveToFile(actionFile);
                    }
                    loadRecords(actionFile);   //Load records from csv
                    break;
                case 2:
                    addEmployee();   //Add a new employee              
                    break;
                case 3:
                    updateEmployee(); //Update employee information              
                    break;
                case 4:
                    deleteEmployee(); //Delete employee                 
                    break;
                case 5:
                    queryEmployee(); //Query employee details 
             
                    break;
                case 6:
                	displayData(); //Display all the data               
                	break;
                case 7:
                    searchEmployee(); //Display all the data               
                	break;

                case 8:
                    System.out.println("Exiting program..."); 
                    scanner.close(); //to prevent resource leak
                    return; //Exit program
                default:
                    System.out.println("Invalid choice. Please try again.");

            }
        }
    }
    
    
    
    //Display the menu to the user
    public void displayMenu(){
        System.out.println("------------------------");
        System.out.println("Employee Management System");
        System.out.println("------------------------");
        System.out.println("1. Load records ");
        System.out.println("2. Add a new employee");
        System.out.println("3. Update Employee information");
        System.out.println("4. Delete employees");
        System.out.println("5. Query employee details");
        System.out.println("6. Display all data");
        System.out.println("7. Search an Employee");
        System.out.println("8. Exit");
        System.out.println("------------------------");
        System.out.println("Enter your choice: ");
    }
    

    
 // Get user choice for above menu
    private int getUserChoice(){
        try{
            return Integer.parseInt(scanner.nextLine());
        }catch(NumberFormatException e){
            System.out.println("Invalid input. Please enter a valid number.");
            return -1; //Return invalid choice
        }
    }


   
    
  //OPTION 1: Load records from csv file AND create action file
    //Load employee from csv file
    public void loadRecords(String fileName){
        try{
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            employees.clear(); //Clear existing employees
            employeesByID.clear(); //Clear existing employee map
            boolean nullDataFound = false; // flag to check if null data note has already been shown
            String line;
            reader.readLine(); //Skip header line
            int lineNumber = 1; //to track line number for error

            while((line = reader.readLine()) != null){
                lineNumber++;
                String[] data = line.split(",");  //split csv line by commas               
                
            try{
            	//validating dataset 
            	if(data.length < 6) {
            	    if (!nullDataFound) {
            	        System.out.println("Note: Some data contains null value.");
            	        nullDataFound = true;
            	    }
            	    continue;
            	}
            	
            
                //Handle type field (Manager, RegualrEmployee,Intern)
                String type = data[0].trim();
                if (type.isEmpty()) {
                    System.out.println("Line " + lineNumber + " - Type: (Empty), skipping row (" + line + ")");
                        continue; // Type is critical, skip row if empty                   }
                }
                
                //Handle ID
                String id = data[1].trim();
                if(id.isEmpty()){                    
                    id = "-"; //Placeholder for name to make it able to update using updateEmployee()
                }
                
                //Handle Name field            
                String name = data[2].trim(); //Name of employee
                if(name.isEmpty()){                    
                    name = "(Empty)"; //Placeholder for name to make it able to update using updateEmployee()
                } 

                //Handle Email field
                String email = data[3].trim(); //Email of employee
                if(email.isEmpty()){                   
                    email = "(Not Provided)"; //Placeholder for name to make it able to update using updateEmployee()
                }


                //Handle base Salary
                //set 0 if baseSalary is missing
                double baseSalary = data[4].trim().isEmpty() ? 0.0 : Double.parseDouble(data[4].trim()); 
                 
                //Handle Performance Rating
              //set average rating 3.0 if its empty
                double performanceRating = data[5].trim().isEmpty() ? 3.0 : Double.parseDouble(data[5].trim());;
                
                //Extrabenefits
                //set 0 if empty
                double extraBenefits = 0.0;
                if (data.length > 6 && !data[6].trim().isEmpty()) {
                    extraBenefits = Double.parseDouble(data[6].trim());
                }

                Employee emp;

                //Check employee type and create corresponding employee object
                switch (type.toLowerCase()) {
                    case "manager":
                    //Handle bonus for manager
                     //Bonus percentage of manager
                    emp = new Manager(id, name, email, baseSalary, performanceRating, extraBenefits); //extraBenefits = bonus perce
                    break;

                    case "regularemployee":
                    emp = new RegularEmployee(id, name, email, baseSalary, performanceRating, extraBenefits);
                    break;

                    case "intern":                    
                    emp = new Intern(id, name, email, baseSalary,performanceRating, extraBenefits);
                    break;

                    default:
                    System.out.println("Line " + lineNumber + " - Unknown employee type: " + type);
                    continue; //Skip invalid employee types
                }                
                employees.add(emp); //Add employee to list
                employeesByID.put(emp.getId(), emp); //Add employee to map
            }catch (NumberFormatException e){
                System.out.println("Skipping line " + lineNumber + ": Invalid number format in line (" + line + ")");
            }catch ( Exception e){
                System.out.println("Skipping line " + lineNumber + ": Error processing line (" + line + ") - " + e.getMessage());
            }
        }
        reader.close();
        System.out.println("Succesfully loaded data from " + fileName);
        
        } catch(FileNotFoundException e){
            System.out.println("Error: File not found - " + e.getMessage());
        } catch(IOException e){
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
    
    
    
    
  //OPTION 2: Add a new employee
  public void addEmployee(){
        try{
            //Get employee details from user
            System.out.println("Enter employee type (Manager, RegularEmployee, Intern): ");
            String type = scanner.nextLine().toLowerCase();
            
            // Validate employee type
            if (!(type.equals("manager") || type.equals("regularemployee") || type.equals("intern"))) {
                System.out.println("Invalid employee type. Please enter Manager, RegularEmployee, or Intern.");
                return;
            }
            
            
         // Input and validate employee ID
            String idPrefix = switch (type) {
                case "manager" -> "M";
                case "regularemployee" -> "R";
                case "intern" -> "I";
                default -> ""; // fallback just in case
            };

            System.out.println("Set employee ID (" + idPrefix + "xx format): ");
            String id = scanner.nextLine().trim().toUpperCase();

            // Check if ID starts with correct prefix
            if (!id.startsWith(idPrefix)) {
                System.out.println("Invalid ID format. ID must start with '" + idPrefix + "' for " + type + ".");
                return;
            }
            
          //Normalise the id and check for duplicates
            id = id.trim().toUpperCase();
            if(employeesByID.containsKey(id)) {
            	System.out.println("Employee ID already exists. Please type unique ID next time");
            	return;
            }

            System.out.println("Enter employee name: ");
            String name = scanner.nextLine();

            System.out.println("Enter employee email: ");
            String email = scanner.nextLine();

            System.out.println("Enter employee base salary: ");
            double baseSalary = Double.parseDouble(scanner.nextLine());
            
            

            
            
            Employee emp;

            //Create employee based on type
            switch (type.toLowerCase()) { 
            //Performance Rating is set to 2.5 as avaerage between 0-5
                case "manager":
                	System.out.println("Enter manager bonus percentage (exclude % sign) : ");
                	double bonusPercentage = Double.parseDouble(scanner.nextLine());
                	emp = new Manager(id, name, email, baseSalary, 2.5, bonusPercentage);
                	break;

                case "regularemployee":
                	System.out.println("Enter Employee Allowance: ");
                    double allowance = Double.parseDouble(scanner.nextLine());                	
                    emp = new RegularEmployee(id, name, email, baseSalary, 2.5, allowance);
                    break;

                case "intern":
                	System.out.println("Enter Intern stipend: ");
                    double stipend = Double.parseDouble(scanner.nextLine());
                	emp = new Intern(id, name, email, baseSalary, 2.5, stipend);
                	break;

                default:
                System.out.println("Invalid employee type. Please try again.");
                return;
            }
            employees.add(emp); //Add employee to list
            employeesByID.put(emp.getId(), emp); //Add employee to map
            saveToFile(actionFile);
            System.out.println("Employee added and saved successfully to: " + actionFile);

        } catch(NumberFormatException e){
            System.out.println("Error: Invalid number format - " + e.getMessage());
         }         
    }
    
    
    
    
    
    
    
    
  //OPTION 3: Update employee information
  public void updateEmployee(){
        System.out.println("Enter employee ID to update: ");
        String id = scanner.nextLine().toUpperCase();
        //Get employee by ID
        Employee emp = employeesByID.get(id);

        if(emp == null){
            System.out.println("Employee not found.");
            return; //Return if employee not found
        }
        //Display current employee details
        System.out.println("Current employee details: ");
        System.out.println(emp);

        //Display update options
        System.out.println("Update Options: ");
        System.out.println("1. Name");
        System.out.println("2. Email");
        System.out.println("3. Base Salary");
        System.out.println("4. Performance Rating");
        System.out.println("5. Issue Warning");
        System.out.println("6. Issue Appreciation");
        System.out.println("7. Apply Bonus");
        System.out.println("8. Apply fine");
        System.out.println("9. Exit");
        System.out.println("Enter your choice: ");

        //Get user choice
        try{
            int options = Integer.parseInt(scanner.nextLine());
            switch(options){
                case 1: //Update name
                    System.out.println("Enter new name: ");
                    emp.setName(scanner.nextLine());
                    break;

                case 2: //Update email
                    System.out.println("Enter new email: ");
                    emp.setEmail(scanner.nextLine());
                    break;

                case 3: //Update base salary
                    System.out.println("Enter new base salary: ");
                    emp.setBaseSalary(Double.parseDouble(scanner.nextLine()));  
                    break;

                case 4: //Update performance rating
                    System.out.println("Enter new performance rating: (0.0 - 5.0) ");
                    emp.getPerformance().setPerformanceRating(Double.parseDouble(scanner.nextLine()));
                    break;

                case 5: //Issue warning
                    emp.getPerformance().addWarning();
                    System.out.println("Warning issued to employee.");
                    break;

                case 6: //Issue appreciation
                    emp.getPerformance().addAppreciationLetter();
                    System.out.println("Appreciation letter issued.");
                    break;

                case 7: //Apply bonus
                    System.out.println("Enter bonus amount: ");
                    double bonus = Double.parseDouble(scanner.nextLine());
                    emp.getSalary().applyBonus(bonus);
                    System.out.println("Bonus applied.");
                    break;

                case 8: //Apply fine
                    System.out.println("Enter fine amount: ");
                    double fine = Double.parseDouble(scanner.nextLine());
                    emp.getSalary().applyFine(fine);
                    System.out.println("Fine applied.");
                    break;

                case 9: //Exit
                    System.out.println("Exiting update employee information.");
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
                    return;
            }
            employeesByID.put(emp.getId(), emp); //Update Hashmap entry
            saveToFile(actionFile); //Save updated data to action file
            System.out.println("Employee information updated and saved to: " + actionFile);
        } catch(NumberFormatException e){
            System.out.println("Invalid number format - " + e.getMessage());
        }
    }
    

    
    
    
 // OPTION 4: DELETE EMPLOYEE
 public void deleteEmployee(){
        System.out.println("Enter employee ID: ");
        String id = scanner.nextLine().toUpperCase();
        Employee emp = getEmployeeById(id); //search by id
        // if ID is not in the database
        if (emp == null) {
            System.out.println("Employee not found");
            return;            
        }

        employees.remove(emp); //Remove from the ArrayList
        employeesByID.remove(emp.getId()); //Remove from the HashMap
        saveToFile(actionFile); //Save to Action file
        System.out.println(emp.getName() + " deleted successfully from the " + actionFile);
      
    }
    
    
    
    
	
    
  //OPTION 5: QUERY EMPLOYEE
  public void queryEmployee(){
        System.out.println("Query options: ");
        System.out.println("1. ID");
        System.out.println("2. Name");
        System.out.println("3. Performance Rating");
        System.out.println("4. Exit");        
        System.out.println("Enter your option: ");
        try{
            int option = Integer.parseInt(scanner.nextLine());
            switch (option) {

                case 1: // Query by ID
                    System.out.println("Enter ID: ");                    
                    Employee emp = getEmployeeById(scanner.nextLine().toUpperCase()); 
                    System.out.println(emp != null ? emp : "Employee ID not found.");
                    break;

                case 2: //Query by name
                    System.out.println("Enter Name: ");
                    String name = scanner.nextLine();
                    //Filter by name in the list
                    List<Employee> byName = employees.stream().filter(e -> e.getName().toLowerCase().contains(name.toLowerCase())).collect(Collectors.toList());

                    byName.forEach(System.out::println);
                    if (byName.isEmpty()) {
                        System.out.println("No employees found.");                        
                    }
                    break;

                case 3:
                System.out.println("Enter minimum performance rating (0.0-5.0)");
                double rating = Double.parseDouble(scanner.nextLine());
                //Filter by Rating
                List<Employee> byRating = employees.stream()
                    .filter(e -> e.getPerformance().getPerformanceRating() >= rating)
                    .collect(Collectors.toList());
                byRating.forEach(System.out::println);
                if (byRating.isEmpty()) {
                    System.out.println("No Employee with such ratings found !");
                }
                break;

                case 4:
                System.out.println("Exiting");
                return;

                default:
                    System.out.println("Invalid Option.");
            }
        }  catch (NumberFormatException e) {
            System.out.println("Invalid input format: " + e.getMessage());
        }
    }
    
    
    
    
    //OPTION 6 : DISPLAY DATA
    public void displayData() {

        Scanner scanner = new Scanner(System.in);
    	//Edge cases for empty dataset
    	if (employees.isEmpty()) {
    		System.out.println("No employees found in the system");
            scanner.close();
    		return;
    	}
        // Prompt for sorting
        System.out.println("Sort employees by (id, name, salary)? [enter for no sorting]");
        String sortField = scanner.nextLine().trim().toLowerCase();

        // Apply sorting if a valid field is provided
        if (!sortField.isEmpty()) {
            if (sortField.equals("id") || sortField.equals("name") || sortField.equals("salary")) {
                System.out.println("Sort in ascending order? (y/n) [y]");
                String orderInput = scanner.nextLine().trim().toLowerCase();
                boolean ascending = orderInput.isEmpty() || orderInput.equals("y");

                // Sort employees
                sortEmployees(sortField, ascending);
            } else {
                System.out.println("Invalid sort field. Displaying without sorting.");
            }
        }

    	// Display sorted employees
    	System.out.println("\n ~~~~~~~~~ ** EMPLOYEE LIST ** ~~~~~~~~~\n "); 	
    	for (Employee emp : employees) {
            System.out.println("-------------------------------------");
            System.out.println(emp);  // Using toString() method in each class
            //Keeping the Total salary hidden to maintain privacy, however can be viewed in excel file
        }

        System.out.println("\n =========== END OF LIST ============= \n ");
        scanner.close();
    }
    


//option 7: Search Employes using Binary search
    public void searchEmployee(){

        Scanner scanner = new Scanner(System.in);

    // Check for empty employee list
    if (employees == null || employees.isEmpty()) {
        System.out.println("No employees found in the system");
        return;
    }
    // Prompt for search field
    System.out.println("Search by (id, name)?");
    String field = scanner.nextLine().trim().toLowerCase();
    if (!field.equals("id") && !field.equals("name")) {
        System.out.println("Invalid field. Use 'id' or 'name'.");
        return;
    }

    // Prompt for search value
    System.out.println("Enter " + field + " to search:");
    String value = scanner.nextLine().trim();

    // Perform search
    SearchResult result = SearchResult.searchEmployees(employees, field, value);

    // Display results
    if (!result.getEmployees().isEmpty()) {
        System.out.println("\n ~~~~~~~~~ ** SEARCH RESULTS ** ~~~~~~~~~\n ");
        for (Employee emp : result.getEmployees()) {
            System.out.println("-------------------------------------");
            System.out.println("Type: " + emp.getClass().getSimpleName());
            System.out.println("ID: " + emp.getId());
            System.out.println("Name: " + emp.getName());
        }
        System.out.println("\n =========== END OF RESULTS ============= \n ");
    }

    // Display search time
    System.out.printf("Search took %.3f ms%n", result.getTimeMs());
}




  //Find Employee by ID using Hashmap, return null if not found
  public Employee getEmployeeById(String id){
     return employeesByID.get(id); //Lookup directly in Hashmap   
    }
    
    
    
    
  //Save employee data to csv file
  public void saveToFile(String fileName){
     try(BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))){
         //Define headers in new csv file
         writer.write("Type,ID,Name,Email,Base Salary,Performance Rating,Extra Benefits,Total Salary\n");
         writer.newLine();
         
         //Write Employee data
         for (Employee emp : employees){
             String type;
             String extraBenefits = "";
             if (emp instanceof Manager) {
            	 type = "Manager";
            	 extraBenefits = String.valueOf(((Manager) emp).getBonusPercentage());
             } else if(emp instanceof RegularEmployee) {
            	 type = "RegularEmployee";
            	 extraBenefits = String.valueOf(((RegularEmployee) emp).getAllownace());
             } else if(emp instanceof Intern) {
            	 type = "Intern";
            	 extraBenefits = String.valueOf(((Intern) emp).getStipend());
             } else {
            	 type = "Unknown";
             }            	 
            	
             //Write the employee data
             writer.write(String.format("%s,%s,%s,%s,%.2f,%.2f,%s,%.2f\n",
            		 type,
            		 emp.getId(),
            		 emp.getName(),
            		 emp.getEmail(),
            		 emp.getBaseSalary(),
            		 emp.getPerformance().getPerformanceRating(),
            		 extraBenefits,
            		 emp.calculateSalary()
            		 ));
         }

     } catch (IOException e){
         System.out.println("Error writing in the file: " + e.getMessage());
     }
    }
    
    

    
    // Adding sorting algorithms using Java library (Tim Sort)
    public void sortEmployees(String field, boolean ascending) {
        if (employees == null || employees.isEmpty()) {
            System.out.println("No employees to sort.");
            return;
        }

        Comparator<Employee> comparator;
        switch (field.toLowerCase()) {
            case "id":
                comparator = Comparator.comparing(Employee::getId, String.CASE_INSENSITIVE_ORDER);
                break;
            case "name":
                comparator = Comparator.comparing(Employee::getName, String.CASE_INSENSITIVE_ORDER);
                break;
            case "salary":
                comparator = Comparator.comparingDouble(Employee::getBaseSalary);
                break;
            case "rating":
                comparator = Comparator.comparingDouble(emp -> emp.getPerformance().getPerformanceRating());
                break;
            default:
                System.out.println("Invalid sort field: " + field + ". Use 'id', 'name', 'salary', or 'rating'.");
                return;
        }

        // Reverse for descending order
        if (!ascending) {
            comparator = comparator.reversed();
        }

        // Handle null values (place at end)
        comparator = Comparator.nullsLast(comparator);

        // Sort employees using Timsort
        Collections.sort(employees, comparator);
        System.out.println("Employees sorted by " + field + " (" + (ascending ? "ascending" : "descending") + ")");
    }
    
    
  //Main program to start the EMS 
    public static void main(String[] args) {
     EMS ems = new EMS();
     ems.showModeSelection();

 }
    
}

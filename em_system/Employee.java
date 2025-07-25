package em_system;
import java.util.UUID;

public abstract class Employee {
	 //Attributes
    private String id;  //Unique identifier for the employee
    private String name; //Name of the employee
    private String email; //Email of the employee
    private double baseSalary; //Base salary of the employee
    private Performance performance; //Performance of the employee
    private Salary salary; //Salary of the employee

    //Constructor to initialize the employee
    public Employee(String id,String name, String email, double baseSalary, double performanceRatings ) {
        this.id = id; 
        this.name = name;
        this.email = email;
        this.baseSalary = baseSalary;
        this.performance = new Performance(performanceRatings);
        this.salary = new Salary();
    }

    //Getters and Setters
    //ID
    public String getId() {
        return id; //return the unique ID
    }

    //Name
    public String getName() {
        return name; //return the name of the employee
    }
    
    public void setId(String id) {
    	this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
    //Email
    public String getEmail() {
        return email; //return the email of the employee
    }
    public void setEmail(String email){
        this.email = email;
    }

    //Base Salary
    public double getBaseSalary() {
        return baseSalary; //return the base salary of the employee
    }

    public void setBaseSalary(double baseSalary) {
        this.baseSalary = baseSalary; //set the base salary of the employee
        this.salary.setBaseSalary(baseSalary);
    }

    //Performance
    public Performance getPerformance() {
        return performance; //return the performance of the employee
    }

    //Salary
    public Salary getSalary() {
        return salary; //return the salary of the employee
    }

    //Abstract method to calculate the salary of the employee implemented in the subclasses
    public abstract double calculateSalary();

    //return a string representation of the employee
    @Override
    public String toString() {
        return "ID =  '" + id + '\'' +
        ", NAME = '" + name + '\'' +
        ", EMAIL = '" + email + '\'' +
        ", BASE SALARY = " + baseSalary 
        ;

    }

}

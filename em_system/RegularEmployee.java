package em_system;

public class RegularEmployee extends Employee {
	//allowance for regular employees
    private double allowance;


    //Constructor to initialize the regular employee
    public RegularEmployee(String id, String name, String email, double baseSalary, double performanceRatings, double allowance) {
        super(id, name, email, baseSalary, performanceRatings);
        this.allowance = allowance;
    }
    
    public double getAllownace(){
    	return allowance;
    }
    
    public void setAllownace(double amount) {
    	this.allowance = amount;
    	
    }
    //Calculate the salary of the regular employee
    @Override
    public double calculateSalary() {
        return getBaseSalary() + allowance;
    }

    //Override the toString method to return a string representation of the regular employee    
    @Override
    public String toString() {
        return "RegularEmployee " + super.toString() +
                ", ALLOWANCE = " + allowance 
                ;
    }

}

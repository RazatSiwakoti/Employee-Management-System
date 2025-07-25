package em_system;

public class Intern extends Employee{
	//stipend for interns
    private double stipend;

    //Constructor to initialize the intern
    public Intern(String id,String name, String email, double baseSalary, double performanceRatings, double stipend) {
        super(id,name, email, baseSalary, performanceRatings);
        this.stipend = stipend;
    }

    //Getters to return the stipend
    public double getStipend(){
        return stipend;
    }
    
    public void setStipend(double amount) {
    	this.stipend = amount;
    }

    //Calculate the salary of the intern
    @Override
    public double calculateSalary() {
        return stipend; //interns have fixed stipend, no base salary and no bonus
    }


    //Override the toString method to return a string representation of the intern
    @Override
    public String toString() {
        return "Intern " + super.toString() +
        ", STIPEND = " + stipend 
        ;
    }
	
	
}

package em_system;

public class Manager extends Employee{
	private double bonusPercentage; //Percentage of base salary as bonus

    //Constructor to initialize the manager
    public Manager(String id, String name, String email, double baseSalary, double performanceRatings, double bonusPercentage) {
        super(id, name, email, baseSalary,performanceRatings); //Call the constructor of the superclass
        this.bonusPercentage = bonusPercentage;
    }

    //Getter to return the bonus percentage
    public double getBonusPercentage() {
        return bonusPercentage;
    }
    //Calculate the salary of the manager
    @Override
    public double calculateSalary() {
        double base = getBaseSalary();
        double fine = getSalary().getFine();
        double bonus = base * (bonusPercentage / 100) - fine;
        return base + bonus;
    }

    //Override the toString method to return a string representation of the manager
    @Override
    public String toString() {
        return "Manager " + super.toString() +
                ", BONUS% = " + bonusPercentage + "%" 
                ;
    }

}

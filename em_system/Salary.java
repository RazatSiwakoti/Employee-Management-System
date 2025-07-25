package em_system;

public class Salary {
	//base salary, bonus, and fine
    private double baseSalary;
    private double bonus;
    private double fine;

    
    
    //constructor to initialize the salary
    public Salary() {
        this.baseSalary = 0;
        this.bonus = 0;
        this.fine = 0;
    }
    
  //Overloading a constructor
    public Salary(double baseSalary) {
        this.baseSalary = baseSalary;
        this.bonus = 0;
        this.fine = 0.0;
    }
    
    //getters
    //Base Salary
    public double getBaseSalary() {
        return baseSalary;
    }

    //setters for the salary
    public void setBaseSalary(double baseSalary) {
        this.baseSalary = baseSalary;
    }

    //Bonus
    public double getBonus() {
        return bonus;
    }

    //apply a bonus to the salary
    public void applyBonus(double amount) {
        bonus += amount;
    }

    //Fine
    public double getFine() {
        return fine;
    }

    //apply a fine to the salary
    public void applyFine(double amount) {
        fine += amount;
    }

    //return a string representation of the salary
    @Override
    public String toString() {
        return "Salary{" +
                "baseSalary=" + baseSalary +
                ", bonus=" + bonus +
                ", fine=" + fine +
                '}';
    }
}

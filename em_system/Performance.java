package em_system;

public class Performance {
	 private double performanceRating;
	    private int warnings;
	    private int appreciationLetters;
	  
	    


	    // constructor
	    public Performance(double performanceRating) {
	        this.performanceRating = performanceRating;
	        this.warnings = 0;
	        this.appreciationLetters = 0;
	    }

	    //Getters 
	    //Performance Rating
	    public double getPerformanceRating() {
	        return performanceRating;
	    }
	    //Warnings
	    public int getWarnings() {
	        return warnings;
	    }
	    //Appreciation Letters
	    public int getAppreciationLetters() {
	        return appreciationLetters;
	    }

	    //Setters
	    //Performance Rating
	    public void setPerformanceRating(double performanceRating) {
	        this.performanceRating = performanceRating;
	    }

	    //Add a warning, increases the count of warnings
	   public void addWarning(){
	    warnings++;
	   }

	   //Add an appreciation letter, increases the count of appreciation letters
	   public void addAppreciationLetter(){
	    appreciationLetters++;
	   }

	   @Override
	   public String toString() {
	    return "Performance{" +
	            "performanceRating=" + performanceRating +
	            ", warnings=" + warnings +
	            ", appreciationLetters=" + appreciationLetters +
	            '}';
	   }

	    

}

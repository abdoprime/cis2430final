import java.io.FileWriter;
import java.util.ArrayList;

public abstract class Degree {
    String title;
    ArrayList<String> listOfRequiredCourseCodes;  //this should probably be an ArrayList<Course>

    public Degree() {
        this.title = null;
        this.listOfRequiredCourseCodes = new ArrayList<>();
    }

    public void saveState() {
        try (FileWriter PoSData = new FileWriter("BootstrapDegrees.txt", true)) {
            String fileLine = this.getDegreeTitle();
            fileLine += ", ";
            for (Course reqCourse : this.getRequiredCourses()) {
                fileLine += reqCourse.getCourseCode();
                fileLine += ",";
            }
            if (fileLine.substring(fileLine.length() - 1).equals(",")) {
                fileLine = fileLine.substring(0, fileLine.length() - 1);
            }
            fileLine += "?";
            PoSData.write(fileLine);
            PoSData.flush();
            PoSData.close();
            System.out.println("Saved Degree information.");
        } catch (Exception e) {
            System.out.println("Failed to successfully save state for Plan Of Study.");
        }
    }

    public void setDegreeTitle(String title) {
        if (title != null && !title.isEmpty())
            this.title = title;
    }
    public String getDegreeTitle() { return this.title; }

    public void setRequiredCourses(ArrayList<String> listOfRequiredCourseCodes) {
        if (listOfRequiredCourseCodes != null && !listOfRequiredCourseCodes.isEmpty())
            this.listOfRequiredCourseCodes = listOfRequiredCourseCodes;
    }


     public ArrayList<Course> getRequiredCourses() 
        { 
 //needs to be implemented.
        	/***algorithm
				for each course code in listOfRequiredCourseCodes:
				     find the course in the course catalog
				     add the found course to an arraylist
			     return the arraylist
	
        	**/

            return new ArrayList<Course>(); 
        } 

    public abstract boolean meetsRequirements(PlanOfStudy thePlan);
    public abstract double numberOfCreditsRemaining(PlanOfStudy thePlan);
    public abstract ArrayList<Course> remainingRequiredCourses(PlanOfStudy thePlan);

    @Override
    public abstract String toString();
    @Override
    public abstract boolean equals(Object o);
    @Override
    public abstract int hashCode();

}

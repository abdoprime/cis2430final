
/**
 * A SEng Degree
 *
 * @author Aron Latinjak
 * @version 10/24/18
 */
import java.util.*;

//Class and all methods written by Aron
public class SEng extends HonoursDegree
{
    final static double creditsRequiredCourses = 10.75; //On required list
    final static double creditsAtAbove3000 = 1.5; //Not on required list but are CIS and 3000/4000 
    final static double creditsFromElectives = 8.25; //Any extra courses, all must sum to 20

    public SEng(String code, CourseCatalog cat)
    {
        super();
        this.setDegreeTitle("Bachelor of Software Engineering");
    }
    /**
    * Checks pos to see if student meets req of degree ~by Aron
    *
    * @param a planofstudy
    * @return true or false
    */
    public boolean meetsRequirements(PlanOfStudy thePlan)
    {
        ArrayList<Course> c = new ArrayList<>();
        ArrayList<Course> req = new ArrayList<>();
        HashMap<String, Course> courseList = thePlan.getCourseList();  
        double creditsRequired = 0.0;
        double credits3000 = 0.0;
        double creditsElectives = 0.0;
        
        c.addAll(courseList.values());
        req = this.getRequiredCourses();
        
        for(Course course:c)
        {
            if(req.contains(course) == true && course.getCourseStatus() == "Complete")
            {
                creditsRequired = creditsRequired + course.getCourseCredit();
                c.remove(course); //If the course is in the required list, remove it to simplify search later on
            }  
        }
        if(creditsRequired < creditsRequiredCourses)
        {
            return(false);
        }
        
        for(Course course:c)
        {
            if(course.getCourseType(course.getCourseCode()) == "CIS" && course.getCourseLevel(course.getCourseCode()) >= 3000 && course.getCourseStatus() == "Complete")
            {
                credits3000 = credits3000 + course.getCourseCredit();
                c.remove(course); //Shrink list even more
            }          
        }
        if(credits3000 < creditsAtAbove3000)
        {
            return(false);
        }
        
        for(Course course:c)
        {
            if(req.contains(course) == false && course.getCourseStatus() == "Complete")
            {
                creditsElectives = creditsElectives + course.getCourseCredit();
            }
        }
        if(creditsElectives < creditsFromElectives)
        {
            return(false);
        }

        return(true);
    }
    /**
    * Checks pos to see credits not completed ~by Aron
    *
    * @param a planofstudy
    * @return double of credits
    */
    public double numberOfCreditsRemaining(PlanOfStudy thePlan)
    {
        ArrayList<Course> c = new ArrayList<>();
        ArrayList<Course> req = new ArrayList<>();
        c = remainingRequiredCourses(thePlan);
        req = this.getRequiredCourses();
        double creditSum;
        
        double creditsRequired = 0.0;
        double credits3000 = 0.0;
        double creditsElectives = 0.0;
        
        for(Course course:c)
        {
            if(req.contains(course) == true && course.getCourseStatus() == "Complete")
            {
                creditsRequired = creditsRequired + course.getCourseCredit();
                c.remove(course); //If the course is in the required list, remove it to simplify search later on
            }  
        }
        
        for(Course course:c)
        {
            if(course.getCourseType(course.getCourseCode()) == "CIS" && course.getCourseLevel(course.getCourseCode()) >= 3000 && course.getCourseStatus() == "Complete")
            {
                credits3000 = credits3000 + course.getCourseCredit();
                c.remove(course); //Shrink list even more
            }          
        }
        
        for(Course course:c)
        {
            if(req.contains(course) == false && course.getCourseStatus() == "Complete")
            {
                creditsElectives = creditsElectives + course.getCourseCredit();
            }
        }
        
        creditSum = creditsRequired + credits3000 + creditsElectives;
        
        return(numCreditsRequired - creditSum);
    }
    /**
    * Checks pos to get credits remaining ~by Aron
    *
    * @param a planofstudy
    * @return an arraylist of the remaining required courses
    */
    public ArrayList<Course> remainingRequiredCourses(PlanOfStudy thePlan)
    {
        ArrayList<Course> c = new ArrayList<>();
        HashMap<String, Course> courseList = thePlan.getCourseList();    
        c.addAll(courseList.values());
        
        for(Course course:c)
        {
            if(course.getCourseStatus() == "Complete")
            {
                c.remove(course);
            }
        }
        
        return(c);
    }
        public int hashCode() 
        {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(getDegreeTitle());
        hash = 41 * hash + Objects.hashCode(this.listOfRequiredCourseCodes);
        return hash;
        }
}

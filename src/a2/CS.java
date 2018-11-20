
/**
 * A CS Degree
 *
 * @author Aron Latinjak
 * @version 10/24/18
 */
import java.util.*;

//Class and all methods written by Aron
public class CS extends HonoursDegree
{
    /*These were created before finding out that courses needed to keep track of electives/minors
    these values solved that problem*/
    final static double creditsRequiredCourses = 8.75; //On required list
    final static double creditsAtAbove3000 = 3.0; //Not on required list but are CIS and 3000/4000 
    final static double creditsFromElectives = 8.25; //Any extra courses, all must sum to 20
    /**
    * constructor to make cs object ~by Aron
    *
    */
    public CS()
    {
        super();
        this.setDegreeTitle("Bachelor of Computer Science");  //Set the title
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
        
        c.addAll(courseList.values());  //Transfer courses from hashmap to arraylist
        req = this.getRequiredCourses();
        
        for(Course course:c)
        {
            if(req.contains(course) == true && course.getCourseStatus() == "Complete")
            {
                creditsRequired = creditsRequired + course.getCourseCredit();
                c.remove(course); //If the course is in the required list, remove it to simplify search later on
            }  
        }
        if(creditsRequired < creditsRequiredCourses)    //Not enough credits from req list
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

        return(true); //Return true if checks are completed
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
        
        creditSum = creditsRequired + credits3000 + creditsElectives; //Sum the total of credits completed
        
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

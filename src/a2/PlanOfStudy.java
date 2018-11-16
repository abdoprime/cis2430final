/*  The PlanOfStudy class should use the FileReader and
    FileWriter classes from java.io for reading and writing.
*/
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;


public class PlanOfStudy {

    private Degree deg;
    private ArrayList<Course> courses;
    private Student student;
    private CourseCatalog catalogCopy;

    public PlanOfStudy() {
        this.deg = null;
        this.courses = new ArrayList<>();
        this.student = new Student();
        this.catalogCopy = new CourseCatalog();
    }

    public void setDegreeProgram(Degree deg) {
        this.deg = deg;
    }

    public void setCourses(ArrayList<Course> courses) { this.courses = courses; }

    public void setStudent(Student student) { this.student = student; }

    public void setCatalog(CourseCatalog catalog) { this.catalogCopy = catalog; }

    public void setCourseStatus(String courseCode, String semester, String courseStatus) {
        for (Course c : this.courses) {
            if (c.getCourseCode() != null && c.getSemesterTaken() != null && c.getCourseCode().equals(courseCode) && c.getSemesterTaken().equals(semester)) {
                c.setCourseStatus(courseStatus);
            }
        }
    }

    public void setCourseGrade(String courseCode, String semester, String grade) {
        for (Course c : this.courses) {
            if (c.getCourseCode() != null && c.getSemesterTaken() != null && c.getCourseCode().equals(courseCode) && c.getSemesterTaken().equals(semester)) {
                c.setCourseGrade(grade);
                System.out.println("Grade updated.");
                return;
            }
        }
        System.out.println("Grade could not be updated.");
    }

    public Degree getDegreeProgram() {
        return this.deg;
    }

    public Student getStudent() { return  this.student; }

    public ArrayList<Course> getCourses() { return this.courses; }

    public CourseCatalog getCatalog() {
        return this.catalogCopy;
    }

    public Course getCourse(String courseCode, String semester) {
        for (Course c : this.courses) {
            if (c.getCourseCode() != null && c.getSemesterTaken() != null && c.getCourseCode().equals(courseCode) && c.getSemesterTaken().equals(semester)) {
                return c;
            }
        }
        return null;
    }

    public Course findCourse(String courseCode) {
        Course found;
        if ((found = this.catalogCopy.findCourse(courseCode)) != null) {
            return found;
        }
        return null;
    }

    public void addCourse(String courseCode, String semester) {
        boolean alreadyAdded = false;
        for (Course code : this.catalogCopy.getCourseCatalog()) {
            if (courseCode.equals(code.getCourseCode())) {
                for (Course c : this.courses) {
                    if (c.getCourseCode().equals(courseCode) && c.getSemesterTaken().equals(semester)) {
                        alreadyAdded = true;
                        System.out.println("Already in the Plan of Study.");
                    }
                }
            }
        }
        if (!alreadyAdded) {
            Course found = isValidCourse(courseCode);
            if (found != null) {
                Course toAdd = new Course(found);
                toAdd.setSemesterTaken(semester);
                toAdd.setCourseStatus("Planned");
                System.out.println(toAdd.toString());
                this.courses.add(toAdd);
                System.out.println("Course added.");
            } else {
                System.out.println("No such course in the catalog.");
            }
        }
    }

    private Course isValidCourse(String courseCode) {
        Course found = this.catalogCopy.findCourse(courseCode);
        if (found != null) {
            return found;
        }
        return null;
    }

    public void removeCourse(String courseCode, String semester) {
        for (Course c : this.courses) {
            if (c.getCourseCode() != null && c.getSemesterTaken() != null && c.getCourseCode().equals(courseCode) && c.getSemesterTaken().equals(semester)) {
                this.courses.remove(c);
                return;
            }
        }
    }

    public double totalCredits() {
        double totalCredits = 0.0;
        for (Course c : this.courses) {
            if (c.getCourseStatus() != null && c.getCourseStatus().equals("Completed")) {
                totalCredits += c.getCourseCredit();
            }
        }
        return totalCredits;
    }

    public void saveState() {
        try (FileWriter PoSData = new FileWriter("PlanOfStudyData.txt")) {
            String fileLine;

            fileLine = this.student.getFirstName();
            fileLine += ",";
            fileLine += this.student.getLastName();
            fileLine += ",";
            fileLine += this.student.getStudentNumber();
            fileLine += "?\n";
            PoSData.write(fileLine);

            fileLine = this.deg.getDegreeTitle();
            fileLine += ", ";
            for (Course reqCourse : this.deg.getRequiredCourses()) {
                fileLine += reqCourse.getCourseCode();
                fileLine += ",";
            }
            if (fileLine.substring(fileLine.length() - 1).equals(",")) {
                fileLine = fileLine.substring(0, fileLine.length() - 1);
            }
            fileLine += "?";
            PoSData.write(fileLine);

            for (Course c : this.courses) {
                fileLine = c.getCourseCode();
                fileLine += ",";
                fileLine += c.getCourseStatus();
                fileLine += ",";
                if (c.getCourseGrade() != null) {
                    fileLine += c.getCourseGrade();
                }
                fileLine += ",";
                fileLine += c.getSemesterTaken();
                fileLine += "?\n";
                PoSData.write(fileLine);
            }
            PoSData.flush();
            PoSData.close();
            System.out.println("State saved.");
        } catch (Exception e) {
            System.out.println("Failed to successfully save state for Plan Of Study.");
        }
    }

    public void importData(String filename) throws IOException {
        FileReader PoSData = new FileReader(filename);
        int filePointer;
        int index = 0;
        String fileLine = "";
        String[] fileContents;

        do {
            filePointer = PoSData.read();
            if (!String.valueOf((char) filePointer).matches("\n") && !String.valueOf((char) filePointer).matches("\\?") && filePointer != -1) {
                fileLine += (char) filePointer;
            } else if (String.valueOf((char) filePointer).matches("\n") || String.valueOf((char) filePointer).matches("\\?") || filePointer == -1) {
                if (fileLine.length() < 3) {
                    continue;
                }
                switch (index) {
                case 0:
                    fileContents = fileLine.split(",", 3);
                    this.student = new Student();
                    this.student.setFirstName(fileContents[0]);
                    this.student.setLastName(fileContents[1]);
                    this.student.setStudentNumber(Integer.parseInt(fileContents[2]));
                    index++;
                    fileLine = "";
                    break;
                case 1:
                    fileContents = fileLine.split(",");
                    if (fileContents.length > 1) {
                        fileContents[1] = fileContents[1].substring(1);
                    }
                    ArrayList<String> reqArrayList = new ArrayList<>(Arrays.asList(fileContents).subList(1, fileContents.length));
                    switch (fileContents[0]) {
                    case "BCG":
                        this.deg = new BCG();
                        this.deg.setDegreeTitle(fileContents[0]);
                        this.deg.setRequiredCourses(reqArrayList);
                        break;
                    // case "CS":
                    //     this.deg = new CS();
                    //     this.deg.setDegreeTitle(fileContents[0]);
                    //     this.deg.setRequiredCourses(reqArrayList);
                    //     break;
                    // case "SEng":
                    //     this.deg = new SEng();
                    //     this.deg.setDegreeTitle(fileContents[0]);
                    //     this.deg.setRequiredCourses(reqArrayList);
                    //     break;
                    default:
                        System.out.println("Invalid Major: " + fileContents[0]);
                        break;
                    }
                    index++;
                    fileLine = "";
                    break;
                default:
                    fileContents = fileLine.split(",", 4);
                    Course fileCourse = this.catalogCopy.findCourse(fileContents[0]);
                    fileCourse.setCourseStatus(fileContents[1]);
                    if (!("".equals(fileContents[2]))) {
                        fileCourse.setCourseGrade(fileContents[2]);
                    }
                    fileCourse.setSemesterTaken(fileContents[3]);
                    this.courses.add(fileCourse);
                    fileLine = "";
                    break;
                }
            }
        } while (filePointer != -1);
        PoSData.close();
    }

    @Override
    public String toString() {
        String toString = "";
        if (this.deg != null) {
            toString = this.deg.toString();
        }
        if (this.courses != null) {
            toString += this.courses.toString();
        }
        if (this.student != null) {
            toString += this.student.toString();
        }
        return toString;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof PlanOfStudy)) {
            return false;
        }

        PlanOfStudy tempPlan = (PlanOfStudy) o;
        if (!(this.deg.equals(tempPlan.deg))) {
            return false;
        }
        if (!(this.courses.equals(tempPlan.courses))) {
            return false;
        }
        return this.student.equals(tempPlan.student);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.deg);
        hash = 97 * hash + Objects.hashCode(this.courses);
        hash = 97 * hash + Objects.hashCode(this.student);
        return hash;
    }

}

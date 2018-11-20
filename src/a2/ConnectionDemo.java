import java.util.ArrayList;

public class ConnectionDemo {
	private String pwd;
	private String user;
	
	public ConnectionDemo(){
		pwd = DBDetails.password;
		user = DBDetails.username;
		testConnection();
	}
	
	public void testConnection(){
		MyConnection c = new MyConnection(user, pwd);
		
		PrepStudentScript initTables = new PrepStudentScript(); //called to initialize our tables
		
		//test clear DBStudent method
		c.deleteAllSavedStudent();

		//test addCourse
		c.addCourse("CIS*1500", "0.5", "Intro to Computer Science", "");
		c.addCourse("CIS*2500", "0.5", "Intermediate Programming", "CIS*1500");
		c.addCourse("CIS*2430", "0.5", "Object Oriented Programming RULES", "CIS*1500");
		c.addCourse("CIS*9999", "0.75", "Oopsies", "");

		//test deleteCourse
		c.deleteCourse("CIS*9999");

		//test getAllCourses
		ArrayList<String> courses = c.getAllCourses();
		for (String course : courses){
			System.out.println(course);
		}
		
		//test getCourse for CIS*1500
		String course = c.findCourse("CIS*1500");
		System.out.println(course);

		//test save student
		ArrayList<String> cl = new ArrayList<String>();
		cl.add("CIS*1500,F13,89,Completed");
		cl.add("CIS*2500,F14,67,Completed");
		DBStudent s = new DBStudent("123123","Matt","MSC",cl);

		c.saveStudent(s);

		//test load student
		DBStudent loadedS = c.loadStudent("123123","Matt");

		System.out.println(loadedS.getId());
		System.out.println(loadedS.getName());
		System.out.println(loadedS.getDegree());
		cl = loadedS.getCourses();
		System.out.println(loadedS.getCourses());
		for (String cInfo : cl){
			//lets get each course from our course list!
			System.out.println(c.findCourse(cInfo.split(",")[0]));
		}

		//test clear Courses
		c.deleteAllCourses();

		courses = c.getAllCourses();
		for (String course2 : courses){
			System.out.println(course);
		}

	}
	
	public static void main (String[] args){
		ConnectionDemo p = new ConnectionDemo();
	}

}

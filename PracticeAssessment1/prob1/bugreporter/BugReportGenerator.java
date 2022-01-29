package lesson10.labs.prob1.bugreporter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;
import lesson10.labs.prob1.classfinder.ClassFinder;

/**
 * This class scans the package lesson10.labs.prob2.javapackage
 * for classes with annotation @BugReport. It then generates a bug report
 * bugreport.txt, formatted like this:
 * 
 * Joe Smith
 *     reportedBy:
 *     classname:
 *     description:
 *     severity:
 *     
 *     reportedBy:
 *     classname:
 *     description:
 *     severity:
 *     
 * Tom Jones
 *     reportedBy:
 *     classname:
 *     description:
 *     severity:
 *     
 *     reportedBy:
 *     classname:
 *     description:
 *     severity:
 * 
 *
 */
public class BugReportGenerator {
	private static final Logger LOG = Logger.getLogger(BugReportGenerator.class.getName());
	private static final String PACKAGE_TO_SCAN = "lesson10.labs.prob1.javapackage";
	private static final String REPORT_NAME = "bug_report.txt";
	private static final String REPORTED_BY = "reportedBy: ";
	private static final String CLASS_NAME = "classname: ";
	private static final String DESCRIPTION = "description: ";
	private static final String SEVERITY = "severity: ";
	public void reportGenerator() {
		List<Class<?>> classes = ClassFinder.find(PACKAGE_TO_SCAN);
		
		//sample code for reading annotations -- you will need to change
		//this quite a bit to solve the problem
		//Sample code below obtains a list of names of developers assigned to bugs

		HashMap<String, StringBuilder> summary=new HashMap<>();
		
		for(Class<?> cl : classes) {
			if(cl.isAnnotationPresent(BugReport.class)) {
				BugReport annotation = (BugReport)cl.getAnnotation(BugReport.class);
				
				String assignedTo = annotation.assignedTo();
				
				StringBuilder output=new StringBuilder();
				output.append("  "+REPORTED_BY+annotation.reportedBy()+"\n");
				output.append("  "+CLASS_NAME+cl.getName()+"\n");
				output.append("  "+DESCRIPTION+annotation.description()+"\n");
				output.append("  "+SEVERITY+annotation.severity()+"\n");
				
				if (summary.containsKey(assignedTo)) {
					summary.get(assignedTo).append("\n"+output);
				}
				else {
					summary.put(assignedTo, output);
				}
			}
		}

//		String path = System.getProperty("user.dir") + "\\src\\"+PACKAGE_TO_SCAN.replace(".", "\\") +"\\"+REPORT_NAME;
		String path = "./src/"+PACKAGE_TO_SCAN.replace(".", "/") +"/"+REPORT_NAME;
//		System.out.println(path);
		try(PrintWriter myWriter = new PrintWriter(new FileWriter(path))) {
			summary.forEach((key, value) -> myWriter.println(key +"\n"+value));
		} catch (IOException e) {
			LOG.warning("Main exception: " + e.getMessage());
			List<Throwable> suppressed = Arrays.asList(e.getSuppressed());	
			suppressed.forEach(except -> LOG.warning("Suppressed message: " 
			                                         + except.getMessage()));
		}
	}
	
	
}

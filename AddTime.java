package timespent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class AddTime {
	private String Id;
	private String Task;
	private double Time;
	
	public AddTime(String id, String task,double time) {
		this.Id=id;
		this.Task= task;
		this.Time= time;
		
	}
	
	public void AddTimeSpent() throws FileNotFoundException {
		String FoundFile = "";
		FileReader reader = null;
		File files = new File("C:\\example");
		File[] IdFiles = files.listFiles(); 
		for (File file: IdFiles) {
			if (file.getName().equals(Id)) {
				FoundFile = file.getName();
				
			}		
	}
		File OpenFile = new File(FoundFile);
		Scanner scan = new Scanner(OpenFile);
		while (scan.hasNext()) {
			String a = scan.next();
			double b = scan.nextDouble();
		if(a.equals(Task)) {
			b = Time;
		}else {
			PrintWriter writer = new PrintWriter(FoundFile);
			writer.print(Task);
			writer.print(Time);
		}
			
		}
		}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getTask() {
		return Task;
	}

	public void setTask(String task) {
		Task = task;
	}

	public double getTime() {
		return Time;
	}

	public void setTime(double time) {
		Time = time;
	}
	
}

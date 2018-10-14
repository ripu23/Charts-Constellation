package chartconstellation.app;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import chartconstellation.app.entities.Chart;

public class Test {

	public static void main(String[] args) {
		Test test = new Test();
		test.run();

	}
	
	private void run() {
		ObjectMapper mapper =  new ObjectMapper();
		try {
			Chart chart = mapper.readValue(new File("/Users/ripu/Documents/ASU/STUDY/DV - 578/Project/chartconstellation/Data_Images/ArunSankar1.json"), Chart.class);
			System.out.println(chart);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

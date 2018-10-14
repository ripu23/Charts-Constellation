package chartconstellation.app;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.ObjectMapper;

import chartconstellation.app.entities.Chart;

public class Test {

	public static void main(String[] args) {
		Test test = new Test();
		test.run();

	}

	private void run() {
		ObjectMapper mapper = new ObjectMapper();

		String path = "/Users/ripu/Documents/projects/Workspace_DV/chartconstellation/src/main/resources/Data_Images";
		try {
			Stream<Path> paths = Files.walk(Paths.get(path));
			paths.forEach(filePath -> {
				if (Files.isRegularFile(filePath)) {

					try {
						
							System.out.println(filePath.getFileName());
							if(filePath.getFileName().toString().contains("json")) {
								Chart chart = mapper.readValue(filePath.toFile(), Chart.class);
								System.out.println(chart);
							}
						
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

}

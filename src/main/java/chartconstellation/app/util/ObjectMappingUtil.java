package chartconstellation.app.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ObjectMappingUtil {

	private static ObjectMapper mapper = new ObjectMapper();

	@SuppressWarnings("unchecked")
	public void convertToObject(String path, Class classType, List list) {
		try {
			Stream<Path> paths = Files.walk(Paths.get(path));
			paths.forEach(filePath -> {
				if (Files.isRegularFile(filePath)) {

					try {
						if (filePath.getFileName().toString().contains("json")) {
							System.out.println(filePath.getFileName());
							Object chart = mapper.readValue(filePath.toFile(), classType);
							list.add(chart);
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

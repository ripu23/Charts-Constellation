package chartconstellation.app.engine;

import com.mongodb.DBObject;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Component
public class DocumentUtil {

    public String readFile(String filename) {
        String result = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            result = sb.toString();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<DBObject> convertToDBObjectList(String path) {
        List<DBObject> list = new ArrayList<>();
        try {

            Stream<Path> paths = Files.walk(Paths.get(path));
            paths.forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {


            if (filePath.getFileName().toString().contains("json")) {

                String jsonData = readFile(filePath.toAbsolutePath().toString());
                DBObject dbo = (DBObject) com.mongodb.util.JSON.parse(jsonData);
                list.add(dbo);
           }


                }
            });
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return list;
    }

}

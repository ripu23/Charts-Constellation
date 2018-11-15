package chartconstellation.app.Controllers;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/images")
public class ImageController {


    @RequestMapping(value = "/sid", method = RequestMethod.GET,
            produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getImage() throws IOException {

        ClassPathResource imgFile = new ClassPathResource("Users/manojyaramsetty/chartconstellation/src/main/resources/Data_Images/ArunSankar1.png");
        byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(bytes);
    }

}

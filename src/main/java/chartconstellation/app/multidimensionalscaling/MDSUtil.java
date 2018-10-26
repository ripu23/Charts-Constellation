package chartconstellation.app.multidimensionalscaling;

import mdsj.MDSJ;
import org.springframework.stereotype.Component;

@Component
public class MDSUtil {

    public double[][] classicalScaling(double[][] input) {
        double[][] output=MDSJ.classicalScaling(input);
        return output;
    }
}

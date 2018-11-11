package chartconstellation.app.multidimensionalscaling;

import org.springframework.stereotype.Component;

import mdsj.MDSJ;

@Component
public class MDSUtil {

    public double[][] classicalScaling(double[][] input) {
        double[][] output=MDSJ.classicalScaling(input);
        return output;
    }
}

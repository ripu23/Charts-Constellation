package chartconstellation.app.multidimensionalscaling;

import mdsj.MDSJ;

public class MDSUtil {

    public double[][] clasicalScaling(double[][] input) {
        double[][] output=MDSJ.classicalScaling(input);
        return output;
    }
}

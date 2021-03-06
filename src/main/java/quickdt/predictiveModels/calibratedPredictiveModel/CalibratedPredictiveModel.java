package quickdt.predictiveModels.calibratedPredictiveModel;

import com.google.common.base.Preconditions;
import quickdt.data.Attributes;
import quickdt.predictiveModels.PredictiveModel;

import java.io.PrintStream;
import java.io.Serializable;


/**
 * Created by alexanderhawk on 3/10/14.
 */

/*
  This class uses the Pool-adjacent violators algorithm to calibrate the probabilities returned by a random forest of probability estimation trees.
*/
public class CalibratedPredictiveModel implements PredictiveModel {
    private static final long serialVersionUID = 8291739965981425742L;
    public Calibrator calibrator;
    public PredictiveModel predictiveModel;
    int binsInCalibrator = 20;

    public CalibratedPredictiveModel (PredictiveModel predictiveModel, Calibrator calibrator) {
        Preconditions.checkArgument(!(predictiveModel instanceof CalibratedPredictiveModel));
        this.predictiveModel = predictiveModel;
        this.calibrator = calibrator;
    }

    public double getProbability(Attributes attributes, Serializable classification) {
        double rawProbability = predictiveModel.getProbability(attributes, classification);
        double probability = calibrator.correct(rawProbability);
        return probability;
    }


    public void dump(PrintStream printStream) {
        predictiveModel.dump(printStream);
    }

    public Serializable getClassificationByMaxProb(Attributes attributes) {
        return predictiveModel.getClassificationByMaxProb(attributes);
    }
}

package org.dad.muth.potato.myo;

import com.thalmic.myo.AbstractDeviceListener;
import com.thalmic.myo.Myo;
import com.thalmic.myo.Quaternion;
import org.dad.muth.potato.GyroPlotter;
import org.dad.muth.potato.javafx.controller.GyroPlotterController;

/**
 * Created by Mayank on 10/10/2016.
 */
public class MyoListener extends AbstractDeviceListener {
    long intervalCount = 0;
    @Override
    public void onOrientationData(Myo myo, long timestamp, Quaternion rotation) {
        super.onOrientationData(myo, timestamp, rotation);
        GyroPlotter.gyroPlotterController.addGyroData(intervalCount++,rotation);

    }

}

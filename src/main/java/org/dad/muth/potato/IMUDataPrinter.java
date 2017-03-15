package org.dad.muth.potato;

import com.thalmic.myo.DeviceListener;
import com.thalmic.myo.Hub;
import com.thalmic.myo.Myo;
import com.thalmic.myo.example.DataCollector;

/**
 * Created by Mayank on 10/5/2016.
 */
public class IMUDataPrinter  {
    public static void main(String[] args){
        try {
            Hub hub = new Hub("org.dad.hello-myo");

            System.out.println("Attempting to find a Myo...");
            Myo myo = hub.waitForMyo(10000);

            if (myo == null) {
                throw new RuntimeException("Unable to find a Myo!");
            }

            System.out.println("Connected to a Myo armband!");
            DeviceListener dataCollector = new DataCollector();
            hub.addListener(dataCollector);

            while (true) {
                hub.run(1000 / 500);
                System.out.print(dataCollector);
            }
        } catch (Exception e) {
            System.err.println("Error: ");
            e.printStackTrace();
            System.exit(1);
        }
    }
}

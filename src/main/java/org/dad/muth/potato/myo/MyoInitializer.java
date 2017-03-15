package org.dad.muth.potato.myo;

import com.thalmic.myo.DeviceListener;
import com.thalmic.myo.Hub;
import com.thalmic.myo.Myo;
import com.thalmic.myo.enums.VibrationType;

/**
 * Created by Mayank on 10/10/2016.
 */
public class MyoInitializer extends Thread{
    Hub hub;
    int duration_ms;
    public MyoInitializer(int duration_ms){
        this.duration_ms = duration_ms;
    }
    public void init(){
        try {
            hub = new Hub("org.dad.muth.potato.myo");

            System.out.println("Attempting to find a Myo...");
            Myo myo = hub.waitForMyo(10000);

            if (myo == null) {
                throw new RuntimeException("Unable to find a Myo!");
            }

            System.out.println("Connected to a Myo armband! :  Felt the vibration? :P " );
            myo.vibrate(VibrationType.VIBRATION_MEDIUM);
            registerListeners();
            refreshData();
        } catch (Exception e) {
            System.err.println("Error: ");
            e.printStackTrace();
            //System.exit(1);
        }
    }

    private void registerListeners() {
        DeviceListener myoListener = new MyoListener();
        hub.addListener(myoListener);
    }
    // excites callbacks?
    private void refreshData(){
        hub.run(duration_ms);
        //System.out.print("Connected!!");
    }

    @Override
    public void run() {
        while(true) {
            refreshData();
            //hub.run(duration_ms);
            try {
                sleep(duration_ms);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

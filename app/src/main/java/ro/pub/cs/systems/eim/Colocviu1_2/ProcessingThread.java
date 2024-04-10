package ro.pub.cs.systems.eim.Colocviu1_2;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Date;
import java.util.Random;

public class ProcessingThread extends Thread{

    private Context context = null;
    private boolean isRunning = true;
    private int sum;

    public ProcessingThread(Context context, int sumParam) {
        this.context = context;
        sum = sumParam;
    }

    @Override
    public void run() {
        while (isRunning) {
            Log.d("debug", "Thread is running" + sum);
            sendMessage();
            sleep();
        }
    }

    private void sendMessage() {
        Intent intent = new Intent();
        intent.setAction(Constants.Action_Type);
        intent.putExtra(Constants.Broadcast, new Date(System.currentTimeMillis()) + " " + sum);
        context.sendBroadcast(intent);
    }

    private void sleep() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    public void stopThread() {
        isRunning = false;
    }
}


package fr.eni.lokacar.lokacar.handler;

import android.os.Handler;
import android.os.Message;

public class ActivityHandler extends Handler {

    private ActivityMessage activity;

    public ActivityHandler(ActivityMessage activity) {
        this.activity = activity;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);

        switch (msg.what){
            case 1 :
                    activity.onStartMessage();
                break;
            case 2 :
                    activity.onProgressMessage(msg.arg1);
                break;
            case 3 :
                    activity.onEndMessage();
                break;
        }

    }
}

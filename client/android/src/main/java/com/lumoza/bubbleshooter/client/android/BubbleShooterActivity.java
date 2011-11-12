package com.lumoza.bubbleshooter.client.android;

import com.lumoza.bubbleshooter.client.core.BubbleShooter;
import playn.android.GameActivity;
import playn.core.PlayN;

public class BubbleShooterActivity extends GameActivity {

    @Override
    public void main() {
        platform().assetManager().setPathPrefix("com/lumoza/bubbleshooter/client/resources");
        PlayN.run(new BubbleShooter());
    }
}

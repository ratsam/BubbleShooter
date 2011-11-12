package com.lumoza.bubbleshooter.client.flash;

import com.lumoza.bubbleshooter.client.core.BubbleShooter;
import playn.core.PlayN;
import playn.flash.FlashGame;
import playn.flash.FlashPlatform;

public class BubbleShooterFlash extends FlashGame {

    @Override
    public void start() {
        FlashPlatform platform = FlashPlatform.register();
        platform.assetManager().setPathPrefix("clientflash/");
        PlayN.run(new BubbleShooter());
    }
}

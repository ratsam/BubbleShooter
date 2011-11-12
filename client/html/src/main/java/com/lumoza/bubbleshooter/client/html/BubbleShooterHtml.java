package com.lumoza.bubbleshooter.client.html;

import com.lumoza.bubbleshooter.client.core.BubbleShooter;
import playn.core.PlayN;
import playn.html.HtmlGame;
import playn.html.HtmlPlatform;

public class BubbleShooterHtml extends HtmlGame {

    @Override
    public void start() {
        HtmlPlatform platform = HtmlPlatform.register();
        platform.assetManager().setPathPrefix("client/");
        PlayN.run(new BubbleShooter());
    }
}

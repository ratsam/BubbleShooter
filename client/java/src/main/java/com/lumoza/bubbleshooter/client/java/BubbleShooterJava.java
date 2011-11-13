package com.lumoza.bubbleshooter.client.java;

import com.lumoza.bubbleshooter.client.core.BubbleShooter;
import playn.core.PlayN;
import playn.java.JavaPlatform;

public class BubbleShooterJava {

    public static void main(String[] args) {
        JavaPlatform platform = JavaPlatform.register();
        platform.assetManager().setPathPrefix("src/main/java/com/lumoza/bubbleshooter/client/resources");
        PlayN.run(new BubbleShooter());
    }
}

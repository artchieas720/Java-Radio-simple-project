package com.company;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.FactoryRegistry;
import javazoom.jl.player.JavaSoundAudioDevice;
import javazoom.jl.player.Player;


import javax.sound.sampled.*;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Scanner;

public class Radio {
    private Player player;
    private Thread thread;


    public FloatControl volControl;
    private AudioDevice device;

    public boolean isRunning() {
        return thread != null;
    }


    public void setVolume(float gain){

        if(this.volControl == null) {
            Class<JavaSoundAudioDevice> clazz = JavaSoundAudioDevice.class;
            Field[] fields = clazz.getDeclaredFields();
            try{
                SourceDataLine source = null;
                for(Field field : fields) {
                    if("source".equals(field.getName())) {
                        field.setAccessible(true);
                        source = (SourceDataLine) field.get(this.device);
                        field.setAccessible(false);
                        this.volControl = (FloatControl) source.getControl(FloatControl.Type.MASTER_GAIN);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (this.volControl != null) {
            float newGain = Math.min(Math.max(gain, volControl.getMinimum()), volControl.getMaximum());
            volControl.setValue(newGain);
        }
    }
    public void stop(){
        if(isRunning()){
            thread.interrupt();
            thread = null;

            if(player != null){
                player.close();
            }
        }
    }

    public void play(String urlOfStation)
    {
        try
        {
            InputStream stream = new URL(urlOfStation).openStream();

            JavaSoundAudioDevice a = new JavaSoundAudioDevice();

            this.device = FactoryRegistry.systemRegistry().createAudioDevice();
            this.player = new Player( stream, device);

            thread = new Thread(() -> {
                try {
                    player.play();
                } catch (JavaLayerException e) {
                    e.printStackTrace();
                }
            });
           thread.start();

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        String line = "";
        try (Scanner scanner = new Scanner(System.in)) {
            while(!line.equals("q")){
                line = scanner.nextLine();

                if(line.matches("^(-?)(0|([1-9][0-9]*))(\\.[0-9]+)?$")) {
                    setVolume(Float.valueOf(line));
                }
            }
        }
    }
}










package main;

import java.io.File ;
import javax.sound.sampled.* ;

public class Sound{

    private File soundFile ;
    private Clip soundClip ;
    private FloatControl soundControl ;
    private boolean timer ;
    private int timeStart ;

    public Sound(String fileName, int timeStart) {
        this(fileName) ;
        this.timeStart = timeStart ; 
        this.timer = true ;
    }

    public Sound(String fileName) {

        this.soundFile = new File("sounds/"+ fileName) ;
        try{
            this.soundClip = AudioSystem.getClip() ;
            this.soundClip.open(AudioSystem.getAudioInputStream(this.soundFile));
            this.soundControl = (FloatControl) soundClip.getControl(FloatControl.Type.MASTER_GAIN);  
            
        } catch (Exception e) { return ; }
        this.timer = false ;
        System.out.println(this.soundFile.getName());
    }

    public void startSound(){
        if(!this.timer? !this.isPlaying() : true){
            try{
                this.soundClip = AudioSystem.getClip() ;
                this.soundClip.open(AudioSystem.getAudioInputStream(this.soundFile));
                this.soundControl = (FloatControl) soundClip.getControl(FloatControl.Type.MASTER_GAIN);  
            } catch (Exception e) { return ; }
                this.soundClip.start() ;
        }
    }

    public void stopSound(){
        this.soundClip.stop() ;
    }

    public boolean isPlaying(){
        return this.soundClip.isRunning() ;
    }

    public void setVolume(float volume) {      
        this.soundControl.setValue(20f * (float) Math.log10(volume));
    }

    public void startProximity(float proximityDis, float xofPlayer, float xfromPlayer) {
        this.startSound() ;
        
        if(Math.abs(xofPlayer-xfromPlayer) < proximityDis){
            this.setVolume((proximityDis-Math.abs(xofPlayer-xfromPlayer))/proximityDis) ;
        } else {
            this.setVolume(0.0f) ;
        }
    }

    public void setTimeStart(int timeStart) {
        this.timeStart = timeStart ;
    }

    public void startTimer(int intervals) {
        if(this.timeStart%intervals == 0) this.startSound() ;
        this.timeStart++ ;
    }

    public void startLoop() {
        this.soundClip.loop(Clip.LOOP_CONTINUOUSLY) ;
    }

}
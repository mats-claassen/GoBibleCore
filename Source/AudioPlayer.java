
import javax.microedition.lcdui.StringItem;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import javax.microedition.media.PlayerListener;

public class AudioPlayer implements PlayerListener, Runnable {

    private Thread thread;
    private Player player;

    private String fileName;

    public AudioPlayer() {
    }

    public void setChapter(String fileName) {
        if (fileName == this.fileName) {
            stopPlaying();
            return;
        }
        if (player != null) {
            playerClose();
        }
        this.fileName = fileName;
        thread = new Thread(this);
        thread.start();
    }

    public void togglePlaying() {
        if (isPlaying())
            playerPause();
        else
            startPlayer();
    }

    public void stopPlaying() {
        if (player != null) {
            try {
                player.stop();
                player.setMediaTime(0);
            } catch (MediaException me) {
                System.err.println(me);
            }
        }
    }

    public void forward() {
        try {
            long mTime = player.getMediaTime();
            long duration = player.getDuration();
            if (duration == Player.TIME_UNKNOWN || mTime == Player.TIME_UNKNOWN)
                return;
            player.setMediaTime(Math.min(duration, mTime + 3 * 1000000));
        } catch (MediaException me) {
            System.err.println(me);
        }
    }

    public void backward() {
        try {
            long mTime = player.getMediaTime();
            player.setMediaTime(Math.max(0, mTime - 3 * 1000000));
        } catch (MediaException me) {
            System.err.println(me);
        }
    }

    public boolean isPlaying() {
        return (player.getState() == Player.STARTED);
    }

    public void run() {
        try {
            /*create a player object*/
            player = Manager.createPlayer(this.fileName);
            player.realize();
            player.prefetch();
            player.addPlayerListener(this);
        } catch (Exception ex) {
            if (player != null) {
                player.close();
                player = null;
            }
            System.err.println("Problem creating player" + ex);
        }
    }

    /*this method will respond to the player events*/
    public void playerUpdate(Player plyr, String evt, Object evtData) {
        if (evt == STARTED) {
            System.out.println("Started Playing Audio");
        } else if (evt == STOPPED) {
            System.out.println("Stopped Playing Audio");
        } else if (evt == CLOSED) {
            System.out.println("Closed Playing Audio");
        } else if (evt == END_OF_MEDIA) {
            System.out.println("Finished playing chapter");
            try {
                player.stop();
            } catch (MediaException me) {
                System.err.println(me);
            }
        }
    }

    public void startPlayer() {
        if (player != null) {
            try {
                long mTime = player.getMediaTime();
                if (mTime >= player.getDuration() || mTime == Player.TIME_UNKNOWN)
                    player.setMediaTime(0);
                player.start();
            } catch (MediaException me) {
                System.err.println(me);
            } catch (Exception ex) {
                System.err.println(ex);
            }
        }
    }

    public void playerPause() {
        if (player != null) {
            try {
                player.stop();
            } catch (MediaException me) {
                System.err.println(me);
            }
        }
    }

    public void playerClose() {
        synchronized (this) {
            if (player != null) {
                try {
                    if (player.getState() == Player.STARTED) {
                        player.stop();
                        player.close();
                    }
                    if (player.getState() == Player.PREFETCHED) {
                        player.deallocate();
                    }
                    if (player.getState() == Player.REALIZED ||
                            player.getState() == Player.UNREALIZED) {
                        player.close();
                    }
                } catch (MediaException me) {
                    System.err.println(me);
                }
            }
            player = null;
        }
    }
}

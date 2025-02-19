package fnf;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

/**
 * Contains the functionality to run a screen where you can control a blorbo with arrow keys like in fnf.
 */
public class GameRunner implements KeyListener {
    
    private final int DELAY = 350;
    
    private JFrame gameFrame;
    private BlorboPanel blorbo;
    
    public GameRunner() {
        this.gameFrame = new JFrame();
        this.gameFrame.setTitle("Friday FNAFs at Funkin's");
        
        this.blorbo = new BlorboPanel("./images/", 2);
        this.blorbo.setName("Cerulean The Protogen");
        
        this.gameFrame.add(this.blorbo);
        this.gameFrame.setSize(this.blorbo.getMinimumSize());
        this.gameFrame.setVisible(true);
        
        this.gameFrame.addKeyListener(this);
        
        Timer timer = new Timer();
        TimerTask tick = new TimerTask() {
            public void run() {
                blorbo.tick();
            }
        };
        timer.scheduleAtFixedRate(tick, 0, this.DELAY);
        
        
        System.out.println(this.blorbo);
    }
    
    public static void main(String[] args) {
        new GameRunner();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        return;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        final int LEFT = 37;
        final int UP = 38;
        final int RIGHT = 39;
        final int DOWN = 40;
        
        switch (e.getKeyCode()) {
            case LEFT:
                this.blorbo.dirPressed(Direction.LEFT);
                break;
            case UP:
                this.blorbo.dirPressed(Direction.UP);
                break;
            case RIGHT:
                this.blorbo.dirPressed(Direction.RIGHT);
                break;
            case DOWN:
                this.blorbo.dirPressed(Direction.DOWN);
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        final int LEFT = 37;
        final int UP = 38;
        final int RIGHT = 39;
        final int DOWN = 40;
        
        switch (e.getKeyCode()) {
            case LEFT:
                this.blorbo.dirReleased(Direction.LEFT);
                break;
            case UP:
                this.blorbo.dirReleased(Direction.UP);
                break;
            case RIGHT:
                this.blorbo.dirReleased(Direction.RIGHT);
                break;
            case DOWN:
                this.blorbo.dirReleased(Direction.DOWN);
                break;
            default:
                break;
        }
    }
}

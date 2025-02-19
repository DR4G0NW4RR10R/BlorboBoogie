package fnf;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * A JPanel that aims to store all the information needed for a blorbo to boogie.
 * Assumes that all sprites are the same size and of the same file extension.
 * Assumes that all animations are the same number of frames long.
 * 
 * @author DR4G0NW4RR10R
 */
public class BlorboPanel extends JPanel{
    
    
    private String folderPath;
    private String name;
    private String extension;
    private Direction dir;
    private int animAdv;
    private int animLength;
    private Map<Direction, BufferedImage[]> spritesheet;
    private Map<Direction, Boolean> dirsHeld;
    private Stack<Direction> dirStack;
    
    /**
     * Creates a BlorboPanel with the following restrictions:
     * All frames must have the same dimensions.
     * All animations must have the same number of frames.
     * All frame images must share the same file extension (.png, .jpg, etc).
     * Frame images must follow the naming convention of N0.png, N1.png, N2.png, etc until reaching N(animation length).png.
     * There must similarly be animations present using U, D, L, and R for the directions (N is neutral).
     * All image files must be present in the same folder.
     * 
     * @param folderPath
     * @param animLength
     */
    public BlorboPanel(String folderPath, int animLength) {
        
        this.folderPath = folderPath;
        this.name = "Tav";
        this.extension = ".png";
        this.animAdv = 0;
        this.animLength = animLength;
        this.dir = Direction.NEUTRAL;
        this.spritesheet = new HashMap<>();
        this.dirsHeld = new HashMap<>();
        this.dirStack = new Stack<>();
        
        this.initSpritesheet();
        this.initDirsHeld();
    }
    
    /**
     * Initializes the directions held map to all false.
     */
    private void initDirsHeld() {
        for (Direction d : Direction.values()) {
            this.dirsHeld.put(d, false);
        }
    }
    
    /**
     * Loads all the BufferedImages that will be needed and stores them in a Map<Direction, BufferedImage[]>.
     * Sets the minimum size of this panel to the size of the neutral zero frame.
     */
    private void initSpritesheet() {
        for (Direction d : Direction.values()) {
            int spriteCount = this.animLength;
            BufferedImage[] spriteline = new BufferedImage[spriteCount];
            for (int i = 0; i < spriteCount; i++) {
                BufferedImage myPicture;
                try {
                    myPicture = ImageIO.read(new File(this.folderPath + d.toString() + i + this.extension));
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                spriteline[i] = myPicture;
            }
            this.spritesheet.put(d, spriteline);
        }
        
        int minX = this.spritesheet.get(this.dir)[0].getWidth();
        int minY = this.spritesheet.get(this.dir)[0].getHeight();
        this.setMinimumSize(new Dimension(minX, minY));
    }
    
    /**
     * Updates everything that should happen every frame.
     */
    public void tick() {
        this.animAdv++;
        if (this.animAdv >= this.animLength) {
            this.animAdv = 0;
        }
        
        this.repaint();
    }
    
    /**
     * Contains functionality for changing directions LIFO.
     */
    public void dirPressed(Direction d) {
        if (this.dirsHeld.get(d) == false) {
            this.dirStack.push(d);
            this.dir = d;
            this.animAdv = 0;
            this.repaint();
        }
        
        this.dirsHeld.put(d, true);
    }
    
    /**
     * Contains functionality for changing directions LIFO.
     */
    public void dirReleased(Direction d) {
        this.dirsHeld.put(d, false);
        
        boolean canShortCircuit = true;
        for (Direction di : Direction.values()) {
            if (this.dirsHeld.get(di)) {
                canShortCircuit = false;
            }
        }
        
        if (canShortCircuit) {
            this.dirStack.clear();
            this.dir = Direction.NEUTRAL;
            this.animAdv = 0;
            this.repaint();
            return;
        }
        
        if (!this.dirStack.peek().equals(d)) {
            return;
        }
        
        while (!this.dirsHeld.get(this.dirStack.peek())) {
            this.dirStack.pop();
        }
        
        this.dir = this.dirStack.peek();
        this.animAdv = 0;
        this.repaint();
    }
    
    /**
     * @return the sprite that should be showing at the current direction and frame
     */
    public BufferedImage getSprite() {
        return this.spritesheet.get(this.dir)[this.animAdv];
    }
    
    
    /**
     * @param the animLength
     */
    public void setAnimLength(int i) {
        this.animLength = i;
    }
    
    /**
     * @return the animLength
     */
    public int getAnimLength() {
        return this.animLength;
    }

    /**
     * @return the folderPath
     */
    public String getFolderPath() {
        return this.folderPath;
    }

    /**
     * @param folderPath the folderPath to set
     */
    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    /**
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the extention
     */
    public String getExtention() {
        return this.extension;
    }

    /**
     * @param extention the extention to set
     */
    public void setExtention(String extention) {
        this.extension = extention;
    }

    @Override
    public String toString() {
        return "BlorboPanel:\n\tName: " + this.name + "\n\tFolder Path: " +
            this.folderPath + "\n\tSize: " + this.getMinimumSize().width +
            " x " + this.getMinimumSize().height + "\n\tAnimation Length: " + this.animLength;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage sprite = getSprite();
        if (sprite != null) {
            g.drawImage(sprite, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }

}

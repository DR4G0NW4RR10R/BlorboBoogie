package fnf;

public enum Direction {
    NEUTRAL,
    UP,
    DOWN,
    LEFT,
    RIGHT;
    
    public String toString() {
        switch(this) {
            case DOWN:
                return "D";
            case LEFT:
                return "L";
            case NEUTRAL:
                return "N";
            case RIGHT:
                return "R";
            case UP:
                 return "U";
            default:
                 return "N";
            
        }
    }
}

package sk.tuke.kpi.oop.game;

public enum Direction {
    NORTH (0, 1),
    EAST (1, 0),
    SOUTH (0, -1),
    WEST (-1, 0),
    NORTHWEST(-1,1),
    NORTHEAST(1,1),
    SOUTHWEST(-1,-1),
    SOUTHEAST(1,-1),
    NONE(0,0);
    private Direction[] alldirections;
    private final int dx;
    private final int dy;
    Direction(int dx, int dy){
        this.dx=dx;
        this.dy=dy;
    }
    public int getDx(){
        return dx;
    }
    public int getDy(){
        return dy;
    }
    public Direction combine(Direction other){
        if (other == null) return this;
        alldirections = Direction.values();

        int newdx = (this.getDx() + other.getDx());
        int newdy = (this.getDy() + other.getDy());

        if(newdx > 1) newdx--;
        else if(newdx < -1) newdx++;
        if(newdy > 1) newdy--;
        else if(newdy < -1) newdy++;
        int i=0;
        while(i!=9){
            if ((alldirections[i].getDx() == newdx) && (alldirections[i].getDy() == newdy)){
                return alldirections[i];
            }
            i++;
        }

        return Direction.NONE;
    }
    public static Direction fromAngle(float angle) {
        if (angle == 0) return NORTH;
        if (angle == 45) return NORTHWEST;
        if (angle == 180) return SOUTH;
        if (angle == 90) return WEST;
        if (angle == 135) return SOUTHWEST;
        if (angle == 225) return SOUTHEAST;
        if (angle == 270) return EAST;
        return NORTHEAST;
    }
        public float getAngle(){
        if(getDx()==0&&getDy()==1) return 0;
        else if(getDx()==1&&getDy()==0) return 270;
        else if(getDx()==0&&getDy()==-1) return 180;
        else if(getDx()==-1&&getDy()==0) return 90;
        return getAngleSecond();
//        }
//        else if( getDx()==-1&&getDy()==1) return 45f;
//        else if( getDx()==1&&getDy()==1) return 315f;
//        else if( getDx()==-1&&getDy()==-1) return 135f;
//        else if( getDx()==1&&getDy()==-1) return 225f;
    }
    public float getAngleSecond(){
        if( getDx()==-1&&getDy()==1) {//System.out.println("a");
        return 45;}
        else if( getDx()==1&&getDy()==1) return 315;
        else if( getDx()==-1&&getDy()==-1) return 135;
        else if( getDx()==1&&getDy()==-1) return 225;
        return 0;
    }
    public Direction deleteDirection(Direction direction){
        if (direction == null) return this;
        alldirections = Direction.values();

        int newdx = (this.getDx() - direction.getDx());
        int newdy = (this.getDy() - direction.getDy());
        int i=0;
        while(i!=9){
            if ((alldirections[i].getDx() == newdx) && (alldirections[i].getDy() == newdy)){
                return alldirections[i];
            }
            i++;
        }
        return Direction.NONE;


    }



}

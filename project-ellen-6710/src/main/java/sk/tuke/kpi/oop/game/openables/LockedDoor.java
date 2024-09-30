package sk.tuke.kpi.oop.game.openables;

public class LockedDoor extends Door {
    private boolean isLocked;
    public LockedDoor(String name,Orientation orientation){
        super(name,orientation);
        isLocked=true;
    }
    public void lock(){
        if(!this.isLocked) {
            this.isLocked=true;
            this.close();
        }
    }
    public void unlock(){
        if(this.isLocked) {
            this.isLocked=false;
            this.open();
        }
    }
    public boolean isLocked(){
        return this.isLocked;
    }
}

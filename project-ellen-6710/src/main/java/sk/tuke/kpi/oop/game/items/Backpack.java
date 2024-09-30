package sk.tuke.kpi.oop.game.items;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.ActorContainer;

import java.util.*;

public class Backpack implements ActorContainer<Collectible> {
    private final int capacity;
    private final String name;
    private List<Collectible> BackpackList=new ArrayList<>();

    public Backpack(String name, int capacity){
        this.capacity=capacity;
        this.name=name;
        BackpackList=new ArrayList<>(this.capacity);
    }

    public int getCapacity(){
        return capacity;
    }

    @Override
    public @NotNull List<Collectible> getContent() {
        return List.copyOf(BackpackList);
    }

    @NotNull
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void add(@NotNull Collectible actor) {
        if(getSize()==capacity){
            throw new IllegalStateException(getName()+" is full!");
        }else {
            BackpackList.add(actor);
        }
    }

    @Override
    public int getSize() {
        return BackpackList.size();

    }

    @Override
    public void remove(@NotNull Collectible actor) {
        if(BackpackList==null){
            throw new IllegalStateException(getName()+" is empty!");
        }else{
            BackpackList.remove(actor);
        }
    }
    public Iterator<Collectible> iterator(){
        return BackpackList.listIterator();
    }

    @Override
    public @Nullable Collectible peek() {
        if(getSize()==0){return null;}
        else {return BackpackList.get(getSize()-1);}
    }

    @Override
    public void shift() {
        if(getSize()<=1){
            return;
        }else{
            Collections.rotate(BackpackList,1);
        }
    }

}

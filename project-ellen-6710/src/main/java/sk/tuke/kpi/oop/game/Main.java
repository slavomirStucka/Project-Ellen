package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.gamelib.backends.lwjgl.LwjglBackend;
import sk.tuke.kpi.oop.game.scenarios.EscapeRoom;
import sk.tuke.kpi.oop.game.scenarios.FirstSteps;
import sk.tuke.kpi.oop.game.scenarios.VlastnyLevel;


public class Main {
    public static void main(String[] args) {
        WindowSetup windowSetup = new WindowSetup("Project Ellen", 1000, 800);
        Game game = new GameApplication(windowSetup, new LwjglBackend());  // v pripade Mac OS bude druhy parameter "new Lwjgl2Backend()"

        //Scene scene = new World("world","maps/mission-impossible.tmx");
        Scene scene = new World("escape-room", "maps/vlastny_level.tmx", new VlastnyLevel.Factory());
        //Scene scene = new World("vlastny-level", "maps/vlastny_level.tmx", new VlastnyLevel.Factory());
        //scene.getCamera();
        game.addScene(scene);
        game.getInput().onKeyPressed(Input.Key.ESCAPE, game::stop);
        //EscapeRoom escapeRoom= new EscapeRoom();
        //scene.addListener(escapeRoom);
        //scene.centerOn(100,0);
        VlastnyLevel vlastnyLevel= new VlastnyLevel();
        scene.addListener(vlastnyLevel);
        game.start();

    }
}

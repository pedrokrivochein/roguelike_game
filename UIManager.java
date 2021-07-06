import java.awt.*;

public class UIManager {
    Main game;

    int x = 0;
    int y = 0;

    public UIManager(Main _game){
        game = _game;
    }

    public void draw(Graphics2D gr){
        x = (int) game.camera.x + 20;
        y = (int) game.camera.y + 20;

        gr.setColor(new Color(40, 40, 40));
        gr.fillRect(x, y, 102, 102);

        gr.setColor(new Color(255, 255, 255));
        for(int _y = 0; _y < 5; _y++){
            for(int _x = 0; _x < 5; _x++)
                if(game.mapManager.currentMap.layout[_y][_x] == 1){
                    if(game.camera.xRoom == _x && game.camera.yRoom == _y){
                        gr.setColor(new Color(120, 120, 120));
                        gr.fillRect(x + (20 * _x) + 2, y + (20 * _y) + 2, 18, 18);
                    }else{
                        gr.setColor(new Color(255, 255, 255));
                        gr.fillRect(x + (20 * _x) + 2, y + (20 * _y) + 2, 18, 18);
                    }
                }
        }
    }
}

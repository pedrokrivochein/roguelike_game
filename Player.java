import java.awt.*;

public class Player {
    public Main game;
    public KeyListenerC keyListener;
    public Weapon weapon;

    public double x;
    public double y;
    public int size = 1;
    public int[] dir = new int[]{0, 0};
    public double rot;
    
    public Player(Main _game){
        game = _game;
        weapon = new Weapon(_game);
        size = 1;
        x = game.mapManager.currentMap.rooms.get(0).y * 80.0D * 30.0D + 400.0D;
        y = game.mapManager.currentMap.rooms.get(0).x * 80.0D * 30.0D + 400.0D;
        dir[0] = 0;
        dir[1] = 0;
    }
 
    public void draw(Graphics2D gr){
        gr.translate(x+ 30, y+ 30);
        gr.rotate(rot);
        gr.setColor(new Color(1, 194, 151));
        gr.translate(-(x+ 30), -(y+ 30));
        gr.fillRect((int) x,(int) y, game.cellsize, game.cellsize);
    }

    public void move() {
        double _x = x;
        double _y = y;
        if(keyListener.keys['W']){ 
            //if(y - 1.0D >= game.camera.minY)
                _y -= 1.0D;
        }
        if(keyListener.keys['S']){
            //if(y + 1.0D <= ((game.camera.yRoom + 1) * 80.0D * 30.0D) - 80)
                _y += 1.0D;
        }
        if(keyListener.keys['A']){
            //if(x - 1.0D >= game.camera.minX)
                _x -= 1.0D;
        }
        if(keyListener.keys['D']){
            //if(x + 1.0D <= ((game.camera.xRoom + 1) * 80.0D * 30.0D) - 80)
                _x += 1.0D;
        }

        checkCollision(_x, _y);
    }

    public void checkCollision(double _x, double _y){
        for(int i = 0; i < game.mapManager.aux; i++){
            int bx = Integer.parseInt(game.mapManager.walls[i].split(" ")[0]);
            int by = Integer.parseInt(game.mapManager.walls[i].split(" ")[1]);

            if (_x + 60 > bx && _x < bx + 80 && _y + 60 > by && _y < by + 80) {
                return;
            }
        }
        x = _x;
        y = _y;
    }
}
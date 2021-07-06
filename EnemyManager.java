import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EnemyManager {
    static Main game;

    class Enemy{
        double x;
        double y;
        double rot;
        
        float speed;

        Player player;

        boolean patrol = true;

        class Target{
            double x;
            double y;

            public Target(double _x, double _y){
                x = _x;
                y = _y;
            }
        }

        Target target;

        public Enemy(double _x, double _y, float _speed){
            x = _x;
            y = _y;
            rot = 0.0D;
            speed = _speed;
            player = Main.instance.player;
            target = new Target(x, y);
        }

        public void draw(Graphics2D gr){
            gr.translate(x+ 30, y+ 30);
            gr.rotate(rot);
            gr.translate(-(x+ 30), -(y+ 30));
            gr.fillRect((int) x, (int) y, 60, 60);
        }

        public void move(){
            rot = Math.atan2(target.y - y, target.x - x);
            x += speed * Math.cos(rot);
            y += speed * Math.sin(rot);

            if(patrol)
                findTarget();
            else{
                target.x = player.x;
                target.y = player.y;
            }

        }

        public void findTarget(){
            if(target.x - x <= 0.4D && target.y - y <= 0.4D){
                target.x += Math.random() * 800.0D;
                target.y += Math.random() * 800.0D;
            }

            double _x = x, _y = y;
            for(float i = 1.0F; i <= 12.0F; i += 0.2F){
                _x = x; 
                _y = y;
                _x += i * Math.cos(rot);
                _y += i * Math.sin(rot);

                if((player.x - _x) <= 0.4D && (player.y - _y) <= 0.4D){
                    target.x = player.x;
                    target.y = player.y;
                    patrol = false;
                }
            }
        }
    }

    class DeadEnemy{
        double x;
        double y;
        double rot;

        public DeadEnemy(double _x, double _y, double _rot){
            x = _x;
            y = _y;
            rot = _rot;
        }

        public void draw(Graphics2D gr){
            gr.translate(x+ 30, y+ 30);
            gr.rotate(rot);
            gr.translate(-(x+ 30), -(y+ 30));
            gr.fillRect((int) x, (int) y, 60, 60);
        }
    }

    public List<Enemy> enemies = new ArrayList<Enemy>();
    public List<DeadEnemy> deadEnemies = new ArrayList<DeadEnemy>();

    public EnemyManager(Main _game){
        game = _game;
    }

    public void update(){
        for(int i = 0; i < enemies.size(); i++){
            enemies.get(i).move();
            if(enemies.get(i).x <= 0.0D || enemies.get(i).y == 0.0D)
                enemies.remove(i);
        }
    }

    public void spawnEnemy(double _x, double _y, float _speed){
        enemies.add(new Enemy(_x, _y, _speed));
    }

    public void destroyEnemy(int _id){
        deadEnemies.add(new DeadEnemy(enemies.get(_id).x, enemies.get(_id).y, enemies.get(_id).rot));
        enemies.remove(_id);
    }
}

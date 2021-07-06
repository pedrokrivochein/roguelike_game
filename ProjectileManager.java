import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectileManager {
    Main game;

    public class Projectile{
        double x;
        double y;
        double rot;

        float speed;

        public Projectile(double _x, double _y, double _rot, float _speed){
            x = _x;
            y = _y;
            rot = _rot;
            speed = _speed;
        }

        public void draw(Graphics2D gr){
            gr.fillOval((int) x + 20,(int) y + 20, 20, 20);
        }

        public void move(){
            x += speed * Math.cos(rot);
            y += speed * Math.sin(rot);
        }
    }

    public List<Projectile> projectiles = new ArrayList<Projectile>();

    public ProjectileManager(Main _game){
        game = _game;
    }

    public void update(){
        for(int i = 0; i < projectiles.size(); i++){
            projectiles.get(i).move();
            checkCollision(i, projectiles.get(i));
        }
    }

    public void spawnProjectile(double _x, double _y, double _rot, float _speed){
        projectiles.add(new Projectile(_x, _y, _rot, _speed));
    }

    public void checkCollision(int _id, Projectile _proj){
        if(_proj.x <= 0.0D || _proj.y == 0.0D){
            projectiles.remove(_id);
            return;
        }

        for(int i = 0; i < game.enemyManager.enemies.size(); i++){
            EnemyManager.Enemy holder = game.enemyManager.enemies.get(i);
            if (_proj.x + 25 > holder.x && _proj.x - 25 < holder.x + 100 && _proj.y + 25 > holder.y && _proj.y - 25 < holder.y + 100) {
                game.enemyManager.destroyEnemy(i);
                projectiles.remove(_id);
                break;
            }
        }
    }
}

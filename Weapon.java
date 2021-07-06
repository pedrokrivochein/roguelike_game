public class Weapon {
    public Main game;

    public Weapon(Main _game){
        game = _game;
    }

    public void shoot(double _x, double _y, double _rot, float _speed){
        game.projectileManager.spawnProjectile(_x, _y, _rot, _speed);
    }
}

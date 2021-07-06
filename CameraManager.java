public class CameraManager {
    Main game;
    double x = 800.0D;
    double y = 800.0D;

    double xRoom = 0;
    double yRoom = 0;

    double minX = 0.0D;
    double minY = 0.0D;
    double maxX = 0.0D;
    double maxY = 0.0D;

    public CameraManager(double _x, double _y, Main _game){
        x = _x;
        y = _y;
        game = _game;
    }

    public void move(){
        double _x, _y;

        _x = game.player.x + 30 - game.width / 2;
        _y = game.player.y + 30 - game.height / 2;

        maxX = 80.0D * 30.0D;
        maxY = 80.0D * 30.0D;

        xRoom = ((game.player.x + 30) - (game.player.x + 30) % maxX) / maxX;
        yRoom = ((game.player.y + 30) - (game.player.y + 30) % maxY) / maxY;

        maxX *= xRoom + 1.0D;
        maxY *= yRoom + 1.0D;

        minX = 80.0D * 30.0D * xRoom;
        minY = 80.0D * 30.0D * yRoom;

        maxX -= game.width;
        maxY -= game.height;
        

        if(_x >= minX)
            if(_x <= maxX)
                x = _x;
            else
                x = maxX;
        else
            x = minX;
        
        if(_y >= minY)
            if(_y <= maxY)
                y = _y;
            else
                y = maxY;
        else
            y = minY;
    }
}

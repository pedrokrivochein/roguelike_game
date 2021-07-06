import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapManager {
    Main game;

    double xOldRoom = 0.0D;
    double yOldRoom = 0.0D;

    class Map{
        int[][] layout = new int[5][5];

        class Room{
            int x, y;
            int doors;
            char[] sides;
            String[] roomLayout;

            public Room(int _x, int _y, int _doors, char[] _sides){
                x = _x;
                y = _y;
                doors = _doors;
                sides = _sides;

                RoomLayouts roomLayouts = new RoomLayouts();
                switch(doors){
                    case 1:
                        for(int i = 0; i < 4; i++){
                            int j;
                            for(j = 0; j < 4; j++){
                                if(roomLayouts.door_one_sides[i].split(" ")[j].toCharArray()[0] == sides[j]){
                                }else
                                    break;
                            }
                            if(j == 4){
                                roomLayout = roomLayouts.door_one_layouts[i];
                                break;
                            }
                        }
                        break;
                    case 2:
                        for(int i = 0; i < 6; i++){
                            int j;
                            for(j = 0; j < 4; j++){
                                if(roomLayouts.door_two_sides[i].split(" ")[j].toCharArray()[0] == sides[j]){
                                }else
                                    break;
                            }
                            if(j == 4){
                                roomLayout = roomLayouts.door_two_layouts[i];
                                break;
                            }
                        }
                        break;
                    case 3:
                        for(int i = 0; i < 4; i++){
                            int j;
                            for(j = 0; j < 4; j++){
                                if(roomLayouts.door_three_sides[i].split(" ")[j].toCharArray()[0] == sides[j]){
                                }else
                                    break;
                            }
                            if(j == 4){
                                roomLayout = roomLayouts.door_three_layouts[i];
                                break;
                            }
                        }
                        break;
                    case 4:
                        for(int i = 0; i < 1; i++){
                            int j;
                            for(j = 0; j < 4; j++){
                                if(roomLayouts.door_four_sides[i].split(" ")[j].toCharArray()[0] == sides[j]){
                                }else
                                    break;
                            }
                            if(j == 4){
                                roomLayout = roomLayouts.door_four_layouts[i];
                                break;
                            }
                        }
                        break;
                }

                for(int x = 0; x < 20; x++){
                    System.out.print(roomLayout[x] + "\n");
                }
            }
        }

        List<Room> rooms = new ArrayList<Room>();

        public Map(){
            generateLayout();
        }

        public void generateLayout(){
            int x, y;
            Random random = new Random();

            for(x = 0; x < 5; x++)
                for(y = 0; y < 5; y++){
                    double r = random.nextDouble();
                    if(r <= 0.5D)
                        layout[x][y] = 1;
                    else
                        layout[x][y] = 0;
                }
            
            for(x = 0; x < 5; x++)
                for(y = 0; y < 5; y++){
                    if(layout[x][y] == 1){
                        //Top
                        if(x + 2 < 5){
                            if(layout[x + 1][y] == 0){
                                if(layout[x + 2][y] == 1){
                                    layout[x + 1][y] = 1;
                                    continue;
                                }
                            }
                        }

                        //Bottom
                        if(x - 2 >= 0){
                            if(layout[x - 1][y] == 0){
                                if(layout[x - 2][y] == 1){
                                    layout[x - 1][y] = 1;
                                    continue;
                                }
                            }
                        }

                        //Right
                        if(y + 2 < 5){
                            if(layout[x][y + 1] == 0){
                                if(layout[x][y + 2] == 1){
                                    layout[x][y + 1] = 1;
                                    continue;
                                }
                            }
                        }

                        //Left
                        if(y - 2 >= 0){
                            if(layout[x][y - 1] == 0){
                                if(layout[x][y - 2] == 1){
                                    layout[x][y - 1] = 1;
                                    continue;
                                }
                            }
                        }
                    }
                }
            
            for(x = 0; x < 5; x++)
                for(y = 0; y < 5; y++){
                    if(layout[x][y] == 1){
                        int doors = 0;
                        char sides[] = {'0', '0', '0', '0'};
                        //Top
                        if(x - 1 >= 0){
                            if(layout[x - 1][y] == 1){
                                doors++;
                                sides[0] = '1';
                            }
                        }

                        //Bottom
                        if(x + 1 < 5){
                            if(layout[x + 1][y] == 1){
                                doors++;
                                sides[2] = '1';
                            }
                        }

                        //Right
                        if(y + 1 < 5){
                            if(layout[x][y + 1] == 1){
                                doors++;
                                sides[1] = '1';
                            }
                        }

                        //Left
                        if(y - 1 >= 0){
                            if(layout[x][y - 1] == 1){
                                doors++;
                                sides[3] = '1';
                            }
                        }

                        if(doors == 0)
                            layout[x][y] = 0;
                        else
                            rooms.add(new Room(x, y, doors, sides));
                    }
                }

            showLayout();
        }

        public void showLayout(){
            for(int x = 0; x < 5; x++){
                for(int y = 0; y < 5; y++)
                    System.out.print(layout[x][y] + " ");
                System.out.print("\n");
            }

            /*for(int i = 0; i < rooms.siye(); i++)
                System.out.print("Room: " + i + " X: " + rooms.get(i).x + " y: " + rooms.get(i).y + " Doors: " + rooms.get(i).doors + "\n");*/
        }
    }

    public Map currentMap;
    public String[] walls = new String[60000];
    int aux = 0;

    public MapManager(Main _game){
        game = _game;
        currentMap = new Map();
    }

    public void generateMap(){
        for(int i = 0; i < currentMap.rooms.size(); i++){
            int x = currentMap.rooms.get(i).x;
            int y = currentMap.rooms.get(i).y;
            for(int j = 0; j < currentMap.rooms.get(i).roomLayout.length; j++){

                //System.out.print("\nTeste: X: " + x + " Y: " + y + " - " + i + " - " + currentMap.rooms.get(i).roomLayout[j] + "\n");
                String[] chars = currentMap.rooms.get(i).roomLayout[j].split(" ");
                for(int z = 0; z < chars.length; z++){
                    if(chars[z].contains("1")){
                        //X Y - Room Position. 0 a 4.
                        //Z J - Blocks Position. 0 a 9;
                        walls[aux] = "" + ((y * 80 * 30) + (z * 80)) + " " + ((x * 80 * 30) + (j * 80));
                        aux++;
                    }
                }

            }
        }
    }

    boolean test = false;
    public void draw(Graphics2D gr){
        if(!test){
            generateMap();
            test = true;
        }

        if(xOldRoom != game.camera.xRoom || yOldRoom != game.camera.yRoom){
            xOldRoom = game.camera.xRoom;
            yOldRoom = game.camera.yRoom;
            game.enemyManager.enemies.clear();
            game.enemyManager.deadEnemies.clear();
            Random random = new Random();
            for(int i = 0; i < 20; i++)
                game.enemyManager.spawnEnemy((xOldRoom * 80.0D * 30.0D) + random.nextDouble() * 80.0D * 30.0D, (yOldRoom * 80.0D * 30.0D) + random.nextDouble() * 80.0D * 30.0D, 0.2F);
        }


        for(int i = 0; i < aux; i++){
            int x = Integer.parseInt(walls[i].split(" ")[0]);
            int y = Integer.parseInt(walls[i].split(" ")[1]);
            gr.fillRect(x, y, 80, 80);
        }
    }
}

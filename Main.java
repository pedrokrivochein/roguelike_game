import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;

import javax.swing.JTextField;

import java.awt.Dimension;

public class Main extends JPanel {
    static Main instance;
    public JFrame frame;
    public CameraManager camera;
    public Player player;
    public EnemyManager enemyManager;
    public ProjectileManager projectileManager;
    public MapManager mapManager;
    public UIManager uiManager;
    
    public int cellsize = 60;
    public int[] apple = new int[2];
    int[][] blocks = new int[cellsize * cellsize][cellsize * cellsize];
    public int width = 1920;
    public int height = 1080;

    public Point mouseP;
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D gr = (Graphics2D) g;

        gr.translate(-camera.x, -camera.y);
        AffineTransform save = gr.getTransform();
        
        //Background
        //gr.setColor(new Color(1, 115, 90));
        gr.setColor(new Color(60, 60, 60));
        gr.fillRect(0, 0, 80 * 30 * 5, 80 * 30 * 5);

        gr.setColor(new Color(40, 40, 40));
        mapManager.draw(gr);

        //Floor

        //DeadEnemies
        gr.setColor(new Color(70, 130, 50));
        for(int i = 0; i < enemyManager.deadEnemies.size(); i++){
            enemyManager.deadEnemies.get(i).draw(gr);
            gr.setTransform(save);
        }

        //Enemies
        gr.setColor(new Color(120, 220, 90));
        for(int i = 0; i < enemyManager.enemies.size(); i++){
            enemyManager.enemies.get(i).draw(gr);
            gr.setTransform(save);
        }

        //Projectiles
        gr.setColor(new Color(20, 20, 20));
        for(int i = 0; i < projectileManager.projectiles.size(); i++){
            projectileManager.projectiles.get(i).draw(gr);
        }

        //Player
        player.draw(gr);
        gr.setTransform(save);

        //UI
        uiManager.draw(gr);

    }

    public static void main(String[] args) throws InterruptedException {
        Main game = new Main();
        instance = game;
        instance.frame = new JFrame("Roguelike");
        instance.mapManager = new MapManager(instance);
        instance.player = new Player(instance);
        instance.camera = new CameraManager(0.0D, 0.0D, instance);
        instance.enemyManager = new EnemyManager(instance);
        instance.projectileManager = new ProjectileManager(instance);
        instance.uiManager = new UIManager(instance);

        JTextField component = new JTextField();
        KeyListenerC keyListener = new KeyListenerC();

        instance.player.keyListener = keyListener;
        keyListener.player = instance.player;

        component.addKeyListener(keyListener);
        instance.frame.setResizable(false);

        JFrame temp = new JFrame();
        temp.pack();
        Insets insets = temp.getInsets();
        temp = null;
        instance.frame.setSize(new Dimension(insets.left + insets.right + instance.width,
                    insets.top + insets.bottom + instance.height));

        instance.frame.setLocationRelativeTo(null);

        instance.frame.add(component);
        instance.frame.add(game);

        instance.frame.addMouseListener(new MouseListener() {
            boolean shooting = false;
            public void mousePressed(MouseEvent m) {
                if(m.getButton() == MouseEvent.BUTTON1 && !shooting) {
                    instance.player.weapon.shoot(instance.player.x, instance.player.y, instance.player.rot, 2.0F);
                    shooting = true;
                }
            }
            public void mouseReleased(MouseEvent m) {
                if(m.getButton() == MouseEvent.BUTTON1) {
                    if(shooting)
                        shooting = false;
                }
            }
            public void mouseEntered(MouseEvent m) { }
            public void mouseExited(MouseEvent m) { }
            public void mouseClicked(MouseEvent m) {
              if(m.getButton() == MouseEvent.BUTTON3) {
                instance.enemyManager.spawnEnemy(800, 800, 0.2F);
              }
            }
        });

        instance.frame.setVisible(true);
        instance.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        while (true) {
            instance.mouseP = MouseInfo.getPointerInfo().getLocation();
            instance.mouseP = new Point(instance.mouseP.x - instance.frame.getLocation().x - 30 - 8, instance.mouseP.y - instance.frame.getLocation().y - 30 - 31);
            instance.mouseP.x += instance.camera.x;
            instance.mouseP.y += instance.camera.y;
            instance.player.rot = Math.atan2(instance.mouseP.y - instance.player.y, instance.mouseP.x - instance.player.x);
        
            instance.player.move();
            instance.camera.move();

            instance.enemyManager.update();
            instance.projectileManager.update();

            game.repaint();

            Thread.sleep(1);
        }
    }

}
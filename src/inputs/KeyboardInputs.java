package inputs;

import main.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInputs implements KeyListener {
    private final GamePanel gamePanel;
    public KeyboardInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W, KeyEvent.VK_UP -> gamePanel.getGame().getPlayer().setUp(true);
            case KeyEvent.VK_S, KeyEvent.VK_DOWN -> gamePanel.getGame().getPlayer().setDown(true);
            case KeyEvent.VK_A, KeyEvent.VK_LEFT -> gamePanel.getGame().getPlayer().setLeft(true);
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> gamePanel.getGame().getPlayer().setRight(true);
            case KeyEvent.VK_ESCAPE -> gamePanel.getGame().getMouse().toggle();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W, KeyEvent.VK_UP -> gamePanel.getGame().getPlayer().setUp(false);
            case KeyEvent.VK_S, KeyEvent.VK_DOWN -> gamePanel.getGame().getPlayer().setDown(false);
            case KeyEvent.VK_A, KeyEvent.VK_LEFT -> gamePanel.getGame().getPlayer().setLeft(false);
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> gamePanel.getGame().getPlayer().setRight(false);
        }
    }
}
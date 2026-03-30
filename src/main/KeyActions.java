package main;

import java.awt.event.*;

public class KeyActions implements KeyListener {

    public boolean upP,  downP, leftP, rightP, actionEP;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_W) {
            upP = true;
        }
        if (key == KeyEvent.VK_A) {
            leftP = true;
        }
        if (key == KeyEvent.VK_S) {
            downP = true;
        }
        if (key == KeyEvent.VK_D) {
            rightP = true;
        }
        if (key == KeyEvent.VK_E) {
            actionEP = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_W) {
            upP = false;
        }
        if (key == KeyEvent.VK_A) {
            leftP = false;
        }
        if (key == KeyEvent.VK_S) {
            downP = false;
        }
        if (key == KeyEvent.VK_D) {
            rightP = false;
        }
        if (key == KeyEvent.VK_E) {
            actionEP = false;
        }
    }
}

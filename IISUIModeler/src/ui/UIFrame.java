package ui;

import javax.swing.JFrame;

public class UIFrame extends JFrame{
    public UIFrame() {
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    public void closeFrame(){
        setVisible(false);
        dispose();
    }
}

package Core;

import UI.MainUI;
import UI.MainUI.UISwitcher;

import javax.swing.JOptionPane;

public class UICore
{
    private static MainUI ui;

    public UICore()
    {
    }
    
    public static void initiate()
    {
        ui = new MainUI();

    }
    
    public static MainUI getMainUI()
    {
        return ui;
    }

    
    public static void switchUI()
    {
        new Thread(new UISwitcher()).start();
    }
    
    public static void showMessageDialog(Object message, String title, int messageType)
    {
        JOptionPane.showMessageDialog(ui, message, title, messageType);
    }
}

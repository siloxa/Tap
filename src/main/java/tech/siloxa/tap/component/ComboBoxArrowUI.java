package tech.siloxa.tap.component;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;

public class ComboBoxArrowUI extends BasicComboBoxUI {

    private Color background;
    private Color foreground;


    public ComboBoxArrowUI(Color background, Color foreground) {
        this.background = background;
        this.foreground = foreground;
    }

    @Override
    protected JButton createArrowButton() {
        final BasicArrowButton basicArrowButton = new BasicArrowButton(
                BasicArrowButton.SOUTH,
                background, background,
                foreground, foreground);
        basicArrowButton.setBorderPainted(false);
        return basicArrowButton;
    }
}

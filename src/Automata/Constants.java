/**
 * AutoAnalyzer (COMP 2013-2014)
 *
 * Eduardo Fernandes (200803951)
 * João Correia      (201208114)
 * José Pinto        (201203811)
 *
 * Constants class
 */

package Automata;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;


public class Constants {
    /* Gui constants */
    public static final String guiName = "AutoAnalyzer";
    public static final Color guiDefaultBackgroundColor = Color.decode("#FAFBFF");
    public static final Color guiDefaultStateColor = Color.ORANGE;
    public static final Color guiDefaultStateBorderColor = new Color(247,150,70);
    public static final Color guiAccepetanceStateBorderColor = new Color(75, 172, 198);
    
    public static int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    public static int height = Toolkit.getDefaultToolkit().getScreenSize().height;
    public static final Dimension guiDefaultWindowSize = new Dimension(430*1366/width, 330*768/height);

    /* File constants */
    public static final int maxFileSize = 4096;

    /* Dotty parser constants */
    public static final String dottyAcceptanceState = "shape=doublecircle";
}

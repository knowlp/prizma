package com.hrzafer.prizma.util;

import javax.swing.*;

/**
 * Bu sınıf temel Kullanıcı Arayüzü (GUI) işlemleri için gerekli bir takım
 * metodlar içerir. İhtiyaca göre genişletilebilir.
 * @author hrzafer.com
 */
public class GUI {

    public static final String NIMBUS = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
    public static final String METAL = "javax.swing.plaf.metal.MetalLookAndFeel";
    public static final String WINDOWS = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
    public static final String MOTIF = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
    public static final String SYSTEM = UIManager.getSystemLookAndFeelClassName();

    /**
     * Pencerenin (JFrame) Look and Feel'ini değiştirir. Örnek kullanım:
     * GUI.setLookandFeel (GUI.NIMBUS, this)
     * @param laf GUI.NIMBUS gibi tanımlı bir
     * @param frame Dokusu (Look and Feel) değişecek pencere (JFrame)
     */
    public static void setLookandFeel(String laf, JFrame frame) {
        try {
            UIManager.setLookAndFeel(laf);
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        SwingUtilities.updateComponentTreeUI(frame);
        frame.pack();
    }

    /**
     * Ekranda Tek Butonlu Mesaj Kutusu Gösterir
     * @param message mesajın içeriği
     * @param title mesajın başlığı
     */
    public static void messageBox(String message, String title) {
        JOptionPane.showConfirmDialog(null, message, title, JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Ekranda Evet ve Hayır butonları olan bir Onaylama Kutusu Gösterir
     * @param message mesajın içeriği
     * @param title mesajın başlığı
     * @return kullanıcının seçimini belirten bir tamsayı
     */
    public static int confirmBox(String message, String title) {
        return JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
    }
}

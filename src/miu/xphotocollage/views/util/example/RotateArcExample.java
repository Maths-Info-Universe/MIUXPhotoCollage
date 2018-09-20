/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miu.xphotocollage.views.util.example;

/**
 *
 * @author Ndadji Maxime
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Arc2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JApplet;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class RotateArcExample extends JPanel {
  MyCanvas canvas;

  JComboBox arcBox, fillBox;

  JSlider sliderX, sliderY, sliderWidth, sliderHeight, sliderT0, sliderT;

  String[] arcLabels = { "Open", "Chord", "Pie" };

  int[] arcTypes = { Arc2D.OPEN, Arc2D.CHORD, Arc2D.PIE };

  String[] colorLabels = { "Black", "White", "Red", "Green", "Blue" };

  Color[] colors = { Color.black, Color.white, Color.red, Color.green,
      Color.blue };

  public RotateArcExample() {
    super(new BorderLayout());
    canvas = new MyCanvas();
        int width = 600;
        int height = 55;
    sliderX = setSlider(0, width, width / 4, width / 2, width / 4);
    sliderY = setSlider(0, height, height / 4,
        height / 2, height / 4);
    sliderWidth = setSlider(0, width, width / 2, width / 2, width / 4);
    sliderHeight = setSlider(0, height, height / 2,
        height / 2, height / 4);
    sliderT0 = setSlider(0, 360, 45, 180, 45);
    sliderT = setSlider(0, 360, 135, 180, 45);

    JPanel panel1 = new JPanel();
    panel1.setLayout(new GridLayout(3, 3));
    panel1.add(new JLabel("Location (x,y): ", JLabel.RIGHT));
    panel1.add(sliderX);
    panel1.add(sliderY);
    panel1.add(new JLabel("Size (w,h): ", JLabel.RIGHT));
    panel1.add(sliderWidth);
    panel1.add(sliderHeight);
    panel1.add(new JLabel("Angles (Th0, Th): ", JLabel.RIGHT));
    panel1.add(sliderT0);
    panel1.add(sliderT);

    add(panel1, BorderLayout.NORTH);

    arcBox = new JComboBox(arcLabels);
    arcBox.setSelectedIndex(0);
    arcBox.setAlignmentX(Component.LEFT_ALIGNMENT);
    arcBox.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JComboBox cb = (JComboBox) e.getSource();
        canvas.arcType = arcTypes[cb.getSelectedIndex()];
        canvas.repaint();
      }
    });

    fillBox = new JComboBox(colorLabels);
    fillBox.setSelectedIndex(0);
    fillBox.setAlignmentX(Component.LEFT_ALIGNMENT);
    fillBox.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JComboBox cb = (JComboBox) e.getSource();
        canvas.fillColor = colors[cb.getSelectedIndex()];
        canvas.repaint();
      }
    });

    JPanel panel2 = new JPanel();
    panel2.setLayout(new GridLayout(1, 4));
    panel2.add(new JLabel("Arc Type: ", JLabel.RIGHT));
    panel2.add(arcBox);
    panel2.add(new JLabel("Fill Type: ", JLabel.RIGHT));
    panel2.add(fillBox);

    add(panel2, BorderLayout.SOUTH);
    add(canvas,BorderLayout.CENTER);
  }

  public JSlider setSlider(int min, int max, int init, int mjrTkSp,
      int mnrTkSp) {
    JSlider slider = new JSlider(JSlider.HORIZONTAL, min, max, init);
    slider.setPaintTicks(true);
    slider.setMajorTickSpacing(mjrTkSp);
    slider.setMinorTickSpacing(mnrTkSp);
    slider.setPaintLabels(true);
    slider.addChangeListener(new SliderListener());
    return slider;
  }

  class MyCanvas extends JLabel {
    Arc2D arc;

    double x, y, w, h, startAngle, extent; 

    Color fillColor;

    int arcType;

    Rectangle2D boundingRec = null;

    public MyCanvas() {
      x = 700 / 4;
      y = 550 / 4;
      w = 600 / 2;
      h = 550 / 2;
      startAngle = 0;
      extent = 135; 
      arcType = Arc2D.OPEN;
      fillColor = Color.black;
      setBackground(Color.white);
    }

    public void paint(Graphics g) {
      Graphics2D g2D = (Graphics2D) g;
      g2D.setColor(Color.white);
      g2D.fill(new Rectangle(getBounds()));
      
      arc = new Arc2D.Double(x, y, w, h, startAngle, extent, arcType);
      if (fillColor == Color.white || arcType == Arc2D.OPEN) {
        g2D.setColor(Color.black);
        g2D.draw(arc);
      } else {
        g2D.setColor(fillColor);
        g2D.fill(arc);
      }

      boundingRec = arc.getBounds2D();
      drawHighlightSquares(g2D, boundingRec);
    }

    public void drawHighlightSquares(Graphics2D g2D, Rectangle2D r) {
      double x = r.getX();
      double y = r.getY();
      double w = r.getWidth();
      double h = r.getHeight();
      g2D.setColor(Color.black);

      g2D.fill(new Rectangle.Double(x - 3.0, y - 3.0, 6.0, 6.0));
      g2D
          .fill(new Rectangle.Double(x + w * 0.5 - 3.0, y - 3.0, 6.0,
              6.0));
      g2D.fill(new Rectangle.Double(x + w - 3.0, y - 3.0, 6.0, 6.0));
      g2D
          .fill(new Rectangle.Double(x - 3.0, y + h * 0.5 - 3.0, 6.0,
              6.0));
      g2D.fill(new Rectangle.Double(x + w - 3.0, y + h * 0.5 - 3.0, 6.0,
          6.0));
      g2D.fill(new Rectangle.Double(x - 3.0, y + h - 3.0, 6.0, 6.0));
      g2D.fill(new Rectangle.Double(x + w * 0.5 - 3.0, y + h - 3.0, 6.0,
          6.0));
      g2D.fill(new Rectangle.Double(x + w - 3.0, y + h - 3.0, 6.0, 6.0));
    }
  }

  class SliderListener implements ChangeListener {
    public void stateChanged(ChangeEvent e) {
      JSlider slider = (JSlider) e.getSource();
      if (slider == sliderX)
        canvas.x = slider.getValue();
      else if (slider == sliderY)
        canvas.y = slider.getValue();
      else if (slider == sliderWidth)
        canvas.w = slider.getValue();
      else if (slider == sliderHeight)
        canvas.h = slider.getValue();
      else if (slider == sliderT0)
        canvas.startAngle = slider.getValue();
      else if (slider == sliderT)
        canvas.extent = slider.getValue();
      canvas.revalidate();
      canvas.repaint();
    }
  }
  public static void main (String[] a){
    JFrame f = new JFrame();
    f.getContentPane().add(new RotateArcExample());
    f.setDefaultCloseOperation(1);
    f.setSize(700, 550);
    f.setVisible(true);  
  }
}

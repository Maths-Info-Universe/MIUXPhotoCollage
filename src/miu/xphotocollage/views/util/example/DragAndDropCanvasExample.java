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
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DragAndDropCanvasExample extends JFrame {

    DrawingCanvas canvas;

    JLabel location;

    public DragAndDropCanvasExample() {
        super();
        Container container = getContentPane();

        canvas = new DrawingCanvas();
        container.add(canvas);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
        panel.add(new JLabel("x,y: ", JLabel.RIGHT));
        location = new JLabel("");
        panel.add(location);

        container.add(panel, BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        setSize(600, 300);
        setVisible(true);
    }

    public static void main(String arg[]) {
        DragAndDropCanvasExample ellipse = new DragAndDropCanvasExample();
    }

    class DrawingCanvas extends JPanel {

        double x, y, w, h;

        int x1, y1, x2, y2;

        Ellipse2D ellipse;

        Ellipse2D selectedShape;

        Rectangle2D boundingRec;

        Cursor curCursor;

        public DrawingCanvas() {
            x = 20;
            y = 20;
            w = 100;
            h = 75;
            setBackground(Color.white);
            addMouseListener(new MyMouseListener());
            addMouseMotionListener(new MyMouseMotionListener());
        }

        @Override
        public void paint(Graphics g) {
            Graphics2D g2D = (Graphics2D) g;
            ellipse = new Ellipse2D.Double(x, y, w, h);
            g2D.draw(ellipse);
            if (boundingRec != null) {
                drawHighlightSquares(g2D, boundingRec);
            }
            if (curCursor != null) {
                setCursor(curCursor);
            }
        }

        public void drawHighlightSquares(Graphics2D g2D, Rectangle2D r) {
            double localx = r.getX();
            double localy = r.getY();
            double localw = r.getWidth();
            double localh = r.getHeight();
            g2D.setColor(Color.black);

            g2D.fill(new Rectangle.Double(localx - 3.0, localy - 3.0, 6.0, 6.0));
            g2D
                    .fill(new Rectangle.Double(localx + localw * 0.5 - 3.0, localy - 3.0, 6.0,
                                    6.0));
            g2D.fill(new Rectangle.Double(localx + localw - 3.0, localy - 3.0, 6.0, 6.0));
            g2D
                    .fill(new Rectangle.Double(localx - 3.0, localy + localh * 0.5 - 3.0, 6.0,
                                    6.0));
            g2D.fill(new Rectangle.Double(localx + localw - 3.0, localy + localh * 0.5 - 3.0, 6.0,
                    6.0));
            g2D.fill(new Rectangle.Double(localx - 3.0, localy + localh - 3.0, 6.0, 6.0));
            g2D.fill(new Rectangle.Double(localx + localw * 0.5 - 3.0, localy + localh - 3.0, 6.0,
                    6.0));
            g2D.fill(new Rectangle.Double(localx + localw - 3.0, localy + localh - 3.0, 6.0, 6.0));
        }

        class MyMouseListener extends MouseAdapter {

            @Override
            public void mousePressed(MouseEvent e) {
                if (ellipse.contains(e.getX(), e.getY())) {
                    selectedShape = ellipse;
                    if (boundingRec != null) {
                        boundingRec = ellipse.getBounds2D();
                    }
                    displayParameters(selectedShape);
                } else {
                    boundingRec = null;
                    location.setText("");
                }
                canvas.repaint();
                x1 = e.getX();
                y1 = e.getY();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (ellipse.contains(e.getX(), e.getY())) {
                    boundingRec = ellipse.getBounds2D();
                    selectedShape = ellipse;

                    displayParameters(selectedShape);
                }

                canvas.repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (ellipse.contains(e.getX(), e.getY())) {
                    selectedShape = ellipse;
                    boundingRec = ellipse.getBounds2D();

                    displayParameters(selectedShape);
                } else {
                    if (boundingRec != null) {
                        boundingRec = null;
                    }
                    location.setText("");
                }
                canvas.repaint();
            }
        }

        class MyMouseMotionListener extends MouseMotionAdapter {

            @Override
            public void mouseDragged(MouseEvent e) {
                if (ellipse.contains(e.getX(), e.getY())) {
                    boundingRec = null;
                    selectedShape = ellipse;
                    x2 = e.getX();
                    y2 = e.getY();
                    x = x + x2 - x1;
                    y = y + y2 - y1;
                    x1 = x2;
                    y1 = y2;
                }
                if (selectedShape != null) {
                    displayParameters(selectedShape);
                }
                canvas.repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                if (ellipse != null) {
                    if (ellipse.contains(e.getX(), e.getY())) {
                        curCursor = Cursor
                                .getPredefinedCursor(Cursor.HAND_CURSOR);
                    } else {
                        curCursor = Cursor.getDefaultCursor();
                    }
                }
                canvas.repaint();
            }
        }

        public void displayParameters(Shape shape) {
            double localx = selectedShape.getX();
            double localy = selectedShape.getY();
            double localw = selectedShape.getWidth();
            double localh = selectedShape.getHeight();
            String locString = "(" + Double.toString(localx) + ","
                    + Double.toString(localy) + ")";
            String sizeString = "(" + Double.toString(localw) + ","
                    + Double.toString(localh) + ")";
            location.setText(locString);
        }
    }
}

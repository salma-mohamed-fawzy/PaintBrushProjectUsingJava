package paintproject;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JRadioButton;
//import javax.swing.filechooser.FileNameExtensionFilter;

public class Paintpanel extends JPanel {

    private List<Shape> shapes; // List to store drawn shapes
    private Shape currentShape; // The current shape being drawn
    private JButton drawLineButton; // Button to draw lines
    private JButton drawCircleButton;
    private JButton drawTriangleButton;
    private JButton drawOvalButton;
    private JButton drawRectButton;
    private JButton clearAll;
    private JButton eraserButton;
    private JButton freehandButton;
    private JButton undoButton;
    private JCheckBox dottedCheckBox;
    private JCheckBox filledCheckBox;
//store the current file chooser directory
    //private File currentDirectory;
    
// Flags to indicate drawing mode
    private boolean isDrawingLine;
    private boolean isDrawingCircle;
    private boolean isDrawingTriangle;
    private boolean isDrawingOval;
    private boolean isDrawingRect;
    private boolean isErasing;
    private boolean isDottedLine;
    private boolean isFreehandDrawing;
    private List<Point> freehandPoints;

    //colors radio buttons
    private JRadioButton RBRED;
    private JRadioButton RBGREEN;
    private JRadioButton RBBLUE;
    private JRadioButton RBYELLOW;
    private ButtonGroup COLORGROUP;

    private Color drawColor; // drawColor: instance variable for the color

    private int prevX, prevY; //to keep track of the eraser

    public Paintpanel() {
        //this.setBackground(Color.WHITE);
        this.setFocusable(true);
        this.shapes = new ArrayList<>();
        this.isDrawingLine = false;
        this.isDrawingCircle = false;
        this.isDrawingTriangle = false;
        this.isDrawingOval = false;
        this.isDrawingRect = false;
        this.isErasing = false;
        this.isDottedLine = false;
        this.isFreehandDrawing = false;
        this.freehandPoints = new ArrayList<>();
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Initialize radio buttons
        RBRED = new JRadioButton("Red");
        RBRED.setBackground(Color.red);

        RBGREEN = new JRadioButton("Green");
        RBGREEN.setBackground(Color.GREEN);
        
        RBBLUE = new JRadioButton("Blue");
        RBBLUE.setBackground(Color.blue);

        RBYELLOW = new JRadioButton("Yellow");
        RBYELLOW.setBackground(Color.YELLOW);

        // Create a button group to ensure only one radio button is selected at a time
        COLORGROUP = new ButtonGroup();
        COLORGROUP.add(RBRED);
        COLORGROUP.add(RBGREEN);
        COLORGROUP.add(RBBLUE);
        COLORGROUP.add(RBYELLOW);
        // Add action listeners to handle color selection
        RBRED.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setDrawColor(Color.RED);
            }

        });

        RBGREEN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setDrawColor(Color.GREEN);
            }
        });

        RBBLUE.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setDrawColor(Color.BLUE);
            }

        });

        RBYELLOW.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setDrawColor(Color.YELLOW);
            }

        });
        // Add radio buttons to the panel
        this.add(RBRED);
        this.add(RBGREEN);
        this.add(RBBLUE);
        this.add(RBYELLOW);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Initialize the buttons
        drawLineButton = new JButton("Draw Line");
        drawLineButton.setBackground(Color.GRAY);
        drawLineButton.setForeground(Color.WHITE);
        drawLineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Toggle drawing lines mode
                isDrawingLine = !isDrawingLine; //= true
                isDrawingCircle = false; // Disable drawing circles
                isDrawingTriangle = false;
                isErasing = false;
                isDrawingOval = false;
                isDrawingRect = false;
                isFreehandDrawing = false;
                // isDottedLine = false;
            }
        });

        drawCircleButton = new JButton("Draw Circle");
        drawCircleButton.setBackground(Color.GRAY);
        drawCircleButton.setForeground(Color.WHITE);
        drawCircleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Toggle drawing circles mode
                isDrawingCircle = !isDrawingCircle;
                isDrawingLine = false; // Disable drawing lines
                isDrawingTriangle = false;
                isErasing = false;
                isDrawingOval = false;
                isDrawingRect = false;
                isFreehandDrawing = false;
                //isDottedLine = false;
            }
        });

        drawTriangleButton = new JButton("Draw Triangle");
         drawTriangleButton.setBackground(Color.GRAY);
        drawTriangleButton.setForeground(Color.WHITE);
        drawTriangleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Toggle drawing triangles mode
                isDrawingTriangle = !isDrawingTriangle;
                isDrawingLine = false; // Disable drawing lines
                isDrawingCircle = false; // Disable drawing circles
                isErasing = false;
                isDrawingOval = false;
                isDrawingRect = false;
                isFreehandDrawing = false;
                // isDottedLine = false;
            }
        });
        eraserButton = new JButton("Eraser");
         eraserButton.setBackground(Color.GRAY);
        eraserButton.setForeground(Color.WHITE);
        eraserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Toggle erasing mode
                isErasing = !isErasing;
                isDrawingLine = false; // Disable drawing lines
                isDrawingCircle = false; // Disable drawing circles
                isDrawingTriangle = false; // Disable drawing triangles
                isDrawingOval = false;
                isDrawingRect = false;
                isFreehandDrawing = false;
                //isDottedLine = false;
            }
        });
        drawOvalButton = new JButton("Draw Oval");
        drawOvalButton.setBackground(Color.GRAY);
        drawOvalButton.setForeground(Color.WHITE);
        drawOvalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Toggle drawing circles mode
                isDrawingOval = !isDrawingOval;
                isDrawingLine = false; // Disable drawing lines
                isDrawingCircle = false;
                isDrawingRect = false;
                isDrawingTriangle = false;
                isErasing = false;
                isFreehandDrawing = false;

            }
        });

        drawRectButton = new JButton("Draw Rectangle");
                drawRectButton.setBackground(Color.GRAY);
        drawRectButton.setForeground(Color.WHITE);
        drawRectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Toggle drawing circles mode
                isDrawingRect = !isDrawingRect;
                isDrawingLine = false; // Disable drawing lines
                isDrawingCircle = false;
                isDrawingOval = false;
                isDrawingTriangle = false;
                isErasing = false;
                isFreehandDrawing = false;
            }
        });

        freehandButton = new JButton("Freehand");
          freehandButton.setBackground(Color.GRAY);
        freehandButton.setForeground(Color.WHITE);
        freehandButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isFreehandDrawing = !isFreehandDrawing;
                isDrawingLine = false; // Disable drawing lines
                isDrawingCircle = false;
                isDrawingOval = false;
                isDrawingTriangle = false;
                isErasing = false;
                isDrawingRect = false;
                clearFreehandPoints();
            }
        });

        undoButton = new JButton("Undo");
          undoButton.setBackground(Color.GRAY);
        undoButton.setForeground(Color.WHITE);
        undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                undoLastShape();
            }
        });
        freehandButton = new JButton("Freehand");
         freehandButton.setBackground(Color.GRAY);
        freehandButton.setForeground(Color.WHITE);
        freehandButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isFreehandDrawing = !isFreehandDrawing;
                isDrawingLine = false;
                isDrawingCircle = false;
                isDrawingTriangle = false;
                isErasing = false;
                isDrawingOval = false;
                isDrawingRect = false;
            }
        });
        clearAll = new JButton("Clear All");
        clearAll.setBackground(Color.GRAY);
        clearAll.setForeground(Color.WHITE);
        clearAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearDrawings();
            }
        });

        dottedCheckBox = new JCheckBox("Dotted ");
        dottedCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isDottedLine = dottedCheckBox.isSelected();
            }
        });

        filledCheckBox = new JCheckBox("Filled");
        filledCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentShape != null && currentShape instanceof Fillable) {
                    ((Fillable) currentShape).setFilled(filledCheckBox.isSelected());
                    repaint();
                }
            }
        });

        // Add the buttons to the panel
        this.add(dottedCheckBox);
        this.add(filledCheckBox);
        this.add(drawLineButton);
        this.add(drawCircleButton);
        this.add(drawTriangleButton);
        this.add(drawOvalButton);
        this.add(drawRectButton);
        this.add(eraserButton);
        this.add(clearAll);
        this.add(freehandButton);
        this.add(undoButton);
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Handle mouseClicked event if needed
            }

            @Override
            public void mousePressed(MouseEvent e) {

                if (isDrawingLine) {
                    currentShape = new Line(e.getX(), e.getY(), e.getX(), e.getY());
                } else if (isDrawingCircle) {
                    currentShape = new Circle(e.getX(), e.getY(), 0); // Initialize circle with radius 0
                } else if (isDrawingTriangle) {
                    // Initialize triangle with three vertices
                    currentShape = new Triangle(new int[]{e.getX(), e.getX() + 50, e.getX() - 50},
                            new int[]{e.getY() - 50, e.getY() + 50, e.getY() + 50});
                } else if (isErasing) {
                    setDrawColor(Color.BLACK); //for the comming shapes
                    int circleRadius = 5;
                    shapes.add(new FilledWhiteCircle(e.getX(), e.getY(), circleRadius));
                    repaint();
                } else if (isDrawingOval) {
                    currentShape = (Shape) new Oval(e.getX(), e.getY(), 0, 0);
                } else if (isDrawingRect) {
                    currentShape = (Shape) new Rect(e.getX(), e.getY(), e.getX(), e.getY());
                } else if (isFreehandDrawing) {
                    freehandPoints.add(new Point(e.getX(), e.getY()));
                    repaint();
                }

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (isFreehandDrawing) {
                    if (!freehandPoints.isEmpty()) {
                        shapes.add(new FreeHand(new ArrayList<>(freehandPoints), drawColor));
                        freehandPoints.clear();
                        repaint();
                    }
                } else if (currentShape != null) {

                    if (filledCheckBox.isSelected() && (currentShape instanceof Rect || currentShape instanceof Oval || currentShape instanceof Circle||currentShape instanceof Triangle)) {
                        ((Fillable) currentShape).setFilled(true);
                    }
                    currentShape.setDotted(isDottedLine);
                    currentShape.setColor(drawColor);
                    shapes.add(currentShape);
                    currentShape = null; //indicates that there is no longer any shape in progress or being drawn
                    repaint();
                }

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // Handle mouseEntered event if needed
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Handle mouseExited event if needed
            }
        });

        this.addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {

                if (isErasing) {
                    // Create and add a filled white circle (eraser)
                    int circleRadius = 5; // Adjust the radius as needed
                    shapes.add(new FilledWhiteCircle(e.getX(), e.getY(), circleRadius));
                    repaint(); // to add all ahapes
                } else if (isFreehandDrawing) {
                    freehandPoints.add(new Point(e.getX(), e.getY()));
                    repaint();
                } else if ((isErasing || isFreehandDrawing || isDrawingLine || isDrawingCircle || isDrawingTriangle || isDrawingRect || isDrawingOval) && currentShape != null) {
                    {
                        if (currentShape instanceof Line) {
                            ((Line) currentShape).setEndX(e.getX());
                            ((Line) currentShape).setEndY(e.getY());
                        } else if (currentShape instanceof Circle) {
                            //rad calc
                            int radius = (int) Math.hypot(e.getX() - ((Circle) currentShape).getCenterX(),
                                    e.getY() - ((Circle) currentShape).getCenterY());
                            //rad setting 
                            ((Circle) currentShape).setRadius(radius);
                        } else if (currentShape instanceof Triangle) {
                            int[] xPoints = ((Triangle) currentShape).getXPoints();
                            int[] yPoints = ((Triangle) currentShape).getYPoints();
                            xPoints[2] = e.getX(); //updating third vertex coordinates 
                            yPoints[2] = e.getY();
                            currentShape.setVertices(xPoints, yPoints);
                        } else if (currentShape instanceof Oval) {
                            ((Oval) currentShape).setWidth(e.getX());
                            ((Oval) currentShape).setHight(e.getY());
                        } else if (currentShape instanceof Rect) {
                            ((Rect) currentShape).setWidth(e.getX());
                            ((Rect) currentShape).setHight(e.getY());
                        } else if (isErasing) {
                            // Create and add a filled white circle (eraser)
                            int circleRadius = 5; // Adjust the radius as needed
                            shapes.add(new FilledWhiteCircle(e.getX(), e.getY(), circleRadius));
                            repaint(); // to add all ahapes
                        }
                    }
                    repaint();
                }
            }

            @Override
            public void mouseMoved(MouseEvent e
            ) {
                // Handle mouseMoved event if needed
            }
        });
    }

    private void setDrawColor(Color color) {
        // Set the draw color based on the selected radio button
        drawColor = color;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Shape shape : shapes) {
            shape.draw(g);
        }

        // Draw the current shape being drawn with the specified color
        if ((isDrawingLine || isDrawingCircle || isDrawingTriangle || isDrawingRect || isDrawingOval) && currentShape != null) {
            currentShape.setDotted(isDottedLine); // Set dotted property for the current shape
            currentShape.draw(g);
        } else if (isFreehandDrawing) {
            FreeHand freeHand = new FreeHand(new ArrayList<>(freehandPoints));
            freeHand.setColor(drawColor);
            freeHand.draw(g);
        }
    }

    private void clearFreehandPoints() {
        freehandPoints.clear();
        repaint();
    }
//clear all shapes drown

    public void clearDrawings() {
        shapes.clear();
        repaint();
    }

    private void undoLastShape() {
        if (!shapes.isEmpty()) {
            shapes.remove(shapes.size() - 1);
            repaint();
        }
    }

}

package paintproject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.List;

public interface Shape {

    void draw(Graphics g);

    int getCenterX();

    int getCenterY();

    void setRadius(int radius);

    Color getColor();  // get the color

    void setColor(Color color);  // set the color

    public void setVertices(int[] xPoints, int[] yPoints);

    void setDotted(boolean isDotted);  //as i don't want to affect previously drawn shapes

    boolean isDotted();  // ensure that each shape can have its own dotted state

}

class Line implements Shape {

    private int startX, startY, endX, endY;
    private Color lineColor;
    private boolean isDotted;  // to prevent effect from prvious shapes 
    //is dotted line to check if line is dootted or not

    public Line(int startX, int startY, int endX, int endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.lineColor = Color.BLACK;
        this.isDotted = false;  // Default to solid line
    }

    private void drawDottedLine(Graphics g) {
        // Draw a dotted line
        g.setColor(lineColor); // Set the color before drawing
        int dashLength = 5; // Adjust the dash length as needed
        int spaceLength = 5; // Adjust the space length as needed

        int dx = endX - startX;
        int dy = endY - startY;
        int steps = Math.max(Math.abs(dx), Math.abs(dy));

        float xIncrement = (float) dx / steps;
        float yIncrement = (float) dy / steps;

        for (int step = 0; step < steps; step += dashLength + spaceLength) {
            int xStart = (int) (startX + step * xIncrement);
            int yStart = (int) (startY + step * yIncrement);
            int xEnd = (int) (startX + (step + dashLength) * xIncrement);
            int yEnd = (int) (startY + (step + dashLength) * yIncrement);

            g.drawLine(xStart, yStart, xEnd, yEnd);
        }
    }

    @Override
    public void draw(Graphics g) {
        if (isDotted) {
            drawDottedLine(g);
        } else {
            // Draw a solid line
            g.setColor(lineColor); // Set the color before drawing
            g.drawLine(startX, startY, endX, endY);
        }
    }

    @Override
    public int getCenterX() {
        return (startX + endX) / 2;
    }

    @Override
    public int getCenterY() {
        return (startY + endY) / 2;
    }

    @Override
    public void setRadius(int radius) {
        // Not applicable for a line
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getEndX() {
        return endX;
    }

    public int getEndY() {
        return endY;
    }

    public void setEndX(int endX) {
        this.endX = endX;
    }

    public void setEndY(int endY) {
        this.endY = endY;
    }

    @Override
    public Color getColor() {
        return lineColor;
    }

    @Override
    public void setColor(Color color) {
        this.lineColor = color;
    }

    @Override
    public void setVertices(int[] xPoints, int[] yPoints) {
        //  throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setDotted(boolean isDotted) {
        this.isDotted = isDotted;
    }

    @Override
    public boolean isDotted() {
        return isDotted;
    }
}

////////////////////////////////////////////////////////////////

class Circle implements Shape, Fillable { 

    private int centerX, centerY, radius;
    private Color circleColor;
    private boolean filled;
    private boolean isDotted;
    private int dashLength = 5;
    private int spaceLength = 5;

    public Circle(int centerX, int centerY, int radius) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
        this.circleColor = Color.BLACK;
        this.isDotted = false;
    }

    private void drawDottedCircle(Graphics g) {
        // Draw a dotted circle
        g.setColor(circleColor);

        int x = centerX - radius;
        int y = centerY - radius;
        int diameter = radius * 2;

        int dashLength = isDotted ? 5 : diameter;  // Adjust the dash length based on the dotted property
        int spaceLength = isDotted ? 5 : 0;   // Adjust the space length based on the dotted property

        for (int step = 0; step < diameter; step += dashLength + spaceLength) {
            int arcStart = step;
            int arcExtent = dashLength;

            g.drawArc(x, y, diameter, diameter, arcStart, arcExtent);
        }
    }

    @Override
    public void draw(Graphics g) {
        if (isDotted) {
            g.setColor(circleColor);
            drawDottedCircle(g);
        } else if (filled) {
            g.setColor(circleColor);
            g.fillOval(centerX - radius, centerY - radius, 2 * radius, 2 * radius);
        } else {
            // Draw a solid circle
            g.setColor(circleColor);
            g.drawOval(centerX - radius, centerY - radius, 2 * radius, 2 * radius);
        }

    }

    @Override
    public int getCenterX() {
        return centerX;
    }

    @Override
    public int getCenterY() {
        return centerY;
    }

    @Override
    public void setRadius(int radius) {
        this.radius = radius;
    }

    @Override
    public Color getColor() {
        return circleColor;
    }

    @Override
    public void setColor(Color color) {
        this.circleColor = color;
    }

    @Override
    public void setVertices(int[] xPoints, int[] yPoints) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setDotted(boolean isDotted) {
        this.isDotted = isDotted;
    }

    @Override
    public boolean isDotted() {
        return isDotted;
    }

    @Override
    public void setFilled(boolean filled) {
        this.filled = filled;
    }
}

/////////////////////////////////////////////////////////////////////////////

class Triangle implements Shape , Fillable {

    private int[] xPoints;
    private int[] yPoints;
    private Color triangleColor;
    private boolean filled;
    private boolean isDotted;
    private int dashLength = 5; // dash length
    private int spaceLength = 5; //  space length

    public Triangle(int[] xPoints, int[] yPoints) {

        this.xPoints = xPoints;
        this.yPoints = yPoints;
        this.triangleColor = Color.BLACK;
        this.isDotted = false;

    }

    private void drawDottedTriangle(Graphics g) {
        // Draw a dotted triangle
        g.setColor(triangleColor);

        int dashLength = isDotted ? 5 : 0;  // Adjust the dash length based on the dotted property
        int spaceLength = isDotted ? 5 : 0;   // Adjust the space length based on the dotted property

        for (int i = 0; i < 3; i++) {
            int x1 = xPoints[i];
            int y1 = yPoints[i];
            int x2 = xPoints[(i + 1) % 3];
            int y2 = yPoints[(i + 1) % 3];

            int dx = x2 - x1;
            int dy = y2 - y1;
            int steps = Math.max(Math.abs(dx), Math.abs(dy));

            float xIncrement = (float) dx / steps;
            float yIncrement = (float) dy / steps;

            for (int step = 0; step < steps; step += dashLength + spaceLength) {
                int xStart = (int) (x1 + step * xIncrement);
                int yStart = (int) (y1 + step * yIncrement);
                int xEnd = (int) (x1 + (step + dashLength) * xIncrement);
                int yEnd = (int) (y1 + (step + dashLength) * yIncrement);

                g.drawLine(xStart, yStart, xEnd, yEnd);
            }
        }
    }

    @Override
    public void draw(Graphics g) {
        if (isDotted) {
            g.setColor(triangleColor);
            drawDottedTriangle(g);
        } else if (filled) {
            g.setColor(triangleColor);  // Set the color before filling
            Graphics2D g2d = (Graphics2D) g;
           g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
             // Ensure vertices are in counterclockwise order for proper filling
            int[] xFilled = {xPoints[0], xPoints[1], xPoints[2]};
            int[] yFilled = {yPoints[0], yPoints[1], yPoints[2]};
            g.fillPolygon(xPoints, yPoints, 3);
        } else {
            // Draw a solid triangle
            g.setColor(triangleColor);
            g.drawPolygon(xPoints, yPoints, 3);
        }
    }
    

    @Override
    public int getCenterX() {
        // Calculate the average x-coordinate of vertices for the center
        return (xPoints[0] + xPoints[1] + xPoints[2]) / 3;
    }

    @Override
    public int getCenterY() {
        // Calculate the average y-coordinate of vertices for the center
        return (yPoints[0] + yPoints[1] + yPoints[2]) / 3;
    }

    @Override
    public void setRadius(int radius) {
        // Not applicable for a triangle
    }

    @Override
    public Color getColor() {
        return triangleColor;
    }

    @Override
    public void setColor(Color color) {
        this.triangleColor = color;
    }

    public int[] getXPoints() {
        return xPoints;
    }

    public int[] getYPoints() {
        return yPoints;
    }

    @Override
    public void setVertices(int[] xPoints, int[] yPoints) {
        this.xPoints = xPoints;
        this.yPoints = yPoints;
    }

    @Override
    public void setDotted(boolean isDotted) {
        this.isDotted = isDotted;
    }

    @Override
    public boolean isDotted() {
        return isDotted;
    }

     @Override
    public void setFilled(boolean filled) {
        this.filled = filled;
    }
}

///////////////////////////////////////////////////////

class FilledWhiteCircle implements Shape {

    private int centerX, centerY, radius;
    private Color circleColor;

    public FilledWhiteCircle(int centerX, int centerY, int radius) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
        this.circleColor = Color.WHITE;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(circleColor);
        g.fillOval(centerX - radius, centerY - radius, 2 * radius, 2 * radius);
        // g.setColor(Color.BLACK);//for the comming shapes
    }

    @Override
    public int getCenterX() {
        return centerX;
    }

    @Override
    public int getCenterY() {
        return centerY;
    }

    @Override
    public void setRadius(int radius) {
        this.radius = radius;
    }

    @Override
    public Color getColor() {
        return circleColor;
    }

    @Override
    public void setColor(Color color) {
        this.circleColor = color;
    }

    @Override
    public void setVertices(int[] xPoints, int[] yPoints) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setDotted(boolean isDotted) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean isDotted() {
        //  throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        return false;
        //  throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

////////////////////////////////////////////////////


class Oval implements Shape, Fillable {

    private boolean filled;
    private boolean isDotted;
    private int startX, startY, width, height;
    private Color ovalColor;

    public Oval(int startX, int startY, int width, int height) {
        this.startX = startX;
        this.startY = startY;
        this.width = width;
        this.height = height;
        this.ovalColor = Color.BLACK;
        this.isDotted = false;
    }
    
     private void drawDottedOval(Graphics g) {
    // Draw a dotted oval
    g.setColor(ovalColor);

    int dashLength = isDotted ? 5 : 0;  // Adjust the dash length based on the dotted property
    int spaceLength = isDotted ? 5 : 0;   // Adjust the space length based on the dotted property

    int x = startX;
    int y = startY;
    int w = width - startX;
    int h = height - startY;

    for (int step = 0; step < 360; step += dashLength + spaceLength) {
        double angle = Math.toRadians(step);
        int xStart = (int) (x + w * 0.5 * Math.cos(angle));
        int yStart = (int) (y + h * 0.5 * Math.sin(angle));

        angle = Math.toRadians(step + dashLength);
        int xEnd = (int) (x + w * 0.5 * Math.cos(angle));
        int yEnd = (int) (y + h * 0.5 * Math.sin(angle));

        g.drawLine(xStart, yStart, xEnd, yEnd);
    }
}

    @Override
    public void draw(Graphics g) {
        g.setColor(ovalColor);  // Set the color before drawing

        if (isDotted) {
            drawDottedOval(g);
        } else {
            if (filled) {
                g.fillOval(Math.min(startX, width), Math.min(startY, height), Math.abs(width - startX), Math.abs(height - startY));
            } else {
                g.drawOval(Math.min(startX, width), Math.min(startY, height), Math.abs(width - startX), Math.abs(height - startY));
            }
        }
    }

    
    @Override
    public int getCenterX() {
        return (startX + width) / 2;
    }

    @Override
    public int getCenterY() {
        return (startY + height) / 2;
    }

    @Override
    public void setRadius(int radius) {
        // Not applicable for an oval
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getWidth() {
        return width;
    }

    public int getHight() {
        return height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHight(int height) {
        this.height = height;
    }

    @Override
    public Color getColor() {
        return ovalColor;
    }

    @Override
    public void setColor(Color color) {
        this.ovalColor = color;
    }

    @Override
    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    @Override
    public void setVertices(int[] xPoints, int[] yPoints) {
        // Not applicable for an oval
    }

    @Override
    public void setDotted(boolean isDotted) {
        this.isDotted = isDotted;
    }

    @Override
    public boolean isDotted() {
        return isDotted;
    }
}


/////////////////////////////////////////////////////

class Rect implements Shape, Fillable {

    private boolean filled;
    private boolean isDotted;
    private int startX, startY, width, height;
    private Color rectColor;

    public Rect(int startX, int startY, int width, int height) {
        this.startX = startX;
        this.startY = startY;
        this.width = width;
        this.height = height;
        this.rectColor = Color.BLACK;
        this.isDotted = false;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(rectColor);  // Set the color before drawing

        if (isDotted) {
            drawDottedRect(g);
        } else {
            if (filled) {
                g.fillRect(Math.min(startX, width), Math.min(startY, height), Math.abs(width - startX), Math.abs(height - startY));
            } else {
                g.drawRect(Math.min(startX, width), Math.min(startY, height), Math.abs(width - startX), Math.abs(height - startY));
            }
        }
    }

 private void drawDottedRect(Graphics g) {
    // Draw a dotted rectangle
    g.setColor(rectColor);

    int dashLength = isDotted ? 5 : 0;  // Adjust the dash length based on the dotted property
    int spaceLength = isDotted ? 5 : 0;   // Adjust the space length based on the dotted property

    int x1 = startX;
    int y1 = startY;
    int x2 = width;
    int y2 = height;

    // Draw top side
    for (int step = 0; step < x2 - x1; step += dashLength + spaceLength) {
        int xStart = x1 + step;
        int yStart = y1;
        int xEnd = Math.min(x1 + step + dashLength, x2);
        int yEnd = y1;
        g.drawLine(xStart, yStart, xEnd, yEnd);
    }

    // Draw right side
    for (int step = 0; step < y2 - y1; step += dashLength + spaceLength) {
        int xStart = x2;
        int yStart = y1 + step;
        int xEnd = x2;
        int yEnd = Math.min(y1 + step + dashLength, y2);
        g.drawLine(xStart, yStart, xEnd, yEnd);
    }

    // Draw bottom side
    for (int step = 0; step < x2 - x1; step += dashLength + spaceLength) {
        int xStart = x2 - step;
        int yStart = y2;
        int xEnd = Math.max(x2 - step - dashLength, x1);
        int yEnd = y2;
        g.drawLine(xStart, yStart, xEnd, yEnd);
    }

    // Draw left side
    for (int step = 0; step < y2 - y1; step += dashLength + spaceLength) {
        int xStart = x1;
        int yStart = y2 - step;
        int xEnd = x1;
        int yEnd = Math.max(y2 - step - dashLength, y1);
        g.drawLine(xStart, yStart, xEnd, yEnd);
    }
}



    @Override
    public int getCenterX() {
        return (startX + width) / 2;
    }

    @Override
    public int getCenterY() {
        return (startY + height) / 2;
    }

    @Override
    public void setRadius(int radius) {
        // Not applicable for a rectangle
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getWidth() {
        return width;
    }

    public int getHight() {
        return height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

   public void setHight(int height) {
    this.height = height;
}

    @Override
    public Color getColor() {
        return rectColor;
    }

    @Override
    public void setColor(Color color) {
        this.rectColor = color;
    }

    @Override
    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    @Override
    public void setVertices(int[] xPoints, int[] yPoints) {
        // Not applicable for a rectangle
    }

    @Override
    public void setDotted(boolean isDotted) {
        this.isDotted = isDotted;
    }

    @Override
    public boolean isDotted() {
        return isDotted;
    }
}

////////////////////////////////////////////////////////

 class FreeHand implements Shape {

    private List<Point> points;
    private Color drawColor;

    public FreeHand(List<Point> points) {
        this.points = points;
        this.drawColor = Color.BLACK; // Set a default color
    }

    // Additional constructor to set the color
    public FreeHand(List<Point> points, Color drawColor) {
        this.points = points;
        this.drawColor = drawColor;
    }

    public List<Point> getPoints() {
        return points;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(drawColor);  // Set the color before drawing
        for (int i = 1; i < points.size(); i++) {
            Point p1 = points.get(i - 1);
            Point p2 = points.get(i);
            g.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
    }

    @Override
    public int getCenterX() {
        return 0;
    }

    @Override
    public int getCenterY() {
        return 0;
    }

    @Override
    public void setRadius(int radius) {
    }

    
    public int getStartX() {
        return 0;
    }

    public int getStartY() {
        return 0;
    }

    
    public int getWidth() {
        return 0;
    }

 
    public int getHight() {
        return 0;
    }


    public void setWidth(int width) {
    }

  
    public void setHight(int height) {
    }

    @Override
    public Color getColor() {
        return drawColor;
    }

    @Override
    public void setColor(Color color) {
        this.drawColor = color;
    }

    @Override
    public void setVertices(int[] xPoints, int[] yPoints) {
        // Handle setting vertices if needed
    }

    @Override
    public void setDotted(boolean isDotted) {
        // Handle setting dotted property if needed
    }

    @Override
    public boolean isDotted() {
        return false;
    }
}
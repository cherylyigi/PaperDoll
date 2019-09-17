import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Ellipse2D;
import java.awt.event.MouseEvent;


public class UpperLeg extends Sprite {

    private Ellipse2D elli = null;
    double total = 0;
    int width = 0;
    int height = 0;
    int originalHeight = 0;
    
    public UpperLeg(int width, int height) {
        super();    
        setPoint(width/2, 0); 
        this.initialize(width, height);
    }
    
    
    private void initialize(int width, int height) {
        elli = new Ellipse2D.Double(0, 0, width, height);
        this.width = width;
        this.height = height;
        this.originalHeight = height;
    }
    public void reset() {
        //  System.out.println(originalHeight);
        //  System.out.println(height);
        // double ratio = ((double) originalHeight) / height;
        // System.out.println(ratio);
        
        
        AffineTransform rotate = AffineTransform.getRotateInstance(Math.toRadians(0 - total), fixPointx, fixPointy);
        transform(rotate); 

        // AffineTransform scale = AffineTransform.getScaleInstance(1, ratio);
        // transform(scale);

        height = originalHeight;
        total = 0;
    };
    
    public boolean pointInside(Point2D p) {
        AffineTransform fullTransform = this.getFullTransform();
        AffineTransform inverseTransform = null;
        try {
            inverseTransform = fullTransform.createInverse();
        } catch (NoninvertibleTransformException e) {
            e.printStackTrace();
        }
        Point2D newPoint = (Point2D)p.clone();
        inverseTransform.transform(newPoint, newPoint);
        return elli.contains(newPoint);
    }

    protected void drawSprite(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.draw(elli);
    }

    protected void handleMouseDragEvent(MouseEvent e) {
        Point2D newPoint = e.getPoint();
        Point2D oldPoint = lastPoint;
        switch (interactionMode) {
            case IDLE:
                ; // no-op (shouldn't get here)
                break;
            case DRAGGING:

                AffineTransform fullTransform = this.getFullTransform();
                AffineTransform inverseTransform = null;
                try {
                    inverseTransform = fullTransform.createInverse();
                } catch (NoninvertibleTransformException p) {
                    p.printStackTrace();
                }
        
                inverseTransform.transform(newPoint, newPoint);
                inverseTransform.transform(oldPoint, oldPoint);
                
                double newDegree = calculateDegree(newPoint);
                double oldDegree = calculateDegree(oldPoint);
            
                double oldTotal = total;
                total += newDegree - oldDegree;

                if (total > 90) {
                    AffineTransform rotate = AffineTransform.getRotateInstance(Math.toRadians(90 - oldTotal), fixPointx, fixPointy);
                    transform(rotate); 
                    total = 90;
                } else if (total < -90) {
                    AffineTransform rotate = AffineTransform.getRotateInstance(Math.toRadians(-90 - oldTotal), fixPointx, fixPointy);
                    transform(rotate); 
                    total = -90;
                } else {
                    AffineTransform rotate = AffineTransform.getRotateInstance(Math.toRadians(newDegree - oldDegree), fixPointx, fixPointy);
                    transform(rotate); 
                }
                if (canS) {
                    double y_diff = newPoint.getY() - oldPoint.getY();
                    double oldHeight = height;
                    height += y_diff;
                    double ratio = ((double) height) / oldHeight;
                    AffineTransform scale = AffineTransform.getScaleInstance(1, ratio);
                    transform(scale);
                }
                
                break;
            case ROTATING:
                break;
            case SCALING:
                ; // Provide scaling code here
                break;

        }
        lastPoint = e.getPoint();
    }

    private double calculateDegree(Point2D mousePoint) {
        double angle1 = Math.atan2(10, 0);
        double angle2 = Math.atan2(mousePoint.getY() - fixPointy, mousePoint.getX() - fixPointx);
        double degree = Math.toDegrees(angle2 - angle1);
        return degree;
    }

    public void scaling(double y_diff) {
        //height += y_diff;
        //elli = new Ellipse2D.Double(0, 0, width, height);
        AffineTransform scale = AffineTransform.getScaleInstance(0, y_diff);
        transform(scale);
        
    }
    
}

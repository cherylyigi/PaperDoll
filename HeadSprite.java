import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Ellipse2D;
import java.awt.event.MouseEvent;


public class HeadSprite extends Sprite {

    private Ellipse2D elli = null;
    double total = 0;

    public HeadSprite(int width, int height) {
        super();
        
        setPoint(width/2, height); 
        this.initialize(width, height);
    }
    
    
    private void initialize(int width, int height) {
        elli = new Ellipse2D.Double(0, 0, width, height);
        
    }
    
    public void reset() {
        AffineTransform rotate = AffineTransform.getRotateInstance(Math.toRadians(0 - total), fixPointx, fixPointy);
        transform(rotate); 
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
        //System.out.println(newPoint.getX());
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
                if (total > 50) {
                    AffineTransform rotate = AffineTransform.getRotateInstance(Math.toRadians(50 - oldTotal), fixPointx, fixPointy);
                    transform(rotate); 
                    total = 50;
                } else if (total < -50) {
                    AffineTransform rotate = AffineTransform.getRotateInstance(Math.toRadians(-50 - oldTotal), fixPointx, fixPointy);
                    transform(rotate); 
                    total = -50;
                } else {
                    AffineTransform rotate = AffineTransform.getRotateInstance(Math.toRadians(newDegree - oldDegree), fixPointx, fixPointy);
                    transform(rotate); 
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
        double angle1 = Math.atan2(-10, 0);
        //System.out.println(mousePoint.getX());
        double angle2 = Math.atan2(mousePoint.getY() - fixPointy, mousePoint.getX() - fixPointx);
        double degree = Math.toDegrees(angle2 - angle1);
        //System.out.println(degree);
        return degree;
    }
    
}

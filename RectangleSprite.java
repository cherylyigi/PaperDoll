import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.event.MouseEvent;


/**
 * A simple demo of how to create a rectangular sprite.
 * 
 * Michael Terry & Jeff Avery
 */
public class RectangleSprite extends Sprite {

    private RoundRectangle2D rect = null;
    private double fromX = 0;
    private double fromY = 0;

    /**
     * Creates a rectangle based at the origin with the specified
     * width and height
     */
    public RectangleSprite(int width, int height) {
        super();
        this.initialize(width, height);
    }
    /**
     * Creates a rectangle based at the origin with the specified
     * width, height, and parent
     */
    public RectangleSprite(int width, int height, Sprite parentSprite) {
        super(parentSprite);
        this.initialize(width, height);
    }
    
    private void initialize(int width, int height) {
        rect = new RoundRectangle2D.Double(0, 0, width, height, 50, 50);
    }

    public void reset() {
        AffineTransform move = AffineTransform.getTranslateInstance(-fromX, -fromY);
        transform(move); 
        fromX = 0;
        fromY = 0;
    };
    
    /**
     * Test if our rectangle contains the point specified.
     */
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
        return rect.contains(newPoint);
    }

    protected void drawSprite(Graphics2D g) {
        g.setColor(Color.BLACK);
        
        g.draw(rect);
    }

    protected void handleMouseDragEvent(MouseEvent e) {
        Point2D newPoint = e.getPoint();
        Point2D oldPoint = lastPoint;
        switch (interactionMode) {
            case IDLE:
                ; // no-op (shouldn't get here)
                break;
            case DRAGGING:
                double x_diff = newPoint.getX() - oldPoint.getX();
                double y_diff = newPoint.getY() - oldPoint.getY();
                fromX += x_diff;
                fromY += y_diff;
                AffineTransform move = AffineTransform.getTranslateInstance(x_diff, y_diff);
                transform(move);
                break;
            case ROTATING:
                break;
            case SCALING:
                ; // Provide scaling code here
                break;

        }
        lastPoint = e.getPoint();
    }
    
}

package cosc202.andie;

import java.awt.Point;
import java.awt.image.*;

/**
 * <p>
 * ImageOperation to apply a Sharpen filter.
 * </p>
 * 
 * <p>
 * A Sharpen filter enhances the differences between neighbouring values, 
 * and can be implemented by convolution with the kernel.
 * </p>
 * 
 * @see java.awt.image.ConvolveOp
 * @author Anthony Deng
 * @version 1.0
 */
public class SharpenFilter implements ImageOperation, java.io.Serializable {
    /**
     * <p>
     * The coordinates of the corners of the selected area. If there is no selected area, these will be equal to -1.
     * </p>
     */
    private int x1, x2, y1, y2 = -1;

    /**
     * <p>
     * Construct a Sharpen filter
     * </p>
     */
    SharpenFilter(){
    }

    /**
     * <p>
     * Construct a Sharpen filter to be applied from p1 to p2
     * </p>
     */
    SharpenFilter(Point p1, Point p2){
        this.x1 = (int) p1.getX();
        this.x2 = (int) p2.getX();
        this.y1 = (int) p1.getY();
        this.y2 = (int) p2.getY();
    }

    /**
     * <p>
     * Apply a Sharpen filter to an image
     * </p>
     * 
     * <p>
     * The Sharpen filter is implemented via convolution. There is no size
     * to the filter as it is applied to the whole image. 
     * </p>
     * 
     * @param input The image to apply the Sharpen filter to.
     * @return The resulting (sharpened) image.
     * @throws Exception Raised if an unexpected {@code Exception} occurs.
     */
    public BufferedImage apply(BufferedImage input) throws Exception {
        BufferedImage output = null;
        try{
            float[] array = {0, -1/2.0f, 0, -1/2.0f, 3, -1/2.0f, 0, -1/2.0f, 0};

            Kernel kernel = new Kernel(3, 3, array);
            AndieConvolveOp convOp = new AndieConvolveOp(kernel);
            output = new BufferedImage(input.getColorModel(), input.copyData(null), input.isAlphaPremultiplied(), null);
            if (x1 != -1 && x2 != -1 && y1 != -1 && y2 != -1) convOp.filter(input, output, x1, y1, x2, y2);
            else convOp.filter(input, output);
        }catch(Exception ex){
            UserMessage.showWarning(UserMessage.NULL_FILE_WARN);
        }
        return output;
    }
}

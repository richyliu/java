import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.text.*;
import java.util.*;
import java.util.List; // resolves problem with java.awt.List and java.util.List

/**
 * A class that represents a picture.  This class inherits from 
 * SimplePicture and allows the student to add functionality to
 * the Picture class.  
 * 
 * @author Barbara Ericson ericson@cc.gatech.edu
 */
public class Picture extends SimplePicture 
{
  ///////////////////// constructors //////////////////////////////////
  
  /**
   * Constructor that takes no arguments 
   */
  public Picture ()
  {
    /* not needed but use it to show students the implicit call to super()
     * child constructors always call a parent constructor 
     */
    super();  
  }
  
  /**
   * Constructor that takes a file name and creates the picture 
   * @param fileName the name of the file to create the picture from
   */
  public Picture(String fileName)
  {
    // let the parent class handle this fileName
    super(fileName);
  }
  
  /**
   * Constructor that takes the width and height
   * @param height the height of the desired picture
   * @param width the width of the desired picture
   */
  public Picture(int height, int width)
  {
    // let the parent class handle this width and height
    super(width,height);
  }
  
  /**
   * Constructor that takes a picture and creates a 
   * copy of that picture
   * @param copyPicture the picture to copy
   */
  public Picture(Picture copyPicture)
  {
    // let the parent class do the copy
    super(copyPicture);
  }
  
  /**
   * Constructor that takes a buffered image
   * @param image the buffered image to use
   */
  public Picture(BufferedImage image)
  {
    super(image);
  }
  
  ////////////////////// methods ///////////////////////////////////////
  
  /**
   * Method to return a string with information about this picture.
   * @return a string with information about the picture such as fileName,
   * height and width.
   */
  public String toString()
  {
    String output = "Picture, filename " + getFileName() + 
      " height " + getHeight() 
      + " width " + getWidth();
    return output;
    
  }
  
  /** Method to set the blue to 0 */
  public void zeroBlue()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        pixelObj.setBlue(0);
      }
    }
  }
  
  /** Method that mirrors the picture around a 
    * vertical mirror in the center of the picture
    * from left to right */
  public void mirrorVertical()
  {
    Pixel[][] pixels = this.getPixels2D();
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int width = pixels[0].length;
    for (int row = 0; row < pixels.length; row++)
    {
      for (int col = 0; col < width / 2; col++)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][width - 1 - col];
        rightPixel.setColor(leftPixel.getColor());
      }
    } 
  }
  
  /** Mirror just part of a picture of a temple */
  public void mirrorTemple()
  {
    int mirrorPoint = 276;
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int count = 0;
    Pixel[][] pixels = this.getPixels2D();
    
    // loop through the rows
    for (int row = 27; row < 97; row++)
    {
      // loop from 13 to just before the mirror point
      for (int col = 13; col < mirrorPoint; col++)
      {
        
        leftPixel = pixels[row][col];      
        rightPixel = pixels[row]                       
                         [mirrorPoint - col + mirrorPoint];
        rightPixel.setColor(leftPixel.getColor());
      }
    }
  }
  
  /** copy from the passed fromPic to the
    * specified startRow and startCol in the
    * current picture
    * @param fromPic the picture to copy from
    * @param startRow the start row to copy to
    * @param startCol the start col to copy to
    */
  public void copy(Picture fromPic, 
                 int startRow, int startCol)
  {
    Pixel fromPixel = null;
    Pixel toPixel = null;
    Pixel[][] toPixels = this.getPixels2D();
    Pixel[][] fromPixels = fromPic.getPixels2D();
    for (int fromRow = 0, toRow = startRow; 
         fromRow < fromPixels.length &&
         toRow < toPixels.length; 
         fromRow++, toRow++)
    {
      for (int fromCol = 0, toCol = startCol; 
           fromCol < fromPixels[0].length &&
           toCol < toPixels[0].length;  
           fromCol++, toCol++)
      {
        fromPixel = fromPixels[fromRow][fromCol];
        toPixel = toPixels[toRow][toCol];
        toPixel.setColor(fromPixel.getColor());
      }
    }   
  }

  /** Method to create a collage of several pictures */
  public void createCollage()
  {
    Picture flower1 = new Picture("flower1.jpg");
    Picture flower2 = new Picture("flower2.jpg");
    this.copy(flower1,0,0);
    this.copy(flower2,100,0);
    this.copy(flower1,200,0);
    Picture flowerNoBlue = new Picture(flower2);
    flowerNoBlue.zeroBlue();
    this.copy(flowerNoBlue,300,0);
    this.copy(flower1,400,0);
    this.copy(flower2,500,0);
    this.mirrorVertical();
    this.write("collage.jpg");
  }
  
  
  /** Method to show large changes in color 
    * @param edgeDist the distance for finding edges
    */
  public void edgeDetection(int edgeDist)
  {
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    Pixel[][] pixels = this.getPixels2D();
    Color rightColor = null;
    for (int row = 0; row < pixels.length; row++)
    {
      for (int col = 0; 
           col < pixels[0].length-1; col++)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][col+1];
        rightColor = rightPixel.getColor();
        if (leftPixel.colorDistance(rightColor) > 
            edgeDist)
          leftPixel.setColor(Color.BLACK);
        else
          leftPixel.setColor(Color.WHITE);
      }
    }
  }
  
  
  /// my own added methods ///
  
  /** Method to set the everything to 0 except blue */
  public void keepOnlyBlue()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
		// set everything to 0, ignoring blue
        pixelObj.setRed(0);
        pixelObj.setGreen(0);
      }
    }
  }
  
  /** Method to negate all pixels to 255 - pixel */
  public void negate()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
		// invert the pixel by subracting it from 255
        pixelObj.setRed(255 - pixelObj.getRed());
        pixelObj.setGreen(255 - pixelObj.getGreen());
        pixelObj.setBlue(255 - pixelObj.getBlue());
      }
    }
  }
  
  /** Method to set all pixels to average of colors */
  public void grayscale()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
		// get the average of red, green, and blue
		int avg = (int)((pixelObj.getRed() + pixelObj.getGreen() + pixelObj.getBlue()) / 3.0);
		// set the pixel to the average color
        pixelObj.setColor(new Color(avg, avg, avg));
      }
    }
  }
  
  /**
   * Method to set "fix" underwater fish by increasing difference between green
   * and blue colors
   */
  public void fixUnderwater()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        if (pixelObj.getGreen() > pixelObj.getBlue()) {
			pixelObj.setGreen((int)(pixelObj.getGreen() * 1.1));
			pixelObj.setBlue((int)(pixelObj.getBlue() * 0.9));
		} else {
			pixelObj.setGreen((int)(pixelObj.getGreen() * 0.9));
			pixelObj.setBlue((int)(pixelObj.getBlue() * 1.1));
		}
      }
    }
  }
  
  /**
   * Method to resize picture to different size by pixelating
   * @param	size	Breaks a picture into a grid of this size
   */
  public void pixelate(int size)
  {
    Pixel[][] pixels = this.getPixels2D();
    for (int i = 0; i < pixels.length; i++)
    {
	  // totals for red, green, and blue
	  double[] totals = new double[]{0, 0, 0};
	  // number of pixels that have been added
	  int count = 0;
      for (int j = 0; j < pixels[0].length; j++)
      {
		if (j % size == 0) {
			totals = new double[] {0, 0, 0};
			count = 0;
			// add centered on the current (i, j) value, using max/min to ensure bounds
			for (int row = i/size * size; row < Math.min(i/size * size + size, pixels.length); row++) {
				for (int col = j/size * size; col < Math.min(j/size * size + size, pixels[0].length); col++) {
					totals[0] += pixels[row][col].getRed();
					totals[1] += pixels[row][col].getGreen();
					totals[2] += pixels[row][col].getBlue();
					count++;
				}
			}
		}
		// set the pixels values to the average
        pixels[i][j].setRed((int)(totals[0]/count));
        pixels[i][j].setGreen((int)(totals[1]/count));
        pixels[i][j].setBlue((int)(totals[2]/count));
      }
      if (i % 50 == 0) {
		  System.out.printf("[pixelate]: %.1f%%\n", (double)i/pixels.length * 100);
	  }
    }
  }
  
  /**
   * Method to blur the picture by changing the pixel to a average of the pixels
   * around it
   * @param	size	Side length of the blur area square
   */
  public Picture blur(int size)
  {
    Pixel[][] pixels = this.getPixels2D();
    Picture result = new Picture(pixels.length, pixels[0].length);
    Pixel[][] resultPixels = result.getPixels2D();
    
    for (int i = 0; i < pixels.length; i++)
    {
      for (int j = 0; j < pixels[0].length; j++)
      {
		// totals for red, green, and blue
		int[] totals = new int[]{0, 0, 0};
		// number of pixels that have been added
		int count = 0;
		// add centered on the current (i, j) value, using max/min to ensure bounds
		for (int row = Math.max(i-size/2, 0); row < Math.min(i-size/2+size, pixels.length); row++) {
			for (int col = Math.max(j-size/2, 0); col < Math.min(j-size/2+size, pixels[0].length); col++) {
				totals[0] += pixels[row][col].getRed();
				totals[1] += pixels[row][col].getGreen();
				totals[2] += pixels[row][col].getBlue();
				count++;
			}
		}
		// set the pixels values to the average
        resultPixels[i][j].setRed(totals[0]/count);
        resultPixels[i][j].setGreen(totals[1]/count);
        resultPixels[i][j].setBlue(totals[2]/count);
      }
      // print progress statements
      if (i % 50 == 0) {
		  System.out.printf("[blur]: %.1f%%\n", (double)i/pixels.length * 100);
	  }
    }
    
    return result;
  }
  
  /**
   * Method to enhance the picture by changing the pixel to the difference from
   * the average of the pixels around it
   * @param	size	Side length of the enhance area square
   */
  public Picture enhance(int size)
  {
    Pixel[][] pixels = this.getPixels2D();
    Picture result = new Picture(pixels.length, pixels[0].length);
    Pixel[][] resultPixels = result.getPixels2D();
    
    for (int i = 0; i < pixels.length; i++)
    {
      for (int j = 0; j < pixels[0].length; j++)
      {
		// totals for red, green, and blue
		int[] totals = new int[]{0, 0, 0};
		// number of pixels that have been added
		int count = 0;
		// add centered on the current (i, j) value, using max/min to ensure bounds
		for (int row = Math.max(i-size/2, 0); row < Math.min(i-size/2+size, pixels.length); row++) {
			for (int col = Math.max(j-size/2, 0); col < Math.min(j-size/2+size, pixels[0].length); col++) {
				totals[0] += pixels[row][col].getRed();
				totals[1] += pixels[row][col].getGreen();
				totals[2] += pixels[row][col].getBlue();
				count++;
			}
		}
		// set the pixels values by multiplying original by 2 and subracting average
        resultPixels[i][j].setRed(2 * pixels[i][j].getRed() - totals[0]/count);
        resultPixels[i][j].setGreen(2 * pixels[i][j].getGreen() - totals[1]/count);
        resultPixels[i][j].setBlue(2 * pixels[i][j].getBlue() - totals[2]/count);
      }
      // print progress statements
      if (i % 50 == 0) {
		  System.out.printf("[enhance]: %.1f%%\n", (double)i/pixels.length * 100);
	  }
    }
    
    return result;
  }
  
  /**
   * Method to shift the picture and wrap it around.
   * @param	percent	Percent of the image width to shift it to the right
   */
  public Picture shiftRight(int percent)
  {
    Pixel[][] pixels = this.getPixels2D();
    Picture result = new Picture(pixels.length, pixels[0].length);
    Pixel[][] resultPixels = result.getPixels2D();
    
    for (int i = 0; i < pixels.length; i++)
    {
      for (int j = 0; j < pixels[0].length; j++)
      {
		// shift the image by the percentage
		int x = (int)(j - pixels[0].length * (percent/100.0));
		// wrap around if necessary
		if (x < 0) x += pixels[0].length;
		
        resultPixels[i][j].setColor(pixels[i][x].getColor());
      }
    }
    
    return result;
  }
  
  /**
   * Method to shift the picture right in a step-wise fashion
   * @param	shiftCount	Amount to shift right on each steps
   * @param steps		Total number of steps to shift with
   */
  public Picture stairStep(int shiftCount, int steps)
  {
    Pixel[][] pixels = this.getPixels2D();
    Picture result = new Picture(pixels.length, pixels[0].length);
    Pixel[][] resultPixels = result.getPixels2D();
    
    for (int i = 0; i < pixels.length; i++)
    {
      for (int j = 0; j < pixels[0].length; j++)
      {
		// get the current stair step, and multiply by shiftCount to get shift width
		int x = (int)(j - shiftCount * (int)(i / ((double)pixels.length / steps)));
		// wrap around if necessary
		if (x < 0) x += pixels[0].length;
		
        resultPixels[i][j].setColor(pixels[i][x].getColor());
      }
    }
    
    return result;
  }
  
  /**
   * Method to turn the picture clockwise 90 degrees
   */
  public Picture turn90()
  {
    Pixel[][] pixels = this.getPixels2D();
    Picture result = new Picture(pixels[0].length, pixels.length);
    Pixel[][] resultPixels = result.getPixels2D();
    
    for (int i = 0; i < pixels.length; i++)
    {
      for (int j = 0; j < pixels[0].length; j++)
      {
		// swap x and y and invert x to rotate the image
		int y = j;
		int x = pixels.length - i - 1;
		
        resultPixels[y][x].setColor(pixels[i][j].getColor());
      }
    }
    
    return result;
  }
  
  /**
   * Method to zoom the picture in the upper left quarter
   */
  public Picture zoomUpperLeft()
  {
    Pixel[][] pixels = this.getPixels2D();
    Picture result = new Picture(pixels.length, pixels[0].length);
    Pixel[][] resultPixels = result.getPixels2D();
    
    for (int i = 0; i < pixels.length; i++)
    {
      for (int j = 0; j < pixels[0].length; j++)
      {
		// divide x & y by 2 to get upper left corner
        resultPixels[i][j].setColor(pixels[i/2][j/2].getColor());
      }
    }
    
    return result;
  }
  
  /**
   * Method to tile the image into 4 by shrinking it 25% and mirroring it.
   */
  public Picture tileMirror()
  {
    Pixel[][] pixels = this.getPixels2D();
    Picture result = new Picture(pixels.length, pixels[0].length);
    Pixel[][] resultPixels = result.getPixels2D();
    
    for (int i = 0; i < pixels.length; i++)
    {
      for (int j = 0; j < pixels[0].length; j++)
      {
		int y = i;
		int x = j;
		// reduce by factor of 2 and flip if necessary
		if (i < pixels.length/2)
			y = i*2;
		else
			y = (pixels.length - i - 1) * 2;
		// same thing in the x direction
		if (j < pixels[0].length/2)
			x = j*2;
		else
			x = (pixels[0].length - j - 1) * 2;
		
        resultPixels[i][j].setColor(pixels[y][x].getColor());
      }
    }
    
    return result;
  }
  
  /** Method to add a watermark to the image */
  public void watermark()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (int i = 0; i < pixels.length; i++)
    {
      for (int j = 0; j < pixels[0].length; j++)
      {
		int y = i/30;
		int x = j/30;
		int offset = y % 4 == 0 ? 20 : -20;
		if (y % 2 == 0 && x % 2 == 0 && (x + y) % 4 == 0) {
			pixels[i][j].setRed(offset + pixels[i][j].getRed());
			pixels[i][j].setBlue(offset + pixels[i][j].getBlue());
			pixels[i][j].setGreen(offset + pixels[i][j].getGreen());
		}
      }
    }
  }
  
  /** Method to detect edges by comparing each pixel to the pixel below */
  public Picture edgeDetectionBelow(int threashold)
  {
    Pixel[][] pixels = this.getPixels2D();
    Picture result = new Picture(pixels.length, pixels[0].length);
    Pixel[][] resultPixels = result.getPixels2D();

    for (int i = 0; i < pixels.length-1; i++)
    {
      for (int j = 0; j < pixels[0].length; j++)
      {
		int val = 255;
		// set pixel to black if edge detected by difference in colors
		if (pixels[i][j].colorDistance(pixels[i+1][j].getColor()) > threashold)
			val = 0;
		
        resultPixels[i][j].setRed(val);
        resultPixels[i][j].setBlue(val);
        resultPixels[i][j].setGreen(val);
      }
    }
    
    return result;
  }
  
  /** Method to make a green screen */
  public Picture greenScreen()
  {
	// the 2 images to impose on the background
    Picture pic1 = new Picture("images/kitten1GreenScreen.jpg");
    Picture pic2 = new Picture("images/mouse1GreenScreen.jpg");
    // the background
    Picture background = new Picture("images/IndoorHouseLibraryBackground.jpg");
    Pixel[][] pixels1 = pic1.getPixels2D();
    Pixel[][] pixels2 = pic2.getPixels2D();
    Pixel[][] bg = background.getPixels2D();
    // hold the final picture
    Picture resultPicture = new Picture(bg.length, bg[0].length);
    Pixel[][] result = resultPicture.getPixels2D();
    
    // where to position the images
    int offset1y = 300;
    int offset1x = 500;
    int offset2y = 480;
    int offset2x = 180;
    Color green = new Color(51, 204, 51);
    int greenThreashold = 100;

    for (int i = 0; i < bg.length-1; i++)
    {
      for (int j = 0; j < bg[0].length; j++)
      {
		// start by setting the result to the backgroun
        result[i][j].setColor(bg[i][j].getColor());
        // later can overwrite it with the cat/mouse image
		
		// put the first image on
		if (
			// if y values are within bounds
			i > offset1y && i < offset1y + pixels1.length &&
			// and x values are within bounds
			j > offset1x && j < offset1x + pixels1[0].length &&
			// and pixel is not green-ish
			pixels1[i-offset1y][j-offset1x].colorDistance(green) > greenThreashold) {
			result[i][j].setColor(pixels1[i-offset1y][j-offset1x].getColor());
		}
		
		// put the second image on
		if (
			// if y values are within bounds
			i > offset2y && i < offset2y + pixels2.length &&
			// and x values are within bounds
			j > offset2x && j < offset2x + pixels2[0].length &&
			// and pixel is not green-ish
			pixels2[i-offset2y][j-offset2x].colorDistance(green) > greenThreashold) {
			result[i][j].setColor(pixels2[i-offset2y][j-offset2x].getColor());
		}
      }
    }
    
    return resultPicture;
  }
  
  /**
   * Method to rotate the image by a certain degree
   */
  public Picture rotate(double theta)
  {
    Pixel[][] pixels = this.getPixels2D();
    Picture result = new Picture(pixels.length, pixels[0].length);
    Pixel[][] resultPixels = result.getPixels2D();
    
    double cy = pixels[0].length/2;
    double cx = pixels.length/2;
    double l = Math.sqrt(Math.pow(cx, 2) + Math.pow(cy, 2));
    double beta = theta + Math.atan2(cy, cx);
    
    // offset of top left corner of rotated image
    int offsety = (int)(cy - l * Math.sin(beta));
    int offsetx = (int)(cx - l * Math.cos(beta));
    
    for (int i = -offsety; i < pixels.length; i++)
    {
      for (int j = -offsetx; j < pixels[0].length; j++)
      {
		int y = (int)(i * Math.cos(theta) - j * Math.sin(theta));
		int x = (int)(i * Math.sin(theta) + j * Math.cos(theta));
		
		if (y >= 0 && y < pixels.length && x >= 0 && x < pixels[0].length && i+offsety < pixels.length && j+offsetx < pixels[0].length)
			resultPixels[i+offsety][j+offsetx].setColor(pixels[y][x].getColor());
      }
    }
    
    return result;
  }
  
  /* Main method for testing - each class in Java can have a main 
   * method 
   */
  public static void main(String[] args) 
  {
    Picture beach = new Picture("images/beach.jpg");
    beach.explore();
    beach.zeroBlue();
    beach.explore();
  }
  
} // this } is the end of class Picture, put all new methods before this
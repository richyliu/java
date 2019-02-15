/**
 * This class contains class (static) methods
 * that will help you test the Picture class 
 * methods.  Uncomment the methods and the code
 * in the main to test.
 * 
 * @author Barbara Ericson 
 */
public class PictureTester
{
  /** Method to test zeroBlue */
  public static void testZeroBlue()
  {
    Picture beach = new Picture("beach.jpg");
    beach.explore();
    beach.zeroBlue();
    beach.explore();
  }
  
  /** Method to test mirrorVertical */
  public static void testMirrorVertical()
  {
    Picture caterpillar = new Picture("caterpillar.jpg");
    caterpillar.explore();
    caterpillar.mirrorVertical();
    caterpillar.explore();
  }
  
  /** Method to test mirrorTemple */
  public static void testMirrorTemple()
  {
    Picture temple = new Picture("images/temple.jpg");
    temple.explore();
    temple.mirrorTemple();
    temple.explore();
  }
  
  /** Method to test the collage method */
  public static void testCollage()
  {
    Picture canvas = new Picture("640x480.jpg");
    canvas.createCollage();
    canvas.explore();
  }
  
  /** Method to test edgeDetection */
  public static void testEdgeDetection()
  {
    Picture swan = new Picture("swan.jpg");
    swan.edgeDetection(10);
    swan.explore();
  }
  
  
  /// my own testers ///
  
  /** Method to test keepOnlyBlue */
  public static void testKeepOnlyBlue()
  {
    Picture beach = new Picture("images/beach.jpg");
    beach.explore();
    beach.keepOnlyBlue();
    beach.explore();
  }
  
  /** Method to test negate */
  public static void testNegate()
  {
    Picture beach = new Picture("images/beach.jpg");
    beach.explore();
    beach.negate();
    beach.explore();
  }
  
  /** Method to test grayscale */
  public static void testGrayscale()
  {
    Picture beach = new Picture("images/beach.jpg");
    beach.explore();
    beach.grayscale();
    beach.explore();
  }
  
  /** Method to test fixUnderwater */
  public static void testFixUnderwater()
  {
    Picture beach = new Picture("images/water.jpg");
    beach.explore();
    beach.fixUnderwater();
    beach.explore();
  }
  
  /** Method to test pixelate */
  public static void testPixelate()
  {
    Picture beach = new Picture("images/beach.jpg");
    beach.explore();
    beach.pixelate(10);
    beach.explore();
  }
  
  /** Method to test blur */
  public static void testBlur()
  {
    Picture beach = new Picture("images/beach.jpg");
    beach.explore();
    beach.blur(10).explore();
  }
  
  /** Method to test enhance */
  public static void testEnhance()
  {
    Picture water = new Picture("images/water.jpg");
    water.explore();
    water.enhance(10).explore();
  }
  
  /** Method to test shiftRight */
  public static void testShiftRight()
  {
    Picture motorcycle = new Picture("images/redMotorcycle.jpg");
    motorcycle.explore();
    motorcycle.shiftRight(10).explore();
  }
  
  /** Method to test stairStep */
  public static void testStairStep()
  {
    Picture motorcycle = new Picture("images/redMotorcycle.jpg");
    motorcycle.explore();
    motorcycle.stairStep(10, 10).explore();
  }
  
  /** Method to test turn90 */
  public static void testTurn90()
  {
    Picture motorcycle = new Picture("images/redMotorcycle.jpg");
    motorcycle.explore();
    motorcycle.turn90().explore();
  }
  
  /** Method to test zoomUpperLeft */
  public static void testZoomUpperLeft()
  {
    Picture motorcycle = new Picture("images/arch.jpg");
    motorcycle.explore();
    Picture shifted = motorcycle.zoomUpperLeft();
    shifted.explore();
  }
  
  /** Method to test tileMirror */
  public static void testTileMirror()
  {
    Picture pic = new Picture("images/butterfly1.jpg");
    pic.explore();
    pic.tileMirror().explore();
  }
  
  /** Method to test edgeDetectionBelow */
  public static void testEdgeDetectionBelow()
  {
    Picture pic = new Picture("images/swan.jpg");
    pic.explore();
    pic.edgeDetectionBelow(20).explore();
  }
  
  /** Method to test greenScreen */
  public static void testGreenScreen()
  {
    Picture pic = new Picture("images/kitten1GreenScreen.jpg");
    pic.greenScreen().explore();
  }
  
  
  /** Main method for testing.  Every class can have a main
    * method in Java */
  public static void main(String[] args)
  {
    // uncomment a call here to run a test
    // and comment out the ones you don't want
    // to run
    //testZeroBlue();
    //testKeepOnlyBlue();
    //testKeepOnlyRed();
    //testKeepOnlyGreen();
    //testNegate();
    //testGrayscale();
    //testFixUnderwater();
    //testPixelate();
    //testBlur();
    //testEnhance();
    //testShiftRight();
    //testStairStep();
    //testTurn90();
    //testZoomUpperLeft();
    //testTileMirror();
    //testWatermark()
    //testEdgeDetectionBelow();
    testGreenScreen();
    //testMirrorTemple();
    //testMirrorArms();
    //testMirrorGull();
    //testMirrorDiagonal();
    //testCollage();
    //testCopy();
    //testEdgeDetection();
    //testEdgeDetection2();
    //testChromakey();
    //testEncodeAndDecode();
    //testGetCountRedOverValue(250);
    //testSetRedToHalfValueInTopHalf();
    //testClearBlueOverValue(200);
    //testGetAverageForColumn(0);
  }
}
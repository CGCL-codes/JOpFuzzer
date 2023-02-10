public class test{
  public static void main(String[] args) {
    	 CanvasFrame canvas = new CanvasFrame("threshhold");
    	 CanvasFrame cnvs=new CanvasFrame("Polygon");
        IplImage orgImg = cvLoadImage("images/finding-red-color-spot.jpg");
        IplImage thresholdImage = hsvThreshold(orgImg);
        cvSaveImage("hsvthreshold.jpg", thresholdImage);
      
        CvMemStorage storage=CvMemStorage.create();
        
        CvSeq squares = new CvContour();
        System.out.println("=====thresholdImage2======="+thresholdImage.depth());
        squares = cvCreateSeq(0, sizeof(CvContour.class), sizeof(CvSeq.class), storage);
        cvFindContours(thresholdImage, storage, squares, Loader.sizeof(CvContour.class), CV_RETR_EXTERNAL, CV_CHAIN_APPROX_SIMPLE);
        CvSeq ss=null;
        for (int i=0; i<1; i++)
        {
            cvDrawContours(thresholdImage, squares, CvScalar.WHITE, CV_RGB(248, 18, 18), 1, -1, 8);
            ss=cvApproxPoly(squares, sizeof(CvContour.class), storage, CV_POLY_APPROX_DP, 8, 0);
        }
        
        IplConvKernel mat=cvCreateStructuringElementEx(7, 7, 3, 3, CV_SHAPE_RECT, null);
        cvDilate(thresholdImage, thresholdImage, mat, CV_C);
        cvErode(thresholdImage, thresholdImage, mat, CV_C);
        cnvs.showImage(thresholdImage);

    }}

package tree.util;

import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import tree.model.TreeMeasurementDots;
import tree.vo.MeasurementResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import static java.lang.Math.*;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static org.opencv.core.Core.*;
import static org.opencv.imgproc.Imgproc.*;


/**
 * 1. @Description:
 * 2. @Author: TianGuisong
 * 3. @Date: Created in 6:09 PM 2018/9/16
 */
public class ImageProcessor {

    private static int dis = 200;
    private static int hold_canny = 120;
    private static int hold_center = 10;
    private static int POINTDIS = 12;
    private static int ROWS = 4000;

    private static Logger logger = Logger.getLogger("calculate process ");

    private static int C_LX[][] = new int[200][200];
    static{
        for ( int L = 1; L<200; L++ ){
            for ( int X = 1; X<200; X++ ){
                int r = (int)round(X*sqrt( 1- pow(X*1.0/( X + 2*PI*L), 2) ));
                C_LX[L][r] = X;
            }
        }
    }

    /**
     * a little correction on DBH fron X
     * @param L, distance between a tree and the mobile phone
     * @param X, the precaculated DBH
     * @return
     */
    private static double getR(double L, double X){
        return C_LX[(int)ceil(L)][(int)ceil(X)];
    }

    private static List<Point> getTwoPoints(Mat src){
        List<Mat> rmat = new ArrayList<>();
        MeasurementResult ans = new MeasurementResult();
        // transfer to hsv model
        Mat hsv = new Mat();
        rmat.add(hsv);
        cvtColor(src, hsv, COLOR_BGR2HSV);

        // filter piexls if not red
        Mat mat_red = new Mat();
        Mat temp = new Mat();
        rmat.add(mat_red);
        rmat.add(temp);
        inRange(hsv, new Scalar(158,50,190), new Scalar(180,255,255), mat_red);
        inRange(hsv, new Scalar(0,50,190), new Scalar(6,255,255), temp);
        add(temp, mat_red, mat_red);

        // ger rid of mid line
        Mat linesP = new Mat();
        rmat.add(linesP);
        HoughLinesP(mat_red, linesP, 1, Math.PI/180, 50, 100, 20);
        for (int x = 0; x < linesP.rows(); x++) {
            double[] l = linesP.get(x, 0);
            if (l[2] - l[0] > 300 && Math.abs(l[3] - l[0]) < 100) {
                int line_y = (int)(l[1]+l[3])/2;
                mat_red.rowRange(max(line_y-50, 0), min(line_y+50, mat_red.rows()-1)).colRange((int)l[0]+1, (int)l[2]-1).setTo(new Scalar(0));
            }
        }

        Mat element_5 = getStructuringElement(MORPH_CROSS, new Size(5,5));
        rmat.add(element_5);
        dilate(mat_red, mat_red, element_5);

        // find bright piexls
        Mat mat_bright = new Mat();
        rmat.add(mat_bright);
        cvtColor(src, mat_bright, COLOR_BGR2GRAY);
        threshold(mat_bright, mat_bright, 220, 255, Imgproc.THRESH_BINARY);
        boxFilter(mat_bright, mat_bright, 0, new Size(9, 9));
        threshold(mat_bright, mat_bright, 0, 255,  0);

        // get border of red and bright piexls
        Mat result = new Mat();
        rmat.add(result);
        multiply(mat_bright, mat_red, result, 1.0, mat_bright.type());
        dilate(result, result ,element_5);

        // find two red circls of the two lasers accordingly
        Mat mat_circles = new Mat();
        rmat.add(mat_circles);
        HoughCircles(result, mat_circles, HOUGH_GRADIENT, 1, dis, hold_canny, hold_center, 5, 40);

        List<Point> circles = new ArrayList<>();
        for (int x = 0; x < mat_circles.cols(); x++) {
            double[] c = mat_circles.get(0, x);
            Point center = new Point((int)c[0], (int)c[1]);
            circles.add(center);
        }
        Collections.sort(circles, (p1, p2) -> {
            if (p1.x < 1500 || p2.x < 1500 )
                return p1.x > 1500? 1:-1;
            return p1.x-1500 < p2.x-1500? 1:-1;
        });

        Point point1 = new Point(0, result.rows());
        Point point2 = new Point(0, -1);
        for (int i = 0; i < circles.size()-1; i++) {
            Point p1 = circles.get(i);
            Point p2 = circles.get(i+1);
            if (p2.x<1500 && abs(p1.x - p2.x) < 40) {
                if (point2.x == floor(p1.x) && point2.y == floor(p1.y)
                        || point1.x == floor(p1.x) && point1.y == floor(p1.y)){
                    point1 = point1.y < p1.y? point1:p1;
                    point1 = point1.y < p2.y? point1:p2;

                    point2 = point2.y > p2.y? point2:p2;
                    point2 = point2.y > p1.y? point2:p1;
                }else if(point1.x == 0){
                    point1 = p1.y < p2.y?p1:p2;
                    point2 = p1.y > p2.y?p1:p2;
                }
            }
        }
        if ( (point1.x == 0 && point1.y == result.rows()) || (point2.x == 0 && point2.y == -1) ) {
            return null;
        }


        // adjust two points
        int sumx = 0;
        int sumy = 0;
        int count = 0;
        for ( int i = point1.x - 30; i<=point1.x+30; i++ ){
            for ( int j = point1.y-30; j<point1.y+30; j++ ){
                if ( mat_bright.get(j, i)[0] > 0 ){
                    sumx += i;
                    sumy += j;
                    count ++;
                }
            }
        }

        point1.x = sumx/count;
        point1.y = sumy/count;

        sumx = 0;
        sumy = 0;
        count = 0;
        for ( int i = point2.x - 30; i<=point2.x+30; i++ ){
            for ( int j = point2.y-30; j<point2.y+30; j++ ){
                if ( mat_bright.get(j, i)[0] > 0 ){
                    sumx += i;
                    sumy += j;
                    count ++;
                }
            }
        }
        point2.x = sumx/count;
        point2.y = sumy/count;

        // release all the mats
        for (Mat mat:rmat){
            mat.release();
        }

        List<Point> points = new ArrayList<>();
        points.add(point1);
        points.add(point2);
        return points;
    }


    /**
     * calculate the dbh of a given image
     * @param src, source image
     * @return
     *
     */
    public static MeasurementResult get_DBH(Mat src){
        MeasurementResult ans = new MeasurementResult();
        List<Point> points = getTwoPoints(src);
        if (points == null){
            ans.setSuccess(false);
            ans.setMsg("points not found");
            ans.setDots(null);
            return ans;
        }
        Point point1 = points.get(0);
        Point point2 = points.get(1);
        List<Mat> rmat = new ArrayList<>();

        /**
         * find red line
         */

        /**
         * red pixels
         */
        int high = 100;
        int midPoint = (point1.y + point2.y)/2;

        cvtColor(src, src, COLOR_BGR2HSV);
        Mat red_roi = src.rowRange(midPoint-high, midPoint+high);
        rmat.add(red_roi);
        Mat add1 = new Mat();
        rmat.add(add1);
        inRange(red_roi, new Scalar(156, 50, 150), new Scalar(255,255,255), add1);
        Mat add2 = new Mat();
        rmat.add(add2);
        inRange(red_roi, new Scalar(0, 50, 150), new Scalar(8,255,255), add2);
        Mat add3 = new Mat();
        rmat.add(add3);
        inRange(red_roi, new Scalar(0, 50, 150), new Scalar(13,255,255), add3);
        add(add1, add2, red_roi);

        Mat element_e = getStructuringElement(MORPH_CROSS, new Size(1,3));
        Mat element_d = getStructuringElement(MORPH_CROSS, new Size(5,1));
        rmat.add(element_e);
        rmat.add(element_d);
        erode(red_roi, red_roi, element_e);
        dilate(red_roi, red_roi, element_d);

        List<Mat> bgr = new ArrayList<>();
        Mat img_roi = src.rowRange(midPoint-high, midPoint+high);
        rmat.add(img_roi);
        split(img_roi, bgr);
        Mat red = bgr.get(2);
        rmat.add(red);
        for ( int i = 0; i<red.cols(); i++ ){
            MinMaxLocResult mmr = minMaxLoc(red.col(i));
            double maxvalue = mmr.maxVal;
            threshold(red.col(i), red.col(i), maxvalue-2, 255, THRESH_BINARY);
        }
        multiply(red, add3, red);

        add(red_roi, red, red_roi);



        /**
         * move to the end points of the line
         */
        // search left and right, reach the end.
        int cnt = countNonZero(red_roi.colRange(point1.x-100, point1.x+100))/200;
        int connect_dis = 3;
        int sh = Math.min(cnt/3, 10);

        int leftx = point1.x - 20;
        for ( ; leftx>connect_dis; leftx-- ){
            if ( mean(red_roi.colRange(leftx-connect_dis, leftx)).val[0] < sh ){
                break;
            }
        }
        leftx = leftx+1+4;

        int rightx = point2.x + 20;
        for ( ; rightx<red_roi.cols() - connect_dis; rightx++ ){
            if ( mean(red_roi.colRange(rightx, rightx+connect_dis)).val[0] < sh ){
                break;
            }
        }
        rightx = rightx-1-4;


        /**
         * calculate DBH
         */
        double point_dis = sqrt((point1.x-point2.x)*(point1.x-point2.x) + (point1.y-point2.y)*(point1.y-point2.y));
        double line_dis = rightx - leftx;
        double tree_width = (line_dis*1.0)/point_dis * POINTDIS;

        // distance from red points to the point where we take picture
        double L = src.rows()/(point_dis*1.0) * POINTDIS * 0.74;

        tree_width = (L + 0.4*tree_width)/L * tree_width;

        double tree_circle = tree_width * Math.PI;
        tree_circle = getR(L, tree_circle);

        tree_width = tree_circle/Math.PI;

        int liney = (point1.y+point2.y)/2;

        ans.setSuccess(true);
        ans.setMsg("success");
        ans.setDiameterCalculate(tree_width);
        TreeMeasurementDots dots = new TreeMeasurementDots();
        dots.setUpx(point1.x);
        dots.setUpy(point1.y);
        dots.setDownx(point2.x);
        dots.setDowny(point2.y);
        dots.setLeftx(leftx);
        dots.setLefty(liney);
        dots.setRightx(rightx);
        dots.setRighty(liney);
        ans.setDots(dots);

        // release all the mats
        for (Mat mat:rmat){
            mat.release();
        }
        return ans;
    }


    /**
     * @param points 有12个数，每两个数代表一个点，以图片左上方为坐标原点；[1][2]，[3][4]是标注的左边上下两个点； [5][6], [7][8]是标注的右边左右两个点，[9][10],[11][12]是之前保存的算出的两个红点。
     * @return double, 这里只返回了一个胸径值，其他的都不需要吧。
     */
    public static double get_DBH(int[] points){
        /**
         * calculate DBH
         */
        double point_dis = sqrt((points[10]-points[8])*(points[10]-points[8]) + (points[11]-points[9])*(points[11]-points[9]));
        double k = (points[3]-points[1])*1.0/points[2]-points[0];
        Point mp = new Point((points[4]+points[6])/2, (points[5]+points[7])/2);
        double line_dis = Math.abs(mp.y - k*mp.x - points[1] + k*points[0])/Math.sqrt(1 + k*k);
        double tree_width = (line_dis*1.0)/point_dis * POINTDIS;

        // distance from red points to the point where we take picture
        double L = ROWS/(point_dis*1.0) * POINTDIS * 0.74;

        tree_width = (L + 0.4*tree_width)/L * tree_width;

        double tree_circle = tree_width * Math.PI;
        tree_circle = getR(L, tree_circle);

        tree_width = tree_circle/Math.PI;

        return tree_width;
    }


    public static class Point{
        int x = 0;
        int y = 0;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

}

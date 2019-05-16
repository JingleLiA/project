package tree.impl;

import org.opencv.core.Mat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tree.dao.TreeMeasurementDotsMapper;
import tree.dao.TreeMeasurementMapper;
import tree.model.TreeMeasurementDots;
import tree.model.TreeMeasurementWithBLOBs;
import tree.service.MeasurementService;
import tree.util.DataTrans;
import tree.util.ImageProcessor;
import tree.vo.CorrectResult;
import tree.vo.MeasurementResult;
import tree.vo.TreeMeasurementVO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.opencv.imgcodecs.Imgcodecs.imread;


/**
 * Created by christine on 2018/9/18.
 */
@Service("MeasurementService")
public class MeasurementImpl implements MeasurementService {

    @Autowired
    TreeMeasurementDotsMapper treeMeasurementDotsMapper;
    @Autowired
    TreeMeasurementMapper treeMeasurementMapper;


    @Override
    public MeasurementResult measure(String mat) {
        Mat src = imread(mat);
        return ImageProcessor.get_DBH(src);
    }

    @Override
    public boolean saveMeasurement(MeasurementResult result,String picName,String treeCode, String time,
                                   String user_code,int times_no_dots,int times_no_treecode) {
        treeMeasurementDotsMapper.insert(result.getDots());
        int dotsId = result.getDots().getId();
        TreeMeasurementWithBLOBs treeMeasurement = new TreeMeasurementWithBLOBs();
        double d = result.getDiameterCalculate();
        treeMeasurement.setDiameterCalculate(DataTrans.getTwoDigitDouble(d));
        treeMeasurement.setDotsCalculate(dotsId);
        treeMeasurement.setPic(picName);
        treeMeasurement.setTime(time);
        treeMeasurement.setTreeCode(treeCode);
        treeMeasurement.setUserCode(user_code);
        treeMeasurement.setTimesNoDots(times_no_dots);
        treeMeasurement.setTimesNoTreecode(times_no_treecode);
        int insertResult = treeMeasurementMapper.insert(treeMeasurement);
        return insertResult!=0 && dotsId!=0;
    }

//    @Override
//    public boolean correctMeasurement(int id,String pic,int left_up_x,int left_up_y,int left_down_x,int left_down_y,
//                                      int right_up_x,int right_up_y,int right_down_x,int right_down_y) {
//        int left_x = (left_up_x + left_down_x) / 2;
//        int left_y = (left_up_y + left_down_y) / 2;
//        int right_x = (right_up_x + right_down_x) / 2;
//        int right_y = (right_up_y + right_down_y) / 2;
//        double angle_l = Math.atan((left_up_x * 1.0 - left_down_x * 1.0) / (left_up_y * 1.0 - left_down_y * 1.0));
//        double angle_r = Math.atan((right_up_x * 1.0 - right_down_x * 1.0) / (right_up_y * 1.0 - right_down_y * 1.0));
//        TreeMeasurementDots dots = new TreeMeasurementDots(left_x,left_y,right_x,right_y,angle_l,angle_r);
//        int insertResult = treeMeasurementDotsMapper.insert(dots);
//        System.out.println(dots);
//        System.out.println(dots.getId());
//        int dotsId = dots.getId();
//        int diameterCorrect = 0;
////        diameterCorrect = pic 是图片路径
//        boolean result = treeMeasurementMapper.updateCorrectInfo(id,diameterCorrect,dotsId) == 1;
//
//
//        return result;
//    }

    @Override
    public CorrectResult correctMeasurement(int id, int[] dots) {
        int left_x = (dots[0] + dots[2]) / 2;
        int left_y = (dots[1] + dots[3]) / 2;
        int right_x = (dots[4] + dots[6]) / 2;
        int right_y = (dots[5] + dots[7]) / 2;
//        int up_x = dots[8];
//        int up_y = dots[9];
//        int down_x = dots[10];
//        int down_y = dots[11];
        double angle_l = Math.atan((dots[0] * 1.0 - dots[2] * 1.0) / (dots[3] * 1.0 - dots[1] * 1.0));
        double angle_r = Math.atan((dots[4] * 1.0 - dots[6] * 1.0) / (dots[7] * 1.0 - dots[5] * 1.0));
        angle_l = (180.0 / Math.PI * angle_l);
        angle_r = (180.0 / Math.PI * angle_r);
        TreeMeasurementDots treeMeasurementDots = new TreeMeasurementDots(left_x,left_y,right_x,right_y,angle_l,angle_r);
        int insertResult = treeMeasurementDotsMapper.insert(treeMeasurementDots);
        int dotsId = treeMeasurementDots.getId();
        double diameterCorrect = 0;
        diameterCorrect = DataTrans.getTwoDigitDouble(ImageProcessor.get_DBH(dots));
        boolean updateResult = treeMeasurementMapper.updateCorrectInfo(id,diameterCorrect,dotsId) == 1;
        CorrectResult result = new CorrectResult();
        boolean success = insertResult == 1 && updateResult == true;
        result.setSuccess(success);
        result.setDiameter_correct(diameterCorrect);
        result.setDots(treeMeasurementDots);
        return result;
    }

    @Override
    public List<TreeMeasurementVO> selectMeasurementsByTreeCode(String treeCode){
        return treeMeasurementMapper.selectVOByTreeCode(treeCode);
    }

    @Override
    public List<TreeMeasurementVO> selectMeasurementsByTime(String beginDate, String endDate){
        return treeMeasurementMapper.selectVOByTime(beginDate,endDate);
    }

    @Override
    public List<TreeMeasurementVO> selectMeasurementsAll() {
        return treeMeasurementMapper.selectVOAll();
    }

}

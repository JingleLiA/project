package tree.service;

import org.springframework.stereotype.Service;
import tree.model.TreeMeasurementWithBLOBs;
import tree.vo.CorrectResult;
import tree.vo.MeasurementResult;
import tree.vo.TreeMeasurementVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by christine on 2018/9/18.
 */
@Service
public interface MeasurementService {

    MeasurementResult measure(String mat);

    boolean saveMeasurement(MeasurementResult result,String picName,String treeCode, String time,
                            String user_code,int times_no_dots,int times_no_treecode);

//    public boolean correctMeasurement(int id,String pic,int left_up_x,int left_up_y,int left_down_x,int left_down_y,
//                                      int right_up_x,int right_up_y,int right_down_x,int right_down_y);

    public CorrectResult correctMeasurement(int id, int[] dots);

    List<TreeMeasurementVO> selectMeasurementsByTreeCode(String treeCode);

    List<TreeMeasurementVO> selectMeasurementsByTime(String beginDate, String endDate);

    List<TreeMeasurementVO> selectMeasurementsAll();
}

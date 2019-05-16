package tree.dao;

import org.springframework.stereotype.Repository;
import tree.model.TreeMeasurement;
import tree.model.TreeMeasurementWithBLOBs;
import tree.vo.TreeMeasurementVO;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface TreeMeasurementMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TreeMeasurementWithBLOBs record);

    int insertSelective(TreeMeasurementWithBLOBs record);

    TreeMeasurementWithBLOBs selectByPrimaryKey(Integer id);

    List<TreeMeasurementWithBLOBs> selectByTreeCode(String treeCode);

    List<TreeMeasurementWithBLOBs> selectByTime(String beginDate,String endDate);

    List<TreeMeasurementVO> selectVOByTreeCode(String treeCode);

    List<TreeMeasurementVO> selectVOByTime(String beginDate, String endDate);

    List<TreeMeasurementVO> selectVOAll();

    int updateByPrimaryKeySelective(TreeMeasurementWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(TreeMeasurementWithBLOBs record);

    int updateByPrimaryKey(TreeMeasurement record);

    List<TreeMeasurementWithBLOBs> selectSingleTreeStartAndEnd(String treeCode);

    List<String> getAllTreeCode();

    List<String> getTreeCodeByCategoryId(String categoryId);

    int updateCorrectInfo(int id,double diameterCorrect,int dotsCorrect);
}
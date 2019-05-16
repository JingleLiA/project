package tree.dao;

import tree.model.TreeMeasurementDots;

public interface TreeMeasurementDotsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TreeMeasurementDots record);

    int insertSelective(TreeMeasurementDots record);

    TreeMeasurementDots selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TreeMeasurementDots record);

    int updateByPrimaryKey(TreeMeasurementDots record);
}
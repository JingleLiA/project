package tree.impl;

import org.springframework.stereotype.Service;
import tree.dao.TreeCategoryMapper;
import tree.dao.TreeMeasurementMapper;
import tree.model.TreeCategory;
import tree.model.TreeMeasurementWithBLOBs;
import tree.service.TreeService;
import tree.util.DataTrans;
import tree.vo.TreeVO;

import javax.annotation.Resource;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service("TreeService")
public class TreeImpl implements TreeService {

    @Resource
    TreeMeasurementMapper treeMeasurementMapper;

    @Inject
    private TreeCategoryMapper treeCategoryMapper;

    @Override
    public TreeVO getSingleTreeData(String treeCode) {
        TreeVO treeVO = new TreeVO();
        treeVO.setTreeCode(treeCode);

        List<TreeMeasurementWithBLOBs> bloBs1 = treeMeasurementMapper.selectByTreeCode(treeCode);

        String firstDate = DataTrans.dateTimeToString(bloBs1.get(0).getTime()).substring(0, 10);
        String lastDate = DataTrans.dateTimeToString(bloBs1.get(bloBs1.size() - 1).getTime()).substring(0, 10);
        int firstValidMeasurementIndex = 0;
        int measureTimes = bloBs1.size();
        bloBs1.forEach(p -> treeVO.setTreeType(treeCategoryMapper.selectByPrimaryKey(bloBs1
                .get(0).getCategoryId())
                .getCategoryName()));
        for (int i = 1; i < bloBs1.size(); i++) {
            String preDate = DataTrans.dateTimeToString(bloBs1.get(i - 1).getTime()).substring(0, 10);
            String proDate = DataTrans.dateTimeToString(bloBs1.get(i).getTime()).substring(0, 10);
            if (preDate.equals(proDate)) {
                measureTimes--;
            }
            if (proDate.equals(firstDate)) {
                firstValidMeasurementIndex++;
            }
        }
        if (firstDate.equals(lastDate)) {
            treeVO.setGrow(0);
        } else {
            long nd = 1000 * 24 * 60 * 60;
            long dayDiff = (bloBs1.get(bloBs1.size() - 1).getTime().getTime() - bloBs1.get(firstValidMeasurementIndex).getTime().getTime()) / nd;
            double diameterDiff = bloBs1.get(bloBs1.size() - 1).getDiameterCalculate() - bloBs1.get(firstValidMeasurementIndex).getDiameterCalculate();
            double grow = diameterDiff / dayDiff * 365;
            treeVO.setGrow(grow);
        }

        treeVO.setMeasureTimes(measureTimes);
        return treeVO;
    }

    @Override
    public List<TreeVO> getAllTreeData() {
        List<String> treeCodes = treeMeasurementMapper.getAllTreeCode();
        return getTreeVOS(treeCodes);
    }

    private List<TreeVO> getTreeVOS(List<String> treeCodes) {
        List<TreeVO> treeVOS = new ArrayList<>();
        for (String treeCode : treeCodes) {
            TreeVO temp = this.getSingleTreeData(treeCode);
            treeVOS.add(temp);
        }
        return treeVOS;
    }

    @Override
    public List<TreeVO> getAllTreeDataByCategoryId(String categoryId) {
        List<String> treeCodes = treeMeasurementMapper.getTreeCodeByCategoryId(categoryId);
        return getTreeVOS(treeCodes);
    }
}

package tree.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import tree.dao.TreeCategoryMapper;
import tree.model.TreeCategory;
import tree.service.TreeCategoryService;
import tree.vo.TreeCategoryVo;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created with 凌神.
 * Description: 
 * User: 87179
 * Date: 2019-04-27
 * Time: 20:03
 */
@Service
public class TreeCategoryServiceImpl implements TreeCategoryService{

    @Resource
    private TreeCategoryMapper treeCategoryMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return treeCategoryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(TreeCategory record) {
        return treeCategoryMapper.insert(record);
    }

    @Override
    public int insertSelective(TreeCategory record) {
        return treeCategoryMapper.insertSelective(record);
    }

    @Override
    public TreeCategory selectByPrimaryKey(Integer id) {
        return treeCategoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(TreeCategory record) {
        return treeCategoryMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(TreeCategory record) {
        return treeCategoryMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<TreeCategoryVo> selectAllInfo() {
        return treeCategoryMapper.selectAll().stream()
                .map(p -> new TreeCategoryVo(p.getId().toString(),p.getCategoryName()))
                .collect(Collectors.toList());
    }

}

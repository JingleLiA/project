package tree.impl;

import org.springframework.stereotype.Service;
import tree.dao.TreeUserMapper;
import tree.model.TreeUser;
import tree.service.TreeUserService;

import javax.annotation.Resource;

/**
 * Created with 凌神.
 * Description:
 * User: 87179
 * Date: 2019-04-24
 * Time: 22:09
 */
@Service
public class TreeUserServiceImpl implements TreeUserService {

    @Resource
    private TreeUserMapper treeUserMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return treeUserMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(TreeUser record) {
        return treeUserMapper.insert(record);
    }

    @Override
    public int insertSelective(TreeUser record) {
        return treeUserMapper.insertSelective(record);
    }

    @Override
    public TreeUser selectByPrimaryKey(Integer id) {
        return treeUserMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(TreeUser record) {
        return treeUserMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(TreeUser record) {
        return treeUserMapper.updateByPrimaryKey(record);
    }

    @Override
    public TreeUser findForLogin(TreeUser record) {
        return treeUserMapper.selectOne(record);
    }

}

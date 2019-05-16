package tree.service;

import org.springframework.stereotype.Service;
import tree.vo.TreeVO;

import java.util.List;

@Service
public interface TreeService {

    public TreeVO getSingleTreeData(String treeCode);

    public List<TreeVO> getAllTreeData();

    public List<TreeVO> getAllTreeDataByCategoryId(String categoryId);
}

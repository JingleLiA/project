package tree.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tree.service.TreeService;
import tree.vo.TreeVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/trees")
public class TreeController {

    @Autowired
    TreeService treeService;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    Map getTreeMeasurements() {
        Map<String, Object> result = new HashMap<String, Object>();
        List<TreeVO> treeVOS = new ArrayList<>();
        treeVOS = treeService.getAllTreeData();
        result.put("data", treeVOS);
        return result;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Map getSingleTreeMeasurements(@PathVariable(value = "id") String tree_code) {
        Map<String, Object> result = new HashMap<String, Object>();
        TreeVO treeVO = treeService.getSingleTreeData(tree_code);
        result.put("data", treeVO);
        return result;
    }

    @RequestMapping(value = "/category/{categoryId}", method = RequestMethod.GET)
    public @ResponseBody
    Map getCategoryInfo(@PathVariable(value = "categoryId") String categoryId) {
        Map<String, Object> result = new HashMap<>();
        result.put("data", treeService.getAllTreeDataByCategoryId(categoryId));
        return result;
    }
}

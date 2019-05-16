package tree.controller;

import com.google.common.collect.Maps;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tree.service.TreeCategoryService;

import javax.inject.Inject;
import java.util.Map;

/**
 * Created with 凌神.
 * Description:
 * User: 87179
 * Date: 2019-04-27
 * Time: 20:33
 */
@RestController
@RequestMapping("/api/categoryInfo")
public class TreeCategoryController {

    @Inject
    private TreeCategoryService treeCategoryService;
    @RequestMapping(method = RequestMethod.GET)
    public Map getCategory() {
        Map<String, Object> result = Maps.newHashMap();
        result.put("data", treeCategoryService.selectAllInfo());
        return result;
    }
}

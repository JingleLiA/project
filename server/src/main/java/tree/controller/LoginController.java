package tree.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tree.model.TreeUser;
import tree.service.TreeUserService;

import javax.inject.Inject;
import java.util.Objects;

/**
 * Created with 凌神.
 * Description:
 * User: 87179
 * Date: 2019-04-24
 * Time: 21:59
 */
@RestController
@RequestMapping("user/")
public class LoginController {

    @Inject
    private TreeUserService treeUserService;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(@RequestBody TreeUser userDto) {
         TreeUser user = treeUserService.findForLogin(userDto);
        JSONObject jsonObject = new JSONObject();
        if (Objects.isNull(user)) {
            jsonObject.put("data", "0");
        } else {
            jsonObject.put("data", "1");
        }
        return jsonObject.toJSONString();
    }


}

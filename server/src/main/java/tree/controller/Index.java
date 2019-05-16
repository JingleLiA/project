package tree.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * Created by christine on 2018/9/17.
 */
@Controller
@RequestMapping(value="/")
public class Index {
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index()throws Exception{
//        System.out.println("in Index Controller");
//        ModelAndView mav = new ModelAndView("/vue/index.html");
//        System.out.println(mav);
        return new ModelAndView("/vue/index.html");
    }
}

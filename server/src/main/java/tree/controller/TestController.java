package tree.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import tree.vo.MeasurementResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api/test")
public class TestController {



    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody
    Map addMeasurement(HttpServletRequest request,
                       HttpServletResponse response,
                       @RequestParam("pic") MultipartFile pic,
                       @RequestParam("tree_code") String tree_code,
                       @RequestParam("time") String time
    )throws Exception{
        Map<String,Object> result = new HashMap<String,Object>();
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        System.out.println(pic.getOriginalFilename());
        result.put("msg","成功");
        result.put("msg2","english");
        return result;
    }
}

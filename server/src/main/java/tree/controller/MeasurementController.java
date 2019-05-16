package tree.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import tree.service.MeasurementService;
import tree.util.DataTrans;
import tree.vo.CorrectResult;
import tree.vo.MeasurementResult;
import tree.vo.TreeMeasurementVO;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by christine on 2018/9/17.
 */
@Controller
@RequestMapping("/api/measurements")
public class MeasurementController {

    @Autowired
    MeasurementService measurementService;

    /**
    * - Web
    * 查找测量记录的接口
    * 参数可能有 始末时间、树木编码
    * 如果没有参数，则选取最近的多少条数据
    * */
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    Map getMeasurements(@RequestParam(value = "beginDate", required = false, defaultValue = "") String beginDate,
                        @RequestParam(value = "endDate", required = false, defaultValue = "") String endDate,
                        @RequestParam(value = "treeCode", required = false, defaultValue = "") String treeCode)throws Exception{
        Map<String,Object> result = new HashMap<String,Object>();
        List<TreeMeasurementVO> blobs = null;
        if(!"".equals(treeCode)){
            blobs = measurementService.selectMeasurementsByTreeCode(treeCode);
        }else if(!"".equals(beginDate) && !"".equals(endDate)){
            blobs = measurementService.selectMeasurementsByTime(beginDate, endDate);
        }else {
            blobs = measurementService.selectMeasurementsAll();
        }
        result.put("data",blobs);
        result.put("total",blobs.size());
        return result;
    }

    /**
    * - APP
    * 新增测量记录的接口
    * */
    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody
    Map addMeasurement(HttpServletRequest request,
                       @RequestParam("pic") MultipartFile pic,
                       @RequestParam("tree_code") String treeCode,
                       @RequestParam("time") String time,
                       @RequestParam("user_code") String userCode,
                       @RequestParam("times_no_dots") int timesNoDots,
                       @RequestParam("times_no_treecode") int timesNoTreecode
    )throws Exception{
        Map<String,Object> result = new HashMap<String,Object>();
        request.setCharacterEncoding("UTF-8");

        if(!DataTrans.isStringDateFormat(time)){
            result.put("success",new Boolean(false));
            result.put("msg","false time format, required yyyy-MM-dd HH:mm:ss");
            return result;
        }

        if(pic!=null) {
            String path = null;
            String type = null;
            String upperType = null;
            String fileName = null;

            fileName = pic.getOriginalFilename();
            type = fileName.indexOf('.') == -1 ? null : fileName.substring(fileName.indexOf('.')+1,fileName.length());
            upperType = type.toUpperCase();
            if(upperType.equals("JPG") || upperType.equals("JPEG") || upperType.equals("PNG")) {
                // 项目在容器中实际发布运行的根路径+/uploads
                String realPath=request.getSession().getServletContext().getRealPath("/uploads/");
                // 自定义的文件名称
                String trueFileName= time.replace(" ","") + treeCode + "." + type;

                // 如果存文件的路径不存在就创建
                File savedFile = new File(realPath);
                if(!savedFile.exists()){
                    savedFile.mkdir();
                }


                // 转存文件到指定的路径
                path=realPath+/*System.getProperty("file.separator")+*/trueFileName;
                System.out.println("存放图片文件的路径:"+path);
                pic.transferTo(new File(path));
                result.put("success", new Boolean(true));
                result.put("msg", "处理成功");

                File file = new File(path);
                System.out.println("exists:"+file.exists());
                System.out.println("size"+file.length());


                MeasurementResult measurementResult = measurementService.measure(path);

                if(measurementResult.getSuccess()){
                    // 图片没有问题时的处理
//                    System.out.println(JSON.toJSONString(measurementResult));
                    boolean dbResult =
                            measurementService.saveMeasurement(measurementResult,trueFileName,treeCode,time,userCode,timesNoDots,timesNoTreecode);
                    result.put("success",dbResult);
                    result.put("code",0);
                    result.put("msg",dbResult?"handle success":"database fail");

                }else{
                    // 图片有问题时的处理
                    result.put("code",1);
                    result.put("success",new Boolean(false));
                    result.put("msg",measurementResult.getMsg());
                }

            } else {
                result.put("code",2);
                result.put("success", new Boolean(false));
                result.put("msg", "only accepts jpg jpeg or png");
            }
        } else {
            result.put("code",3);
            result.put("success", new Boolean(false));
            result.put("msg", "no pic");
        }

        return result;
    }


    /*
    * - Web
    * 修改测量记录的接口
    * */
//    @RequestMapping(method = RequestMethod.POST)
//    public @ResponseBody
//    Map addMeasurementTest(
//            HttpServletRequest request,
//            @RequestParam("pic") MultipartFile pic
//    )throws Exception{
//        Map<String,Object> result = new HashMap<String,Object>();
//        result.put("ADD","SUCCESS");
//        return result;
//    }

    /**
    * - Web
    * 修改测量记录的接口
    * */
    @RequestMapping(method = RequestMethod.PATCH)
    public @ResponseBody
    Map updateMeasurement(HttpServletRequest request,
                          @RequestParam("id") int id,
                          @RequestParam("pic") String pic,
                          @RequestParam("left_up_x") int left_up_x,
                          @RequestParam("left_up_y") int left_up_y,
                          @RequestParam("left_down_x") int left_down_x,
                          @RequestParam("left_down_y") int left_down_y,
                          @RequestParam("right_up_x") int right_up_x,
                          @RequestParam("right_up_y") int right_up_y,
                          @RequestParam("right_down_x") int right_down_x,
                          @RequestParam("right_down_y") int right_down_y,
                          @RequestParam("dots") String dots_str)throws Exception{
        Map<String,Object> result = new HashMap<String,Object>();
        String[] strs = dots_str.split(",");
        int[] ints = new int[12];
        for(int i = 0; i < strs.length;i++){
            ints[i] = Integer.parseInt(strs[i]);
        }

        CorrectResult correctResult = measurementService.correctMeasurement(id,ints);
        result.put("data",correctResult);
        return result;
    }


}

package tree.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tree.vo.TreeMeasurementVO;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

@Controller
public class APKController {

    @RequestMapping(value="/apkVersion",method = RequestMethod.GET)
    public @ResponseBody
    Map getNewestVersion(HttpServletRequest request)throws Exception{
        Map<String,Object> result = new HashMap<String,Object>();
        String apkPath=request.getSession().getServletContext().getRealPath("/apk/");
        File file = new File(apkPath);
        if(file.isDirectory()){
            File[] files = file.listFiles();
            ArrayList<Double> versions = new ArrayList<>();
            System.out.println("2");
            if(files.length > 0){
                System.out.println("3");
                for(File f: files){
                    String name = f.getName();
                    if(name.indexOf("tree") == 0 && name.indexOf(".apk") == name.length() - 4){
                        System.out.println("file    name:   "+name);
                        String v = name.substring(5,name.length() - 4);
                        try{
                            double version = Double.parseDouble(v);
                            versions.add(version);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
            Collections.sort(versions);
            result.put("versions",versions);
            result.put("version",versions.get(versions.size() - 1));
            result.put("newestPath","/apk/tree_"+versions.get(versions.size() - 1)+".apk");

            ArrayList<String> persons = new ArrayList<>();
            persons.add("000001");
            persons.add("000002");
            persons.add("000003");
            persons.add("000004");
            persons.add("000005");
            result.put("userCode",persons);
        }else{
            result.put("versions",new ArrayList<>());
            result.put("version",null);
            result.put("newestPath",null);
        }



        return result;
    }

    @RequestMapping(value="/apk/newest",method = RequestMethod.GET)
    public @ResponseBody
    Map getNewestAPK(HttpServletRequest request,
                     @RequestParam(value = "version", required = true, defaultValue = "") String version)throws Exception{
        Map<String,Object> result = new HashMap<String,Object>();
        String apkPath=request.getSession().getServletContext().getRealPath("/apk/");
        File apk = new File(apkPath+"tree_"+version+".apk");
        if(apk.exists()){

        }else{

        }

        return result;
    }
}

package top.Youting.teach.service;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.URL;

public class FileIO {
    public static boolean creatFile(String path){
            File file = new File(path);
            if (file.getParentFile().canWrite()){
                if(!file.exists()){
                    file.mkdirs();
                   // System.out.println("creat dirs"+path);
                }
            }else{
                return false;
            }

        return true;
    }
    public static boolean ifFileExist(String path){
        File file = new File(path);
        if(file.exists()){
            return true;
        }
        return false;
    }
    public static String ReadFile(String path){
        BufferedReader reader = null;
        String laststr = "";
        try{
            FileInputStream fileInputStream = new FileInputStream(path);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            while((tempString = reader.readLine()) != null){
                laststr += tempString;
            }
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return laststr;
    }
    public static String downloadFromUrl(String url,String dir,String name) {

        try {
            URL httpurl = new URL(url);
           // System.out.println("正在下载"+name);
            File f = new File(dir + name);
            FileUtils.copyURLToFile(httpurl, f);
        } catch (Exception e) {
            //e.printStackTrace();
            return "失败";
        }
        return "成功";
    }

    public static String getFileNameFromUrl(String url){
        String name = null;
        try {
            int ss = url.lastIndexOf(".");
             name = url.substring(ss,ss+4);
        }catch (Exception e){

        }
        return name;
    }
}

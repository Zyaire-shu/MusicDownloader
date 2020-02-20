package top.Youting.teach.service;

import java.io.File;

public class Downloader extends Thread {
    private String downloader[];
    private String url;
    private String songName;
    private String atrist;
    private static String path = null;
    private String fileName ;
    private int fi ;
    @Override
    public void run(){
        path = path+File.separator+downloader[3]+ File.separator;
        if(!FileIO.creatFile(path)){
            System.out.println("路径创建失败！");
        }
         url = downloader[0];
         songName = downloader[1];
         atrist = downloader[2];
        fileName = atrist+" "+"-"+" "+songName+FileIO.getFileNameFromUrl(url);
        if(FileIO.ifFileExist(path+fileName)){
            System.out.println(fileName+"已存在，跳过");
            return;
        }
        String ans = FileIO.downloadFromUrl(url,path,fileName);
        System.out.println("第"+fi+"首："+songName+"下载"+ans);
    }
    public Downloader setPath(String path){
        this.path = path;
        return this;
    }
    public Downloader(String[] a ,int fi){
        this.fi = fi+1;
        this.downloader = a;
    }


}

package top.Youting.teach;
import top.Youting.teach.service.FileIO;
import top.Youting.teach.service.Starter;


import java.io.File;
import java.util.Scanner;

public class Main {
    private static Object s;
    private static String mypath;
    public static void main(String [] args) {
        Scanner sc = new Scanner(System.in);
        do{
            System.out.println("输入下载路径:");
            sc = new Scanner(System.in);
             mypath = sc.next();
            if(FileIO.creatFile(mypath)){
                System.out.println("路径创建成功");
                break;
            }
            System.out.println("路径不可写入");
        }while(true);
        for(;;){
            new Starter().programStart(mypath);
        }
//    Youting y = new Youting("netease");
//    System.out.println( y.lyric("463895473"));
    }

}

package top.Youting.teach.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Starter {
    //音乐网站
    private String server = "netease";
    String[] suppose = new String[]{"netease","tencent","xiami","kugou","baidu"};
    //private  String mypath = "";
    int servercount = 0;
    Youting you ;
    List<String []> result ;
    public void programStart(String path){
       // this.mypath = path;
        Scanner sc = new Scanner(System.in);
        Boolean flag = true;

//        do{
//            System.out.println("输入模式");
//            System.out.println("1,单曲下载\n2,歌单下载");
//            int a = sc.nextInt();
//            if(0<a && a<3){
//                this.server = suppose[a];
//                flag = false;
//            }
//            System.out.println("序号不正确，请重新输入");
//        }while (flag);
        you = new Youting(this.server);
        System.out.println("输入歌单id：");
        String playlistid = sc.next();
        result = JsonTool.doPlaylistData(you.playlist(playlistid),this.server);
        System.out.println("本歌单一共"+result.size()+"首歌");
        for(int i= 0;i<result.size();i++){
            String [] a = result.get(i);
//            System.out.println(a[1]);
//            for (int p = 0;p <a.length;p++){
//                System.out.println("the"+p+"is"+a[p]);
//            }
            a[0] = JsonTool.doUrlData(you.url(a[0]),this.server);
            if(a[0].equals("")){
                //System.out.println("inner should satrt");
                a[0] = research(a);
               // System.out.println("url"+url+"name"+a[1]);
            }
                //System.out.println("第"+i+"首："+a[1]+"\n下载链接："+a[0]);
            new Downloader(a,i).setPath(path).start();

        }
    }
    public String research(String[] a){
        String url = null;
        servercount ++;
        List<String[]> searchRes = new ArrayList();
        //在一个音乐平台获取不到链接就在其他平台搜索同一歌手
       // System.out.println("the"+ servercount+"retry"+"the server is "+suppose[servercount]);
        Youting yy = new Youting(suppose[servercount]);
        searchRes = JsonTool.doSearchData(yy.search(a[1]),suppose[servercount]);
        for(int i = 0;i<searchRes.size();i++){
            String [] s = searchRes.get(i);
            String name =s[2].substring(2,s[2].length()-2);
            //System.out.println(name+"+++++"+a[2]);
           if(a[2].equals(name)){
            url = JsonTool.doUrlData(yy.url(s[0]),suppose[servercount]);
            //System.out.println("found answer"+url);
                if(!url.equals("")){
                    servercount =0;
                    //System.out.println("found answer"+url);
                    return url;
                }
           }
        }
        if(servercount<4){
             return research(a);
        }
        servercount =0;
        System.out.println("fail");
        return url;
    }
}

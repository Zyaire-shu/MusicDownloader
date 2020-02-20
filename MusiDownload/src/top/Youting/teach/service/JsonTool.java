package top.Youting.teach.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonTool {


    public static List<String[]> doSearchData(String jsonString, String server){
        JSONArray jb = new JSONArray();
        try {
            jb = JSONArray.parseArray(jsonString);
        }catch(Exception e ){

        }
        List<String []> result = new ArrayList<>();
        for(int i = 0;i < jb.size();i++){
            String [] a = new String[3];
            a[0] = jb.getJSONObject(i).getString("id");
            a[1] = jb.getJSONObject(i).getString("name");
            a[2] = jb.getJSONObject(i).getString("artist");
            result.add(a);
        }
        return result;
    }
    public static List<String []> doPlaylistData(String jsonString,String server){
        List<String []> result = new ArrayList<>();
        switch (server){
            case "netease":
                try {
                    JSONObject jb = JSONObject.parseObject(jsonString);
                    JSONObject playlist = jb.getJSONObject("playlist");
                    JSONArray tracks = playlist.getJSONArray("tracks");
                    for(int i = 0; i <tracks.size();i++){
                        String [] a = new String [4];
                        a[0] = tracks.getJSONObject(i).getString("id");
                        //歌曲名
                        a[1] = tracks.getJSONObject(i).getString("name");
                        //歌手名
                        a[2] = tracks.getJSONObject(i).getJSONArray("ar").getJSONObject(0).getString("name");
                        //歌单名
                        a[3] = playlist.getString("name");
                        //System.out.println(a[0]+a[1]+a[2]+a[3]);
                        result.add(a);
                    }
                }catch (Exception e){

                }

                break;
            case "tencent":

                break;
            default:
                throw new IllegalStateException("Unexpected value: " + server);
        }

        return result;
    }
    public static String doUrlData(String urlData , String server){
        //传入包含歌曲下载链接的json字符串
        String url = null;
        JSONObject urlJSONObj = JSONObject.parseObject(urlData);
         url = urlJSONObj.getString("url");
        return url;
    }
}

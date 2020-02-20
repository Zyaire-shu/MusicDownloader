package top.Youting.teach.net;

import java.util.Iterator;
import java.util.Map;

public class NetTool {
    /**
     * Java实现PHP中的http_build_query()效果
     * @param array
     *        key=value形式的二位数组
     * @return
     */
    public static String http_build_query(Map<String,Object> array){
        String reString = "";
        //遍历数组形成akey=avalue&bkey=bvalue&ckey=cvalue形式的的字符串
        Iterator it = array.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<String,Object> entry =(Map.Entry) it.next();
            //中间加个过滤,去掉token和checksum
//            if("token".equals(entry.getKey())||"checksum".equals(entry.getKey())){
//                continue;
//            }
            String key = entry.getKey();
            Object value = entry.getValue();
            reString += key+"="+value+"&";
        }
        reString = reString.substring(0, reString.length()-1);
        //将得到的字符串进行处理得到目标格式的字符串
        reString = java.net.URLEncoder.encode(reString);
        reString = reString.replace("%3D", "=").replace("%26", "&");
        return reString;
    }

}

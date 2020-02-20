package top.Youting.teach.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

import top.Youting.teach.net.NetTool;
import javax.net.ssl.*;

public class Youting {
    //默认网易云
    private String server ;
    private int temp;
    private Map<String,String> headers = new HashMap<>();
    Youting(){}
    public Youting(String value){
        this.site(value);
    }
    public void site(String value){
        String[] suppose = new String[]{"netease","tencent","xiami","kugou","baidu"};
        this.server = Arrays.asList(suppose).contains(value)? value:"netease";
        //还没有设置header
        curlSet();
    }

    private String exec(Map a){
        //这里得到需要请求数据的url
        String url = a.get("url")+"?"+ NetTool.http_build_query((Map<String, Object>) a.get("body"));
        String data = null;
        if(a.get("method").equals("GET")){
            //判断是否是get
           // System.out.println("the url is:"+url);
            try {
                data = doGet(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(a.get("method").equals("POST")){
            //System.out.println("the url is:"+url);
            try {
                data = doPost(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //这里以上都是成功
        return unicodeToUtf8(data);
    }

    public String search(String keyword){
        Map<String , Object> api = new HashMap<String,Object>();
        Map<String , Object> parm = new HashMap<String,Object>();
        api.put("method","GET");
        api.put("url","http://ting.yeyulingfeng.com/api.php");
        parm.put("types","search");
        parm.put("pages",1);
        parm.put("count",20);
        parm.put("source",this.server);
        parm.put("name",keyword);
        api.put("body",parm);
        return this.exec(api);
    }
    public String url(String ... id ){
        String []inputs = new String[2];
        int br;
        Map<String , Object> api = new HashMap<String,Object>();
        Map<String , Object> parm = new HashMap<String,Object>();
        if(id.length ==1){
            br = 320;
        }else{
            br = Integer.parseInt(id[1]);
        }
        api.put("method","GET");
        api.put("url","http://ting.yeyulingfeng.com/api.php");
        parm.put("types","url");
        parm.put("source",this.server);
        parm.put("id",id[0]);
        parm.put("br",br);
        api.put("body",parm);
        temp = br;
        return this.exec(api);
    }
    public String lyric(String id ){
        Map<String , Object> api = new HashMap<String,Object>();
        Map<String , Object> parm = new HashMap<String,Object>();
        api.put("method","GET");
        api.put("url","http://ting.yeyulingfeng.com/api.php");
        parm.put("types","lyric");
        parm.put("source",this.server);
        parm.put("id",id);
        api.put("body",parm);
        return this.exec(api);
    }
    public String playlist(String id){
        Map<String , Object> api = new HashMap<String,Object>();
        Map<String , Object> parm = new HashMap<String,Object>();
        api.put("method","GET");
        api.put("url","http://ting.yeyulingfeng.com/api.php");
        parm.put("types","playlist");
        parm.put("source",this.server);
        parm.put("id",id);
        api.put("body",parm);
        return this.exec(api);
    }
    private void curlSet(){
//        switch(this.server){
//            case "netease":
//                headers.put("Referer", "https://music.163.com/");
//                headers.put("Cookie", "appver=1.5.9; os=osx; __remember_me=true; osver=%E7%89%88%E6%9C%AC%2010.13.5%EF%B7C%88%E7%89%88%E5%8F%B7%2017F77%EF%BC%89;");
                headers.put("Accep", "*/*");
                headers.put("X-Real-IP", "188."+"48."+(new Random().nextInt(63)+90)+"."+(new Random().nextInt(51)+60));
                headers.put("Accept-Language", "zh-CN,zh;q=0.8,gl;q=0.6,zh-TW;q=0.4");
                headers.put("Connection", "keep-alive");
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
//            break;
//            case "tencent":
//                headers.put("Referer", "http://y.qq.com");
//                headers.put("Cookie", " pgv_pvi=7574824960; pgv_pvid=593694468; pac_uid=0_5e44eebdb93c1; pgv_si=s3973105664; pgv_info=ssid=s5075121528; RK=mgJBzwsyXo; ptcz=495b7d2753e96e293d7e3c662ad9dc0387d769e60abacd224ad799c2fc0de9c3; qqmusic_key=Q_H_L_2iwXBv50eiRqDSDyIOyi4EtXtfiTKH0Ni841VrtSJ9ARyMd2Ai3Q7WdUc4bbuh4; psrf_qqopenid=4DE40ECF97A8EAEC2A9046EFA47AFC4D; p_lskey=Q_H_L_2iwXBv50eiRqDSDyIOyi4EtXtfiTKH0Ni841VrtSJ9ARyMd2Ai3Q7WdUc4bbuh4; psrf_qqunionid=0468A547613EAAE2DA64597CA62FAAB9; psrf_qqrefresh_token=8D608CEEA550E8094D871A7E41C6D3E9; psrf_musickey_createtime=1582008235; psrf_qqaccess_token=544DF39C291995BB8FD04373E015359B; uin=1278895492; psrf_access_token_expiresAt=1589784235; qm_keyst=Q_H_L_2iwXBv50eiRqDSDyIOyi4EtXtfiTKH0Ni841VrtSJ9ARyMd2Ai3Q7WdUc4bbuh4; qqmusic_fromtag=66");
//                headers.put("Accep", "*/*");
//                headers.put("Accept-Language", "zh-CN,zh;q=0.8,gl;q=0.6,zh-TW;q=0.4");
//                headers.put("Connection", "keep-alive");
//                headers.put("Content-Type", "application/x-www-form-urlencoded");
//                headers.put("User-Agent", "QQ%E9%9F%B3%E4%B9%90/54409 CFNetwork/901.1 Darwin/17.6.0 (x86_64)");
//                break;
//        }
    }
    //将get到的response的unicode编码转化为utf-8格式，可以进行输出和存储
    public static String unicodeToUtf8(String theString) {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len; ) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    // Read the xxxx
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed   \\uxxxx   encoding.");
                        }
                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';
                    outBuffer.append(aChar);
                }
            } else
                outBuffer.append(aChar);
        }
        return outBuffer.toString();
    }
//    private String tencent_url(String result){
//        Map<String , Object> api = new HashMap<String,Object>();
//        Map<String , Object> parm = new HashMap<String,Object>();
//        List<String[]> type = new ArrayList<>();
//        Map<String ,String> url = new HashMap<>();
//        Map<String,Object> playload = new HashMap<>();
//        Map<String,Object> req_0 = new HashMap<>();
//        Map<String,Object> innerparam = new HashMap<>();
//        JSONObject data = JSONObject.parseObject(result);
//        //long guid = Math.abs(new Random().nextLong()% Long.valueOf("10000000000"));
//        var s = System.currentTimeMillis();
//        long guid = Math.abs(Math.round(2147483647*Math.random())*s%Long.valueOf("10000000000"));
//        type.add(new String[]{"size_320mp3","320","M800","mp3"});
//        type.add(new String[]{"size_192aac","192","C600","m4a"});
//        type.add(new String[]{"size_128mp3","128","M500","mp3"});
//        type.add(new String[]{"size_96aac","96","C400","m4a"});
//        type.add(new String[]{"size_48aac","48","C200","m4a"});
//        type.add(new String[]{"size_24aac","24","C100","m4a"});
//        playload.put("req_0",req_0);
//        req_0.put("module","vkey.GetVkeyServer");
//        req_0.put("method","CgiGetVkey");
//        req_0.put("param",innerparam);
//        innerparam.put("guid",new StringBuilder().append(guid));
//        innerparam.put("uin","0");
//        innerparam.put("loginflag",1);
//        innerparam.put("platform","20");
//        String aa [] =type.get(0);
////        for (String[] aa:
////             type) {
//            innerparam.put("songmid",data.getJSONArray("data").getJSONObject(0).getString("mid"));
//            innerparam.put("filename",aa[2]+data.getJSONArray("data").getJSONObject(0).getJSONObject("file").getString("media_mid")+"."+aa[3]);
//            innerparam.put("songtype", Integer.valueOf(data.getJSONArray("data").getJSONObject(0).getString("type")));
//        //}
//        api.put("method","GET");
//        api.put("url","https://u.y.qq.com/cgi-bin/musicu.fcg");
//        api.put("body",parm);
//        parm.put("format","json");
//        parm.put("platform","yqq.json");
//        parm.put("needNewCode",0);
//        parm.put("data",JSON.toJSONString(playload));
//        //String stringapi = JSON.toJSONString(api);
//        System.out.println("the to send data"+JSON.toJSONString(playload));
//        JSONObject response = JSONObject.parseObject(this.exec(api));
//        System.out.println(response);
//        //String vkeys =
////        for (Map.Entry<String, String[]> entry : type.entrySet()) {
////            var a = entry.getKey();
////            var b = entry.getValue();
////            if(data.getJSONArray("data").getJSONObject(0).getJSONObject("file").getString(a) !=  null && Integer.parseInt(b[0]) <= (int)this.temp) {
////                url.put("url", "http://ws.stream.qqmusic.qq.com/" + b[1] + data.getJSONArray("data").getJSONObject(0).getJSONObject("file").getString("media_mid")
////                        + "." + b[2] + "?vkey=" + key + "&guid=" + guid + "&uid=0&fromtag=30");
////                url.put("size", data.getJSONArray("data").getJSONObject(0).getJSONObject("file").getString(a));
////                url.put("br", b[0]);
////            }
////            System.out.println("Item : " + entry.getKey() + " Count : " + entry.getValue());
////        }
////        if(url.isEmpty()){
////            url.put("url",null);
////            url.put("size","0");
////            url.put("br","-1");
////        }
//
//        return  JSON.toJSONString(url);
//    }
    private static final class DefaultTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }
    private HttpsURLConnection getHttpsURLConnection(String uri, String method) throws IOException {
        SSLContext ctx = null;
        try {
            ctx = SSLContext.getInstance("TLS");
            ctx.init(new KeyManager[0], new TrustManager[]{new DefaultTrustManager()}, new SecureRandom());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        SSLSocketFactory ssf = ctx.getSocketFactory();

        URL url = new URL(uri);
        HttpsURLConnection httpsConn = (HttpsURLConnection) url.openConnection();
        httpsConn.setSSLSocketFactory(ssf);
        for(Map.Entry<String, String> vo : headers.entrySet()) {
            String key =vo.getKey();
            String value = vo.getValue();
            //System.out.println(key+"  "+value);
            httpsConn.setRequestProperty(key, value);
        }
        /*
        在握手期间，如果 URL 的主机名和服务器的标识主机名不匹配，
        则验证机制可以回调此接口的实现程序来确定是否应该允许此连接。
        策略可以是基于证书的或依赖于其他验证方案。
        当验证 URL 主机名使用的默认规则失败时使用这些回调。
         */
        httpsConn.setHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String arg0, SSLSession arg1) {
                return true;
            }
        });
        httpsConn.setRequestMethod(method);
        httpsConn.setDoInput(true);
        httpsConn.setDoOutput(true);
        return httpsConn;
    }
    private HttpURLConnection getHttpURLConnection(String uri, String method) throws IOException {
        URL url = new URL(uri);
       // System.out.println("this is the method in httpconnect");
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        for(Map.Entry<String, String> vo : headers.entrySet()) {
            String key =vo.getKey();
            String value = vo.getValue();
            //System.out.println(key+"  "+value);
            httpConn.setRequestProperty(key, value);
        }
        httpConn.setRequestMethod(method);
        httpConn.setDoInput(true);
        httpConn.setDoOutput(true);
        return httpConn;
    }
    public  String doGet(String uri) throws IOException {
        String s =  uri.substring(0,5);
        String result = null;
        if(s.equals("https")){
            HttpsURLConnection httpsConn = getHttpsURLConnection(uri, "GET");
            result = new String(getBytesFromStream(httpsConn.getInputStream()));
        }else{
            HttpURLConnection httpConn = getHttpURLConnection(uri,"GET");
            result = new String(getBytesFromStream(httpConn.getInputStream()));
        }
        return result;
    }
    public  String doPost(String uri) throws IOException {
        String s =  uri.substring(0,5);
        String result = null;
        if(s.equals("https")){
            HttpsURLConnection httpsConn = getHttpsURLConnection(uri, "POST");
            result = new String(getBytesFromStream(httpsConn.getInputStream()));
        }else{
            HttpURLConnection httpConn = getHttpURLConnection(uri,"POST");
            result = new String(getBytesFromStream(httpConn.getInputStream()));
        }
        return result;
    }
    private static byte[] getBytesFromStream(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] kb = new byte[1024];
        int len;
        while ((len = is.read(kb)) != -1) {
            baos.write(kb, 0, len);
        }
        byte[] bytes = baos.toByteArray();
        baos.close();
        is.close();
        return bytes;
    }

}

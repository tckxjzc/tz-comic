package com.tckxjzc.tzserver;

import android.net.Uri;
import android.util.Log;

import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpRequest;
import com.koushikdutta.async.http.AsyncHttpResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpTest {

    static String testHtml="\n" +
            "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n" +
            "<meta name=\"viewport\" content=\"width=device-width,height=device-height, initial-scale=1.0, user-scalable=no, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0 \" />\n" +
            "<meta name=\"apple-mobile-web-app-capable\" content=\"yes\" />\n" +
            "<meta name=\"applicable-device\" content=\"mobile\" />\n" +
            "<meta property=\"og:url\" content=\"http://www.1dyf.com/xuanhuan/shouyi/\" />\n" +
            "<title>寿医漫画_寿医漫画免费_寿医漫画全集免费在线阅读-6漫画</title>\n" +
            "<meta name=\"keywords\" content=\"寿医漫画,玄幻漫画\" />\n" +
            "<meta name=\"description\" content=\"6漫画提供寿医的简介,寿医地球在数万年前原本是一个修仙星球，内有天地两道，仙修与佛门为天道，妖鬼魔神为地道，但由于天地两道修者的战争与对抗，使其内灵气慢慢消散直至变成修仙费星后，所有能走的仙者都飞出星际寻找机缘，而存留仙者也因\" />\n" +
            "\n" +
            "<link rel=\"stylesheet\" type=\"text/css\" href=\"/template/wap2/css/reset.css?20200714014916\">\n" +
            "<link rel=\"stylesheet\" type=\"text/css\" href=\"/template/wap2/css/style.css?20200714014916\">\n" +
            "<link rel=\"stylesheet\" type=\"text/css\" href=\"/template/wap2/css/p1.css?20200714014916\">\n" +
            "<link rel=\"stylesheet\" type=\"text/css\" href=\"/template/wap2/css/redcolor.css?20200714014916\" />\n" +
            "<link type=\"text/css\" href=\"/statics/pinglun/css/pinglun.css?20200714014916\" rel=\"stylesheet\" media=\"screen\" />\n" +
            "<link type=\"text/css\" href=\"/template/wap2/css/reset.css?20200714014916\" rel=\"stylesheet\" />\n" +
            "<link type=\"text/css\" href=\"/template/wap2/css/style.css?20200714014916\" rel=\"stylesheet\" />\n" +
            "<link type=\"text/css\" href=\"/template/wap2/css/redcolor.css?20200714014916\" rel=\"stylesheet\" />\n" +
            "\n" +
            "<script type=\"text/javascript\">var qTcms4g={dir:\"/\",gimg:\"/template/wap2/images/\"};</script>\n" +
            "<script type=\"text/javascript\">var qingtiancms_Details={G_mubanpage:\".html\",id:\"3592\",hits:\"8564\",webdir:\"/\",pinglunid:\"10\",pinglunid1:\"\",pinglunid2:\"\",pinglunid3:\"\",title:\"寿医\",classid1pinyin:\"xuanhuan/\",titlepinyin:\"shouyi\"};var uyan_config = {'su':'/3592/'}; </script>\n" +
            "<script type=\"text/javascript\" src=\"/template/wap2/js/public.js?20200714014916\"></script>\n" +
            "<script type=\"text/javascript\" src=\"/template/wap2/js/p.js?20200714014916\"></script>\n" +
            "</head>\n" +
            "<body onresize=\"Detail.dd.myResize();\" class=\"d-bak-gray\">\n" +
            "\n" +
            "<div class=\"topBar\">\n" +
            "<a href=\"http://m.1dyf.com/\"><img src=\"/template/wap2/logo/logo.png\" class=\"mainlogo\" alt=\"6漫画\"></a><a href=\"#doc-oc-demo1\" data-am-offcanvas><img src=\"/template/wap2/images/menu_logo.png\" class=\"menulogo d-fr\"></a><a href=\"/html/fenlei.html\"><img src=\"/template/wap2/images/search_logo.png\" class=\"searchlogo d-fl\"></a>\n" +
            "</div>\n" +
            "\n" +
            "<div class=\"Introduct\">\n" +
            "\n" +
            "<div class=\"Introduct_Sub\">\n" +
            "<div class=\"pic\" id=\"Cover\">\n" +
            "<img src=\"http://img.1dyf.com/upload2/3592/2020/07-24/20200724120135174680_small.jpg\" width=\"100%\" title=\"寿医\">\n" +
            "</div>\n" +
            "<div class=\"sub_r\">\n" +
            "<p class=\"txtItme h1\">寿医</p>\n" +
            "<p class=\"txtItme c1\">状态：07回-五行融合</p>\n" +
            "<p class=\"txtItme\">作者：正月</p>\n" +
            "<p class=\"txtItme\">标签：玄幻,穿越,重生</p>\n" +
            "<p class=\"txtItme\">点击：<span id=\"g_div_hits\">8564</span></p>\n" +
            "<p class=\"txtItme\">时间：07-24</p>\n" +
            "</div>\n" +
            "</div>\n" +
            "</div>\n" +
            "<div style=\"clear:both\"></div>\n" +
            "<div class=\"detailForm d-border\">\n" +
            "<ul class=\"am-avg-sm-2 am-thumbnails toolBar\">\n" +
            "<li class=\"am-thumbnail\" id=\"shownextchapter\">\n" +
            "<a href=\"/xuanhuan/shouyi/385572.html\">\n" +
            "<span id=\"readInfo\"></span>\n" +
            "<img id=\"readBtnImg\" onload=\"Detail.dd.getHeight();\" src=\"/template/wap2/images/detail_btn_1_n.png\" alt=\"阅读\"></a>\n" +
            "</li>\n" +
            "<li class=\"am-thumbnail\">\n" +
            "<a href=\"javascript:void(0)\" onClick=\"Detail.dd.commentsClick2()\">\n" +
            "<img src=\"/template/wap2/images/detail_btn_2_a.png\" alt=\"点评\" id=\"collect_icon\"></a>\n" +
            "</li>\n" +
            "</ul>\n" +
            "<div class=\"detailContent\">\n" +
            "<p class=\"d-nowrap-clamp d-nowrap-clamp-2\">地球在数万年前原本是一个修仙星球，内有天地两道，仙修与佛门为天道，妖鬼魔神为地道，但由于天地两道修者的战争与对抗，使其内灵气慢慢消散直至变成修仙费星后，所有能走的仙者都飞出星际寻找机缘，而存留仙者也因无法晋升而一一陨落，直至地球变成了一个只有凡人的星球。 而主角，便成了地球上存留的唯一一个地修！</p>\n" +
            "<a href=\"javascript:void(0)\" onClick=\"Detail.dd.showContent()\" class=\"more\"><img src=\"/template/wap2/images/arrow_down.png\"></a>\n" +
            "</div>\n" +
            "</div>\n" +
            "<a name=\"c_list\"></a>\n" +
            "\n" +
            "<div class=\"chapterList d-border\">\n" +
            "<ul class=\"am-avg-sm-2 am-thumbnails selecter\">\n" +
            "<li onclick=\"Detail.dd.chaptersClick(1, this);\" class=\"ib active\">章节<span></span></li>\n" +
            "<li onclick=\"Detail.dd.commentsClick(this);\" id=\"li_pinglun\">评论</li>\n" +
            "</ul>\n" +
            "<div class=\"chapters\" id=\"chapterList_1\">\n" +
            "<ul class=\"am-avg-sm-4 am-thumbnails list hide\" id=\"chapterList_ul_1\"><li><a href=\"/xuanhuan/shouyi/385579.html\" class=\"d-nowrap\">07回-五行融合</a></li><li><a href=\"/xuanhuan/shouyi/385578.html\" class=\"d-nowrap\">06回-莫尘无世</a></li><li><a href=\"/xuanhuan/shouyi/385577.html\" class=\"d-nowrap\">05回-轮回道</a></li><li><a href=\"/xuanhuan/shouyi/385576.html\" class=\"d-nowrap\">04回-帮我一个忙</a></li><li><a href=\"/xuanhuan/shouyi/385575.html\" class=\"d-nowrap\">03回-还好控制了一下</a></li><li><a href=\"/xuanhuan/shouyi/385574.html\" class=\"d-nowrap\">02回-他们都是我的病人</a></li><li><a href=\"/xuanhuan/shouyi/385573.html\" class=\"d-nowrap\">01回-你需要帮助吗？</a></li><li><a href=\"/xuanhuan/shouyi/385572.html\" class=\"d-nowrap\">《寿医》预告-正式开启</a></li></ul>\n" +
            "<p class=\"more\">\n" +
            "<a href=\"javascript:Detail.dd.charpterMore(1);\" class=\"mm\" id=\"a_mores\">查看更多</a>\n" +
            "<span class=\"date\">最近更新:07-24</span></p>\n" +
            "</div>\n" +
            "</div>\n" +
            "\n" +
            "<div class=\"manList\" id=\"tjmanhua\" style=\"margin-top: 5px\">\n" +
            "<div class=\"titleBar\"><img src=\"/template/wap2/images/index_title_1.png\"><span class=\"title\">推荐漫画</span><a href=\"/paihang/\" class=\"other d-fr\" title=\"原创精品\"><span>更多</span><img src=\"/template/wap2/images/arrow_right.png\"></a></div>\n" +
            "<ul class=\"am-avg-sm-3 am-thumbnails list\">\n" +
            "<li class=\"am-thumbnail\"><a href=\"/rexue/jipinlamabuhaore/\" title=\"极品辣妈不好惹\"><img src=\"http://www.1dyf.com/upload2/3093/2019/08-16/20190816131303_4218ilrghfde_small.jpg\" alt=\"极品辣妈不好惹漫画\"></a><p class=\"d-nowrap\"><a href=\"/rexue/jipinlamabuhaore/\" title=\"极品辣妈不好惹\">极品辣妈不好惹</a></p></li><li class=\"am-thumbnail\"><a href=\"/lianai/funvpuyuqiongshaoye/\" title=\" 女 仆\"><img src=\"http://www.1dyf.com/upload2/2954/2019/07-19/20190719123719_4218mpaowohu_small.jpg\" alt=\" 女 仆漫画\"></a><p class=\"d-nowrap\"><a href=\"/lianai/funvpuyuqiongshaoye/\" title=\" 女 仆\"> 女 仆</a></p></li><li class=\"am-thumbnail\"><a href=\"/rexue/shiguangheniduhenmei/\" title=\"都很美\"><img src=\"http://www.1dyf.com/upload2/2370/2019/01-17/20190117202553_9843fsxhxwar_small.jpg\" alt=\"都很美漫画\"></a><p class=\"d-nowrap\"><a href=\"/rexue/shiguangheniduhenmei/\" title=\"都很美\">都很美</a></p></li><li class=\"am-thumbnail\"><a href=\"/lianai/aojiaowangyezhongtianzuo/\" title=\"王爷种田妃\"><img src=\"http://www.1dyf.com/upload2/2146/2018/12-13/20181213234154_6093nxrgewdm_small.jpg\" alt=\"王爷种田妃漫画\"></a><p class=\"d-nowrap\"><a href=\"/lianai/aojiaowangyezhongtianzuo/\" title=\"王爷种田妃\">王爷种田妃</a></p></li><li class=\"am-thumbnail\"><a href=\"/kehuan/zhuiwobayinyangguan/\" title=\" 追 我 \"><img src=\"http://www.1dyf.com/upload2/2957/2019/07-19/20190719124049_3593tyiwpcig_small.jpg\" alt=\" 追 我 漫画\"></a><p class=\"d-nowrap\"><a href=\"/kehuan/zhuiwobayinyangguan/\" title=\" 追 我 \"> 追 我 </a></p></li><li class=\"am-thumbnail\"><a href=\"/shenghuo/tianmideqiyue/\" title=\" 契 约\"><img src=\"http://www.1dyf.com/upload2/2856/2019/06-26/20190626162047_1562jcqwilbq_small.jpg\" alt=\" 契 约漫画\"></a><p class=\"d-nowrap\"><a href=\"/shenghuo/tianmideqiyue/\" title=\" 契 约\"> 契 约</a></p></li>\n" +
            "</ul>\n" +
            "</div>\n" +
            "<div class=\"commentList d-border\">\n" +
            "<script>var JqTConfig={\"newsid\":qingtiancms_Details.id,\"dir\":qingtiancms_Details.webdir,\"newsid2\":0,\"status\":\"1\",\"pinglunnumb\":\"0\",\"hits\":\"8564\"}</script>\n" +
            "<div id=\"p-cpmment-div-div\">\n" +
            "<div id=\"p-cpmment-div\">\n" +
            "</div>\n" +
            "</div>\n" +
            "</div>\n" +
            "\n" +
            "<div class=\"pbottom\">\n" +
            "<div class=\"pageLine\"><img src=\"/template/wap2/images/m/images/head_line.gif\" width=\"100%\"></div>\n" +
            "<p class=\"record\">&copy;6漫画 苏ICP备20030000号-1 <a href=\"http://m.1dyf.com/\"><span style=\"color:#5a5858\">返回首页</span></a></p>\n" +
            "</div>\n" +
            "<div style=\"display:none\"><script type=\"text/javascript\" src=\"https://s13.cnzz.com/z_stat.php?id=1274869743&web_id=1274869743\"></script>\n" +
            "<script type=\"text/javascript\" src=\"https://s9.cnzz.com/z_stat.php?id=1278897119&web_id=1278897119\"></script></div>\n" +
            "<script>\n" +
            "(function(){\n" +
            "    var bp = document.createElement('script');\n" +
            "    var curProtocol = window.location.protocol.split(':')[0];\n" +
            "    if (curProtocol === 'https') {\n" +
            "        bp.src = 'https://zz.bdstatic.com/linksubmit/push.js';\n" +
            "    }\n" +
            "    else {\n" +
            "        bp.src = 'http://push.zhanzhang.baidu.com/push.js';\n" +
            "    }\n" +
            "    var s = document.getElementsByTagName(\"script\")[0];\n" +
            "    s.parentNode.insertBefore(bp, s);\n" +
            "})();\n" +
            "</script>\n" +
            "<div class=\"d-mask\" id=\"mask\" onclick=\"Detail.dd.publishBarRecover();\"></div>\n" +
            "<a href=\"javascript:void(0);\" onClick=\"Detail.dd.returnTop()\"><img src=\"/template/wap2/images/return_top_logo.png\" class=\"returnTop\" alt=\"返回顶部\"></a>\n" +
            "\n" +
            "<div id=\"doc-oc-demo1\" class=\"am-offcanvas\">\n" +
            "<div class=\"am-offcanvas-bar am-offcanvas-bar-flip\">\n" +
            "<div class=\"am-offcanvas-content\">\n" +
            "<div class=\"rightMenu\">\n" +
            "<ul class=\"menuList\">\n" +
            "<li><a href=\"http://m.1dyf.com/\" class=\"menu_logo_1\"></a></li>\n" +
            "<li><a href=\"/html/fenlei.html\" class=\"menu_logo_2\"></a></li>\n" +
            "<li><a href=\"/html/history.html\" class=\"menu_logo_4\"></a></li>\n" +
            "</ul>\n" +
            "</div>\n" +
            "</div>\n" +
            "</div>\n" +
            "</div>\n" +
            "<script language=\"javascript\">Detail.Init();All.K.Init();</script>\n" +
            "<script language=\"javascript\" src=\"/statics/pinglun/js/pinglun.js\"></script>\n" +
            "</body></html>";

    OkHttpClient client=new OkHttpClient();

    public void  test(){
        RequestBody requestBody = RequestBody.create("searchword=%CE%B4%C0%B4", MediaType.get("application/x-www-form-urlencoded"));

        Request request = new Request.Builder()
                .url(ServerConfig.BASE_URL+"/search.asp")
                .post(requestBody)
                .build();

        new Thread(()->{
            try {
                Response execute = client.newCall(request).execute();
                Log.d("okhttp:test",  "-------------------------");
                byte[] buff=execute.body().bytes();
                Log.d("okhttp:test",  ""+buff.length);
                String s = new String(buff, StandardCharsets.UTF_8);
                Log.d("okhttp:test",  ""+ s.substring(s.length()/2));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void  test2(AsyncHttpRequest request){
        AsyncHttpClient.getDefaultInstance().execute(request,(ex, response)->{

        });
    }
    public void  test3(String request){
        Uri uri=Uri.parse(request);
        AsyncHttpRequest asyncHttpRequest = new AsyncHttpRequest(uri, "GET");

//        Future<AsyncHttpResponse> execute = AsyncHttpClient.getDefaultInstance().execute(asyncHttpRequest, (ex, response) -> {
//
//        });
//        execute.done((e,result)->{
//            Log.d("execute", "test3: -------------------");
//            Log.d("execute", "test3: "+result.code());
//            result.setDataCallback((emitter,bufferList)->{
//                emitter.pause();
//                Log.d("execute", "test3: "+bufferList.remaining());
//            });
//        });

//        AsyncHttpClient.getDefaultInstance().executeFile(asyncHttpRequest,context.getExternalCacheDir().getAbsolutePath()+"/"+"tmp",new AsyncHttpClient.FileCallback(){
//            @Override
//            public void onCompleted(Exception e, AsyncHttpResponse source, File result) {
//                Log.d("execute", "test3: "+result.length());
//                Log.d("execute", "test3: "+context.getExternalCacheDir().getAbsolutePath()+"/"+"tmp");
//            }
//        });
        AsyncHttpClient.getDefaultInstance().executeByteBufferList(asyncHttpRequest,new AsyncHttpClient.DownloadCallback(){

            @Override
            public void onCompleted(Exception e, AsyncHttpResponse source, ByteBufferList result) {
                Log.d("execute", "test3: "+result.remaining());
            }
        });
    }
    public void test4(){
//        execute.done((e,result)->{
//            request.setDataCallback((emitter,bufferList)->{
//                Log.d("proxy:done", ":done " + bufferList.remaining());
//                Log.d("proxy:done", ":done " + bufferList.hasRemaining());
//                response.send("ok");
//            });
//        });
    }
    public void  test5(){
        //            if(request.getUrl().startsWith("/search.asp")){
//                Object body = request.getBody().get();
//                String word=null;
//                if(body instanceof Multimap){
//                   word =((Multimap) body).getString("searchword");
//                    Log.d("TAG", "searchword: "+word);
//                    Log.d("TAG", "searchword: "+Uri.encode(word));
//                    Log.d("TAG", "searchword: "+Uri.decode(word));
//                    Log.d("TAG", "searchword: "+Uri.decode(Uri.encode(word)));
//                    Log.d("TAG", "searchword: "+Uri.encode(Uri.decode(word)));
//                }
//                if(word!=null){
//                    try {
//                        response.send("text/html",search(Uri.decode(word)));
//                    } catch (IOException e) {
//                        response.send(e.toString());
//                    }
//                }else {
//                    response.send("不能为空");
//                }
//
//                return;
//            }else {
//                asyncHttpRequest.setBody(request.getBody());
//            }
    }

}

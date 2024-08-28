package org.cherrygirl.manager;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.cherrygirl.config.ResourcesConfig;
import org.cherrygirl.pojo.*;
import org.cherrygirl.service.CookiesService;
import org.cherrygirl.utils.BilibiliApiRequestUtil;
import org.cherrygirl.utils.WbiUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户空间manager
 */
@Service
public class SpaceManager {

    @Autowired
    private CookiesService cookiesService;


    @Autowired
    private ResourcesConfig resourcesConfig;

    @Autowired
    private ScancodeLoginManager scancodeLoginManager;

    public String getDownlownFile(){
        return resourcesConfig.getRoot() + "/bilibiliDownloadVideo/";
    }

    public List<BilibiliWbiSearchResponseDataListVListVO> getAllWorks(Long mid) {
        //cookie
        BilibiliFingerResponseDataVO finger = WbiUtil.getFinger();
        String b3 = finger.getB_3();
        String b4 = finger.getB_4();
        List<NameValueVO> list = new ArrayList<>();
        list.add(new NameValueVO("buvid3", b3));
        list.add(new NameValueVO("buvid4", b4));
        String cookies = scancodeLoginManager.getStringCookies(list);
        BilibiliWbiSearchResponseVO allWorks = getAllWorks(mid, 1L, cookies);
        List<BilibiliWbiSearchResponseDataListVListVO> vlist = allWorks.getData().getList().getVlist();
        BilibiliWbiSearchResponseDataPageVO page = allWorks.getData().getPage();
        Long ceil = (long) Math.ceil(page.getCount() / 30.0);
        if(ceil > 1){
            Long pn = page.getPn() + 1;
            while (pn <= ceil){
                BilibiliWbiSearchResponseVO allWorksFor = getAllWorks(mid, pn++, cookies);
                List<BilibiliWbiSearchResponseDataListVListVO> vlist1 = allWorksFor.getData().getList().getVlist();
                vlist.addAll(vlist1);
            }
        }
        return vlist;
    }

    /**
     * 分页获取用户投稿
     */
    public BilibiliWbiSearchResponseVO getAllWorks(Long mid, Long qn, String cookies) {

        String api = "https://api.bilibili.com/x/space/wbi/arc/search";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("mid", mid);
        paramMap.put("pn", qn);
        paramMap.put("ps", 30);
        paramMap.put("tid", 0);
        paramMap.put("keyword", "");
        paramMap.put("order", "pubdate");
        paramMap.put("platform", "web");
        paramMap.put("web_location", "1550101");
        paramMap.put("order_avoided", true);
        paramMap.put("dm_img_list", "[{\"x\":2028,\"y\":1163,\"z\":0,\"timestamp\":181,\"k\":74,\"type\":0},{\"x\":2419,\"y\":1339,\"z\":1,\"timestamp\":290,\"k\":84,\"type\":0},{\"x\":3268,\"y\":1375,\"z\":69,\"timestamp\":467,\"k\":105,\"type\":0}]");
        paramMap.put("dm_img_inter", "{\"ds\":[{\"t\":0,\"c\":\"\",\"p\":[255,85,85],\"s\":[13,5348,4546]}],\"wh\":[3930,5780,26],\"of\":[247,494,247]}");
        paramMap.put("dm_img_str", "V2ViR0wgMS4wIChPcGVuR0wgRVMgMi4wIENocm9taXVtKQ");
        paramMap.put("dm_cover_img_str", "QU5HTEUgKEFNRCwgQU1EIFJhZGVvbihUTSkgR3JhcGhpY3MgKDB4MDAwMDE2MzgpIERpcmVjdDNEMTEgdnNfNV8wIHBzXzVfMCwgRDNEMTEpR29vZ2xlIEluYy4gKEFNRC");
        //header
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Cookie", cookies);
        headerMap.put("Host", "api.bilibili.com");
        headerMap.put("Referer", "https://space.bilibili.com");
        headerMap.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36");
        String jsonBody = BilibiliApiRequestUtil.get(api, cookies, paramMap, headerMap, true);
        return JSON.parseObject(jsonBody, BilibiliWbiSearchResponseVO.class);
    }
}

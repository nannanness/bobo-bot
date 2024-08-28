package org.cherrygirl.command;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.*;
import net.mamoe.mirai.utils.ExternalResource;
import org.apache.commons.lang3.StringUtils;
import org.cherrygirl.config.ResourcesConfig;
import org.cherrygirl.manager.VideoManager;
import org.cherrygirl.pojo.BilibiliInterfaceResponseVO;
import org.cherrygirl.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * bilibili视频解析
 */
@Component
public class BilibiliVideoParseCommand implements Command {

    @Autowired
    private ResourcesConfig resourcesConfig;
    @Autowired
    private VideoManager videoManager;
    @Override
    public void run(GroupMessageEvent event, int id, String... params) throws IOException {
        Group group = event.getGroup();

        LightApp lightApp = (LightApp) event.getMessage().stream().filter(LightApp.class::isInstance).findFirst().orElse(null);
        if(lightApp != null){
            System.out.println("检测到小程序，，，");
            String content = lightApp.getContent();
            System.out.println("content:  " + content);
            JSONObject jsonObject = JSONObject.parseObject(content);
            String app = jsonObject.getString("app");
            if("com.tencent.miniapp_01".equals(app)){
                JSONObject meta = jsonObject.getJSONObject("meta");
                JSONObject detail1 = meta.getJSONObject("detail_1");
                String qqdocurl = detail1.getString("qqdocurl");
                System.out.println(qqdocurl);
                HttpRequest httpRequest = HttpRequest.get(qqdocurl);
                HttpResponse httpResponse = httpRequest.execute();
                String location = httpResponse.header("Location");
                String bv = null;
                String pattern = "BV[A-Za-z0-9]+";
                // 创建 Pattern 对象
                Pattern r = Pattern.compile(pattern);
                // 现在创建 imgUrl matcher 对象
                Matcher m = r.matcher(location);
                if (m.find( )) {
                    bv = m.group(0);
                }
                System.out.println(bv);
                BilibiliInterfaceResponseVO resultVO = videoManager.getVideoInformation(null, bv);
//                System.out.println("bilibili:" + JSON.toJSONString(resultVO));
                String picUrl = resultVO.getData().getPic();
                //\/:*?"<>|
                String originTitle = FileUtil.checkLegalityFilename(resultVO.getData().getTitle());
                File picFile = new File(videoManager.getDownlownFile(), originTitle + ".jpg");
                HttpUtil.downloadFile(picUrl, picFile);
                ExternalResource resource = ExternalResource.create(picFile);
                Image image = event.getGroup().uploadImage(resource);
                String aiConclusion = videoManager.getAiConclusion(null, resultVO.getData().getBvid(), resultVO.getData().getCid(), resultVO.getData().getOwner().getMid());
                MessageChainBuilder chainBuilder = new MessageChainBuilder()
                        .append(Image.fromId(image.getImageId()))
                        .append("标题：").append(originTitle).append("\n")
                        .append("作者：").append(resultVO.getData().getOwner().getName()).append("\n")
                        .append("简介：").append(resultVO.getData().getDesc());
                if(StringUtils.isNotEmpty(aiConclusion)){
                    chainBuilder.append("\n").append("AI总结：").append(aiConclusion);
                }
                group.sendMessage(chainBuilder.build());
                List<String> playerVideoUrlList = videoManager.getPlayerVideoUrl(null, resultVO.getData().getBvid(), resultVO.getData().getCid());
//                System.out.println("bilibili playerVideoUrl:" + JSON.toJSONString(resultVO));
                String filenamePre = FileUtil.checkLegalityFilename(resultVO.getData().getOwner().getName() + "_" + originTitle);
                File fileDict = new File(videoManager.getDownlownFile() , filenamePre + ".mp4");
                Long fileLength = videoManager.downloadVideo(playerVideoUrlList, fileDict.getAbsolutePath());
                ExternalResource resource2 = ExternalResource.create(fileDict);
                ShortVideo shortVideo = group.uploadShortVideo(resource2, resource2, fileDict.getName());
                resource.close();
                resource2.close();
                group.sendMessage(shortVideo);

            }
        }
    }
}
package org.cherrygirl.command;

import com.alibaba.fastjson.JSON;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.utils.ExternalResource;
import org.apache.commons.lang3.StringUtils;
import org.cherrygirl.config.ResourcesConfig;
import org.cherrygirl.manager.CoinManager;
import org.cherrygirl.pojo.WxResult;
import org.cherrygirl.service.PictureService;
import org.cherrygirl.utils.TalkUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 文心一格，生成图片
 */
@Component
public class ChatImgWxCommand implements Command, FriendCommand{

    @Autowired
    private CoinManager coinManager;

    @Autowired
    private ResourcesConfig resourcesConfig;

    @Override
    public void run(GroupMessageEvent event, int id, String... params) throws IOException {
        String text = null;
        long qq = event.getSender().getId();
        if(params != null && params.length > 0 && StringUtils.isNotEmpty(params[0])){
            text = params[0];
        } else {
            event.getGroup().sendMessage(new At(qq).plus("格式有误，请输入：生成图片 猫咪（图片内容）"));
            return;
        }
        event.getGroup().sendMessage(new At(qq).plus("你的图片生成中，稍等一下哦~\n（本技术由Stable Diffusion模型提供）"));
        WxResult wxResult = TalkUtil.imgWx(resourcesConfig.getRoot() + "/image/WXIMG", text);
        if(WxResult.TYPE_IMG_FAIL.equals(wxResult.getType())){
            event.getGroup().sendMessage(new At(qq).plus(wxResult.getResult()));
            return;
        }
        List<String> imgList = wxResult.getImgList();
        if(!imgList.isEmpty()){
            System.out.println("WXIMG name : " + JSON.toJSONString(imgList));
            MessageChainBuilder msg = new MessageChainBuilder();
            msg.append(new At(qq)).append(" 你的图片生成好了\n");
            imgList.forEach(img -> {
                File file = new File(img);
                if(file.exists()){
                    ExternalResource resource = ExternalResource.create(file);
                    Image image = event.getGroup().uploadImage(resource);
                    try {
                        resource.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    msg.append(Image.fromId(image.getImageId()));
                }
            });
            msg.append("\n本次生成共消耗tokens：" + wxResult.getCount() + "，花费波币：" + wxResult.getCount() + "）");
            event.getGroup().sendMessage(msg.build());
            if(wxResult.getCount() != 0){
                coinManager.subCoins(event.getGroup().getId(), qq, -wxResult.getCount());
            }
        }else{
            event.getGroup().sendMessage("图片生成失败，波币无消耗");
        }
    }

    @Override
    public void runFriend(FriendMessageEvent event, long qq, String message) throws IOException {
        String text = null;
        String s = "绘图";
        if(message.startsWith(s)){
            text = message.replaceFirst(s, "");
            if(StringUtils.isEmpty(text)){
                event.getFriend().sendMessage("格式有误，请输入：绘图 猫咪（图片内容）");
                return;
            }
            event.getFriend().sendMessage("图片生成中，稍等一下哦~\n（本技术由Stable Diffusion模型提供）");
            WxResult wxResult = TalkUtil.imgWx(resourcesConfig.getRoot() + "/image/WXIMG", text);
            if(WxResult.TYPE_IMG_FAIL.equals(wxResult.getType())){
                event.getFriend().sendMessage(wxResult.getResult());
                return;
            }
            List<String> imgList = wxResult.getImgList();
            if(!imgList.isEmpty()){
                System.out.println("WXIMG friend name : " + JSON.toJSONString(imgList));
                event.getFriend().sendMessage(" 图片生成好了\n");
                imgList.forEach(img -> {
                    File file = new File(img);
                    if(file.exists()){
                        ExternalResource resource = ExternalResource.create(file);
                        Image image = event.getFriend().uploadImage(resource);
                        event.getFriend().sendMessage(Image.fromId(image.getImageId()));
                        try {
                            resource.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                event.getFriend().sendMessage("本次生成共消耗tokens：" + wxResult.getCount() );
            }else{
                event.getFriend().sendMessage("图片生成失败");
            }
        }

    }
}

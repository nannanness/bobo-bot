package org.cherrygirl.command;

import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.*;
import org.apache.commons.lang3.StringUtils;
import org.cherrygirl.command.handler.GroupCommandHandler;
import org.cherrygirl.manager.CoinManager;
import org.cherrygirl.manager.MessageRecordManager;
import org.cherrygirl.service.ImgService;
import org.cherrygirl.manager.ImgManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.cherrygirl.chinesechess.ResponseType.image;

/**
 * 上传图片
 * @author nannanness
 */
@Component
public class UploadImgCommand implements Command{

    @Autowired
    private ImgService imgService;

    @Autowired
    private ImgManager imgManager;

    @Autowired
    private CoinManager coinManager;

    @Autowired
    MessageRecordManager messageRecordManager;

    private boolean flag;
    private boolean send;
    private long uploader;
    private long uploadTime;
    private long uploadGroup;

    public void upLoading(long groupId, long uploader, long uploadTime){
        flag = true;
        this.uploader = uploader;
        this.uploadTime = uploadTime;
        this.uploadGroup = groupId;
    }

    public void upLoadFinish(int id){
        send = false;
        flag = false;
        this.uploader = 0;
        this.uploadTime = 0;
        this.uploadGroup = 0;
        GroupCommandHandler.clearCache(id);
    }


    private void uploadImging(GroupMessageEvent event,  int id){
        MessageChain messageChain = event.getMessage();
        Image image = (Image) messageChain.stream().filter(Image.class::isInstance).findFirst().orElse(null);
        if(image != null){
            long groupId = event.getGroup().getId();
            long qq = event.getSender().getId();
            String url = Image.queryUrl(image);
            String file = imgManager.uploadImg(url);
            if(StringUtils.isNotEmpty(file)){
                boolean b = imgService.addImgDdd(file);
                if(b){
                    coinManager.add50Coins(groupId, qq);
                    event.getGroup().sendMessage(new At(event.getSender().getId()).plus("感谢您的上传！波币+50"));
                    upLoadFinish(id);
                }
            }
        }
    }
    private void uploadImging(GroupMessageEvent event, List<String> urlList, int id, long fromId){
        if(urlList != null){
            long groupId = event.getGroup().getId();
            long qq = event.getSender().getId();
            urlList.forEach(url -> {
                String file = imgManager.uploadImg(url);
                if(StringUtils.isNotEmpty(file)){
                    boolean b = imgService.addImgDdd(file);
                    if(b){
                        coinManager.add50Coins(groupId, qq);
                        event.getGroup().sendMessage(new At(qq).plus("感谢您的上传！波币+50"));
                        if(qq != fromId){
                            coinManager.add50Coins(groupId, fromId);
                            event.getGroup().sendMessage(new At(fromId).plus("感谢您的上传！波币+50"));
                        }

                    }
                }
            });
        }
        upLoadFinish(id);
    }


    @Override
    public void run(GroupMessageEvent event, int id, String... params) throws IOException {
        System.out.println("upload ddd length : " + params.length);
        if(!flag){
            QuoteReply quoteReply = (QuoteReply) event.getMessage().stream().filter(QuoteReply.class::isInstance).findFirst().orElse(null);
            if (quoteReply != null) {
                String ids = Arrays.toString(quoteReply.getSource().getIds());
                long formId = quoteReply.getSource().getFromId();
                long groupId = event.getGroup().getId();
                List<String> queryImgList = messageRecordManager.queryImg (ids, groupId, formId);
                uploadImging(event, queryImgList, id, formId);
            } else {
                upLoading(event.getGroup().getId(), event.getSender().getId(), System.currentTimeMillis() + 1000 * 18);
                if(!send){
                    event.getGroup().sendMessage("请发送您要上传的图片！");
                    send = true;
                }
            }
        } else if(uploadTime < System.currentTimeMillis()){
            upLoadFinish(id);
        } else if(flag && uploadGroup == event.getGroup().getId() && uploader == event.getSender().getId()) {
            uploadImging(event, id);
        }
    }

}

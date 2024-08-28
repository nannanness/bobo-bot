package org.cherrygirl.command;

import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.utils.ExternalResource;
import org.cherrygirl.domain.TalkDO;
import org.cherrygirl.manager.CoinManager;
import org.cherrygirl.manager.SignInImgManager;
import org.cherrygirl.manager.TalkManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

/**
 * 每日签到
 * @author nannanness
 */
@Slf4j
@Component
public class SignInTodayCommand implements Command {

    @Autowired
    private CoinManager coinManager;

    @Autowired
    private TalkManager talkManager;
    @Autowired
    private SignInImgManager signInImgManager;

    @Override
    public void run(GroupMessageEvent event, int id, String... params) throws IOException {

        long groupId = event.getGroup().getId();
        long qq = event.getSender().getId();
        String s = groupId + ":" + qq;
        try {
            TalkDO talkDO = talkManager.viewTodayTalks(groupId, qq);
            if(talkDO == null || talkDO.getSignInToday() == 0) {
                coinManager.add50Coins(groupId, qq);
                talkManager.signInToday(groupId, qq);
                String qqUrl = event.getGroup().getMembers().get(Long.parseLong(String.valueOf(qq))).getAvatarUrl();
                String qqSource = signInImgManager.genSource(qqUrl, String.valueOf(qq));
                String signImg = signInImgManager.getSignImg(qqSource, String.valueOf(qq));
                File file = new File(signImg);
                if(file.exists()){
                    System.out.println("sign name : " + file.getName());
                    ExternalResource resource = ExternalResource.create(file);
                    Image image = event.getGroup().uploadImage(resource);
                    resource.close();
                    event.getGroup().sendMessage(new At(event.getSender().getId()).plus("签到成功，波币+50").plus(Image.fromId(image.getImageId())));
                }

            } else if(talkDO.getSignInToday() == 1){
                event.getGroup().sendMessage(new At(event.getSender().getId()).plus("你今天已经签到过了哟"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}

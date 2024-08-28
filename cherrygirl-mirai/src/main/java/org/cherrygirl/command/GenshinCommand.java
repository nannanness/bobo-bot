package org.cherrygirl.command;

import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.utils.ExternalResource;
import org.cherrygirl.domain.CoinDO;
import org.cherrygirl.domain.TalkDO;
import org.cherrygirl.manager.CoinManager;
import org.cherrygirl.manager.GenshinManager;
import org.cherrygirl.manager.TalkManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * 原神抽卡
 * @author nannanness
 */
@Slf4j
@Component
public class GenshinCommand implements Command {

    @Autowired
    private GenshinManager genshinManager;
    @Autowired
    private CoinManager coinManager;


    @Override
    public void run(GroupMessageEvent event, int id, String... params) throws IOException {

        long groupId = event.getGroup().getId();
        long qq = event.getSender().getId();
        String s = groupId + ":" + qq;
        CoinDO coinDO = coinManager.viewCoins(groupId, qq);
        if(coinDO != null && coinDO.getCoin() != null){
            if(coinDO.getCoin() < 200){
                event.getGroup().sendMessage(new At(event.getSender().getId()).plus("波币不足哟"));
            } else {
                coinManager.sub200Coins(event.getGroup().getId(), event.getSender().getId());
                List<String> tenCompaniesGif = genshinManager.getTenCompaniesGif(String.valueOf(groupId), String.valueOf(qq));
                if(!tenCompaniesGif.isEmpty()){
                    String img = tenCompaniesGif.get(0);
                    File file = new File(img);
                    if(file.exists()){
                        ExternalResource resource = ExternalResource.create(file);
                        Image image = event.getGroup().uploadImage(resource);
                        resource.close();
                        event.getGroup().sendMessage(new At(event.getSender().getId()).plus("本次十连收获颇丰，来看看吧!\n" +
                                "（消耗200波币）\n").plus(Image.fromId(image.getImageId())));
                    }else {
                        event.getGroup().sendMessage(new At(event.getSender().getId()).plus("波波忙晕了，请一会儿再来吧~"));
                    }
                }else {
                    event.getGroup().sendMessage(new At(event.getSender().getId()).plus("波波忙晕了，请一会儿再来吧~"));
                }
            }
        } else {
            event.getGroup().sendMessage(new At(event.getSender().getId()).plus("波币不足哟"));
        }
    }
}

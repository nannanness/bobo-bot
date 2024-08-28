package org.cherrygirl.command;

import cn.hutool.core.util.RandomUtil;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.ShortVideo;
import net.mamoe.mirai.utils.ExternalResource;
import org.cherrygirl.config.ResourcesConfig;
import org.cherrygirl.domain.WorksInfoDO;
import org.cherrygirl.manager.SpaceManager;
import org.cherrygirl.pojo.BilibiliWbiSearchResponseDataListVListVO;
import org.cherrygirl.service.WorksInfoService;
import org.cherrygirl.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * bilibili作品信息下载
 */
@Component
public class BilibiliWorksDownloadCommand implements Command, FriendCommand{

    @Autowired
    private WorksInfoService worksInfoService;
    @Autowired
    private SpaceManager spaceManager;
    @Override
    public void run(GroupMessageEvent event, int id, String... params) throws IOException {

    }

    @Override
    public void runFriend(FriendMessageEvent event, long qq, String message) throws IOException {
        System.out.println("mid: " + message);
        List<BilibiliWbiSearchResponseDataListVListVO> allWorks = spaceManager.getAllWorks(Long.parseLong(message));
        System.out.println("当前作品数量：" + allWorks.size());
        List<WorksInfoDO> worksInfoDOS = WorksInfoDO.turnFromVList(allWorks);
        boolean b = worksInfoService.saveBatch(worksInfoDOS);
        System.out.println("入库信息：" + b);
        if(b){
            event.getFriend().sendMessage("已入库，当前作品数量：" + allWorks.size());
        }else {
            event.getFriend().sendMessage("入库失败，请查看日志信息");
        }
    }
}
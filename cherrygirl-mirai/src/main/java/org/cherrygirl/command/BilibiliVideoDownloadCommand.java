package org.cherrygirl.command;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.apache.commons.lang3.StringUtils;
import org.cherrygirl.config.ResourcesConfig;
import org.cherrygirl.domain.WorksInfoDO;
import org.cherrygirl.manager.SpaceManager;
import org.cherrygirl.manager.VideoManager;
import org.cherrygirl.pojo.BilibiliInterfaceResponseVO;
import org.cherrygirl.pojo.BilibiliWbiSearchResponseDataListVListVO;
import org.cherrygirl.service.WorksInfoService;
import org.cherrygirl.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BilibiliVideoDownloadCommand implements Command, FriendCommand{

    @Autowired
    private WorksInfoService worksInfoService;
    @Autowired
    private VideoManager videoManager;
    @Autowired
    private ResourcesConfig resourcesConfig;
    @Override
    public void run(GroupMessageEvent event, int id, String... params) throws IOException {

    }

    @Override
    public void runFriend(FriendMessageEvent event, long qq, String message) throws IOException {
        System.out.println("mid: " + message);
        QueryWrapper<WorksInfoDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(WorksInfoDO::getMid, Long.parseLong(message));
        queryWrapper.lambda().eq(WorksInfoDO::getIsDownload, 0);
        List<WorksInfoDO> list = worksInfoService.list(queryWrapper);
        System.out.println("当前未下载作品数量：" + list.size());
        List<Long> listDownloadIdList = new ArrayList<>(list.size());
        list.forEach(worksInfoDO -> {
            BilibiliInterfaceResponseVO videoInformation = videoManager.getVideoInformation(null, worksInfoDO.getBvid());
            if(!videoInformation.getData().is_upower_exclusive()){
                String biliRoot = resourcesConfig.getBiliRoot();
                String foldername = videoInformation.getData().getOwner().getName() + "的视频列表";
                File folderFile = new File(biliRoot, foldername);
                boolean isMaker = FileUtil.makeFile(folderFile.getAbsolutePath());
                String title = FileUtil.checkLegalityFilename(videoInformation.getData().getTitle());
                File downloadFile = new File(folderFile, title + ".mp4");

                Long cid = videoInformation.getData().getCid();
                List<String> playerVideoUrlList = videoManager.getPlayerVideoUrl(null, worksInfoDO.getBvid(), cid);

                Long length = videoManager.downloadVideo(playerVideoUrlList, downloadFile.getAbsolutePath());
                if(length > 0){
                    listDownloadIdList.add(worksInfoDO.getId());
                }
            }
        });
        List<WorksInfoDO> worksInfoDOList = list.stream().filter(worksInfoDO -> listDownloadIdList.contains(worksInfoDO.getId())).collect(Collectors.toList());
        worksInfoDOList.forEach(worksInfoDO -> worksInfoDO.setIsDownload(1));
        boolean b = worksInfoService.updateBatchById(worksInfoDOList);
        if(b){
            event.getFriend().sendMessage("下载成功，未下载作品数量：" + list.size() + "本次下载：" + worksInfoDOList.size());
        }else {
            event.getFriend().sendMessage("下载失败，请查看日志信息");
        }
    }
}

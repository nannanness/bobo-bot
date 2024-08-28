package org.cherrygirl.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.cherrygirl.pojo.BilibiliWbiSearchResponseDataListVListVO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author nannanness
 */
@TableName("cherrygirl_works_info")
public class WorksInfoDO {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField(value = "aid")
    private Long aid;
    @TableField(value = "mid")
    private Long mid;
    @TableField(value = "bvid")
    private String bvid;
    @TableField(value = "pic")
    private String pic;
    @TableField(value = "author")
    private String author;
    @TableField(value = "title")
    private String title;
    @TableField(value = "is_download")
    private Integer isDownload;
    @TableField(value = "created")
    private Timestamp created;

    public static List<WorksInfoDO> turnFromVList(List<BilibiliWbiSearchResponseDataListVListVO> listVListVOList){
        List<WorksInfoDO> list = new ArrayList<>(listVListVOList.size());
        listVListVOList.forEach(listVListVO -> {
            WorksInfoDO worksInfoDO = new WorksInfoDO();
            worksInfoDO.setAid(listVListVO.getAid());
            worksInfoDO.setBvid(listVListVO.getBvid());
            worksInfoDO.setAuthor(listVListVO.getAuthor());
            worksInfoDO.setMid(listVListVO.getMid());
            worksInfoDO.setPic(listVListVO.getPic());
            worksInfoDO.setTitle(listVListVO.getTitle());
            worksInfoDO.setCreated(new Timestamp(Long.parseLong(listVListVO.getCreated() + "000")));
            worksInfoDO.setIsDownload(0);
            list.add(worksInfoDO);
        });
        return list;
    }

    public WorksInfoDO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAid() {
        return aid;
    }

    public void setAid(Long aid) {
        this.aid = aid;
    }

    public Long getMid() {
        return mid;
    }

    public void setMid(Long mid) {
        this.mid = mid;
    }

    public String getBvid() {
        return bvid;
    }

    public void setBvid(String bvid) {
        this.bvid = bvid;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getIsDownload() {
        return isDownload;
    }

    public void setIsDownload(Integer isDownload) {
        this.isDownload = isDownload;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }
}

package org.cherrygirl.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BilibiliInterfaceResponseDataVO {
    private String bvid;
    private Long aid;
    private Long cid;
    private String pic;
    private String title;
    private String desc;
    private boolean is_upower_exclusive;
    private BilibiliInterfaceResponseDataVideoOwnerVO owner;
}

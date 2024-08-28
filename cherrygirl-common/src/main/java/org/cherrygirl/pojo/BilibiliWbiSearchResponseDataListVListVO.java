package org.cherrygirl.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BilibiliWbiSearchResponseDataListVListVO {
    private String pic;
    private String author;
    private String title;
    private String bvid;
    private Long aid;
    private Long mid;
    private Long created;

}

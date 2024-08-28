package org.cherrygirl.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BilibiliWbiSearchResponseDataVO {
    private BilibiliWbiSearchResponseDataListVO list;
    private BilibiliWbiSearchResponseDataPageVO page;
}

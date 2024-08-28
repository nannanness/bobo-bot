package org.cherrygirl.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BilibiliWbiSearchResponseDataPageVO {
    private Long pn;
    private Long ps;
    private Long count;
}

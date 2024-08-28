package org.cherrygirl.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BilibiliWbiSearchResponseVO {
    private Integer code;
    private String message;
    private BilibiliWbiSearchResponseDataVO data;
}

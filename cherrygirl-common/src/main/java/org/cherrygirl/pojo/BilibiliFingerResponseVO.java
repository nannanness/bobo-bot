package org.cherrygirl.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BilibiliFingerResponseVO {
    private Integer code;
    private String message;
    private BilibiliFingerResponseDataVO data;
}

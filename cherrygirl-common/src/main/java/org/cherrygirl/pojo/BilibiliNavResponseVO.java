package org.cherrygirl.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BilibiliNavResponseVO {

    private Integer code;
    private String message;
    private BilibiliNavResponseDataVO data;
}

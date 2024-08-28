package org.cherrygirl.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BilibiliVideoConclusionResponseDataVO {

    private Integer code;
    private BilibiliVideoConclusionResponseDataModelResultVO model_result;
}

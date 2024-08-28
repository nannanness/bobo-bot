package org.cherrygirl.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BilibiliVideoConclusionResponseDataModelResultVO {
    private Integer result_type;
    private String summary;
}

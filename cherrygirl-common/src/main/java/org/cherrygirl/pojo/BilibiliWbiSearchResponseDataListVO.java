package org.cherrygirl.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BilibiliWbiSearchResponseDataListVO {
    private List<BilibiliWbiSearchResponseDataListVListVO> vlist;
}

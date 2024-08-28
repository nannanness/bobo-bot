package org.cherrygirl.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WxResult {

    public static final String TYPE_TEXT = "text";

    public static final String TYPE_IMG = "img";

    public static final String TYPE_IMG_FAIL = "fail";

    public static final String TYPE_IMG_ILLEGAL = "生成词含有非法内容";

    public static final String TYPE_IMG_TIME_OUT_TITLE = "生成超时";

    private String result;

    private boolean clear;

    private int count;

    private String type;

    private List<String> imgList;

}
package org.cherrygirl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.cherrygirl.domain.ImgDO;
import org.cherrygirl.domain.TextDO;

import java.util.List;

public interface TextService  extends IService<TextDO> {

    boolean addText(TextDO textDO);

    boolean addText(String text, int type);

    boolean addText(String text);

    List<TextDO> getRandomTextByType(int type);

    TextDO getRandomText(int type);

    String getTextStr();
}

package org.cherrygirl.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.cherrygirl.dao.ImgMapper;
import org.cherrygirl.dao.TextMapper;
import org.cherrygirl.domain.ImgDO;
import org.cherrygirl.domain.TextDO;
import org.cherrygirl.service.ImgService;
import org.cherrygirl.service.TextService;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

/**
 * @author nannanness
 */
@Service
public class TextServiceImpl extends ServiceImpl<TextMapper, TextDO> implements TextService {
    @Override
    public boolean addText(TextDO textDO) {
        int insert = super.baseMapper.insert(textDO);
        return insert > 0;
    }

    @Override
    public boolean addText(String text, int type) {
        TextDO textDO = new TextDO();
        String encodeStr = new String(Base64.getEncoder().encode(text.getBytes()));
        byte[] decode = Base64.getDecoder().decode(encodeStr);
        textDO.setText(decode);
        textDO.setType(type);
        int insert = super.baseMapper.insert(textDO);
        return insert > 0;
    }

    @Override
    public boolean addText(String text) {
        TextDO textDO = new TextDO();
        String encodeStr = new String(Base64.getEncoder().encode(text.getBytes()));
        byte[] decode = Base64.getDecoder().decode(encodeStr);
        textDO.setText(decode);
        int insert = super.baseMapper.insert(textDO);
        return insert > 0;
    }

    @Override
    public List<TextDO> getRandomTextByType(int type) {
        QueryWrapper<TextDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(TextDO::getType, type);
        return super.baseMapper.selectList(queryWrapper);
    }

    @Override
    public TextDO getRandomText(int type) {
        QueryWrapper<TextDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(TextDO::getType, type);
        List<TextDO> textDOS = super.baseMapper.selectList(queryWrapper);
        return textDOS.get(RandomUtil.randomInt(textDOS.size()));
    }

    @Override
    public String getTextStr() {
        List<TextDO> textDOS = super.baseMapper.selectList(null);
        byte[] text = textDOS.get(RandomUtil.randomInt(textDOS.size())).getText();
        return new String(text);
    }
}

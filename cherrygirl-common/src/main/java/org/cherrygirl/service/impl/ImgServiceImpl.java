package org.cherrygirl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.cherrygirl.dao.ImgMapper;
import org.cherrygirl.domain.ImgDO;
import org.cherrygirl.service.ImgService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

/**
 * @author nannanness
 */
@Service
public class ImgServiceImpl extends ServiceImpl<ImgMapper, ImgDO> implements ImgService {

    @Override
    public boolean addImg(ImgDO imgDo) {
        int insert = baseMapper.insert(imgDo);
        if(insert > 0){
            return true;
        }
        return false;
    }

    @Override
    public boolean addImgDdd(String name) {
        ImgDO imgDo = new ImgDO();
        imgDo.setName(name);
        imgDo.setType(1);
        int insert = baseMapper.insert(imgDo);
        if(insert > 0){
            return true;
        }
        return false;
    }

    @Override
    public List<ImgDO> getRandomImgByType(int type) {
        QueryWrapper<ImgDO> qw = new QueryWrapper<>();
        qw.lambda().eq(ImgDO::getType, type);
        return baseMapper.selectList(qw);
    }

    @Override
    public ImgDO getDdd() {
        List<ImgDO> imgDOList = getRandomImgByType(1);
        ImgDO imgDo = imgDOList.get(new Random().nextInt(imgDOList.size()));
        return imgDo;
    }
}

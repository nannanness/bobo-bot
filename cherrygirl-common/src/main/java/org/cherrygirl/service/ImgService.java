package org.cherrygirl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.cherrygirl.domain.ImgDO;

import java.util.List;

/**
 * @author nannanness
 */
public interface ImgService extends IService<ImgDO> {

    boolean addImg(ImgDO imgDo);

    boolean addImgDdd(String name);

    List<ImgDO> getRandomImgByType(int type);

    ImgDO getDdd();
}

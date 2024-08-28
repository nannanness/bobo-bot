package org.cherrygirl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.cherrygirl.dao.ImgMapper;
import org.cherrygirl.dao.MessageRecordMapper;
import org.cherrygirl.domain.ImgDO;
import org.cherrygirl.domain.MessageRecordDO;
import org.cherrygirl.service.ImgService;
import org.cherrygirl.service.MessageRecordService;
import org.springframework.stereotype.Service;

@Service
public class MessageRecordServiceImpl extends ServiceImpl<MessageRecordMapper, MessageRecordDO> implements MessageRecordService {
}

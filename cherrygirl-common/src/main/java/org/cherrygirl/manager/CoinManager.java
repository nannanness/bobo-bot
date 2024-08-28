package org.cherrygirl.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.cherrygirl.domain.CoinDO;
import org.cherrygirl.service.CoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author nannanness
 */
@Service
public class CoinManager {

    @Autowired
    private CoinService coinService;

    public boolean addCoins(Long group, Long qq, long coin) {
        QueryWrapper<CoinDO> qw = new QueryWrapper<>();
        qw.lambda().eq(CoinDO::getQq, qq);
        qw.lambda().eq(CoinDO::getGroupId, group);
        List<CoinDO> coinDOS = coinService.getBaseMapper().selectList(qw);
        CoinDO coinDO = new CoinDO();
        if(coinDOS.isEmpty()){
            coinDO.setQq(qq);
            coinDO.setGroupId(group);
            coinDO.setCoin(coin);
        }else {
            coinDO = coinDOS.stream().findFirst().get();
            coinDO.setCoin(coinDO.getCoin() == null ? coin : coinDO.getCoin() + coin);
        }
        return coinService.saveOrUpdate(coinDO);
    }

    public CoinDO viewCoins(Long group, Long qq) {
        QueryWrapper<CoinDO> qw = new QueryWrapper<>();
        qw.lambda().eq(CoinDO::getQq, qq);
        qw.lambda().eq(CoinDO::getGroupId, group);
        List<CoinDO> coinDOS = coinService.getBaseMapper().selectList(qw);
        return coinDOS.stream().findFirst().orElse(null);
    }

    public boolean add50Coins(Long group, Long qq) {
        return addCoins(group, qq, 50);
    }

    public boolean add100Coins(Long group, Long qq) {
        return addCoins(group, qq, 100);
    }

    public boolean sub20Coins(Long group, Long qq) {
        return addCoins(group, qq, -20);
    }
    public boolean subCoins(Long group, Long qq, int coin) {
        return addCoins(group, qq, coin);
    }

    public boolean sub500Coins(Long group, Long qq) {
        return addCoins(group, qq, -500);
    }

    public boolean sub200Coins(Long group, Long qq) {
        return addCoins(group, qq, -200);
    }
}

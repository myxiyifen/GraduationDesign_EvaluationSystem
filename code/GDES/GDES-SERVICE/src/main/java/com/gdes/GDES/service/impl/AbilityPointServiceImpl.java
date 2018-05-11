package com.gdes.GDES.service.impl;

import com.gdes.GDES.dao.AbilitypointMapper;
import com.gdes.GDES.model.Abilitypoint;
import com.gdes.GDES.service.AbilitypointService;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class AbilitypointServiceImpl implements AbilitypointService{

    @Resource
    private AbilitypointMapper abilitypointMapper;

    public Abilitypoint queryByAbilityPointId(Integer id_ap) throws Exception {
        return abilitypointMapper.selectByPrimaryKey(id_ap);
    }
}

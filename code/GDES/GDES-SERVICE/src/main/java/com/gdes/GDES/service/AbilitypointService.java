package com.gdes.GDES.service;

import com.gdes.GDES.model.Abilitypoint;

public interface AbilitypointService {

    /**
     * 根据能力点id查询
     * @param id_ap
     * @return
     * @throws Exception
     */
    public Abilitypoint queryByAbilityPointId(Integer id_ap) throws Exception;
}

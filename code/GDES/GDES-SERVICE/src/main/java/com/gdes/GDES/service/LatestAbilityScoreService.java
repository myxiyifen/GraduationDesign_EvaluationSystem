package com.gdes.GDES.service;

import com.gdes.GDES.model.Latestabilityscore;

import java.util.List;

/**
 * 最新能力点得分 业务接口
 */
public interface LatestabilityscoreService {
    /**
     * 插入记录
     * @param latestabilityscore
     * @return
     * @throws Exception
     */
    public int addLatestabilityscore(Latestabilityscore latestabilityscore) throws Exception;

    /**
     * 按学生id查询
     * @param sid
     * @return
     * @throws Exception
     */
    public List<Latestabilityscore> queryByStudentId(String sid) throws Exception;
}

package com.gdes.GDES.service;

import com.gdes.GDES.model.Major;

import java.util.List;

public interface MajorService {
    /**
     * 根据id查询
     * @param mid
     * @return
     * @throws Exception
     */
    public List<Major> queryByMajorId(String mid) throws Exception;
}

package com.gdes.GDES.service;

import com.gdes.GDES.model.Major;

import java.util.List;

public interface MajorService {
    public List<Major> queryByMajorId(String mid) throws Exception;
}

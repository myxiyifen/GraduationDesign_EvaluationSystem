package com.gdes.GDES.service;

import com.gdes.GDES.model.Student;

import java.util.List;

public interface StudentService {
    /**
     * 根据专业id查询学生id
     * @param mid
     * @return
     * @throws Exception
     */
    public List<String> getStudentListByMajorId(String mid) throws Exception;
}

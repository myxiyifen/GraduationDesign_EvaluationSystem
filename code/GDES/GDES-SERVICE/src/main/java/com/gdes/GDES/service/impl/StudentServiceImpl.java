package com.gdes.GDES.service.impl;

import com.gdes.GDES.dao.StudentMapper;
import com.gdes.GDES.model.Student;
import com.gdes.GDES.model.StudentExample;
import com.gdes.GDES.service.StudentService;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class StudentServiceImpl implements StudentService {

    @Resource
    private StudentMapper studentMapper;

    public List<String> getStudentListByMajorId(String mid) throws Exception {
        return studentMapper.getStudentListByMajorId(mid);
    }
}

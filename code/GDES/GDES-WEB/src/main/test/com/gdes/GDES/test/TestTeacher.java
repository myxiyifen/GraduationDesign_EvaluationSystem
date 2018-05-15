import com.gdes.GDES.model.Teacher;
import com.gdes.GDES.service.TeacherService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by 96906 on 2018/5/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:config/applicationContext.xml")
public class TestTeacher {

    @Resource
    private TeacherService teacherService;

    @Test
    public void testGetTeacherById() throws Exception {
        Teacher teacher = teacherService.getTeacherById("1");
        System.out.println(teacher.getNameT());
    }
}

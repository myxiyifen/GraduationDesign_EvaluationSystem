import com.gdes.GDES.service.StudentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 96906 on 2018/5/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:config/applicationContext.xml")
public class TestStudent {
    @Resource
    private StudentService studentService;

    @Test
    public void getListStudent() throws Exception {
        List<String> list = studentService.getStudentListByMajorId("01");
        for(int i=0;i<list.size();i++) {
            System.out.println(list.get(i));
        }
    }
}

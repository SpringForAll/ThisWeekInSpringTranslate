import com.spring4all.App;
import com.spring4all.service.impl.MailServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class MailTest {

    @Autowired
    MailServiceImpl mailService;

//    @Before
//    public void init() {
//        if (null == mailService) {
//            mailService = new MailService();
//        }
//    }

    @Test
    public void senMailText() {
        mailService.sendText("标题-测试邮件", "邮件主体内容", new String[]{"1490520869@qq.com", "522858454@qq.com"});
    }

    @Test
    public void sendMailFile() throws MessagingException {
        mailService.sendTextAndFile("标题","邮件主题内容","weekly.md","周报文件内容","1490520869@qq.com","522858454@qq.com");
    }
}

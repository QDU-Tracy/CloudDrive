package org.cloudpan.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"
})
public class PreviewTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PreviewService previewService;

    @Test
    public void addOnePicPreviewTest(){
        int i=previewService.addOnePicPreview(19, "C://xxx.jpg");
        logger.debug("return:" + i);
    }
    @Test
    public void getOneUrlTest(){
        String url = previewService.getOneUrl(17);
    }

}

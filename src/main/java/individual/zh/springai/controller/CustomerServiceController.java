/**
 * ============================================================
 * 版权： 广州市新维数据科技有限公司新架构产品部所有(c) 2026
 * 文件：individual.zh.springai.controller
 * 所含类: SpringAiController
 * 文件作用描述 TODO
 * 修改记录：
 * 日期                                      作者         版本     内容
 * =============================================================
 * 2026/4/14  21:27      zh     v1.0.0   新建
 * =============================================================
 */

package individual.zh.springai.controller;

import individual.zh.springai.repository.ChatHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;

/**
 * <p>Titile:SpringAiController</p >
 * <p>ProjectName: </p >
 * <p>Description:TODO()  </p >
 * <p>Copyright: Copyright (c) 2023</p >
 * <p>Company: 新维数据 </p >
 *
 * @BelongsProject: SpringAI
 * @BelongsPackage: individual.zh.springai.controller
 * @Author: zh
 * @CreateTime: 2026/4/14  21:27
 * @Description: TODO
 * @Version: 1.0
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/ai")
public class CustomerServiceController {

    @Qualifier("serviceChatClient")
    @Autowired
    private final ChatClient serviceChatClient;

    private final ChatHistoryRepository chatHistoryRepository;

    /**
     * 服务
     *
     * @param prompt
     * @param chatId
     * @return {@link Flux< String>}
     * @throws Exception
     * @title service
     * @description
     * @author zh
     * @date 2026-05-08 22:06
     *
     **/
    @RequestMapping(value = "/service", produces = "text/html;charset=utf-8")
    public Flux<String> service(String prompt, String chatId) {
        // @author zh @date 2026-04-27 21:34:19 @description 保存会话id
        chatHistoryRepository.save(chatId, "service");
        // @author zh @date 2026-04-27 21:35:11 @description 请求模型
        return serviceChatClient
                .prompt()
                .user(prompt)
                .advisors(advisor -> advisor.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId))
                .stream()
                .content();
    }

}

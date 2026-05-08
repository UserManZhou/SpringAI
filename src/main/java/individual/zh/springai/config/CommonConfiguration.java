/**
 * ============================================================
 * 版权： 广州市新维数据科技有限公司新架构产品部所有(c) 2026
 * 文件：individual.zh.springai.config
 * 所含类: CommonConfiguration
 * 文件作用描述 TODO
 * 修改记录：
 * 日期                                      作者         版本     内容
 * =============================================================
 * 2026/4/14  22:29      zh     v1.0.0   新建
 * =============================================================
 */

package individual.zh.springai.config;

import individual.zh.springai.constants.SystemConstants;
import individual.zh.springai.tools.CourseTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Titile:CommonConfiguration</p >
 * <p>ProjectName: </p >
 * <p>Description:TODO()  </p >
 * <p>Copyright: Copyright (c) 2023</p >
 * <p>Company: 新维数据 </p >
 *
 * @BelongsProject: SpringAI
 * @BelongsPackage: individual.zh.springai.config
 * @Author: zh
 * @CreateTime: 2026/4/14  22:29
 * @Description: TODO
 * @Version: 1.0
 */
@Configuration
public class CommonConfiguration {

    /**
     * 创建ChatMemory
     *
     * @param
     * @return {@link ChatMemory}
     * @throws Exception
     * @title chatMemory
     * @description
     * @author zh
     * @date 2026-04-27 20:54
     *
     **/
    @Bean
    public ChatMemory chatMemory() {
        return new InMemoryChatMemory();
    }

    /**
     * 创建ChatClient
     *
     * @param ollamaChatModel
     * @return {@link ChatClient}
     * @throws Exception
     * @title chatClient
     * @description
     * @author zh
     * @date 2026-04-15 20:25
     *
     **/
    @Bean
    public ChatClient chatClient(OllamaChatModel ollamaChatModel, ChatMemory chatMemory) {
        return ChatClient.builder(ollamaChatModel)
                .defaultSystem("你是一个牛马，你的名字叫陈总。用 陈总的身份进行回答问题")
                .defaultAdvisors(
                        // @author zh @date 2026-04-27 20:53:50 @description 添加日志
                        new SimpleLoggerAdvisor(),
                        // @author zh @date 2026-04-27 20:53:53 @description 添加聊天记忆
                        new MessageChatMemoryAdvisor(chatMemory))
                .build();
    }

    /**
     * 创建ChatClient
     *
     * @param openAiChatModel
     * @param chatMemory
     * @return {@link ChatClient}
     * @throws Exception
     * @title gameChatClient
     * @description
     * @author zh
     * @date 2026-04-29 19:23
     *
     **/
    @Bean
    public ChatClient gameChatClient(OpenAiChatModel openAiChatModel, ChatMemory chatMemory) {
        return ChatClient.builder(openAiChatModel)
                .defaultSystem(SystemConstants.GAME_SYSTEM_PROMPT)
                .defaultAdvisors(
                        // @author zh @date 2026-04-27 20:53:50 @description 添加日志
                        new SimpleLoggerAdvisor(),
                        // @author zh @date 2026-04-27 20:53:53 @description 添加聊天记忆
                        new MessageChatMemoryAdvisor(chatMemory))
                .build();
    }

    /**
     * 创建ChatClient
     *
     * @param openAiChatModel
     * @param chatMemory
     * @param courseTools
     * @return {@link ChatClient}
     * @throws Exception
     * @title serviceChatClient
     * @description
     * @author zh
     * @date 2026-05-08 21:55
     *
     **/
    @Bean
    public ChatClient serviceChatClient(OpenAiChatModel openAiChatModel, ChatMemory chatMemory, CourseTools courseTools) {
        return ChatClient.builder(openAiChatModel)
                .defaultSystem(SystemConstants.SERVICES_SYSTEM_PROMPT)
                .defaultAdvisors(
                        // @author zh @date 2026-04-27 20:53:50 @description 添加日志
                        new SimpleLoggerAdvisor(),
                        // @author zh @date 2026-04-27 20:53:53 @description 添加聊天记忆
                        new MessageChatMemoryAdvisor(chatMemory))
                .defaultTools(courseTools)
                .build();
    }
}

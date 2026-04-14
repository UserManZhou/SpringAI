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

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
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
     * 创建ChatClient
     *
     * @param ollamaChatModel
     * @return {@link ChatClient}
     * @throws Exception
     * @title chatClient
     * @description
     * @author zh
     * @date 2026-04-14 22:31
     *
     **/
    @Bean
    public ChatClient chatClient(OllamaChatModel ollamaChatModel) {
        return ChatClient.builder(ollamaChatModel).build();
    }
}

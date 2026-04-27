/**
 * ============================================================
 * 版权： 广州市新维数据科技有限公司新架构产品部所有(c) 2026
 * 文件：individual.zh.springai.entity.vo
 * 所含类: MessageVo
 * 文件作用描述 TODO
 * 修改记录：
 * 日期                                      作者         版本     内容
 * =============================================================
 * 2026/4/27  21:54      zh     v1.0.0   新建
 * =============================================================
 */

package individual.zh.springai.entity.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.ai.chat.messages.Message;

/**
 * <p>Titile:MessageVo</p >
 * <p>ProjectName: </p >
 * <p>Description:TODO()  </p >
 * <p>Copyright: Copyright (c) 2023</p >
 * <p>Company: 新维数据 </p >
 *
 * @BelongsProject: SpringAI
 * @BelongsPackage: individual.zh.springai.entity.vo
 * @Author: zh
 * @CreateTime: 2026/4/27  21:54
 * @Description: TODO
 * @Version: 1.0
 */
@NoArgsConstructor
@Data
public class MessageVo {

    private String role;

    private String content;

    /**
     * 构造函数
     *
     * @param message
     * @return {@link null}
     * @throws Exception
     * @title MessageVo
     * @description
     * @author zh
     * @date 2026-04-27 21:57
     *
     **/
    public MessageVo(Message message) {
        switch (message.getMessageType()) {
            case USER:
                this.role = "user";
                break;
            case ASSISTANT:
                this.role = "assistant";
                break;
            case SYSTEM:
                this.role = "system";
                break;
            default:
                this.role = "unknown";
                break;
        }
        this.content = message.getText();
    }

}

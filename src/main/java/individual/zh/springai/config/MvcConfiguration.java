/**
 * ============================================================
 * 版权： 广州市新维数据科技有限公司新架构产品部所有(c) 2026
 * 文件：individual.zh.springai.config
 * 所含类: MvcConfiguration
 * 文件作用描述 TODO
 * 修改记录：
 * 日期                                      作者         版本     内容
 * =============================================================
 * 2026/4/15  21:23      zh     v1.0.0   新建
 * =============================================================
 */

package individual.zh.springai.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p>Titile:MvcConfiguration</p >
 * <p>ProjectName: </p >
 * <p>Description:TODO()  </p >
 * <p>Copyright: Copyright (c) 2023</p >
 * <p>Company: 新维数据 </p >
 *
 * @BelongsProject: SpringAI
 * @BelongsPackage: individual.zh.springai.config
 * @Author: zh
 * @CreateTime: 2026/4/15  21:23
 * @Description: TODO
 * @Version: 1.0
 */
@Component
public class MvcConfiguration implements WebMvcConfigurer {

    /**
     * 添加跨域请求
     *
     * @param registry
     * @return
     * @throws Exception
     * @title addCorsMappings
     * @description
     * @author zh
     * @date 2026-04-15 21:23
     *
     **/
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD")
                .allowedHeaders("*");
    }

}

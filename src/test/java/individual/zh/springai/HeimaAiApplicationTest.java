/**
 * ============================================================
 * 版权： 广州市新维数据科技有限公司新架构产品部所有(c) 2026
 * 文件：individual.zh.springai
 * 所含类: HeimaAiApplicationTest
 * 文件作用描述 TODO
 * 修改记录：
 * 日期                                      作者         版本     内容
 * =============================================================
 * 2026/5/11  23:01      zh     v1.0.0   新建
 * =============================================================
 */

package individual.zh.springai;

import individual.zh.springai.util.VectorDistanceUtils;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.util.Arrays;
import java.util.List;

/**
 * <p>Titile:HeimaAiApplicationTest</p >
 * <p>ProjectName: </p >
 * <p>Description:TODO()  </p >
 * <p>Copyright: Copyright (c) 2023</p >
 * <p>Company: 新维数据 </p >
 *
 * @BelongsProject: SpringAI
 * @BelongsPackage: individual.zh.springai
 * @Author: zh
 * @CreateTime: 2026/5/11  23:01
 * @Description: TODO
 * @Version: 1.0
 */
@SpringBootTest
public class HeimaAiApplicationTest {

    @Autowired
    private OpenAiEmbeddingModel openAiEmbeddingModel;

    @Autowired
    private VectorStore vectorStore;

    @Test
    void testVectorStore() {
        Resource resource = new FileSystemResource("中二知识笔记.pdf");
        // @author zh @date 2026-05-14 22:11:25 @description 创建一个pdf文件读取器
        PagePdfDocumentReader reader = new PagePdfDocumentReader(resource,
                PdfDocumentReaderConfig.builder()
                        .withPageExtractedTextFormatter(ExtractedTextFormatter.defaults())
                        .withPagesPerDocument(1)
                        .build());
        // @author zh @date 2026-05-14 22:17:19 @description 读取pdf文件
        List<Document> read = reader.read();
        // @author zh @date 2026-05-14 22:17:31 @description 添加到向量存储中
        vectorStore.add(read);
        // @author zh @date 2026-05-14 22:17:56 @description 搜索相似
        SearchRequest searchRequest = SearchRequest
                .builder()
                // @author zh @date 2026-05-14 22:26:36 @description 设置查询
                .query("论语中教育的目的是什么")
                // @author zh @date 2026-05-14 22:26:40 @description 设置返回的相似度
                .topK(1)
                // @author zh @date 2026-05-14 22:26:43 @description 设置相似度阈值
                .similarityThreshold(0.6)
                // @author zh @date 2026-05-14 22:28:33 @description 设置过滤条件
                .filterExpression("file_name == '中二知识笔记.pdf'")
                .build();
        //List<Document> documents = vectorStore.similaritySearch("论语中教育的目的是什么");
        List<Document> documents = vectorStore.similaritySearch(searchRequest);
        // @author zh @date 2026-05-14 22:20:00 @description 输出
        if (documents != null) {
            for (Document document : documents) {
                System.out.println("document.getScore() = " + document.getScore());
                System.out.println("document.getId() = " + document.getId());
                System.out.println("document.getText() = " + document.getText());
            }
        } else {
            System.out.println("没有找到相似的");
        }
    }

    @Test
    void contextLoads() {
        float[] floats = openAiEmbeddingModel.embed("你好");
        System.out.println("Arrays.toString(floats) = " + Arrays.toString(floats));
    }

    @Test
    void testEmbedding() {
        // 1.测试数据
        // 1.1.用来查询的文本，国际冲突
        String query = "global conflicts";

        // 1.2.用来做比较的文本
        String[] texts = new String[]{
                "哈马斯称加沙下阶段停火谈判仍在进行 以方尚未做出承诺",
                "土耳其、芬兰、瑞典与北约代表将继续就瑞典“入约”问题进行谈判",
                "日本航空基地水井中检测出有机氟化物超标",
                "国家游泳中心（水立方）：恢复游泳、嬉水乐园等水上项目运营",
                "我国首次在空间站开展舱外辐射生物学暴露实验",
        };
        // 2.向量化
        // 2.1.先将查询文本向量化
        float[] queryVector = openAiEmbeddingModel.embed(query);

        // 2.2.再将比较文本向量化，放到一个数组
        List<float[]> textVectors = openAiEmbeddingModel.embed(Arrays.asList(texts));

        // 3.比较欧氏距离
        // 3.1.把查询文本自己与自己比较，肯定是相似度最高的
        System.out.println(VectorDistanceUtils.euclideanDistance(queryVector, queryVector));
        // 3.2.把查询文本与其它文本比较
        for (float[] textVector : textVectors) {
            System.out.println(VectorDistanceUtils.euclideanDistance(queryVector, textVector));
        }
        System.out.println("------------------");

        // 4.比较余弦距离
        // 4.1.把查询文本自己与自己比较，肯定是相似度最高的
        System.out.println(VectorDistanceUtils.cosineDistance(queryVector, queryVector));
        // 4.2.把查询文本与其它文本比较
        for (float[] textVector : textVectors) {
            System.out.println(VectorDistanceUtils.cosineDistance(queryVector, textVector));
        }
    }

}

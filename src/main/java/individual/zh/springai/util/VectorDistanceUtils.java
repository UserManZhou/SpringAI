/**
 * ============================================================
 * 版权： 广州市新维数据科技有限公司新架构产品部所有(c) 2026
 * 文件：individual.zh.springai.util
 * 所含类: VectorDistanceUtils
 * 文件作用描述 TODO
 * 修改记录：
 * 日期                                      作者         版本     内容
 * =============================================================
 * 2026/5/11  23:14      zh     v1.0.0   新建
 * =============================================================
 */

package individual.zh.springai.util;

/**
 * <p>Titile:VectorDistanceUtils</p >
 * <p>ProjectName: </p >
 * <p>Description:TODO()  </p >
 * <p>Copyright: Copyright (c) 2023</p >
 * <p>Company: 新维数据 </p >
 *
 * @BelongsProject: SpringAI
 * @BelongsPackage: individual.zh.springai.util
 * @Author: zh
 * @CreateTime: 2026/5/11  23:14
 * @Description: TODO
 * @Version: 1.0
 */
public class VectorDistanceUtils {
    // 防止实例化
    private VectorDistanceUtils() {
    }

    // 浮点数计算精度阈值
    private static final double EPSILON = 1e-12;

    /**
     * 计算欧氏距离
     *
     * @param vectorA 向量A（非空且与B等长）
     * @param vectorB 向量B（非空且与A等长）
     * @return 欧氏距离
     * @throws IllegalArgumentException 参数不合法时抛出
     */
    public static double euclideanDistance(float[] vectorA, float[] vectorB) {
        validateVectors(vectorA, vectorB);

        double sum = 0.0;
        for (int i = 0; i < vectorA.length; i++) {
            double diff = vectorA[i] - vectorB[i];
            sum += diff * diff;
        }
        return Math.sqrt(sum);
    }

    /**
     * 计算余弦距离
     *
     * @param vectorA 向量A（非空且与B等长）
     * @param vectorB 向量B（非空且与A等长）
     * @return 余弦距离，范围[0, 2]
     * @throws IllegalArgumentException 参数不合法或零向量时抛出
     */
    public static double cosineDistance(float[] vectorA, float[] vectorB) {
        validateVectors(vectorA, vectorB);

        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;

        for (int i = 0; i < vectorA.length; i++) {
            dotProduct += vectorA[i] * vectorB[i];
            normA += vectorA[i] * vectorA[i];
            normB += vectorB[i] * vectorB[i];
        }

        normA = Math.sqrt(normA);
        normB = Math.sqrt(normB);

        // 处理零向量情况
        if (normA < EPSILON || normB < EPSILON) {
            throw new IllegalArgumentException("Vectors cannot be zero vectors");
        }

        // 处理浮点误差，确保结果在[-1,1]范围内
        double similarity = dotProduct / (normA * normB);
        similarity = Math.max(Math.min(similarity, 1.0), -1.0);

        return 1 - similarity;
    }

    // 参数校验统一方法
    private static void validateVectors(float[] a, float[] b) {
        if (a == null || b == null) {
            throw new IllegalArgumentException("Vectors cannot be null");
        }
        if (a.length != b.length) {
            throw new IllegalArgumentException("Vectors must have same dimension");
        }
        if (a.length == 0) {
            throw new IllegalArgumentException("Vectors cannot be empty");
        }
    }
}

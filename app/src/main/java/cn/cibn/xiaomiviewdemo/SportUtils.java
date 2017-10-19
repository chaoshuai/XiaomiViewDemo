package cn.cibn.xiaomiviewdemo;

/**
 * Created by 15210 on 2017/2/7.
 */

public class SportUtils {
    /**
     * 获取路程
     * @param sex 性别0 男，1女
     * @param stature 身高 （m）
     * @param stepNumber 步数
     * @return m
     */
    public static int getKilometresByStepNumber(int sex, double stature, int stepNumber) {
        if (sex == 0) {
            return (int) (0.415 * stature * stepNumber);
        } else {
            return (int) (0.413 * stature * stepNumber);
        }
    }

    /**
     * 获取活动时长
     * @param kilometres 公里数 （m）
     * @return 秒
     */
    public static double getActiveDurationByKilometres(int kilometres) {
        return kilometres / 1.2;
    }

    /**
     * 获取卡路里
     * @param sex  性别0 男，1女
     * @param weight  体重 kg
//     * @param metabolism  代谢时长（从0点到现在的时间，单位小时）
     * @param kilometres m
     * @return 千卡
     */
    public static int getCalorieBySex(int sex, float weight,int kilometres) {
        if (sex == 0) {
            return (int) ((weight * 4 * getActiveDurationByKilometres(kilometres)) / 3600.f);
        } else {
            return (int) ((weight * 4 * getActiveDurationByKilometres(kilometres)) / 3600.f);
        }
    }

    /**
     * 获取BMI
     * @param weight  体重 kg
     * @param stature 身高 （m）
     * @return
     */
    public static double getBMIbyStature(float weight,float stature) {
        return weight/stature/stature;
    }

    /**
     *  获取BMI值
     * @param weight 体重
     * @param height 身高
     * @return
     */
    public static int getBmi(double weight , double height ) {

        double i = height * height;
        double v = weight / i;
        int BMI = (int)v;
        return BMI;
    }

    /**
     *   获取体重
     * @param bmi  BMI
     * @param height  身高
     * @return
     */

    public static int getJin(int bmi , double height ) {

        double v = height * height;
        double v1 = bmi * v;
        int  Jin = (int)v1;
        return Jin;
    }

}

package com.rzlearn.user.manager;

import com.rzlearn.user.common.util.RedisUtil;
import com.rzlearn.user.constant.BusinessConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

/**
 * <p>ClassName:VerificationCodeManager</p>
 * <p>Description:验证码管理</p>
 *
 * @author ZhangWenbing
 * @date 2018-06-15 11:33:47
 **/
@Service
@Slf4j
public class VerificationCodeManager {

    public static final int INT = 20;
    public static final int INT_COLOR = 255;
    public static final int INT_RGB = 3;
    private static Random random = new Random();

    @Autowired
    private RedisUtil redisUtil;

    public String outputVerifyImage(int width, int height, ServletOutputStream outputStream, int verifySize) throws IOException {
        String verifyCode = generateVerifyCode(verifySize);
        outputImage(width, height, outputStream, verifyCode);
        return verifyCode;
    }

    public Boolean saveCode(String key, String code) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(BusinessConst.VERIFY_PREFIX).append(key);
        log.info("验证码保存:{},:{}", stringBuilder.toString(), code);
        Boolean result = redisUtil.set(stringBuilder.toString(), code, 600L);
        if (!result) {
            log.error("验证码保存失败");
        }
        return result;
    }

    public String getCode(String key) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(BusinessConst.VERIFY_PREFIX).append(key);
        return redisUtil.get(stringBuilder.toString(), String.class);
    }

    /**
     * <p>ClassName:VerificationCodeManager</p>
     * <p>Description:使用系统默认字符源生成验证码</p>
     *
     * @param verifySize 验证码长度
     * @return 验证码
     * @author ZhangWenbing
     * @date 2018-06-15 11:38:54
     **/
    public String generateVerifyCode(int verifySize) {
        return generateVerifyCode(verifySize, BusinessConst.VERIFY_CODES);
    }

    /**
     * <p>ClassName:VerificationCodeManager</p>
     * <p>Description:使用指定源生成验证码</p>
     *
     * @param verifySize 验证码长度
     * @param sources    验证码字符源
     * @return 验证码
     * @author ZhangWenbing
     * @date 2018-06-15 11:38:31
     **/
    public String generateVerifyCode(int verifySize, String sources) {
        if (sources == null || sources.length() == 0) {
            sources = BusinessConst.VERIFY_CODES;
        }
        int codesLen = sources.length();
        Random rand = new Random(System.currentTimeMillis());
        StringBuilder verifyCode = new StringBuilder(verifySize);
        for (int i = 0; i < verifySize; i++) {
            verifyCode.append(sources.charAt(rand.nextInt(codesLen - 1)));
        }
        return verifyCode.toString();
    }

    /**
     * <p>ClassName:VerificationCodeManager</p>
     * <p>Description:输出指定验证码图片流</p>
     *
     * @param width        宽度
     * @param height       高度
     * @param outputStream 验证码流
     * @param code         验证码
     * @throws IOException
     * @author ZhangWenbing
     * @date 2018-06-15 11:38:03
     **/
    public void outputImage(int width, int height, ServletOutputStream outputStream, String code) throws IOException {
        int verifySize = code.length();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Random rand = new Random();
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Color[] colors = new Color[5];
        Color[] colorSpaces = new Color[]{Color.WHITE, Color.CYAN, Color.GRAY, Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.YELLOW};
        float[] fractions = new float[colors.length];
        for (int i = 0; i < colors.length; i++) {
            colors[i] = colorSpaces[rand.nextInt(colorSpaces.length)];
            fractions[i] = rand.nextFloat();
        }
        Arrays.sort(fractions);

        // 设置边框色
        g2.setColor(Color.GRAY);
        g2.fillRect(0, 0, width, height);

        Color c = getRandColor(200, 250);
        // 设置背景色
        g2.setColor(c);
        g2.fillRect(0, 2, width, height - 4);

        //绘制干扰线
        Random random = new Random();
        //设置线条的颜色
        g2.setColor(getRandColor(160, 200));
        for (int i = 0; i < INT; i++) {
            int x = random.nextInt(width - 1);
            int y = random.nextInt(height - 1);
            int xl = random.nextInt(6) + 1;
            int yl = random.nextInt(12) + 1;
            g2.drawLine(x, y, x + xl + 40, y + yl + 20);
        }

        // 添加噪点
        float yawpRate = 0.02f;
        int area = (int) (yawpRate * width * height);
        for (int i = 0; i < area; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int rgb = getRandomIntColor();
            image.setRGB(x, y, rgb);
        }

        // 使图片扭曲
        shear(g2, width, height, c);

        g2.setColor(getRandColor(100, 160));
        int fontSize = height - 4;
        Font font = new Font("Algerian", Font.ITALIC, fontSize);
        g2.setFont(font);
        char[] chars = code.toCharArray();
        for (int i = 0; i < verifySize; i++) {
            AffineTransform affine = new AffineTransform();
            affine.setToRotation(0d, 0d, 0d);
            g2.setTransform(affine);
            g2.drawChars(chars, i, 1, ((width - 10) / verifySize) * i + 5, height / 2 + fontSize / 2 - 10);
        }
        g2.dispose();
        ImageIO.write(image, "jpg", outputStream);
    }

    private Color getRandColor(int fc, int bc) {
        if (fc > INT_COLOR) {
            fc = INT_COLOR;
        }
        if (bc > INT_COLOR) {
            bc = INT_COLOR;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    private int getRandomIntColor() {
        int[] rgb = getRandomRgb();
        int color = 0;
        for (int c : rgb) {
            color = color << 8;
            color = color | c;
        }
        return color;
    }

    private int[] getRandomRgb() {
        int[] rgb = new int[3];
        for (int i = 0; i < INT_RGB; i++) {
            rgb[i] = random.nextInt(255);
        }
        return rgb;
    }

    private void shear(Graphics g, int width, int height, Color color) {
        shearX(g, width, height, color);
        shearY(g, width, height, color);
    }

    private void shearX(Graphics g, int width, int height, Color color) {
        int period = random.nextInt(2);
        boolean borderGap = true;
        int frames = 1;
        int phase = random.nextInt(2);
        for (int i = 0; i < height; i++) {
            double d = (double) (period >> 1) * Math.sin((double) i / (double) period + (6.2831853071795862D * (double) phase) / (double) frames);
            g.copyArea(0, i, width, 1, (int) d, 0);
            if (borderGap) {
                g.setColor(color);
                g.drawLine((int) d, i, 0, i);
                g.drawLine((int) d + width, i, width, i);
            }
        }
    }

    private void shearY(Graphics g, int width, int height, Color color) {
        int period = random.nextInt(40) + 10;
        boolean borderGap = true;
        int frames = 20;
        int phase = 7;
        for (int i = 0; i < width; i++) {
            double d = (double) (period >> 1) * Math.sin((double) i / (double) period + (6.2831853071795862D * (double) phase) / (double) frames);
            g.copyArea(i, 0, 1, height, 0, (int) d);
            if (borderGap) {
                g.setColor(color);
                g.drawLine(i, (int) d, i, 0);
                g.drawLine(i, (int) d + height, i, height);
            }
        }
    }
}

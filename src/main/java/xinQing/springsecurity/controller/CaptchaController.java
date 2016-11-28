package xinQing.springsecurity.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import xinQing.springsecurity.util.SessionUtil;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * 验证码功能
 *
 * Created by xuan on 16-11-25.
 */
@Controller
public class CaptchaController {

    private static final Logger log = Logger.getLogger(CaptchaController.class);

    /*------------------------------------------------------------------
     *  定义常量 开始
     *-----------------------------------------------------------------*/
    private static final int width = 60;//定义图片的width
    private static final int height = 20;//定义图片的height
    private static final int codeCount = 4;//定义图片上显示验证码的个数
    private static final int xx = 13;
    private static final int fontHeight = 18;
    private static final int codeY = 16;
    private static final char[] codeSequence = ("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789").toCharArray();
    /*------------------------------------------------------------------
     *  定义常量 结束
     *-----------------------------------------------------------------*/


    /**
     * 获取验证码
     *
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws IOException I/O exception
     */
    @RequestMapping(value = "/captcha", method = RequestMethod.GET)
    public void getCode(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // 定义图像buffer
        BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics gd = buffImg.getGraphics();
        // 设定背景色
        gd.setColor(getRandColor(200, 250));
        gd.fillRect(0, 0, width, height);
        // 设定字体，字体的大小应该根据图片的高度来定。
        gd.setFont(new Font("Times New Roman", Font.PLAIN, fontHeight));
        // 创建一个随机数生成器类
        Random random = new Random();
        // 随机产生40条干扰线，使图象中的认证码不易被其它程序探测到。
        gd.setColor(getRandColor(160, 200));
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            gd.drawLine(x, y, x + xl, y + yl);
        }

        // randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
        StringBuilder randomCode = new StringBuilder();
        int red, green, blue;
        // 随机产生codeCount数字的验证码。
        for (int i = 0; i < codeCount; i++) {
            // 得到随机产生的验证码数字。
            String code = String.valueOf(codeSequence[random.nextInt(36)]);
            // 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
            red = random.nextInt(110);
            green = random.nextInt(110);
            blue = random.nextInt(110);
            // 用随机产生的颜色将验证码绘制到图像中。
            gd.setColor(new Color(red + 20, green + 20, blue + 20));
            gd.drawString(code, i * xx + 6, codeY);
            // 将产生的四个随机数组合在一起。
            randomCode.append(code);
        }

        // 将四位数字的验证码保存到Session中。
        HttpSession session = req.getSession();
        log.debug("验证码：" + randomCode.toString());
        session.setAttribute(SessionUtil.CAPTCHA, randomCode.toString());

        // 禁止图像缓存。
        resp.setHeader("Pragma", "no-cache");
        resp.setHeader("Cache-Control", "no-cache");
        resp.setDateHeader("Expires", 0);
        resp.setContentType("image/jpeg");

        // 将图像输出到Servlet输出流中。
        ServletOutputStream outputStream = resp.getOutputStream();
        ImageIO.write(buffImg, "jpeg", outputStream);
        outputStream.close();
    }

    private Color getRandColor(int fc, int bc) {
        //给定范围获得随机颜色
        Random random = new Random();
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
}

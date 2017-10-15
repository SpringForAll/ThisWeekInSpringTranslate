import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 测试生成二维码图片
 *
 * @author <a href="mailto:baiwenhui@autobole.com">bwh</a>
 * @see {http://kenglxn.github.io/QRGen/}
 * @since 2017/10/9
 */
public class QRImageGenerateTest {

    public static void main(String[] args) {

        ByteArrayOutputStream bout =
                QRCode.from("http://memorynotfound.com")
                        .withSize(250, 250)
                        .to(ImageType.PNG)
                        .stream();


        try {
            OutputStream out = new FileOutputStream("/picture/qr-code.png");
            bout.writeTo(out);
            out.flush();
            out.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

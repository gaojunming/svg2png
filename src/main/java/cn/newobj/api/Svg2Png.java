package cn.newobj.api;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletResponse;

import java.io.*;

/**
 * svg转换png api
 */
@RestController
public class Svg2Png {

    /**
     * batik框架不支持svg1.2，1.2新特性需处理掉
     * @param svgText
     * @param res
     * @throws IOException
     * @throws TranscoderException
     */
    @RequestMapping(path = "/svg2png",method = RequestMethod.POST)
    public void svg2png(@RequestParam(name = "svgText") String svgText, HttpServletResponse res) throws IOException, TranscoderException {
        res.setContentType("image/png; charset=utf-8");
        // 创建png转换器
        PNGTranscoder transcoder = new PNGTranscoder();
        // 配置转换选项
        transcoder.addTranscodingHint(PNGTranscoder.KEY_WIDTH, new Float(200));//图宽
        transcoder.addTranscodingHint(PNGTranscoder.KEY_HEIGHT, new Float(200));//图高
        Reader reader= null;
        OutputStream ostream= null;
        try{
            //转码器的输入
            //这里根据自己项目需要去处理
            svgText= svgText.replaceAll("fill=\"param\\(fill\\)(?:\\s#.{6,6})?\"","")
                    .replaceAll("fill=\"url\\(#.+?\\)\"","")
                    .replaceAll("<use .+?/>","")
                    .replaceAll("clip-path=\"url\\(#.+?\\)\"","");
            System.out.println(svgText);
            reader= new StringReader(svgText);
            TranscoderInput input = new TranscoderInput(reader);
            // 转码器的输出
            ostream = res.getOutputStream();
            TranscoderOutput output = new TranscoderOutput(ostream);
            // 转码
            transcoder.transcode(input, output);
            // 完成关闭流
            ostream.flush();
        }finally {
            if(reader!=null){
                reader.close();
            }
            if(ostream!=null){
                ostream.close();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("<use xlink:href=\"#beard-01_clip_1\" width=\"168\" height=\"152\" transform=\"translate(66.03 92.1)\"/>".replaceAll("<use .+?/>",""));
    }

    /**
     * 基于浏览器的转换：因为JBrowserDriver依赖的webkit版本较低不支持svg1.2，但不会报错
     * @param svgText
     * @param res
     * @return
     */
    /*@RequestMapping(path = "/svg2png",method = RequestMethod.POST)
    public String svg2png(@RequestParam(name = "svgText") String svgText, HttpServletResponse res)  {
        Object base64Png= App.driver.executeAsyncScript("svgToPng(arguments[0],arguments[arguments.length - 1]);",svgText);
        System.out.println(base64Png);
        //不知道为什么js脚本的执行结果会包含换行符(\n)和回车符(\r)，这里需要替换掉
        return base64Png.toString().replaceAll("(?:\r\n)|\n","");
    }*/

    /*public static void main(String[] args) throws Exception{
        File file = new File(ClassLoader.getSystemResource("svg_to_png.html").toString());
        InputStream is= new FileInputStream(file);
        Reader reader= new InputStreamReader(is);
        char[] readBuffer= new char[(int) file.length()];
        StringBuffer svgStr= new StringBuffer();
        while (reader.read(readBuffer)!=-1){
            svgStr.append(readBuffer);
        }

        JBrowserDriver driver = new JBrowserDriver();

        driver.get(ClassLoader.getSystemResource("svg_to_png.html").toString());
        Object result= driver.executeAsyncScript("svgToPng(arguments[0],arguments[arguments.length - 1]);",svgStr);
        System.out.println(result);

        driver.quit();
    }*/
}

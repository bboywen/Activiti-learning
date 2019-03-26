package imporven.activiti.Rudiments.repository;

import imporven.activiti.Rudiments.core.ProcessDiagramGeneratorExt;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Resource;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.db.DbSqlSession;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ResourceEntity;
import org.activiti.engine.impl.persistence.entity.ResourceEntityImpl;
import org.activiti.engine.impl.util.IoUtil;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.image.exception.ActivitiImageException;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.*;
import java.util.List;

/**
 * @author imporven
 * @version 1.0.0
 * @date 2019-3-19 22:28
 */
public class ImageTest {


    ProcessEngine processEngine;
    RepositoryService repositoryService;

    @Before
    public void initProcess() {
        processEngine = ProcessEngines.getProcessEngine("spring");
        repositoryService = processEngine.getRepositoryService();
    }

    /**
     * 流程图片相关操作了解
     */
    @Test
    public void viewImage() throws IOException {

        Deployment deployment = Optional.of(repositoryService.createDeploymentQuery()
                .deploymentKey("leave")
                .deploymentTenantId("A")
                .latest()
                .singleResult()).orElseThrow(()->new RuntimeException("没有找到key为leave的部署对象"));

        List<String> deploymentResourceNames = repositoryService.getDeploymentResourceNames(deployment.getId());
        String pngName = deploymentResourceNames.stream()
                .filter(resourceName -> resourceName.endsWith(".png"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("没有找到相应图片名称"));

        String fileName = "C:\\" + pngName;
        InputStream resourceAsStream = repositoryService.getResourceAsStream(deployment.getId(), pngName);
        FileUtils.copyInputStreamToFile(resourceAsStream,new File(fileName));

    }

    /**
     * 生成图片
     * @throws IOException
     */
    //BpmnModel bpmnModel, String imageType, List<String> highLightedActivities, List<String> highLightedFlows, String activityFontName, String labelFontName, String annotationFontName, ClassLoader customClassLoader, double scaleFactor
    @Test
    public void generator() throws IOException {

        ProcessDiagramGenerator processDiagramGenerator = processEngine.getProcessEngineConfiguration().getProcessDiagramGenerator();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey("leave")
                .processDefinitionTenantId("A")
                .latestVersion()
                .singleResult();

        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinition.getId());

        List<String> highLightedActivities = new ArrayList<>();
        highLightedActivities.add("Task_1");

        List<String> highLightedFlows = new ArrayList<>();
        highLightedFlows.add("SequenceFlow_0vm9ytd");

        InputStream inputStream = processDiagramGenerator.generateDiagram(bpmnModel, "png", highLightedActivities, highLightedFlows, "宋体", "宋体", "宋体", null, 1.0D);
        FileUtils.copyInputStreamToFile(inputStream,new File("C:\\diagrame\\leave.png"));

    }

    /**
     * 定制图片生成 连线高亮颜色改为绿色 Task图标修改
     */
    @Test
    public void customGeneratorImage() throws IOException {

        ProcessDiagramGenerator processDiagramGenerator = new ProcessDiagramGeneratorExt();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey("leave")
                .processDefinitionTenantId("A")
                .latestVersion()
                .singleResult();

        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinition.getId());

        List<String> highLightedActivities = new ArrayList<>();
        highLightedActivities.add("Task_1");

        List<String> highLightedFlows = new ArrayList<>();
        highLightedFlows.add("SequenceFlow_0vm9ytd");

        InputStream inputStream = processDiagramGenerator.generateDiagram(bpmnModel, "png", highLightedActivities, highLightedFlows, "宋体", "宋体", "宋体", null, 1.0D);
        FileUtils.copyInputStreamToFile(inputStream,new File("C:\\diagrame\\leave.png"));
    }

    /**
     * 使用Graphics2D画图片
     */
    @Test
    public void drawImage() throws IOException {
        BufferedImage image = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        graphics.setBackground(new Color(255, 255, 255, 0));
        graphics.clearRect(0, 0, 1000, 1000);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setPaint(Color.black);
        Font font = new Font("宋体", Font.BOLD, 12);
        graphics.setFont(font);

        graphics.drawRect(500,500,80,40);
        graphics.draw3DRect(400,400,200,80,false);
        graphics.draw3DRect(300,300,200,80,true);
        graphics.drawLine(100,100,200,100);


        //画文本
        FontRenderContext frc = graphics.getFontRenderContext();
        Point2D point2D = new Point(100, 110);
        TextLayout layout = new TextLayout("This is a string",font,frc);
        layout.draw(graphics,(float) point2D.getX(),(float)point2D.getY());

        Rectangle2D bounds = layout.getBounds();
        bounds.setRect(bounds.getX()+ point2D.getX(),
        bounds.getY()+ point2D.getY(), bounds.getWidth(),bounds.getHeight());
        graphics.draw(bounds);

        String text = "This is a string,This is a string,This is a string,This is a string,This is a string";
        Point2D textPoint = new Point(600, 600);
        paintText(text,textPoint,graphics);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", out);

        } catch (IOException e) {
            throw new ActivitiImageException("Error while generating process image", e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch(IOException ignore) {
            }
        }
        FileUtils.writeByteArrayToFile(new File("C:\\diagrame\\test.png"),out.toByteArray());
    }

    public void paintText(String text,Point2D textPoint,Graphics2D graphics) {

        Point2D pen = new Point(10, 20);
        Graphics2D g2d = (Graphics2D)graphics;
        FontRenderContext frc = g2d.getFontRenderContext();

        //AttributedString保存文本和相关属性信息
        AttributedString as = new AttributedString(text);
        as.addAttribute(TextAttribute.FOREGROUND, graphics.getPaint());
        as.addAttribute(TextAttribute.FONT, graphics.getFont());
        //AttributedCharacterIterator允许通过文本和相关属性信息进行迭代。
        AttributedCharacterIterator aci = as.getIterator();
        //LineBreakMeasurer类允许将样式文本分解成适合特定视觉进步的线（或段）。
        LineBreakMeasurer measurer = new LineBreakMeasurer(aci, frc);

        paintText2(graphics,0,aci);
        //文本隔行长度
//        float wrappingWidth = 100;
//        int textY = (int)textPoint.getY();
//        while (measurer.getPosition() < text.length()) {
//
//            TextLayout layout = measurer.nextLayout(wrappingWidth);
//            //文本上升
//            textY += (layout.getAscent());
//            float dx = layout.isLeftToRight() ?
//                    0 : (wrappingWidth - layout.getAdvance());
//
//            layout.draw(graphics, pen.x + dx, pen.y);
//            pen.y += layout.getDescent() + layout.getLeading();
//        }
    }

    public void paintText2(Graphics2D graphics,int tabCount,AttributedCharacterIterator styledText) {

        float leftMargin = 600, rightMargin = 700;
        float[] tabStops = { 100, 250 };

        // assume styledText is an AttributedCharacterIterator, and the number
        // of tabs in styledText is tabCount

        int[] tabLocations = new int[tabCount+1];
        int i = 0;
        for (char c = styledText.first(); c != styledText.DONE; c = styledText.next()) {
            if (c == '\t') {
                tabLocations[i++] = styledText.getIndex();
            }
        }
        tabLocations[tabCount] = styledText.getEndIndex() - 1;

        // Now tabLocations has an entry for every tab's offset in
        // the text.  For convenience, the last entry is tabLocations
        // is the offset of the last character in the text.

        LineBreakMeasurer measurer = new LineBreakMeasurer(styledText,graphics.getFontRenderContext());
        int currentTab = 0;
        float verticalPos = 20;

        while (measurer.getPosition() < styledText.getEndIndex()) {

            // Lay out and draw each line.  All segments on a line
            // must be computed before any drawing can occur, since
            // we must know the largest ascent on the line.
            // TextLayouts are computed and stored in a Vector;
            // their horizontal positions are stored in a parallel
            // Vector.

            // lineContainsText is true after first segment is drawn
            boolean lineContainsText = false;
            boolean lineComplete = false;
            float maxAscent = 0, maxDescent = 0;
            float horizontalPos = leftMargin;
            Vector layouts = new Vector(1);
            Vector penPositions = new Vector(1);

            while (!lineComplete) {
                float wrappingWidth = rightMargin - horizontalPos;
                TextLayout layout =
                        measurer.nextLayout(wrappingWidth,
                                tabLocations[currentTab]+1,
                                lineContainsText);

                // layout can be null if lineContainsText is true
                if (layout != null) {
                    layouts.addElement(layout);
                    penPositions.addElement(new Float(horizontalPos));
                    horizontalPos += layout.getAdvance();
                    maxAscent = Math.max(maxAscent, layout.getAscent());
                    maxDescent = Math.max(maxDescent,
                            layout.getDescent() + layout.getLeading());
                } else {
                    lineComplete = true;
                }

                lineContainsText = true;

                if (measurer.getPosition() == tabLocations[currentTab]+1) {
                    currentTab++;
                }

                if (measurer.getPosition() == styledText.getEndIndex())
                    lineComplete = true;
                else if (horizontalPos >= tabStops[tabStops.length-1])
                    lineComplete = true;

                if (!lineComplete) {
                    // move to next tab stop
                    int j;
                    for (j=0; horizontalPos >= tabStops[j]; j++) {}
                    horizontalPos = tabStops[j];
                }
            }

            verticalPos += maxAscent;

            Enumeration layoutEnum = layouts.elements();
            Enumeration positionEnum = penPositions.elements();

            // now iterate through layouts and draw them
            while (layoutEnum.hasMoreElements()) {
                TextLayout nextLayout = (TextLayout) layoutEnum.nextElement();
                Float nextPosition = (Float) positionEnum.nextElement();
                nextLayout.draw(graphics, nextPosition.floatValue(), verticalPos);
            }

            verticalPos += maxDescent;
        }
    }


    /**
     * 获取系统所有字体
     */
    @Test
    public void getSysFont(){
        GraphicsEnvironment localGraphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        System.out.println(Arrays.toString(localGraphicsEnvironment.getAvailableFontFamilyNames()));
    }
}

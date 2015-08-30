import java.applet.Applet;
import java.applet.AppletContext;
import java.awt.*;
import java.net.URL;

public class WoW extends Applet
{

    public void stop()
    {
    }

    public void paint(Graphics g)
    {
        Color CCol = new Color(m_BackCol);
        g.setColor(CCol);
        g.fillRect(0, 0, size().width, size().height);
        if(MouseIsDown)
        {
            CCol = new Color(m_GraphCol);
            g.setColor(CCol);
            g.fillPolygon(Xpos, Ypos, m_Peaks * 2);
            CCol = new Color(m_FontCol);
            g.setColor(CCol);
            g.drawString(m_TextDown, TextDownXY.width, TextDownXY.height);
        } else
        {
            CCol = new Color(m_GraphCol);
            g.setColor(CCol);
            int Owidth = (int)((float)size().width * m_SizePer);
            int Oheight = (int)((float)size().height * m_SizePer);
            int Ox = (int)(((float)size().width * (1.0F - m_SizePer)) / 2.0F);
            int Oy = (int)(((float)size().height * (1.0F - m_SizePer)) / 2.0F);
            g.fillOval(Ox, Oy, Owidth, Oheight);
            CCol = new Color(m_FontCol);
            g.setColor(CCol);
            g.drawString(m_TextUp, TextUpXY.width, TextUpXY.height);
        }
        g.drawString(tempOUT, 20, 20);
    }

    private void CreateMatrix()
    {
        Xpos = new int[m_Peaks * 2];
        Ypos = new int[m_Peaks * 2];
        Dimension GraphSize = size();
        double Cir = 6.2831853071795862D / (double)(m_Peaks * 2);
        GraphSize.width = GraphSize.width / 2;
        GraphSize.height = GraphSize.height / 2;
        for(int i = 0; i < m_Peaks; i++)
        {
            Xpos[i * 2] = (int)((double)GraphSize.width * Math.sin(Cir * (double)i * 2D)) + GraphSize.width;
            Xpos[i * 2 + 1] = (int)((double)((float)GraphSize.width * m_SizePer) * Math.sin(Cir * (double)(i * 2 + 1))) + GraphSize.width;
            Ypos[i * 2] = (int)((double)GraphSize.height * Math.cos(Cir * (double)i * 2D)) + GraphSize.height;
            Ypos[i * 2 + 1] = (int)((double)((float)GraphSize.height * m_SizePer) * Math.cos(Cir * (double)(i * 2 + 1))) + GraphSize.height;
        }

    }

    public String[][] getParameterInfo()
    {
        String info[][] = {
            {
                "URL", "String", "The relative address of the new document"
            }, {
                "Target", "String", "The target window, which this link goes"
            }, {
                "TextUp", "String", "Text when mouse button is not pressed"
            }, {
                "SizePer", "float", "Size of inner circle (in percent)"
            }, {
                "FontStyle", "String", "Style of font (PLAIN, BOLD, ITALIC)"
            }, {
                "FontSize", "int", "Size of font"
            }, {
                "FontCol", "int", "Colour of text"
            }, {
                "Peaks", "int", "Number of peaks"
            }, {
                "FontName", "String", "Font to use"
            }, {
                "TextDown", "String", "Text when mouse button is pressed"
            }, {
                "GraphCol", "int", "Colour of graph"
            }, {
                "BackCol", "int", "Colour of background"
            }
        };
        return info;
    }

    public boolean mouseUp(Event evt, int x, int y)
    {
        MouseIsDown = false;
        if((4 != evt.modifiers) & (8 != evt.modifiers) & wasLeftButton)
            getAppletContext().showDocument(DestURL, m_Target);
        wasLeftButton = false;
        repaint();
        return true;
    }

    public void destroy()
    {
    }

    private void SetMyFont(String FontName, String FontStyle, int FontSize)
    {
        int nStyle = 0;
        if(FontStyle.equalsIgnoreCase("BOLD"))
            nStyle = 1;
        if(FontStyle.equalsIgnoreCase("ITALIC"))
            nStyle = 2;
        Font font = new Font(FontName, nStyle, FontSize);
        setFont(font);
    }

    public WoW()
    {
        m_URL = "";
        m_Target = "_self";
        m_TextUp = "WOW!";
        m_TextDown = m_TextUp;
        m_FontName = "Courier";
        m_FontStyle = "PLAIN";
        m_FontSize = 14;
        m_GraphCol = 0xffffff;
        m_BackCol = 0xe8e6d0;
        m_Peaks = 12;
        m_SizePer = 0.5F;
    }

    public void start()
    {
    }

    public String getAppletInfo()
    {
        return "Name: WoW\r\n" + "Author: Panos Katsaloulis\r\n" + "E-mail :  teras@writeme.com";
    }

    public boolean mouseDown(Event evt, int x, int y)
    {
        wasLeftButton = false;
        if((4 != evt.modifiers) & (8 != evt.modifiers))
        {
            MouseIsDown = true;
            wasLeftButton = true;
        }
        repaint();
        return true;
    }

    public void init()
    {
        String param = getParameter("SizePer");
        if(param != null)
            m_SizePer = Float.valueOf(param).floatValue();
        param = getParameter("FontStyle");
        if(param != null)
            m_FontStyle = param;
        param = getParameter("FontSize");
        if(param != null)
            m_FontSize = Integer.parseInt(param);
        param = getParameter("FontCol");
        if(param != null)
            m_FontCol = Integer.parseInt(param);
        param = getParameter("Peaks");
        if(param != null)
            m_Peaks = Integer.parseInt(param);
        param = getParameter("FontName");
        if(param != null)
            m_FontName = param;
        param = getParameter("TextUp");
        if(param != null)
            m_TextUp = param;
        param = getParameter("TextDown");
        if(param != null)
            m_TextDown = param;
        else
            m_TextDown = m_TextUp;
        param = getParameter("GraphCol");
        if(param != null)
            m_GraphCol = Integer.parseInt(param);
        param = getParameter("BackCol");
        if(param != null)
            m_BackCol = Integer.parseInt(param);
        param = getParameter("URL");
        if(param != null)
            m_URL = param;
        param = getParameter("Target");
        if(param != null)
            m_Target = param;
        MouseIsDown = false;
        CreateMatrix();
        SetMyFont(m_FontName, m_FontStyle, m_FontSize);
        TextUpXY = CenterText(m_TextUp);
        TextDownXY = CenterText(m_TextDown);
        DestURL = getDocumentBase();
        try
        {
            DestURL = new URL(DestURL, m_URL);
        }
        catch(Exception e)
        {
            stop();
        }
    }

    private Dimension CenterText(String Txt)
    {
        Dimension XY = new Dimension(0, 0);
        Dimension WinSize = size();
        Font font = getFont();
        FontMetrics FontMetr = getFontMetrics(font);
        int FontWidth = FontMetr.stringWidth(Txt);
        int FontHeight = FontMetr.getHeight();
        int FontDesc = FontMetr.getDescent();
        XY.width = (WinSize.width - FontWidth) / 2;
        XY.height = (WinSize.height + FontHeight) / 2 - FontDesc;
        return XY;
    }

    public boolean mouseMove(Event evt, int x, int y)
    {
        showStatus("Jump to : " + DestURL.toString());
        return true;
    }

    private String m_URL;
    private String m_Target;
    private String m_TextUp;
    private String m_TextDown;
    private String m_FontName;
    private String m_FontStyle;
    private int m_FontSize;
    private int m_FontCol;
    private int m_GraphCol;
    private int m_BackCol;
    private int m_Peaks;
    private float m_SizePer;
    private final String PARAM_URL = "URL";
    private final String PARAM_Target = "Target";
    private final String PARAM_TextUp = "TextUp";
    private final String PARAM_SizePer = "SizePer";
    private final String PARAM_FontStyle = "FontStyle";
    private final String PARAM_FontSize = "FontSize";
    private final String PARAM_FontCol = "FontCol";
    private final String PARAM_Peaks = "Peaks";
    private final String PARAM_FontName = "FontName";
    private final String PARAM_TextDown = "TextDown";
    private final String PARAM_GraphCol = "GraphCol";
    private final String PARAM_BackCol = "BackCol";
    private boolean MouseIsDown;
    private boolean wasLeftButton;
    private int Xpos[];
    private int Ypos[];
    private Color GraphCol;
    private Color TextCol;
    private Dimension TextUpXY;
    private Dimension TextDownXY;
    private URL DestURL;
    private String tempOUT;
}

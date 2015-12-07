
import java.io.IOException;
import java.io.PrintWriter;


import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Servlet implementation class Transform
 */
public class Transform extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Transform() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String filter = request.getParameter("filter");
    	PrintWriter out = response.getWriter();
    		if (filter == null) {
    			throw new ServletException("Invalid URI");
    		}
    		else {
    			Document xmlDoc = filterXml(filter);    			
    			response.setContentType("text/html");
    			out = response.getWriter();
    			if (xmlDoc != null) {
    				printStyledHtml(xmlDoc, out);
    			}
    			else {
    				out.print("<html><head></head><body>Unfortunately your filter does not match any UNIT</body></html>");
    			}
    		}
	}

	private void printStyledHtml(Document xmlDoc, PrintWriter out) {
                
        // Create dom source for the document
        DOMSource domSource=new DOMSource(xmlDoc);
        TransformerFactory tf = TransformerFactory .newInstance();
        ServletContext context = getServletContext();
        StreamSource xslSS=new StreamSource(context.getRealPath("ACMTrento.xsl")); 
        Transformer t;
		try {
			t = tf.newTransformer(xslSS);					
			t.transform(domSource,new StreamResult(out));
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Document filterXml(String filter) {
		DocumentBuilderFactory domFactory= DocumentBuilderFactory.newInstance(); 
		domFactory.setNamespaceAware(true); 
		DocumentBuilder builder;
		Document doc = null;
		try {
			builder = domFactory.newDocumentBuilder();
			ServletContext context = getServletContext();
			doc = builder.parse(context.getRealPath("ACMTrento.xml"));
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		XPathFactory xf = XPathFactory.newInstance();
		XPath xp = xf.newXPath();
		String expr = "//UNIT[starts-with(@ID,\""+filter+"\") and @TYPE=\"CORE\"]/UNIT_NAME | //UNIT[starts-with(@ID,\""+filter+"\") and @TYPE=\"CORE\"]/TOPICS";
		Document newXmlDocument = null;
		try {
			XPathExpression xexpr = xp.compile(expr);
			NodeList nodes = (NodeList) xexpr.evaluate(doc, XPathConstants.NODESET);
			if (nodes.getLength() > 0) {
				newXmlDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		        Element root = newXmlDocument.createElement("root");
		        newXmlDocument.appendChild(root);
		        for (int i = 0; i < nodes.getLength(); i++) {
		            Node node = nodes.item(i);	            
		            Node copyNode = newXmlDocument.importNode(node, true);
		            root.appendChild(copyNode);
		        }	
			}			
	        
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return newXmlDocument;		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

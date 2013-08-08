package org.jboss.errai.ui.rebind.chain;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.stanbol.enhancer.engines.htmlextractor.impl.DOMBuilder;
import org.jboss.errai.codegen.meta.MetaClass;
import org.jboss.errai.ui.shared.DomVisit;
import org.jboss.errai.ui.shared.DomVisitor;
import org.jboss.errai.ui.shared.chain.Chain;
import org.jboss.errai.ui.shared.chain.Command;
import org.jboss.errai.ui.shared.chain.Context;
import org.jsoup.Jsoup;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * @author edewit@redhat.com
 */
public class TemplateCatalog {
  public static final String ELEMENT = "CURRENT_ELEMENT";
  public static final String FILENAME = "CURRENT_FILE";
  public static final String RESULT = "result";

  private final Map<URL, Context> contextMap = new HashMap<URL, Context>();
  private final Chain chain = new Chain();

  public static TemplateCatalog createTemplateCatalog(Command... commands) {
    TemplateCatalog catalog = new TemplateCatalog();
    for (Command command : commands) {
      catalog.chain.addCommand(command);
    }
    return catalog;
  }

  public void visitTemplate(URL template, MetaClass widget) {
    final Document document = parseTemplate(template);
    synchronized (this) {
      for (int i = 0; i < document.getChildNodes().getLength(); i++) {
        final Node node = document.getChildNodes().item(i);
        if (node instanceof Element) {
          visitTemplate((Element) node, template, widget);
        }
      }
    }
  }

  /**
   * Parses the template into a document.
   * 
   * @param template
   *          the location of the template to parse
   */
  protected Document parseTemplate(URL template) {
    InputStream inputStream = null;
    try {
      inputStream = template.openStream();
      return DOMBuilder.jsoup2DOM(Jsoup.parse(inputStream, "UTF-8", ""));
    }
    catch (Exception e) {
      throw new IllegalArgumentException("could not read template " + template, e);
    }
    finally {
      IOUtils.closeQuietly(inputStream);
    }
  }

  private void visitTemplate(Element element, URL templateFileName, MetaClass widget) {
    Context context = chain.createInitialContext();
    Context existing = contextMap.get(templateFileName);

    contextMap.put(templateFileName, context);
    context.put(FILENAME, widget);
    DomVisit.visit(element, new TemplateDomVisitor(templateFileName));

    if (existing != null) {
      // temporary fix for ERRAI-604
      context.putAll(existing);
    }
  }

  public Object getResult(URL template, String key) {
    final Context context = contextMap.get(template);
    if (context == null) {
      throw new IllegalArgumentException("no context found for template " + template);
    }
    return context.get(key);
  }

  /**
   * for testing purposes.
   * 
   * @return the initialized chain
   */
  Chain getChain() {
    return chain;
  }

  private class TemplateDomVisitor implements DomVisitor {

    private final URL templateFileName;

    private TemplateDomVisitor(URL templateFileName) {
      this.templateFileName = templateFileName;
    }

    @Override
    public boolean visit(Element element) {
      final Context context = contextMap.get(templateFileName);
      context.put(ELEMENT, element);
      chain.execute(context);
      return true;
    }
  }
}

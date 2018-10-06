package main.java.com.epam.lab.web.page_objects;

import java.lang.reflect.Field;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.DefaultFieldDecorator;
import org.openqa.selenium.support.pagefactory.ElementLocator;

public class CustomFieldDecorator extends DefaultFieldDecorator {

	public CustomFieldDecorator(SearchContext searchContext) {
        super(new DefaultElementLocatorFactory(searchContext));
    }
 
    /**
     * ����� ���������� �������� ��� ������� ���� � ������
     */
    @Override
    public Object decorate(ClassLoader loader, Field field) {
        Class<?> decoratableClass = decoratableClass(field);
        // ���� ����� ���� ������������
        if (decoratableClass != null) {
             ElementLocator locator = factory.createLocator(field);
             if (locator == null) {
                  return null;
             }
 
             // �������   
             return createElement(loader, locator, decoratableClass);   
        }
        return super.decorate(loader, field);
    }
 
    /**
     * ���������� ������������ ����� ����, 
     * ���� null ���� ����� �� �������� ��� ����������
     */
    private Class<?> decoratableClass(Field field) {
         
        Class<?> clazz = field.getType();
         
        // � �������� ������ ���� �����������, ����������� WebElement
        try {
            clazz.getConstructor(WebElement.class);
        } catch (Exception e) {
            return null;
        } 
         
        return clazz;
    }
     
    /**
     * �������� ��������.
     * ������� WebElement � �������� ��� � ��������� ����� 
     */
    protected <T> T createElement(ClassLoader loader, 
                                ElementLocator locator, Class<T> clazz) {
        WebElement proxy = proxyForLocator(loader, locator);
        return createInstance(clazz, proxy);
    }
 
    /**
     * ������� ��������� ������, 
     * ������� ����������� � ���������� WebElement
     */
    private <T> T createInstance(Class<T> clazz, WebElement element) {
        try {
            return (T) clazz.getConstructor(WebElement.class)
                                    .newInstance(element);
        } catch (Exception e) {
            throw new AssertionError(
                    "WebElement can't be represented as " + clazz
                    );
        } 
    }
}

package com.qiu.ssm;

import com.qiu.ssm.resource.DefaultResourceLoader;
import com.qiu.ssm.resource.ElInterpreter;
import com.qiu.ssm.resource.ResourceLoader;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResourceTest {

    @Test
    public void testYaml(){
        DefaultResourceLoader resourceLoader=new DefaultResourceLoader();
        String value = resourceLoader.getValue("server.port");
        Assert.assertEquals("79779",value);
        String cc = resourceLoader.getValue("aaa.bbb");

        Assert.assertEquals("cc",cc);
        String password = resourceLoader.getValue("jdbc.password");
        Assert.assertEquals("abc",password);
    }
    @Test
    public void testMatchEl(){
        String s="dajbsk${123456a}dahsj${dhasjkda}kd";
        String[] contentInfo = getContentInfo(s);
        ResourceLoader resourceLoader=new DefaultResourceLoader();
        String parsing = ElInterpreter.parsing(resourceLoader,s);
        Assert.assertEquals("dajbsk123dahsjabckd",parsing);
        Assert.assertEquals(Arrays.toString(new String[]{"${123456a}","${dhasjkda}"}),Arrays.toString(contentInfo));
    }
    public static String[] getContentInfo(String content) {
        Pattern regex = Pattern.compile("\\$\\{([^}]*)}");
        Matcher matcher = regex.matcher(content);
        ArrayList<String> sql = new ArrayList<>();
        while(matcher.find()) {
            sql.add(matcher.group()/*matcher.group(1)*/);
        }
        return sql.toArray(new String[0]);
    }

}

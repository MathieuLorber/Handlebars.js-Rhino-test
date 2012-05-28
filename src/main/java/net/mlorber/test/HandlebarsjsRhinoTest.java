package net.mlorber.test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class HandlebarsjsRhinoTest {

	public static void main(String[] args) throws IOException {
		String handlebar = getFileContent("handlebars-1.0.0.beta.6.js");
		String renderTemplate = getFileContent("renderTemplate.js");
		String template = getFileContent("template.html");
		
		Context context = Context.enter();
		Scriptable scope = context.initStandardObjects();
		
		HandlebarContext handlebarContext = new HandlebarContext();
		handlebarContext.setTitle("My New Post");
		handlebarContext.setBody("This is my first post!");
		scope.put("context", scope, handlebarContext);
		scope.put("template", scope, template);

		context.evaluateString(scope, handlebar, "somefile", 1, null);
		Object result = context.evaluateString(scope, renderTemplate, "somefile", 1, null);

		System.out.println(Context.toString(result));

		Context.exit();
	}

	private static String getFileContent(String fileName) {
		try {
			return Files.toString(new File(HandlebarsjsRhinoTest.class.getClassLoader().getResource(fileName).toURI()), Charsets.UTF_8);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

}

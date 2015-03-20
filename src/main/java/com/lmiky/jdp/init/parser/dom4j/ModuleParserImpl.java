package com.lmiky.jdp.init.parser.dom4j;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.lmiky.jdp.exception.ParseException;
import com.lmiky.jdp.init.parser.ModuleParser;
import com.lmiky.jdp.module.pojo.Function;
import com.lmiky.jdp.module.pojo.Module;
import com.lmiky.jdp.module.pojo.ModuleGroup;
import com.lmiky.jdp.util.Environment;

/**
 * 模块解析实现类
 * @author lmiky
 * @date 2013-10-13
 */
public class ModuleParserImpl implements ModuleParser {
	private String moduleConfigPath;

	/*
	 * (non-Javadoc)
	 * @see com.lmiky.jdp.init.parser.ModuleParser#parse()
	 */
	@SuppressWarnings("rawtypes")
	public List<ModuleGroup> parse() throws ParseException {
		List<ModuleGroup> moduleGroups = new ArrayList<ModuleGroup>();
		SAXReader reader = new SAXReader();
		Document document;
		try {
			document = reader.read(Environment.getClassPath() + moduleConfigPath);
			Element root = document.getRootElement();
			// 模块组菜单
			for (Iterator groups = root.elementIterator("moduleGroup"); groups.hasNext();) {
				Element moduleGroupElement = (Element) groups.next();
				ModuleGroup moduleGroup = new ModuleGroup();
				moduleGroup.setName(moduleGroupElement.attributeValue("name"));
				moduleGroup.setPath(moduleGroupElement.attributeValue("path"));
				Set<Module> modules = new HashSet<Module>();
				moduleGroup.setModules(modules);
				//模块
				for(Iterator groupModules = moduleGroupElement.elementIterator("module"); groupModules.hasNext(); ) {
					Element moduleElement = (Element) groupModules.next();
					Module module = new Module();
					module.setName(moduleElement.attributeValue("name"));
					module.setPath(moduleElement.attributeValue("path"));
					module.setGroup(moduleGroup);
					Set<Function> functions = new HashSet<Function>();
					module.setFunctions(functions);
					//方法
					for(Iterator moduleFunctions = moduleElement.elementIterator("function"); moduleFunctions.hasNext(); ) {
						Element functionElement = (Element) moduleFunctions.next();
						Function function = new Function();
						function.setName(functionElement.attributeValue("name"));
						function.setAuthorityCode(functionElement.attributeValue("authorityCode"));
						function.setSort(Integer.parseInt(functionElement.attributeValue("sort")));
						function.setModule(module);
						functions.add(function);
						System.out.println(function.getName() + "-" + function.getAuthorityCode());
					}
					modules.add(module);
				}
				moduleGroups.add(moduleGroup);
			}
		} catch (DocumentException e) {
			throw new ParseException(e.getMessage());
		}
		return moduleGroups;
	}

	/**
	 * @return the moduleConfigPath
	 */
	public String getModuleConfigPath() {
		return moduleConfigPath;
	}

	/**
	 * @param moduleConfigPath the moduleConfigPath to set
	 */
	public void setModuleConfigPath(String moduleConfigPath) {
		this.moduleConfigPath = moduleConfigPath;
	}

}

 /*
 * Copyright 2012 trewys GmbH
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package de.trewys.data.access;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SQLStatementReader {

	private static SQLStatementReader instance = new SQLStatementReader();

	public static SQLStatementReader getInstance() {
		return instance;
	}

	private String sqlLocation = "/de/trewys/prato/data/access/sql/";
	
	
	public String getSqlLocation() {
		return sqlLocation;
	}

	public void setSqlLocation(String sqlLocation) {
		this.sqlLocation = sqlLocation;
	}

	public String getSQL(String fileName, String name) {
		
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setValidating(false);
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(this.getClass().getResourceAsStream(
					sqlLocation + fileName + ".sql.xml"));
			
			Node rootNode = document.getFirstChild();
			
			NodeList children = rootNode.getChildNodes();

			for (int i = 0; i < children.getLength(); i++) {
				Node node = children.item(i);
			    if (node.getNodeType() == Node.ELEMENT_NODE) {
					String nodeName = node.getAttributes().getNamedItem("name").getTextContent();
					if (name.equals(nodeName)) {
						return node.getTextContent();
					}

				}
			} 
			throw new RuntimeException("SQL '" + name + "' in Datei '" + fileName + ".sql.xml' nicht gefunden");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public String getSQLFile(String fileName) {
		InputStream sqlInput = this.getClass().getResourceAsStream(fileName);
		if (sqlInput != null) {
			try {
				StringWriter writer = new StringWriter();
				char[] buffer = new char[1024];
				try {
					Reader reader = new BufferedReader(new InputStreamReader(sqlInput, "UTF-8"));
					int n;
					while ((n = reader.read(buffer)) != -1) {
						writer.write(buffer, 0, n);
					}
				} finally {
					sqlInput.close();
				}
				
				return replaceInstances(writer.toString());
//				return writer.toString();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		return null;
	}
	
	private String replaceInstances(String origin) {
		Map<String, String> keys = DBInstances.getInstance().getKeyMap();
		
		if (!keys.isEmpty()) {
			StringBuffer sb = new StringBuffer();
			
			for (String key : keys.keySet()) {
				String value = keys.get(key);
				Pattern p = Pattern.compile(key);
				Matcher m = p.matcher(origin);
				
				while(m.find()) {
					m.appendReplacement(sb, value);
				}
				
				m.appendTail(sb);
			}
			
			return sb.toString();
		} else {
			return origin;
		}
		
	}
}
